use anyhow::Result;
use std::path::{Path, PathBuf};
use tracing::{info, warn};

use crate::storage::db::Database;
use crate::storage::queue::{self, QueueItem};
use super::engine::WhisperEngine;

pub struct TranscriptionPipeline {
    batch_engine: WhisperEngine,
    language: String,
    enable_vad: bool,
    queue_dir: PathBuf,
}

impl TranscriptionPipeline {
    pub fn new(
        batch_model_path: &Path,
        language: String,
        enable_vad: bool,
        queue_dir: PathBuf,
    ) -> Result<Self> {
        let batch_engine = WhisperEngine::new(batch_model_path, false)?; // CPU-only
        Ok(Self {
            batch_engine,
            language,
            enable_vad,
            queue_dir,
        })
    }

    /// Transcribe a WAV file and store all segments in the database.
    /// Returns the number of segments stored.
    pub fn process_recording(
        &self,
        meeting_id: &str,
        wav_path: &Path,
        db: &Database,
    ) -> Result<usize> {
        info!("Starting batch transcription for meeting {}", meeting_id);

        let audio = WhisperEngine::load_wav(wav_path)?;
        let segments = self.batch_engine.transcribe(&audio, &self.language, self.enable_vad)?;

        let count = segments.len();
        for (start_ms, end_ms, text, confidence) in &segments {
            db.insert_segment(meeting_id, *start_ms, *end_ms, text, *confidence, true)?;
        }

        info!("Stored {} segments for meeting {}", count, meeting_id);
        Ok(count)
    }

    /// Process all pending items in the queue directory, one at a time.
    /// Spec §13.4: sequential processing only — no parallel jobs.
    pub fn drain_queue(&self, db: &Database) -> Result<usize> {
        let items = queue::pending_items(&self.queue_dir);
        if items.is_empty() {
            return Ok(0);
        }

        info!("Processing {} queued transcription job(s)", items.len());
        let mut processed = 0;

        for item_path in &items {
            match QueueItem::load(item_path) {
                Err(e) => {
                    warn!("Skipping unreadable queue item {:?}: {}", item_path, e);
                    let _ = QueueItem::move_to_failed(item_path, &e.to_string());
                }
                Ok(item) => {
                    let wav = PathBuf::from(&item.wav_path);
                    match self.process_recording(&item.meeting_id, &wav, db) {
                        Ok(n) => {
                            info!(
                                meeting_id = %item.meeting_id,
                                segments = n,
                                "Transcription complete"
                            );
                            let _ = std::fs::remove_file(item_path);
                            processed += 1;
                        }
                        Err(e) => {
                            warn!(
                                meeting_id = %item.meeting_id,
                                error = %e,
                                "Transcription failed"
                            );
                            let _ = QueueItem::move_to_failed(item_path, &e.to_string());
                        }
                    }
                }
            }
        }

        Ok(processed)
    }
}
