-- migrations/001_initial.sql
-- Meeting Assistant — Initial Schema
-- Executed on first run by src/storage/db.rs

PRAGMA journal_mode = WAL;         -- Required: concurrent reads without write blocking
PRAGMA foreign_keys = ON;
PRAGMA synchronous = NORMAL;       -- WAL mode makes NORMAL safe

-- ─── Core Tables ──────────────────────────────────────────────────────────────

CREATE TABLE IF NOT EXISTS meetings (
    id              TEXT PRIMARY KEY,           -- UUID v4
    title           TEXT NOT NULL DEFAULT '',
    started_at      TEXT NOT NULL,              -- ISO 8601: 2026-04-30T09:00:00Z
    ended_at        TEXT,
    duration_secs   INTEGER,
    audio_path      TEXT,                       -- Relative path to WAV file
    status          TEXT NOT NULL DEFAULT 'recording',
                    -- recording | processing | complete | error | abandoned
    tags            TEXT DEFAULT '',            -- Comma-separated
    project         TEXT DEFAULT '',
    sensitivity     TEXT NOT NULL DEFAULT 'normal',  -- normal | confidential | restricted
    participant_count INTEGER DEFAULT 1,
    created_at      TEXT NOT NULL DEFAULT (datetime('now'))
);

CREATE TABLE IF NOT EXISTS segments (
    id              INTEGER PRIMARY KEY AUTOINCREMENT,
    meeting_id      TEXT NOT NULL REFERENCES meetings(id) ON DELETE CASCADE,
    start_ms        INTEGER NOT NULL,           -- Milliseconds from meeting start
    end_ms          INTEGER NOT NULL,
    speaker         TEXT DEFAULT 'unknown',     -- 'me' | 'speaker_1' | 'unknown'
    text            TEXT NOT NULL,
    confidence      REAL DEFAULT 1.0,           -- Whisper confidence score (0.0-1.0)
    is_final        INTEGER DEFAULT 0,          -- 0 = live chunk; 1 = post-call batch
    created_at      TEXT NOT NULL DEFAULT (datetime('now'))
);

CREATE TABLE IF NOT EXISTS summaries (
    id              INTEGER PRIMARY KEY AUTOINCREMENT,
    meeting_id      TEXT NOT NULL REFERENCES meetings(id) ON DELETE CASCADE,
    summary_type    TEXT NOT NULL,              -- brief | standard | detailed
    content         TEXT NOT NULL,
    model_used      TEXT,
    prompt_tokens   INTEGER,
    completion_tokens INTEGER,
    created_at      TEXT NOT NULL DEFAULT (datetime('now'))
);

CREATE TABLE IF NOT EXISTS actions (
    id              INTEGER PRIMARY KEY AUTOINCREMENT,
    meeting_id      TEXT NOT NULL REFERENCES meetings(id) ON DELETE CASCADE,
    description     TEXT NOT NULL,
    owner           TEXT DEFAULT '',
    due_date        TEXT,                       -- ISO 8601 date string
    status          TEXT NOT NULL DEFAULT 'open',  -- open | done | cancelled
    source_segment_id INTEGER REFERENCES segments(id),
    created_at      TEXT NOT NULL DEFAULT (datetime('now'))
);

CREATE TABLE IF NOT EXISTS decisions (
    id              INTEGER PRIMARY KEY AUTOINCREMENT,
    meeting_id      TEXT NOT NULL REFERENCES meetings(id) ON DELETE CASCADE,
    description     TEXT NOT NULL,
    context         TEXT DEFAULT '',
    source_segment_id INTEGER REFERENCES segments(id),
    created_at      TEXT NOT NULL DEFAULT (datetime('now'))
);

CREATE TABLE IF NOT EXISTS consent_log (
    id              INTEGER PRIMARY KEY AUTOINCREMENT,
    meeting_id      TEXT NOT NULL REFERENCES meetings(id) ON DELETE CASCADE,
    consent_given   INTEGER NOT NULL,           -- 1 = yes, 0 = no
    consent_method  TEXT NOT NULL,              -- button | verbal | chat
    logged_at       TEXT NOT NULL DEFAULT (datetime('now'))
);

CREATE TABLE IF NOT EXISTS config (
    key             TEXT PRIMARY KEY,
    value           TEXT NOT NULL,
    updated_at      TEXT NOT NULL DEFAULT (datetime('now'))
);

-- ─── Full-Text Search ─────────────────────────────────────────────────────────

CREATE VIRTUAL TABLE IF NOT EXISTS segments_fts USING fts5(
    text,
    content='segments',
    content_rowid='id'
);

-- Keep FTS in sync with segments table
CREATE TRIGGER IF NOT EXISTS segments_ai AFTER INSERT ON segments BEGIN
    INSERT INTO segments_fts(rowid, text) VALUES (new.id, new.text);
END;

CREATE TRIGGER IF NOT EXISTS segments_au AFTER UPDATE OF text ON segments BEGIN
    INSERT INTO segments_fts(segments_fts, rowid, text) VALUES ('delete', old.id, old.text);
    INSERT INTO segments_fts(rowid, text) VALUES (new.id, new.text);
END;

CREATE TRIGGER IF NOT EXISTS segments_ad AFTER DELETE ON segments BEGIN
    INSERT INTO segments_fts(segments_fts, rowid, text) VALUES ('delete', old.id, old.text);
END;

-- ─── Indexes ──────────────────────────────────────────────────────────────────

CREATE INDEX IF NOT EXISTS idx_segments_meeting_id ON segments(meeting_id);
CREATE INDEX IF NOT EXISTS idx_meetings_started_at ON meetings(started_at);
CREATE INDEX IF NOT EXISTS idx_meetings_status ON meetings(status);
CREATE INDEX IF NOT EXISTS idx_actions_meeting_id ON actions(meeting_id);
CREATE INDEX IF NOT EXISTS idx_actions_status ON actions(status);
