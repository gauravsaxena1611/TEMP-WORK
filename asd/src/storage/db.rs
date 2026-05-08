use anyhow::{Context, Result};
use rusqlite::{Connection, params};
use std::path::Path;

pub struct Database {
    conn: Connection,
}

impl Database {
    /// Open (or create) the SQLite database and run migrations.
    pub fn open(db_path: &Path) -> Result<Self> {
        if let Some(parent) = db_path.parent() {
            std::fs::create_dir_all(parent)
                .with_context(|| format!("Cannot create database directory: {:?}", parent))?;
        }

        let conn = Connection::open(db_path)
            .with_context(|| format!("Cannot open database at: {:?}", db_path))?;

        conn.execute_batch("
            PRAGMA journal_mode = WAL;
            PRAGMA foreign_keys = ON;
            PRAGMA synchronous = NORMAL;
            PRAGMA cache_size = -32000;
            PRAGMA temp_store = MEMORY;
        ").context("Failed to set database pragmas")?;

        let db = Self { conn };
        db.run_migrations()?;

        Ok(db)
    }

    fn run_migrations(&self) -> Result<()> {
        self.conn.execute_batch("
            CREATE TABLE IF NOT EXISTS schema_migrations (
                version INTEGER PRIMARY KEY,
                applied_at TEXT NOT NULL DEFAULT (datetime('now'))
            );
        ").context("Failed to create migrations table")?;

        let version_applied: bool = self.conn
            .query_row(
                "SELECT COUNT(*) FROM schema_migrations WHERE version = 1",
                [],
                |row| row.get::<_, i64>(0),
            )
            .unwrap_or(0) > 0;

        if !version_applied {
            let schema = include_str!("../../migrations/001_initial.sql");
            self.conn.execute_batch(schema)
                .context("Failed to run migration 001_initial.sql")?;

            self.conn.execute(
                "INSERT INTO schema_migrations (version) VALUES (1)",
                [],
            ).context("Failed to record migration 001")?;

            tracing::info!("Database migration 001 applied successfully");
        }

        Ok(())
    }

    pub fn connection(&self) -> &Connection {
        &self.conn
    }

    /// Insert a new meeting record.
    pub fn insert_meeting(&self, id: &str, started_at: &str) -> Result<()> {
        self.conn.execute(
            "INSERT INTO meetings (id, started_at) VALUES (?1, ?2)",
            params![id, started_at],
        ).context("Failed to insert meeting")?;
        Ok(())
    }

    /// Update meeting status and end time.
    pub fn complete_meeting(&self, id: &str, ended_at: &str, duration_secs: i64) -> Result<()> {
        self.conn.execute(
            "UPDATE meetings SET status = 'complete', ended_at = ?1, duration_secs = ?2 WHERE id = ?3",
            params![ended_at, duration_secs, id],
        ).context("Failed to complete meeting")?;
        Ok(())
    }

    /// Insert a transcription segment. Returns the row id.
    pub fn insert_segment(
        &self,
        meeting_id: &str,
        start_ms: i64,
        end_ms: i64,
        text: &str,
        confidence: f64,
        is_final: bool,
    ) -> Result<i64> {
        self.conn.execute(
            "INSERT INTO segments (meeting_id, start_ms, end_ms, text, confidence, is_final)
             VALUES (?1, ?2, ?3, ?4, ?5, ?6)",
            params![meeting_id, start_ms, end_ms, text, confidence, is_final as i64],
        ).context("Failed to insert segment")?;
        Ok(self.conn.last_insert_rowid())
    }

    /// Retrieve all segments for a meeting, ordered by start time.
    pub fn get_segments(&self, meeting_id: &str) -> Result<Vec<(i64, i64, String)>> {
        let mut stmt = self.conn.prepare(
            "SELECT start_ms, end_ms, text FROM segments WHERE meeting_id = ?1 ORDER BY start_ms"
        ).context("Failed to prepare segments query")?;

        let rows = stmt.query_map(params![meeting_id], |row| {
            Ok((row.get::<_, i64>(0)?, row.get::<_, i64>(1)?, row.get::<_, String>(2)?))
        }).context("Failed to query segments")?;

        Ok(rows.filter_map(|r| r.ok()).collect())
    }

    pub fn insert_summary(&self, meeting_id: &str, summary_type: &str, content: &str) -> Result<()> {
        self.conn.execute(
            "INSERT INTO summaries (meeting_id, summary_type, content) VALUES (?1, ?2, ?3)",
            params![meeting_id, summary_type, content],
        ).context("Failed to insert summary")?;
        Ok(())
    }

    pub fn insert_action(
        &self,
        meeting_id: &str,
        description: &str,
        owner: Option<&str>,
        due_date: Option<&str>,
    ) -> Result<()> {
        self.conn.execute(
            "INSERT INTO actions (meeting_id, description, owner, due_date) VALUES (?1, ?2, ?3, ?4)",
            params![meeting_id, description, owner, due_date],
        ).context("Failed to insert action")?;
        Ok(())
    }

    pub fn insert_decision(&self, meeting_id: &str, description: &str, context: Option<&str>) -> Result<()> {
        self.conn.execute(
            "INSERT INTO decisions (meeting_id, description, context) VALUES (?1, ?2, ?3)",
            params![meeting_id, description, context],
        ).context("Failed to insert decision")?;
        Ok(())
    }

    /// Return the most recent completed meeting ID, if any.
    pub fn last_completed_meeting(&self) -> Result<Option<String>> {
        let mut stmt = self.conn.prepare(
            "SELECT id FROM meetings WHERE status = 'complete' ORDER BY ended_at DESC LIMIT 1"
        ).context("Failed to prepare last meeting query")?;

        let mut rows = stmt.query([]).context("Failed to query last meeting")?;
        Ok(rows.next()?.map(|r| r.get::<_, String>(0)).transpose()?)
    }

    /// Return all meetings started today with their brief summaries.
    pub fn daily_digest(&self) -> Result<Vec<(String, String, String, Option<i64>, Option<String>)>> {
        let mut stmt = self.conn.prepare(
            "SELECT m.id, m.title, m.started_at, m.duration_secs, s.content
             FROM meetings m
             LEFT JOIN summaries s ON s.meeting_id = m.id AND s.summary_type = 'brief'
             WHERE date(m.started_at) = date('now')
             ORDER BY m.started_at ASC"
        ).context("Failed to prepare daily digest query")?;

        let rows = stmt.query_map([], |row| {
            Ok((
                row.get::<_, String>(0)?,
                row.get::<_, String>(1)?,
                row.get::<_, String>(2)?,
                row.get::<_, Option<i64>>(3)?,
                row.get::<_, Option<String>>(4)?,
            ))
        }).context("Failed to query daily digest")?;

        Ok(rows.filter_map(|r| r.ok()).collect())
    }

    /// FTS5 keyword search across all segment text.
    pub fn keyword_search(&self, query: &str, limit: usize) -> Result<Vec<(String, String, f64)>> {
        let fts_query = format!("\"{}\"", query.replace('"', ""));
        let mut stmt = self.conn.prepare(
            "SELECT m.id, s.text, rank
             FROM segments_fts
             JOIN segments s ON segments_fts.rowid = s.id
             JOIN meetings m ON s.meeting_id = m.id
             WHERE segments_fts MATCH ?1
             ORDER BY rank
             LIMIT ?2"
        ).context("Failed to prepare FTS query")?;

        let rows = stmt.query_map(params![fts_query, limit as i64], |row| {
            Ok((
                row.get::<_, String>(0)?,
                row.get::<_, String>(1)?,
                row.get::<_, f64>(2).unwrap_or(0.0),
            ))
        }).context("Failed to execute FTS search")?;

        Ok(rows.filter_map(|r| r.ok()).collect())
    }
}
