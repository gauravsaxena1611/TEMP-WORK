use anyhow::{Context, Result};
use arrow_array::{FixedSizeListArray, Float32Array, Int64Array, RecordBatch, RecordBatchIterator, StringArray};
use arrow_schema::{DataType, Field, Schema};
use futures::TryStreamExt;
use lancedb::query::{ExecutableQuery, QueryBase};
use std::path::PathBuf;
use std::sync::Arc;

pub struct VectorStore {
    db_path: PathBuf,
}

impl VectorStore {
    pub fn new(db_path: PathBuf) -> Self {
        Self { db_path }
    }

    async fn connect(&self) -> Result<lancedb::Connection> {
        std::fs::create_dir_all(&self.db_path)?;
        lancedb::connect(self.db_path.to_str().unwrap())
            .execute()
            .await
            .context("Failed to connect to LanceDB")
    }

    fn schema() -> Arc<Schema> {
        Arc::new(Schema::new(vec![
            Field::new("meeting_id", DataType::Utf8, false),
            Field::new("segment_id", DataType::Int64, false),
            Field::new("text", DataType::Utf8, false),
            Field::new(
                "vector",
                DataType::FixedSizeList(
                    Arc::new(Field::new("item", DataType::Float32, true)),
                    384,
                ),
                false,
            ),
        ]))
    }

    fn make_batch(
        schema: &Arc<Schema>,
        meeting_id: &str,
        segment_id: i64,
        text: &str,
        vector: &[f32],
    ) -> Result<RecordBatch> {
        let values = Float32Array::from(vector.to_vec());
        let vec_field = Arc::new(Field::new("item", DataType::Float32, true));
        let vectors =
            FixedSizeListArray::try_new(vec_field, 384, Arc::new(values), None)
                .context("Failed to create vector array")?;

        RecordBatch::try_new(
            schema.clone(),
            vec![
                Arc::new(StringArray::from(vec![meeting_id])),
                Arc::new(Int64Array::from(vec![segment_id])),
                Arc::new(StringArray::from(vec![text])),
                Arc::new(vectors),
            ],
        )
        .context("Failed to create record batch")
    }

    /// Store an embedding. Creates the LanceDB table on first call.
    pub async fn insert(
        &self,
        meeting_id: &str,
        segment_id: i64,
        text: &str,
        vector: &[f32],
    ) -> Result<()> {
        let conn = self.connect().await?;
        let schema = Self::schema();
        let batch = Self::make_batch(&schema, meeting_id, segment_id, text, vector)?;

        match conn.open_table("segments").execute().await {
            Ok(table) => {
                table
                    .add(RecordBatchIterator::new(vec![Ok(batch)], schema))
                    .execute()
                    .await
                    .context("Failed to add row to LanceDB")?;
            }
            Err(_) => {
                conn.create_table(
                    "segments",
                    RecordBatchIterator::new(vec![Ok(batch)], schema),
                )
                .execute()
                .await
                .context("Failed to create LanceDB table")?;
            }
        }
        Ok(())
    }

    /// Return top-k semantically similar segments as (meeting_id, text, distance).
    pub async fn search(
        &self,
        query: &[f32],
        limit: usize,
    ) -> Result<Vec<(String, String, f64)>> {
        let conn = self.connect().await?;
        let Ok(table) = conn.open_table("segments").execute().await else {
            return Ok(vec![]);
        };

        let stream = table
            .query()
            .nearest_to(query)
            .context("Failed to build vector query")?
            .limit(limit)
            .execute()
            .await
            .context("Failed to execute vector search")?;

        let batches: Vec<RecordBatch> =
            stream.try_collect().await.context("Failed to collect search results")?;

        let mut results = Vec::new();
        for batch in &batches {
            let Some(meeting_col) = batch.column_by_name("meeting_id") else { continue };
            let Some(text_col) = batch.column_by_name("text") else { continue };

            let meeting_ids = meeting_col.as_any().downcast_ref::<StringArray>();
            let texts = text_col.as_any().downcast_ref::<StringArray>();

            if let (Some(mids), Some(txts)) = (meeting_ids, texts) {
                for i in 0..batch.num_rows() {
                    results.push((mids.value(i).to_string(), txts.value(i).to_string(), 0.0));
                }
            }
        }

        Ok(results)
    }
}
