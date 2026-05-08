use anyhow::Result;
use crate::ai::embedding::EmbeddingModel;
use crate::storage::vectors::VectorStore;

pub struct SemanticResult {
    pub meeting_id: String,
    pub text: String,
    pub distance: f64,
}

pub struct SemanticSearch {
    embedding: EmbeddingModel,
    vectors: VectorStore,
}

impl SemanticSearch {
    pub fn new(embedding: EmbeddingModel, vectors: VectorStore) -> Self {
        Self { embedding, vectors }
    }

    pub async fn search(&mut self, query: &str, limit: usize) -> Result<Vec<SemanticResult>> {
        let vec = self.embedding.embed(query)?;
        let rows = self.vectors.search(&vec, limit).await?;
        Ok(rows
            .into_iter()
            .map(|(meeting_id, text, distance)| SemanticResult { meeting_id, text, distance })
            .collect())
    }
}
