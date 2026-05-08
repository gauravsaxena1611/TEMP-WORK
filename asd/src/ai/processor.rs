use anyhow::Result;
use serde::Deserialize;
use std::path::Path;
use tracing::{error, info, warn};

use crate::ai::embedding::EmbeddingModel;
use crate::ai::llm::LlmClient;
use crate::ai::prompts;
use crate::config::AppConfig;
use crate::storage::db::Database;
use crate::storage::vectors::VectorStore;

#[derive(Debug)]
pub enum ProcessingResult {
    Success { segments: usize },
    TranscribeOnly { segments: usize },
}

pub struct AiProcessor {
    llm: Option<LlmClient>,
    embedding: Option<EmbeddingModel>,
    vectors: VectorStore,
}

impl AiProcessor {
    pub fn new(config: &AppConfig, base_dir: &Path, data_dir: &Path) -> Self {
        let llm = if config.llm.enabled {
            match LlmClient::new(
                &config.llm.endpoint,
                config.llm.temperature,
                config.llm.max_tokens,
                config.llm.timeout_secs,
            ) {
                Ok(c) => Some(c),
                Err(e) => {
                    warn!("LLM client init failed: {}", e);
                    None
                }
            }
        } else {
            None
        };

        let model_path = base_dir.join(&config.embedding.model_path);
        let tokenizer_path = model_path
            .parent()
            .unwrap_or(Path::new("."))
            .join("tokenizer.json");

        let embedding = match EmbeddingModel::new(&model_path, &tokenizer_path) {
            Ok(m) => Some(m),
            Err(e) => {
                warn!("Embedding model unavailable — semantic search disabled. ({})", e);
                None
            }
        };

        let vectors = VectorStore::new(data_dir.join("db").join("vectors"));

        Self { llm, embedding, vectors }
    }

    /// Run the full post-call AI pipeline for a meeting. Gracefully degrades when
    /// the LLM or embedding model is unavailable (spec §13.2).
    pub async fn process_meeting(&mut self, meeting_id: &str, db: &Database) -> ProcessingResult {
        let segments = match db.get_segments(meeting_id) {
            Ok(s) => s,
            Err(e) => {
                error!("Failed to load segments for meeting {}: {}", meeting_id, e);
                return ProcessingResult::TranscribeOnly { segments: 0 };
            }
        };

        if segments.is_empty() {
            warn!("No segments found for meeting {}", meeting_id);
            return ProcessingResult::TranscribeOnly { segments: 0 };
        }

        // Generate and store embeddings (best-effort — never blocks on failure)
        if let Some(model) = &mut self.embedding {
            for (start_ms, _, text) in &segments {
                match model.embed(text) {
                    Ok(vec) => {
                        if let Err(e) = self.vectors.insert(meeting_id, *start_ms, text, &vec).await {
                            error!("Vector insert failed: {}", e);
                        }
                    }
                    Err(e) => warn!("Embedding failed: {}", e),
                }
            }
            info!(count = segments.len(), "Embeddings stored");
        }

        let Some(llm) = &self.llm else {
            return ProcessingResult::TranscribeOnly { segments: segments.len() };
        };

        if !llm.health_check().await {
            warn!("LLM server unreachable — transcribe-only mode");
            return ProcessingResult::TranscribeOnly { segments: segments.len() };
        }

        let transcript: String = segments
            .iter()
            .map(|(_, _, t)| t.as_str())
            .collect::<Vec<_>>()
            .join(" ");

        // Summary
        match llm.complete(&prompts::summary_prompt(&transcript)).await {
            Ok(content) => {
                if let Err(e) = db.insert_summary(meeting_id, "standard", &content) {
                    error!("Failed to store summary: {}", e);
                }
            }
            Err(e) => warn!("Summary generation failed: {}", e),
        }

        // Action items
        match llm.complete(&prompts::actions_extraction_prompt(&transcript)).await {
            Ok(json) => {
                if let Err(e) = Self::store_actions(meeting_id, &json, db) {
                    error!("Failed to store actions: {}", e);
                }
            }
            Err(e) => warn!("Actions extraction failed: {}", e),
        }

        // Decisions
        match llm.complete(&prompts::decisions_extraction_prompt(&transcript)).await {
            Ok(json) => {
                if let Err(e) = Self::store_decisions(meeting_id, &json, db) {
                    error!("Failed to store decisions: {}", e);
                }
            }
            Err(e) => warn!("Decisions extraction failed: {}", e),
        }

        info!(meeting_id = %meeting_id, "AI processing complete");
        ProcessingResult::Success { segments: segments.len() }
    }

    fn store_actions(meeting_id: &str, json: &str, db: &Database) -> Result<()> {
        #[derive(Deserialize)]
        struct Action {
            description: String,
            owner: Option<String>,
            due_date: Option<String>,
        }
        let actions: Vec<Action> = serde_json::from_str(json).unwrap_or_default();
        for a in actions {
            db.insert_action(meeting_id, &a.description, a.owner.as_deref(), a.due_date.as_deref())?;
        }
        Ok(())
    }

    fn store_decisions(meeting_id: &str, json: &str, db: &Database) -> Result<()> {
        #[derive(Deserialize)]
        struct Decision {
            description: String,
            context: Option<String>,
        }
        let decisions: Vec<Decision> = serde_json::from_str(json).unwrap_or_default();
        for d in decisions {
            db.insert_decision(meeting_id, &d.description, d.context.as_deref())?;
        }
        Ok(())
    }
}
