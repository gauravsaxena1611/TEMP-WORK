use anyhow::Result;
use crate::storage::db::Database;

pub struct KeywordResult {
    pub meeting_id: String,
    pub text: String,
    pub score: f64,
}

pub struct KeywordSearch<'a> {
    db: &'a Database,
}

impl<'a> KeywordSearch<'a> {
    pub fn new(db: &'a Database) -> Self {
        Self { db }
    }

    pub fn search(&self, query: &str, limit: usize) -> Result<Vec<KeywordResult>> {
        let rows = self.db.keyword_search(query, limit)?;
        Ok(rows
            .into_iter()
            .map(|(meeting_id, text, score)| KeywordResult { meeting_id, text, score })
            .collect())
    }
}
