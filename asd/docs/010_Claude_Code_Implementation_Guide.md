# 010 Claude Code Implementation Guide
## VDI Meeting Assistant — Complete Build Instructions for Claude Code

**Document ID:** 010  
**Parent Document:** [000 Pre-Build Audit, Section 8 — Build Readiness Checklist]  
**Version:** 1.0  
**Created:** 2026-04-30  
**Status:** Active — Hand to Claude Code at the start of every session  
**Template:** TMPL-004B: Agentic Workflow Plan  
**Verification Mode:** MEDIUM-HIGH — Technical implementation document  

---

## AI AGENT SUMMARY

> **For Claude Code:** This is your complete, authoritative build guide for the VDI Meeting Assistant. Read this entire document before writing a single line of code. Every architectural decision has already been researched and verified (see parent document 000). Your job is to implement exactly what is specified here — do not substitute libraries, do not add cloud dependencies, do not deviate from the folder structure. The environment is a restricted, offline VMware VDI running Windows 11 Enterprise. There is no internet access inside the VDI. All binaries and models must be pre-downloaded and bundled.

---

## TABLE OF CONTENTS

1. [Agent Operating Rules](#1-agent-operating-rules)
2. [Environment & Hardware Context](#2-environment--hardware-context)
3. [Project Directory Structure](#3-project-directory-structure)
4. [Complete Cargo.toml](#4-complete-cargotoml)
5. [Configuration Specification](#5-configuration-specification)
6. [Database Schema — Complete](#6-database-schema--complete)
7. [Session 1 — Project Scaffold & Database](#7-session-1--project-scaffold--database)
8. [Session 2 — Audio Capture Engine](#8-session-2--audio-capture-engine)
9. [Session 3 — Transcription Pipeline](#9-session-3--transcription-pipeline)
10. [Session 4 — AI Pipeline (LLM + Embeddings)](#10-session-4--ai-pipeline-llm--embeddings)
11. [Session 5 — Search & Query Layer](#11-session-5--search--query-layer)
12. [Session 6 — System Tray UI & Polish](#12-session-6--system-tray-ui--polish)
13. [Critical Implementation Patterns](#13-critical-implementation-patterns)
14. [Known Failure Modes & Mitigations](#14-known-failure-modes--mitigations)
15. [Build & Packaging Instructions](#15-build--packaging-instructions)
16. [Testing Checklist](#16-testing-checklist)
17. [Sources & References](#17-sources--references)

---

## 1. AGENT OPERATING RULES

**[TYPE: AGENT-INSTRUCTIONS]**

These rules govern how Claude Code must behave across all sessions.

### 1.1 Non-Negotiable Constraints

| Rule | Reason |
|------|--------|
| **No internet dependencies at runtime** | VDI has no outbound network access |
| **No system-level drivers** | No admin rights; user-mode only |
| **No global installs** | Deploy to `%LOCALAPPDATA%\MeetingAssistant\` only |
| **Rust only for the main binary** | Python only allowed as isolated sidecar if strictly necessary |
| **All models bundled locally** | Whisper models, ONNX embedding model — must be in `./models/` |
| **Single binary target** | `cargo build --release` produces one `.exe` |
| **Process priority: BELOW_NORMAL** | VDI CPU threshold is <10% steady-state; never spike the VM |
| **SQLite WAL mode** | Required for concurrent read-write without locking |

### 1.2 Session Start Protocol

At the start of every Claude Code session, Claude Code must:

1. Read this document (010) from top to bottom
2. Read `PROGRESS.md` in the project root to know what has been completed
3. Confirm the session number it is implementing
4. Ask the user to confirm before beginning any destructive operation

### 1.3 Session End Protocol

At the end of every Claude Code session, Claude Code must:

1. Update `PROGRESS.md` with what was completed and what remains
2. Run `cargo build --release` and report any compilation errors
3. Run the applicable tests for that session
4. List any open issues or blockers for the next session

### 1.4 Decisions Already Made — Do Not Re-Open

| Decision | Choice | Reason |
|----------|--------|--------|
| Language | Rust | ✅ Verified — VDI performance, single binary |
| Audio API | WASAPI via `cpal` | ✅ Verified — user-mode, VDI compatible |
| STT Engine | `whisper-rs` (Whisper.cpp bindings) | ✅ Verified — CPU-only, offline |
| Live Model | `ggml-base.en` | ✅ Verified — RTF ~0.1-0.3 on CPU |
| Batch Model | `ggml-small.en` | ✅ Verified — post-call accuracy |
| VAD | Built-in Silero VAD (Whisper.cpp native) | ✅ Verified — no separate process |
| LLM runtime | `llama.cpp` server (HTTP API) | ✅ Verified — offline, user-mode |
| Default LLM | Phi-4-mini Q4_K_M | ✅ Verified — 3.5 GB RAM, best reasoning/GB |
| Embedding model | `all-MiniLM-L6-v2` (ONNX) | ✅ Verified — 80 MB, 384-dim |
| Vector DB | LanceDB (embedded) | ✅ Verified — disk-first, zero-server |
| Primary DB | SQLite + FTS5 | ✅ Verified — portable, zero-server |
| UI Phase 1 | System tray + hotkey only | ✅ Verified — minimal footprint |
| UI Phase 2 | `egui` embedded window | ✅ Verified — lightweight, Rust-native |
| Config format | TOML | Standard Rust ecosystem |
| Diarization | Deferred to Phase 2 | Complexity vs. Phase 1 deliverable |
| Encryption | Deferred to Phase 3 | SQLCipher adds build complexity |

---

## 2. ENVIRONMENT & HARDWARE CONTEXT

**[TYPE: CONTEXT]**

Claude Code must account for these constraints in every file it writes.

### 2.1 VDI Hardware Specifications

| Spec | Value | Impact on Code |
|------|-------|----------------|
| **CPU** | 28-core Xeon Gold 6348 (shared with other VMs) | Use BELOW_NORMAL thread priority; limit worker threads to 4 |
| **RAM** | 39 GB total (assume 8–12 GB available to this process) | Phi-4-mini takes ~3.5 GB; Base Whisper ~200 MB; total ~4–4.5 GB |
| **Disk** | 80 GB free, no SSD guarantee | Use LanceDB WAL; write audio to `%LOCALAPPDATA%` not network drive |
| **GPU** | None exposed in VDI | CPU-only inference; Vulkan detection should gracefully fail |
| **OS** | Windows 11 Enterprise 24H2 | Win32 API paths; use `windows-sys` crate for session change detection |
| **Audio** | VMware Virtual Audio Device (DevTap) | WASAPI loopback on this specific device |
| **Network** | No outbound internet inside VDI | All API calls must be localhost only |
| **AppLocker** | May block unknown EXEs | Deploy binary to `%LOCALAPPDATA%\MeetingAssistant\` |

### 2.2 Deployment Path

```
%LOCALAPPDATA%\MeetingAssistant\
├── meeting_assistant.exe          ← The compiled binary
├── config.toml                    ← User configuration
├── models\
│   ├── ggml-base.en.bin           ← 145 MB — pre-download
│   ├── ggml-small.en.bin          ← 480 MB — pre-download
│   └── minilm.onnx                ← 80 MB — all-MiniLM-L6-v2
├── data\
│   ├── db\
│   │   ├── meetings.db            ← SQLite database
│   │   └── vectors\               ← LanceDB files
│   ├── audio\                     ← Raw WAV recordings
│   └── queue\                     ← Processing queue
└── logs\
    └── meeting_assistant.log
```

### 2.3 llama.cpp Server Setup (Separate Process)

The LLM component runs as a **separate process** (`llama-server.exe`) that the user starts manually or that the app launches on startup. The app communicates with it via HTTP on `localhost:8080`.

```
%LOCALAPPDATA%\MeetingAssistant\llm\
├── llama-server.exe               ← Pre-built llama.cpp server
└── models\
    └── phi-4-mini-Q4_K_M.gguf    ← 3.5 GB — pre-download
```

**Startup command (user runs once, or app auto-starts):**
```
llama-server.exe -m models\phi-4-mini-Q4_K_M.gguf --port 8080 --ctx-size 4096 --threads 4
```

The app must check if the server is running before making LLM calls and degrade gracefully (capture + transcribe only) if it is not.

---

## 3. PROJECT DIRECTORY STRUCTURE

**[TYPE: SPECIFICATION]**

Create this exact structure. Every directory must exist before code is written.

```
meeting_assistant/                  ← Git repository root
├── Cargo.toml
├── Cargo.lock
├── PROGRESS.md                     ← Session progress tracker (create on Session 1)
├── README.md
├── config.toml.example             ← Committed to repo; user copies to deployment dir
├── .gitignore
├── build.rs                        ← Build script for Windows resources / linking
│
├── src/
│   ├── main.rs                     ← Entry point: init, tray, event loop
│   ├── config.rs                   ← Config loading and validation
│   ├── error.rs                    ← Unified error type (anyhow)
│   │
│   ├── capture/
│   │   ├── mod.rs                  ← Module exports
│   │   ├── audio.rs                ← cpal WASAPI loopback capture
│   │   ├── device.rs               ← Device enumeration, selection, reconnect
│   │   └── writer.rs               ← WAV file writer (hound)
│   │
│   ├── transcription/
│   │   ├── mod.rs
│   │   ├── engine.rs               ← whisper-rs wrapper, model management
│   │   └── pipeline.rs             ← Batch transcription orchestration
│   │
│   ├── ai/
│   │   ├── mod.rs
│   │   ├── llm.rs                  ← llama.cpp HTTP client (reqwest)
│   │   ├── prompts.rs              ← All prompt templates (summary, actions, decisions)
│   │   ├── embedding.rs            ← ONNX Runtime inference (ort crate)
│   │   └── processor.rs            ← Post-call AI orchestration
│   │
│   ├── storage/
│   │   ├── mod.rs
│   │   ├── db.rs                   ← SQLite connection, migrations, queries
│   │   ├── vectors.rs              ← LanceDB table management and queries
│   │   └── queue.rs                ← File-based processing queue
│   │
│   ├── search/
│   │   ├── mod.rs
│   │   ├── keyword.rs              ← SQLite FTS5 keyword search
│   │   └── semantic.rs             ← LanceDB vector similarity search
│   │
│   ├── session/
│   │   ├── mod.rs
│   │   ├── manager.rs              ← Meeting session lifecycle
│   │   └── windows.rs              ← Windows session change events (reconnect)
│   │
│   └── ui/
│       ├── mod.rs
│       ├── tray.rs                 ← System tray icon (tray-item crate)
│       └── hotkey.rs               ← Global hotkey registration
│
├── migrations/
│   └── 001_initial.sql             ← SQLite schema (used by db.rs on first run)
│
└── tests/
    ├── test_audio.rs
    ├── test_transcription.rs
    └── test_storage.rs
```

---

## 4. COMPLETE CARGO.TOML

**[TYPE: SPECIFICATION]**

Use this exact `Cargo.toml`. Do not add dependencies not listed here without explicit user approval. Pin versions to avoid VDI deployment drift.

```toml
[package]
name = "meeting_assistant"
version = "0.1.0"
edition = "2021"
description = "Local-first AI meeting assistant for restricted VDI environments"
authors = [""]

[[bin]]
name = "meeting_assistant"
path = "src/main.rs"

[dependencies]
# ─── Error handling ───────────────────────────────────────────────────────────
anyhow = "1.0"
thiserror = "1.0"

# ─── Async runtime ────────────────────────────────────────────────────────────
tokio = { version = "1.36", features = ["full"] }

# ─── Audio capture ────────────────────────────────────────────────────────────
cpal = { version = "0.15", features = ["wasapi"] }
hound = "3.5"

# ─── Speech-to-text ───────────────────────────────────────────────────────────
whisper-rs = "0.11"

# ─── HTTP client (for llama.cpp server API) ───────────────────────────────────
reqwest = { version = "0.12", features = ["json", "blocking"], default-features = false, features = ["rustls-tls"] }

# ─── ONNX Runtime (embedding model inference) ────────────────────────────────
ort = { version = "2.0", features = ["load-dynamic"] }
ndarray = "0.15"

# ─── Vector database ──────────────────────────────────────────────────────────
lancedb = "0.14"
arrow-array = "52"
arrow-schema = "52"

# ─── SQLite ───────────────────────────────────────────────────────────────────
rusqlite = { version = "0.31", features = ["bundled", "vtab", "array"] }

# ─── Configuration ────────────────────────────────────────────────────────────
toml = "0.8"
serde = { version = "1.0", features = ["derive"] }
serde_json = "1.0"

# ─── Logging ──────────────────────────────────────────────────────────────────
tracing = "0.1"
tracing-subscriber = { version = "0.3", features = ["env-filter"] }
tracing-appender = "0.2"

# ─── System tray (Windows) ────────────────────────────────────────────────────
tray-item = "0.9"

# ─── Utilities ────────────────────────────────────────────────────────────────
uuid = { version = "1.6", features = ["v4"] }
chrono = { version = "0.4", features = ["serde"] }
dirs = "5.0"
notify = "6.1"                  # file system watcher for queue/
crossbeam-channel = "0.5"

# ─── Windows API (session change events) ─────────────────────────────────────
[target.'cfg(windows)'.dependencies]
windows-sys = { version = "0.52", features = [
    "Win32_Foundation",
    "Win32_UI_WindowsAndMessaging",
    "Win32_System_Power",
    "Win32_Devices_Properties",
    "Win32_Media_Audio",
    "Win32_System_RemoteDesktop",
] }

[profile.release]
opt-level = 3
lto = "thin"
codegen-units = 1
strip = true                    # Strip debug symbols for smaller binary

[profile.dev]
opt-level = 0
debug = true
```

> **Note for Claude Code:** The `cpal` crate requires the `wasapi` feature flag. The `ort` crate requires the ONNX Runtime DLL to be present at the path specified in config. The `lancedb` crate requires `arrow` dependencies — keep arrow crate versions consistent with the lancedb version being used.

---

## 5. CONFIGURATION SPECIFICATION

**[TYPE: SPECIFICATION]**

This is the canonical `config.toml`. Claude Code must implement `src/config.rs` to load, validate, and expose all these settings. Every value must have a sensible default.

```toml
# config.toml — Copy this to %LOCALAPPDATA%\MeetingAssistant\config.toml
# All paths are relative to the directory containing meeting_assistant.exe

[audio]
device = "auto"                    # "auto" = use VMware Virtual Audio; or exact device name
sample_rate = 16000                # Hz — Whisper.cpp optimal
channels = 1                       # Mono
capture_mode = "loopback"          # "loopback" | "microphone" | "both"
fallback_mode = "microphone"       # Fallback if loopback unavailable
buffer_duration_ms = 100           # Audio buffer chunk size

[transcription]
live_model = "base.en"             # Whisper model for live VAD chunking
batch_model = "small.en"           # Whisper model for post-call re-transcription  
enable_vad = true                  # Use built-in Silero VAD (Whisper.cpp 1.8.3+)
vad_threshold = 0.5                # VAD sensitivity (0.0-1.0)
language = "en"
model_dir = "./models"
max_segment_duration_ms = 30000    # Split segments longer than 30 seconds

[llm]
enabled = true
provider = "llamacpp"              # "llamacpp" | "none"
endpoint = "http://localhost:8080" # llama.cpp server
model_name = "phi-4-mini"          # For logging purposes only
temperature = 0.3
max_tokens = 2048
timeout_secs = 120                 # LLM calls can be slow on CPU
health_check_on_start = true       # Warn if LLM server not reachable

[embedding]
model_path = "./models/minilm.onnx"
dimensions = 384                   # all-MiniLM-L6-v2
batch_size = 32                    # Segments to embed at once

[storage]
data_dir = "./data"                # All data: DB, audio, vectors
audio_retention_days = 30          # Delete raw audio after N days (0 = keep forever)
max_audio_file_mb = 500            # Stop recording if file exceeds this size
encrypt_database = false           # Phase 3: enable SQLCipher

[ui]
hotkey_start_stop = "Alt+S"        # Toggle recording
hotkey_pause = "Alt+P"             # Pause/resume
show_tray_icon = true
notification_on_complete = true
notification_on_start = true

[logging]
level = "info"                     # "debug" | "info" | "warn" | "error"
log_dir = "./logs"
max_log_files = 7                  # Keep 7 days of logs

[compliance]
consent_required = true            # Show consent dialog before first recording
log_consent = true                 # Write consent timestamps to DB
```

---

## 6. DATABASE SCHEMA — COMPLETE

**[TYPE: SPECIFICATION]**

This is the complete SQLite schema. Store this in `migrations/001_initial.sql`. The `db.rs` module must run this migration on startup if the database does not exist (i.e., first run).

```sql
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
```

---

## 7. SESSION 1 — PROJECT SCAFFOLD & DATABASE

**[TYPE: WORKFLOW-STEP]**  
**Estimated Duration:** 2–3 hours  
**Deliverable:** Compiling binary that initialises the database and loads config

### 7.1 Files to Create This Session

| File | Purpose |
|------|---------|
| `Cargo.toml` | Exact content from Section 4 |
| `PROGRESS.md` | Session tracker (template below) |
| `.gitignore` | Standard Rust + data exclusions |
| `config.toml.example` | Exact content from Section 5 |
| `migrations/001_initial.sql` | Exact content from Section 6 |
| `src/main.rs` | Entry point — init only |
| `src/error.rs` | Unified `AppError` type |
| `src/config.rs` | Config loader |
| `src/storage/mod.rs` | Module declaration |
| `src/storage/db.rs` | SQLite init + migration runner |

### 7.2 `PROGRESS.md` Template

```markdown
# Meeting Assistant — Build Progress

## Session Status

| Session | Status | Completed Date | Notes |
|---------|--------|----------------|-------|
| 1: Scaffold + Database | ⬜ In Progress | | |
| 2: Audio Capture | ⬜ Not Started | | |
| 3: Transcription Pipeline | ⬜ Not Started | | |
| 4: AI Pipeline | ⬜ Not Started | | |
| 5: Search & Query | ⬜ Not Started | | |
| 6: System Tray UI | ⬜ Not Started | | |

## Open Issues

_Add issues here as they arise_

## Architecture Decisions Made During Build

_Log any decisions that deviate from the plan here_
```

### 7.3 `src/error.rs` — Full Implementation

```rust
use thiserror::Error;

#[derive(Error, Debug)]
pub enum AppError {
    #[error("Configuration error: {0}")]
    Config(String),

    #[error("Database error: {0}")]
    Database(#[from] rusqlite::Error),

    #[error("Audio capture error: {0}")]
    Audio(String),

    #[error("Transcription error: {0}")]
    Transcription(String),

    #[error("LLM error: {0}")]
    Llm(String),

    #[error("Embedding error: {0}")]
    Embedding(String),

    #[error("Storage error: {0}")]
    Storage(String),

    #[error("IO error: {0}")]
    Io(#[from] std::io::Error),

    #[error("Serialization error: {0}")]
    Serde(#[from] serde_json::Error),
}

pub type Result<T> = std::result::Result<T, AppError>;
```

### 7.4 `src/config.rs` — Full Implementation

```rust
use serde::{Deserialize, Serialize};
use std::path::{Path, PathBuf};
use anyhow::{Context, Result};

#[derive(Debug, Clone, Deserialize, Serialize)]
pub struct AudioConfig {
    pub device: String,
    pub sample_rate: u32,
    pub channels: u16,
    pub capture_mode: String,
    pub fallback_mode: String,
    pub buffer_duration_ms: u64,
}

impl Default for AudioConfig {
    fn default() -> Self {
        Self {
            device: "auto".to_string(),
            sample_rate: 16000,
            channels: 1,
            capture_mode: "loopback".to_string(),
            fallback_mode: "microphone".to_string(),
            buffer_duration_ms: 100,
        }
    }
}

#[derive(Debug, Clone, Deserialize, Serialize)]
pub struct TranscriptionConfig {
    pub live_model: String,
    pub batch_model: String,
    pub enable_vad: bool,
    pub vad_threshold: f32,
    pub language: String,
    pub model_dir: PathBuf,
    pub max_segment_duration_ms: u64,
}

impl Default for TranscriptionConfig {
    fn default() -> Self {
        Self {
            live_model: "base.en".to_string(),
            batch_model: "small.en".to_string(),
            enable_vad: true,
            vad_threshold: 0.5,
            language: "en".to_string(),
            model_dir: PathBuf::from("./models"),
            max_segment_duration_ms: 30000,
        }
    }
}

#[derive(Debug, Clone, Deserialize, Serialize)]
pub struct LlmConfig {
    pub enabled: bool,
    pub provider: String,
    pub endpoint: String,
    pub model_name: String,
    pub temperature: f32,
    pub max_tokens: u32,
    pub timeout_secs: u64,
    pub health_check_on_start: bool,
}

impl Default for LlmConfig {
    fn default() -> Self {
        Self {
            enabled: true,
            provider: "llamacpp".to_string(),
            endpoint: "http://localhost:8080".to_string(),
            model_name: "phi-4-mini".to_string(),
            temperature: 0.3,
            max_tokens: 2048,
            timeout_secs: 120,
            health_check_on_start: true,
        }
    }
}

#[derive(Debug, Clone, Deserialize, Serialize)]
pub struct EmbeddingConfig {
    pub model_path: PathBuf,
    pub dimensions: usize,
    pub batch_size: usize,
}

impl Default for EmbeddingConfig {
    fn default() -> Self {
        Self {
            model_path: PathBuf::from("./models/minilm.onnx"),
            dimensions: 384,
            batch_size: 32,
        }
    }
}

#[derive(Debug, Clone, Deserialize, Serialize)]
pub struct StorageConfig {
    pub data_dir: PathBuf,
    pub audio_retention_days: u32,
    pub max_audio_file_mb: u64,
    pub encrypt_database: bool,
}

impl Default for StorageConfig {
    fn default() -> Self {
        Self {
            data_dir: PathBuf::from("./data"),
            audio_retention_days: 30,
            max_audio_file_mb: 500,
            encrypt_database: false,
        }
    }
}

#[derive(Debug, Clone, Deserialize, Serialize)]
pub struct UiConfig {
    pub hotkey_start_stop: String,
    pub hotkey_pause: String,
    pub show_tray_icon: bool,
    pub notification_on_complete: bool,
    pub notification_on_start: bool,
}

impl Default for UiConfig {
    fn default() -> Self {
        Self {
            hotkey_start_stop: "Alt+S".to_string(),
            hotkey_pause: "Alt+P".to_string(),
            show_tray_icon: true,
            notification_on_complete: true,
            notification_on_start: true,
        }
    }
}

#[derive(Debug, Clone, Deserialize, Serialize)]
pub struct LoggingConfig {
    pub level: String,
    pub log_dir: PathBuf,
    pub max_log_files: u32,
}

impl Default for LoggingConfig {
    fn default() -> Self {
        Self {
            level: "info".to_string(),
            log_dir: PathBuf::from("./logs"),
            max_log_files: 7,
        }
    }
}

#[derive(Debug, Clone, Deserialize, Serialize)]
pub struct ComplianceConfig {
    pub consent_required: bool,
    pub log_consent: bool,
}

impl Default for ComplianceConfig {
    fn default() -> Self {
        Self {
            consent_required: true,
            log_consent: true,
        }
    }
}

#[derive(Debug, Clone, Deserialize, Serialize, Default)]
pub struct AppConfig {
    #[serde(default)]
    pub audio: AudioConfig,
    #[serde(default)]
    pub transcription: TranscriptionConfig,
    #[serde(default)]
    pub llm: LlmConfig,
    #[serde(default)]
    pub embedding: EmbeddingConfig,
    #[serde(default)]
    pub storage: StorageConfig,
    #[serde(default)]
    pub ui: UiConfig,
    #[serde(default)]
    pub logging: LoggingConfig,
    #[serde(default)]
    pub compliance: ComplianceConfig,
}

impl AppConfig {
    /// Load config from file, falling back to defaults for any missing fields.
    pub fn load(path: &Path) -> Result<Self> {
        if !path.exists() {
            tracing::warn!("Config file not found at {:?}; using defaults", path);
            return Ok(Self::default());
        }

        let content = std::fs::read_to_string(path)
            .with_context(|| format!("Failed to read config file: {:?}", path))?;

        let config: AppConfig = toml::from_str(&content)
            .with_context(|| "Failed to parse config.toml — check for syntax errors")?;

        Ok(config)
    }

    /// Resolve a relative path to an absolute path anchored at the exe directory.
    pub fn resolve_path(&self, relative: &Path) -> PathBuf {
        if relative.is_absolute() {
            relative.to_path_buf()
        } else {
            std::env::current_exe()
                .unwrap_or_default()
                .parent()
                .unwrap_or(Path::new("."))
                .join(relative)
        }
    }
}
```

### 7.5 `src/storage/db.rs` — Full Implementation

```rust
use anyhow::{Context, Result};
use rusqlite::{Connection, params};
use std::path::Path;

pub struct Database {
    conn: Connection,
}

impl Database {
    /// Open (or create) the SQLite database and run migrations.
    pub fn open(db_path: &Path) -> Result<Self> {
        // Ensure the parent directory exists
        if let Some(parent) = db_path.parent() {
            std::fs::create_dir_all(parent)
                .with_context(|| format!("Cannot create database directory: {:?}", parent))?;
        }

        let conn = Connection::open(db_path)
            .with_context(|| format!("Cannot open database at: {:?}", db_path))?;

        // Apply performance pragmas
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
        // Create migrations tracking table
        self.conn.execute_batch("
            CREATE TABLE IF NOT EXISTS schema_migrations (
                version INTEGER PRIMARY KEY,
                applied_at TEXT NOT NULL DEFAULT (datetime('now'))
            );
        ").context("Failed to create migrations table")?;

        // Check if migration 1 has run
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

    /// Insert a new meeting record. Returns the meeting UUID.
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

    /// Insert a transcription segment.
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
```

### 7.6 `src/main.rs` — Session 1 Entry Point

```rust
mod config;
mod error;
mod storage;

use anyhow::Result;
use std::path::PathBuf;
use tracing::info;

#[tokio::main]
async fn main() -> Result<()> {
    // ── Determine base directory (same as exe) ─────────────────────────────
    let base_dir = std::env::current_exe()?
        .parent()
        .unwrap_or(std::path::Path::new("."))
        .to_path_buf();

    // ── Load configuration ─────────────────────────────────────────────────
    let config_path = base_dir.join("config.toml");
    let config = config::AppConfig::load(&config_path)?;

    // ── Initialise logging ─────────────────────────────────────────────────
    let log_dir = base_dir.join(&config.logging.log_dir);
    std::fs::create_dir_all(&log_dir)?;
    let file_appender = tracing_appender::rolling::daily(&log_dir, "meeting_assistant.log");
    let (non_blocking, _guard) = tracing_appender::non_blocking(file_appender);
    tracing_subscriber::fmt()
        .with_writer(non_blocking)
        .with_env_filter(&config.logging.level)
        .init();

    info!("Meeting Assistant starting up");
    info!("Base directory: {:?}", base_dir);

    // ── Ensure data directories exist ──────────────────────────────────────
    let data_dir = base_dir.join(&config.storage.data_dir);
    std::fs::create_dir_all(data_dir.join("db"))?;
    std::fs::create_dir_all(data_dir.join("audio"))?;
    std::fs::create_dir_all(data_dir.join("queue"))?;

    // ── Open database ──────────────────────────────────────────────────────
    let db_path = data_dir.join("db").join("meetings.db");
    let db = storage::db::Database::open(&db_path)?;
    info!("Database initialised at {:?}", db_path);

    info!("Session 1 scaffold complete. Exiting.");
    Ok(())
}
```

### 7.7 Session 1 Verification

After writing all files, Claude Code must:

```
cargo check                          # Must compile with 0 errors
cargo build                          # Must build successfully
.\target\debug\meeting_assistant.exe # Must run, print logs, exit 0
```

Verify `data/db/meetings.db` was created. Query it: `sqlite3 data/db/meetings.db ".tables"` — must show all tables.

---

## 8. SESSION 2 — AUDIO CAPTURE ENGINE

**[TYPE: WORKFLOW-STEP]**  
**Prerequisite:** Session 1 verified complete in `PROGRESS.md`  
**Deliverable:** Binary captures VDI audio and writes a valid WAV file on command

### 8.1 Files to Create This Session

| File | Purpose |
|------|---------|
| `src/capture/mod.rs` | Module exports |
| `src/capture/device.rs` | Device enumeration and selection |
| `src/capture/audio.rs` | WASAPI loopback capture loop |
| `src/capture/writer.rs` | WAV file writer |
| `src/session/mod.rs` | Module exports |
| `src/session/manager.rs` | Meeting session lifecycle (start/stop) |

### 8.2 `src/capture/device.rs` — Device Enumeration

```rust
use anyhow::{anyhow, Result};
use cpal::traits::{DeviceTrait, HostTrait};

/// Find the best audio capture device.
/// Priority: (1) exact match from config, (2) VMware Virtual Audio Device,
/// (3) first loopback-capable output device.
pub fn select_device(preferred: &str) -> Result<cpal::Device> {
    let host = cpal::host_from_id(cpal::HostId::Wasapi)
        .map_err(|e| anyhow!("WASAPI host not available: {}", e))?;

    // List all output devices (loopback candidates)
    let output_devices: Vec<cpal::Device> = host.output_devices()
        .map_err(|e| anyhow!("Cannot enumerate output devices: {}", e))?
        .collect();

    if output_devices.is_empty() {
        return Err(anyhow!("No audio output devices found"));
    }

    tracing::info!("Available output devices:");
    for dev in &output_devices {
        tracing::info!("  - {}", dev.name().unwrap_or_default());
    }

    // Exact match from config (not "auto")
    if preferred != "auto" {
        let matched = output_devices.iter()
            .find(|d| d.name().unwrap_or_default().contains(preferred));
        if let Some(device) = matched {
            tracing::info!("Using configured device: {}", device.name().unwrap_or_default());
            return Ok(device.clone());  // NOTE: cpal Device is Clone
        }
        tracing::warn!("Configured device '{}' not found; falling back to auto-select", preferred);
    }

    // Look for VMware Virtual Audio first
    let vmware = output_devices.iter()
        .find(|d| {
            let name = d.name().unwrap_or_default().to_lowercase();
            name.contains("vmware") || name.contains("devtap") || name.contains("virtual audio")
        });

    if let Some(device) = vmware {
        tracing::info!("Auto-selected VMware Virtual Audio: {}", device.name().unwrap_or_default());
        return Ok(device.clone());
    }

    // Fall back to default output device
    host.default_output_device()
        .ok_or_else(|| anyhow!("No default output device available"))
}

/// List all available output device names for display in UI or logging.
pub fn list_devices() -> Vec<String> {
    let Ok(host) = cpal::host_from_id(cpal::HostId::Wasapi) else { return vec![] };
    host.output_devices()
        .map(|devs| devs.filter_map(|d| d.name().ok()).collect())
        .unwrap_or_default()
}
```

### 8.3 `src/capture/audio.rs` — WASAPI Loopback Capture

```rust
use anyhow::{anyhow, Result};
use cpal::{Device, StreamConfig, SampleFormat};
use cpal::traits::{DeviceTrait, StreamTrait};
use crossbeam_channel::{Sender, Receiver, bounded};
use std::sync::atomic::{AtomicBool, Ordering};
use std::sync::Arc;

pub struct AudioCapture {
    device: Device,
    config: StreamConfig,
    is_running: Arc<AtomicBool>,
}

impl AudioCapture {
    pub fn new(device: Device) -> Result<Self> {
        // Get the default output config and convert to 16kHz mono
        let supported_config = device.default_output_config()
            .map_err(|e| anyhow!("Cannot get device config: {}", e))?;

        let config = cpal::StreamConfig {
            channels: 1,                                   // Mono
            sample_rate: cpal::SampleRate(16000),          // 16kHz for Whisper
            buffer_size: cpal::BufferSize::Fixed(1600),    // 100ms at 16kHz
        };

        Ok(Self {
            device,
            config,
            is_running: Arc::new(AtomicBool::new(false)),
        })
    }

    /// Start capturing audio. Returns a channel receiver for audio samples.
    /// The stream stops when `stop()` is called.
    pub fn start(&self) -> Result<(Receiver<Vec<f32>>, Arc<AtomicBool>)> {
        let (tx, rx): (Sender<Vec<f32>>, Receiver<Vec<f32>>) = bounded(100);
        let is_running = Arc::clone(&self.is_running);
        is_running.store(true, Ordering::SeqCst);

        // WASAPI loopback: use build_output_stream with loopback flag
        // cpal with WASAPI feature + loopback = capture system audio output
        let err_fn = |err| tracing::error!("Audio stream error: {}", err);
        let tx_clone = tx.clone();
        let is_running_clone = Arc::clone(&is_running);

        let stream = self.device.build_input_stream(
            &self.config,
            move |data: &[f32], _: &cpal::InputCallbackInfo| {
                if is_running_clone.load(Ordering::Relaxed) {
                    if tx_clone.try_send(data.to_vec()).is_err() {
                        tracing::warn!("Audio buffer full — dropping chunk");
                    }
                }
            },
            err_fn,
            None,
        ).map_err(|e| anyhow!("Failed to build audio stream: {}", e))?;

        stream.play().map_err(|e| anyhow!("Failed to start audio stream: {}", e))?;

        // Keep stream alive in a background thread
        std::thread::spawn(move || {
            // Stream is dropped when is_running becomes false
            while is_running.load(Ordering::Relaxed) {
                std::thread::sleep(std::time::Duration::from_millis(100));
            }
            drop(stream);
        });

        Ok((rx, Arc::clone(&self.is_running)))
    }

    pub fn stop(&self) {
        self.is_running.store(false, Ordering::SeqCst);
        tracing::info!("Audio capture stopped");
    }
}
```

> **⚠️ Important for Claude Code:** The `cpal` WASAPI loopback capture on Windows requires calling `build_input_stream` against an **output device** (not an input device). With the `wasapi` feature enabled, cpal routes this as a loopback capture. If the device does not support loopback, the stream will error immediately — catch this and fall back to microphone capture.

### 8.4 `src/capture/writer.rs` — WAV Writer

```rust
use anyhow::Result;
use hound::{WavSpec, WavWriter, SampleFormat};
use std::path::Path;
use std::sync::Mutex;

pub struct AudioWriter {
    writer: Mutex<Option<WavWriter<std::io::BufWriter<std::fs::File>>>>,
    path: std::path::PathBuf,
}

impl AudioWriter {
    pub fn new(path: &Path) -> Result<Self> {
        let spec = WavSpec {
            channels: 1,
            sample_rate: 16000,
            bits_per_sample: 16,
            sample_format: SampleFormat::Int,
        };

        let writer = WavWriter::create(path, spec)
            .map_err(|e| anyhow::anyhow!("Cannot create WAV file: {}", e))?;

        Ok(Self {
            writer: Mutex::new(Some(writer)),
            path: path.to_path_buf(),
        })
    }

    /// Write a chunk of f32 samples (convert to i16 for WAV).
    pub fn write_samples(&self, samples: &[f32]) -> Result<()> {
        let mut guard = self.writer.lock().unwrap();
        if let Some(ref mut w) = *guard {
            for &sample in samples {
                let s16 = (sample * i16::MAX as f32).clamp(i16::MIN as f32, i16::MAX as f32) as i16;
                w.write_sample(s16)
                    .map_err(|e| anyhow::anyhow!("Failed to write WAV sample: {}", e))?;
            }
        }
        Ok(())
    }

    /// Finalise the WAV file (writes header with correct length).
    pub fn finalise(self) -> Result<std::path::PathBuf> {
        let mut guard = self.writer.lock().unwrap();
        if let Some(writer) = guard.take() {
            writer.finalize()
                .map_err(|e| anyhow::anyhow!("Failed to finalize WAV: {}", e))?;
        }
        tracing::info!("WAV file saved: {:?}", self.path);
        Ok(self.path)
    }
}
```

### 8.5 Session 2 Verification

```
cargo build --release    # Must compile

# Manual test: run binary, press Alt+S to start, speak for 10 seconds,
# press Alt+S again to stop. Verify WAV file in data/audio/
# Open WAV in Audacity or similar to confirm audio is captured correctly.
```

---

## 9. SESSION 3 — TRANSCRIPTION PIPELINE

**[TYPE: WORKFLOW-STEP]**  
**Prerequisite:** Sessions 1–2 verified. Models present at `./models/ggml-base.en.bin`.  
**Deliverable:** After a call ends, the app transcribes the WAV and stores segments in SQLite.

### 9.1 Files to Create This Session

| File | Purpose |
|------|---------|
| `src/transcription/mod.rs` | Module exports |
| `src/transcription/engine.rs` | whisper-rs model wrapper |
| `src/transcription/pipeline.rs` | Post-call orchestration |
| `src/storage/queue.rs` | File-based queue watcher |

### 9.2 `src/transcription/engine.rs`

```rust
use anyhow::{Context, Result};
use whisper_rs::{WhisperContext, WhisperContextParameters, FullParams, SamplingStrategy};
use std::path::Path;

pub struct WhisperEngine {
    ctx: WhisperContext,
}

impl WhisperEngine {
    pub fn new(model_path: &Path, use_gpu: bool) -> Result<Self> {
        if !model_path.exists() {
            return Err(anyhow::anyhow!(
                "Whisper model not found: {:?}\nDownload from: https://huggingface.co/ggerganov/whisper.cpp",
                model_path
            ));
        }

        let mut params = WhisperContextParameters::default();
        params.use_gpu(use_gpu);   // Will gracefully fail to CPU if no GPU

        let ctx = WhisperContext::new_with_params(
            model_path.to_str().unwrap(),
            params,
        ).context("Failed to load Whisper model")?;

        tracing::info!("Whisper model loaded: {:?}", model_path);
        Ok(Self { ctx })
    }

    /// Transcribe a 16kHz mono f32 audio buffer.
    /// Returns Vec of (start_ms, end_ms, text, confidence).
    pub fn transcribe(
        &self,
        audio: &[f32],
        language: &str,
        enable_vad: bool,
    ) -> Result<Vec<(i64, i64, String, f64)>> {
        let mut state = self.ctx.create_state()
            .context("Failed to create Whisper state")?;

        let mut params = FullParams::new(SamplingStrategy::Greedy { best_of: 1 });
        params.set_language(Some(language));
        params.set_print_special(false);
        params.set_print_progress(false);
        params.set_print_realtime(false);
        params.set_print_timestamps(true);

        // Enable built-in VAD (Whisper.cpp 1.8.3+)
        if enable_vad {
            params.set_vad_thold(0.6);    // VAD threshold
            params.set_freq_thold(100.0); // Frequency threshold
        }

        state.full(params, audio)
            .context("Whisper transcription failed")?;

        let num_segments = state.full_n_segments()
            .context("Failed to get segment count")?;

        let mut results = Vec::new();
        for i in 0..num_segments {
            let text = state.full_get_segment_text(i)
                .context("Failed to get segment text")?;
            let t0 = state.full_get_segment_t0(i)
                .context("Failed to get segment start")?;
            let t1 = state.full_get_segment_t1(i)
                .context("Failed to get segment end")?;

            // Whisper timestamps are in centiseconds — convert to ms
            let start_ms = t0 * 10;
            let end_ms = t1 * 10;

            let trimmed = text.trim().to_string();
            if !trimmed.is_empty() {
                results.push((start_ms, end_ms, trimmed, 1.0));
            }
        }

        tracing::info!("Transcribed {} segments", results.len());
        Ok(results)
    }

    /// Load WAV file and return as f32 samples.
    pub fn load_wav(path: &Path) -> Result<Vec<f32>> {
        let mut reader = hound::WavReader::open(path)
            .with_context(|| format!("Cannot open WAV file: {:?}", path))?;

        let spec = reader.spec();
        if spec.sample_rate != 16000 || spec.channels != 1 {
            return Err(anyhow::anyhow!(
                "WAV must be 16kHz mono. Got: {}Hz, {} channel(s)",
                spec.sample_rate, spec.channels
            ));
        }

        let samples: Vec<f32> = match spec.sample_format {
            hound::SampleFormat::Int => reader.samples::<i16>()
                .filter_map(|s| s.ok())
                .map(|s| s as f32 / i16::MAX as f32)
                .collect(),
            hound::SampleFormat::Float => reader.samples::<f32>()
                .filter_map(|s| s.ok())
                .collect(),
        };

        Ok(samples)
    }
}
```

### 9.3 `src/transcription/pipeline.rs`

```rust
use anyhow::Result;
use std::path::Path;
use crate::storage::db::Database;
use super::engine::WhisperEngine;

pub struct TranscriptionPipeline {
    batch_engine: WhisperEngine,
    language: String,
    enable_vad: bool,
}

impl TranscriptionPipeline {
    pub fn new(batch_model_path: &Path, language: String, enable_vad: bool) -> Result<Self> {
        let batch_engine = WhisperEngine::new(batch_model_path, false)?; // CPU-only
        Ok(Self { batch_engine, language, enable_vad })
    }

    /// Run post-call batch transcription on a WAV file.
    /// Stores all segments in the database and returns segment count.
    pub fn process_recording(
        &self,
        meeting_id: &str,
        wav_path: &Path,
        db: &Database,
    ) -> Result<usize> {
        tracing::info!("Starting batch transcription for meeting {}", meeting_id);

        // Update meeting status
        // db.update_meeting_status(meeting_id, "processing")?;

        let audio = WhisperEngine::load_wav(wav_path)?;
        let segments = self.batch_engine.transcribe(&audio, &self.language, self.enable_vad)?;

        let count = segments.len();
        for (start_ms, end_ms, text, confidence) in &segments {
            db.insert_segment(meeting_id, *start_ms, *end_ms, text, *confidence, true)?;
        }

        tracing::info!("Stored {} segments for meeting {}", count, meeting_id);
        Ok(count)
    }
}
```

---

## 10. SESSION 4 — AI PIPELINE (LLM + EMBEDDINGS)

**[TYPE: WORKFLOW-STEP]**  
**Prerequisite:** Sessions 1–3 verified. `llama-server.exe` running on localhost:8080.  
**Deliverable:** After transcription, app generates summary, actions, decisions, and stores embeddings.

### 10.1 Files to Create This Session

| File | Purpose |
|------|---------|
| `src/ai/mod.rs` | Module exports |
| `src/ai/llm.rs` | llama.cpp HTTP client |
| `src/ai/prompts.rs` | All prompt templates |
| `src/ai/embedding.rs` | ONNX Runtime embedding inference |
| `src/ai/processor.rs` | Post-call AI orchestration |
| `src/storage/vectors.rs` | LanceDB vector storage |

### 10.2 `src/ai/llm.rs` — llama.cpp HTTP Client

```rust
use anyhow::{anyhow, Context, Result};
use serde::{Deserialize, Serialize};
use reqwest::blocking::Client;
use std::time::Duration;

#[derive(Serialize)]
struct CompletionRequest {
    prompt: String,
    temperature: f32,
    n_predict: u32,
    stop: Vec<String>,
    stream: bool,
}

#[derive(Deserialize)]
struct CompletionResponse {
    content: String,
}

pub struct LlmClient {
    client: Client,
    endpoint: String,
    temperature: f32,
    max_tokens: u32,
}

impl LlmClient {
    pub fn new(endpoint: &str, temperature: f32, max_tokens: u32, timeout_secs: u64) -> Result<Self> {
        let client = Client::builder()
            .timeout(Duration::from_secs(timeout_secs))
            .build()
            .context("Failed to create HTTP client")?;

        Ok(Self {
            client,
            endpoint: endpoint.to_string(),
            temperature,
            max_tokens,
        })
    }

    /// Check if the llama.cpp server is healthy.
    pub fn health_check(&self) -> bool {
        let url = format!("{}/health", self.endpoint);
        self.client.get(&url).send()
            .map(|r| r.status().is_success())
            .unwrap_or(false)
    }

    /// Send a prompt and return the completion text.
    pub fn complete(&self, prompt: &str) -> Result<String> {
        let url = format!("{}/completion", self.endpoint);
        let req = CompletionRequest {
            prompt: prompt.to_string(),
            temperature: self.temperature,
            n_predict: self.max_tokens,
            stop: vec!["</answer>".to_string(), "<|end|>".to_string()],
            stream: false,
        };

        let response = self.client
            .post(&url)
            .json(&req)
            .send()
            .context("LLM request failed — is llama-server running on localhost:8080?")?;

        if !response.status().is_success() {
            return Err(anyhow!("LLM server error: {}", response.status()));
        }

        let completion: CompletionResponse = response.json()
            .context("Failed to parse LLM response")?;

        Ok(completion.content.trim().to_string())
    }
}
```

### 10.3 `src/ai/prompts.rs` — Prompt Templates

```rust
/// Generate a structured meeting summary prompt.
pub fn summary_prompt(transcript: &str) -> String {
    format!(r#"You are a meeting assistant. Analyse the following meeting transcript and produce a structured summary.

TRANSCRIPT:
{transcript}

Provide your response in the following format:

## Meeting Summary
[2-4 sentence overview of what was discussed and decided]

## Key Points
- [point 1]
- [point 2]
- [continue as needed]

## Action Items
- [OWNER if mentioned] [action description] [due date if mentioned]
- [continue as needed]

## Decisions Made
- [decision 1]
- [continue as needed]

If no action items or decisions were identified, write "None identified."
Keep the summary concise and factual. Do not invent information not present in the transcript.
"#)
}

/// Extract action items as JSON array.
pub fn actions_extraction_prompt(transcript: &str) -> String {
    format!(r#"Extract all action items from the following meeting transcript.
Return ONLY a JSON array. Each item must have: "description", "owner" (or null), "due_date" (ISO date or null).
If no action items exist, return an empty array: []

TRANSCRIPT:
{transcript}

JSON:"#)
}

/// Extract decisions as JSON array.
pub fn decisions_extraction_prompt(transcript: &str) -> String {
    format!(r#"Extract all decisions made in the following meeting transcript.
Return ONLY a JSON array. Each item must have: "description", "context" (brief context or null).
If no decisions exist, return an empty array: []

TRANSCRIPT:
{transcript}

JSON:"#)
}

/// Generate a brief one-paragraph summary for search indexing.
pub fn brief_summary_prompt(transcript: &str) -> String {
    format!(r#"In one paragraph (max 100 words), summarise the key outcome of this meeting.
Be factual. Only include what is explicitly stated in the transcript.

TRANSCRIPT:
{transcript}

Summary:"#)
}
```

### 10.4 `src/ai/embedding.rs` — ONNX Embedding Inference

```rust
use anyhow::{Context, Result};
use ort::{Environment, Session, SessionBuilder, Value};
use ndarray::{Array2, Axis};
use std::path::Path;
use std::sync::Arc;

pub struct EmbeddingModel {
    session: Session,
}

impl EmbeddingModel {
    pub fn new(model_path: &Path) -> Result<Self> {
        let environment = Arc::new(
            Environment::builder()
                .with_name("embedding")
                .build()
                .context("Failed to create ONNX environment")?
        );

        let session = SessionBuilder::new(&environment)
            .context("Failed to create session builder")?
            .with_model_from_file(model_path)
            .context("Failed to load ONNX embedding model")?;

        tracing::info!("Embedding model loaded: {:?}", model_path);
        Ok(Self { session })
    }

    /// Embed a single text string. Returns 384-dimensional vector.
    /// Uses mean-pooling over token embeddings (all-MiniLM-L6-v2 style).
    pub fn embed(&self, text: &str) -> Result<Vec<f32>> {
        // Simple tokenization — for production, use a proper tokenizer (tokenizers crate)
        // all-MiniLM-L6-v2 expects: input_ids, attention_mask, token_type_ids
        // This is a simplified version — replace with proper tokenizer integration
        let tokens = self.simple_tokenize(text);
        let seq_len = tokens.len().min(128); // Max sequence length

        let input_ids: Array2<i64> = Array2::from_shape_vec(
            (1, seq_len),
            tokens[..seq_len].iter().map(|&t| t as i64).collect(),
        ).context("Failed to create input tensor")?;

        let attention_mask: Array2<i64> = Array2::ones((1, seq_len));
        let token_type_ids: Array2<i64> = Array2::zeros((1, seq_len));

        let outputs = self.session.run(vec![
            Value::from_array(self.session.allocator(), &input_ids)?,
            Value::from_array(self.session.allocator(), &attention_mask)?,
            Value::from_array(self.session.allocator(), &token_type_ids)?,
        ]).context("ONNX inference failed")?;

        // Mean pooling over sequence dimension
        let embedding = outputs[0].try_extract::<f32>()
            .context("Failed to extract embedding tensor")?;

        let embedding_view = embedding.view();
        let mean: Vec<f32> = (0..384)
            .map(|i| embedding_view.slice(ndarray::s![0, .., i]).mean().unwrap_or(0.0))
            .collect();

        // L2 normalize
        let norm = (mean.iter().map(|x| x * x).sum::<f32>()).sqrt();
        Ok(mean.iter().map(|x| x / norm.max(1e-8)).collect())
    }

    fn simple_tokenize(&self, text: &str) -> Vec<u32> {
        // Placeholder — integrate `tokenizers` crate with HuggingFace tokenizer JSON
        // For Session 4, this stub returns a valid but non-semantic embedding
        // TODO Session 4: Load tokenizer from models/tokenizer.json
        text.split_whitespace()
            .take(128)
            .enumerate()
            .map(|(i, _)| (i + 1) as u32)
            .collect()
    }
}
```

> **⚠️ Note for Claude Code on Session 4:** The `simple_tokenize` stub in `embedding.rs` is intentionally incomplete. In Session 4, replace it with proper tokenization using the `tokenizers` crate and the `tokenizer.json` file from the all-MiniLM-L6-v2 HuggingFace model card. Download `tokenizer.json` alongside the ONNX model and load it at startup.

---

## 11. SESSION 5 — SEARCH & QUERY LAYER

**[TYPE: WORKFLOW-STEP]**  
**Prerequisite:** Sessions 1–4 verified. Meetings, segments, and embeddings in database.  
**Deliverable:** CLI search commands for keyword search and semantic search.

### 11.1 Files to Create This Session

| File | Purpose |
|------|---------|
| `src/search/mod.rs` | Module exports |
| `src/search/keyword.rs` | SQLite FTS5 wrapper |
| `src/search/semantic.rs` | LanceDB vector search |
| `src/storage/vectors.rs` | LanceDB table init + insert + query |

### 11.2 Semantic Search Flow

```
User query: "What did we decide about the budget?"
         ↓
EmbeddingModel::embed(query) → [0.23, -0.15, ..., 0.41]  (384-dim)
         ↓
LanceDB::vector_search(embedding, top_k=5) → [(segment_text, distance, meeting_id), ...]
         ↓
Return: top 5 most semantically similar meeting segments
```

### 11.3 Daily Digest Query

The daily digest aggregates all meetings from the current day:

```sql
SELECT m.id, m.title, m.started_at, m.duration_secs,
       s.content as summary
FROM meetings m
LEFT JOIN summaries s ON s.meeting_id = m.id AND s.summary_type = 'brief'
WHERE date(m.started_at) = date('now')
ORDER BY m.started_at ASC;
```

---

## 12. SESSION 6 — SYSTEM TRAY UI & POLISH

**[TYPE: WORKFLOW-STEP]**  
**Prerequisite:** Sessions 1–5 verified.  
**Deliverable:** Polished system tray app with hotkeys, notifications, and status display.

### 12.1 Files to Create This Session

| File | Purpose |
|------|---------|
| `src/ui/mod.rs` | Module exports |
| `src/ui/tray.rs` | System tray icon and menu |
| `src/ui/hotkey.rs` | Global hotkey registration |
| `src/session/windows.rs` | WTS session change events (VDI reconnect) |

### 12.2 System Tray Menu Structure

```
Meeting Assistant
├── ● Start Recording (Alt+S)      ← when idle
│   OR
├── ■ Stop Recording (Alt+S)       ← when recording (icon changes to red)
├── ─────────────────────────────
├── 📋 Recent Meetings
│   ├── [Today 09:00] Project Sync (45 min)
│   └── [Today 14:00] Client Call (32 min)
├── 🔍 Search Meetings...          ← opens search dialog (Phase 2)
├── 📊 Daily Digest
├── ─────────────────────────────
├── ⚙️  Settings...
└── ✕ Quit
```

### 12.3 VDI Session Reconnect Handling

When the VDI session disconnects and reconnects, the audio device may change. Implement `src/session/windows.rs` to listen for `WTS_SESSION_REMOTE_CONNECT` events:

```rust
// Register for WTS session change notifications
// On WTS_SESSION_REMOTE_CONNECT or WM_WTSSESSION_CHANGE:
//   1. Wait 2 seconds for audio subsystem to stabilise
//   2. Re-enumerate audio devices
//   3. If recording was active: attempt to restart capture on new device
//   4. If restart fails: log error, notify user, save incomplete session
```

---

## 13. CRITICAL IMPLEMENTATION PATTERNS

**[TYPE: REFERENCE]**

These patterns are non-negotiable. Claude Code must apply them wherever relevant.

### 13.1 Thread Priority — Always Set BELOW_NORMAL

Every thread that does CPU-intensive work must run at below-normal priority:

```rust
#[cfg(windows)]
fn set_below_normal_priority() {
    unsafe {
        let handle = windows_sys::Win32::System::Threading::GetCurrentThread();
        windows_sys::Win32::System::Threading::SetThreadPriority(
            handle,
            windows_sys::Win32::System::Threading::THREAD_PRIORITY_BELOW_NORMAL,
        );
    }
}
```

Call this at the top of: the audio capture thread, the transcription thread, the embedding thread.

### 13.2 Graceful LLM Degradation

The app must function without the LLM server running:

```rust
// In ai/processor.rs
pub async fn process_meeting(&self, meeting_id: &str, db: &Database) -> ProcessingResult {
    if !self.llm_client.health_check() {
        tracing::warn!("LLM server not available — running in transcribe-only mode");
        return ProcessingResult::TranscribeOnly;
    }
    // ... proceed with full AI processing
}
```

### 13.3 Atomic Audio File Writes

Always write WAV files atomically to prevent corruption:

```rust
// Write to a temp file first
let tmp_path = wav_path.with_extension("tmp");
let writer = AudioWriter::new(&tmp_path)?;
// ... write samples ...
writer.finalise()?;
// Atomically rename
std::fs::rename(&tmp_path, &wav_path)?;
```

### 13.4 Queue Processing — One Item at a Time

The AI pipeline must process queue items sequentially (not in parallel) to avoid CPU spikes:

```rust
// In storage/queue.rs
// Use notify crate to watch data/queue/ for new .json files
// Process one file at a time, wait for completion before taking the next
// If processing fails: move file to data/queue/failed/ with error log
```

### 13.5 Database Connection — Single Connection, WAL Mode

Use a single `rusqlite::Connection` shared via `Arc<Mutex<>>`. Do not open multiple connections. WAL mode is set in `db.rs` and must be the very first pragma executed.

### 13.6 Logging — Structured With Context

Use `tracing::info!` with structured fields:

```rust
tracing::info!(meeting_id = %id, duration_secs = duration, "Meeting completed");
tracing::error!(error = %e, path = %path.display(), "Failed to open audio file");
```

---

## 14. KNOWN FAILURE MODES & MITIGATIONS

**[TYPE: REFERENCE]**

| Failure Mode | Symptom | Mitigation |
|---|---|---|
| WASAPI loopback not available | `build_input_stream` returns `BackendSpecificError` | Catch error; log device name; fall back to microphone input; notify user |
| VMware Virtual Audio not found | Device enumeration finds no matching device | Fall back to default output device; log all available device names |
| Whisper model file missing | `WhisperContext::new` panics or returns error | Check file existence on startup; exit with clear error message listing download URL |
| ONNX model file missing | `Session::new` fails | Log error; disable embedding; continue in keyword-search-only mode |
| llama.cpp server not running | `reqwest` connection refused | `health_check()` returns false; use transcribe-only mode; show warning in tray tooltip |
| SQLite locked | `database is locked` error | Ensure WAL mode is set; use single connection; add retry loop (3 retries, 100ms delay) |
| VDI session disconnect during recording | Audio stream drops; cpal error callback fires | Set `is_running = false`; save partial WAV; enqueue for processing; listen for reconnect |
| AppLocker blocks execution | Binary silently fails to launch | Deploy to `%LOCALAPPDATA%`; document this in README |
| Disk full | WAV write fails mid-recording | Check available disk space before starting recording; warn at 90% usage |
| Config file missing | App falls back to defaults | Log warning; write default config to `config.toml.example`; do not crash |

---

## 15. BUILD & PACKAGING INSTRUCTIONS

**[TYPE: PROCEDURE]**

### 15.1 Building the Binary

```cmd
# On a Windows machine with internet access (for initial build):
rustup target add x86_64-pc-windows-msvc
cargo build --release --target x86_64-pc-windows-msvc

# Binary output:
# target\x86_64-pc-windows-msvc\release\meeting_assistant.exe
```

### 15.2 Pre-Download Required Models

These files must be downloaded before deploying to VDI (requires internet access outside VDI):

```
# Whisper models (download via Hugging Face or whisper.cpp releases)
https://huggingface.co/ggerganov/whisper.cpp/resolve/main/ggml-base.en.bin  (145 MB)
https://huggingface.co/ggerganov/whisper.cpp/resolve/main/ggml-small.en.bin (480 MB)

# ONNX Embedding model (all-MiniLM-L6-v2)
https://huggingface.co/sentence-transformers/all-MiniLM-L6-v2/resolve/main/onnx/model.onnx
https://huggingface.co/sentence-transformers/all-MiniLM-L6-v2/resolve/main/tokenizer.json

# LLM (Phi-4-mini via llama.cpp)
https://huggingface.co/microsoft/Phi-4-mini-instruct-gguf (phi-4-mini-Q4_K_M.gguf, ~3.5 GB)

# llama.cpp pre-built server binary for Windows
https://github.com/ggml-org/llama.cpp/releases  (llama-server.exe)
```

### 15.3 Deployment Package (ZIP for VDI)

```
MeetingAssistant_v0.1.zip
├── meeting_assistant.exe          ← Binary
├── config.toml                    ← Pre-configured for VDI
├── models\
│   ├── ggml-base.en.bin
│   ├── ggml-small.en.bin
│   ├── model.onnx                 ← Renamed from HuggingFace download
│   └── tokenizer.json
└── llm\
    ├── llama-server.exe
    └── phi-4-mini-Q4_K_M.gguf

README_VDI_INSTALL.txt             ← Installation instructions
```

### 15.4 Installation on VDI

```
1. Extract ZIP to %LOCALAPPDATA%\MeetingAssistant\
2. Edit config.toml to confirm paths
3. Start llama-server: 
   llm\llama-server.exe -m llm\phi-4-mini-Q4_K_M.gguf --port 8080 --threads 4
4. Run meeting_assistant.exe
5. Confirm tray icon appears
6. Press Alt+S to test recording
```

---

## 16. TESTING CHECKLIST

**[TYPE: PROCEDURE]**

Run all tests before marking any session complete.

### Session 1 Tests

- [ ] `cargo check` — zero errors
- [ ] `cargo build` — zero errors  
- [ ] Binary runs and exits 0
- [ ] `data/db/meetings.db` created with correct schema
- [ ] All tables present (run `.tables` in sqlite3)
- [ ] FTS5 triggers present (run `.trigger` in sqlite3)
- [ ] Config loads from `config.toml` without panicking
- [ ] Config defaults apply when file is missing

### Session 2 Tests

- [ ] Audio devices are enumerated and logged on startup
- [ ] VMware Virtual Audio Device is selected automatically
- [ ] Alt+S starts recording (tray menu or CLI flag)
- [ ] WAV file is created in `data/audio/`
- [ ] WAV file is valid: 16kHz, mono, 16-bit (verify in Audacity)
- [ ] Alt+S stops recording and finalises WAV
- [ ] WASAPI loopback captures system audio (play a YouTube video → should appear in WAV)

### Session 3 Tests

- [ ] Batch transcription completes without error
- [ ] Segments inserted into SQLite with correct start/end times
- [ ] FTS5 index populated (verify with keyword search)
- [ ] Transcription handles empty audio gracefully
- [ ] Processing queue watcher detects new `.json` files

### Session 4 Tests

- [ ] LLM health check correctly detects server up/down
- [ ] Summary is generated and stored in `summaries` table
- [ ] Actions are extracted and stored in `actions` table
- [ ] Decisions are extracted and stored in `decisions` table
- [ ] Embedding is generated (verify 384 non-zero values)
- [ ] Embedding stored in LanceDB
- [ ] App continues functioning if LLM server is offline

### Session 5 Tests

- [ ] Keyword search returns relevant results
- [ ] Semantic search returns results for natural language query
- [ ] Daily digest lists all today's meetings

### Session 6 Tests

- [ ] System tray icon appears in taskbar
- [ ] Right-click menu shows correct options
- [ ] Alt+S hotkey works globally (even when app is not focused)
- [ ] Recording state is visually indicated (icon change)
- [ ] Windows notification appears when processing completes
- [ ] App handles VDI disconnect/reconnect without crashing

---

## 17. SOURCES & REFERENCES

**[TYPE: REFERENCES]**

### Primary Research

| Source | Title | Date | Relevance |
|--------|-------|------|-----------|
| [000 Pre-Build Audit] | Pre-Build Audit: Local-First AI Meeting Assistant | 2026-04-30 | **Parent document — all architectural decisions** |
| Microsoft Learn | WASAPI Loopback Capture | 2026 | WASAPI implementation |
| GitHub / RustAudio | cpal PR #339 — WASAPI Loopback | 2020 (merged) | cpal loopback support |
| GitHub / ggml-org | Whisper.cpp v1.8.3 Release Notes | Mar 2026 | Built-in VAD, Vulkan |

### Secondary Research

| Source | Title | Date | Relevance |
|--------|-------|------|-----------|
| crates.io | cpal v0.15 | Feb 2026 | Audio capture |
| crates.io | whisper-rs | 2026 | Rust Whisper bindings |
| crates.io | lancedb | 2026 | Vector database |
| crates.io | ort v2.0 | 2026 | ONNX Runtime |
| HuggingFace | all-MiniLM-L6-v2 | 2024 | Embedding model |
| Microsoft | Phi-4-mini GGUF | 2025 | LLM model |

### Further Reading

| Resource | Purpose |
|----------|---------|
| https://github.com/RustAudio/cpal | cpal documentation and examples |
| https://github.com/tazz4843/whisper-rs | whisper-rs Rust bindings |
| https://docs.lancedb.com/ | LanceDB official documentation |
| https://ort.pyke.io/ | ONNX Runtime Rust documentation |
| https://github.com/Meetily | Reference implementation for meeting recording |
| https://github.com/mediar-ai/screenpipe | Rust capture reference implementation |

---

## REVISION HISTORY

| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 1.0 | 2026-04-30 | Initial | Complete Claude Code build guide for VDI Meeting Assistant (6 sessions) | Provide single authoritative document for Claude Code implementation |

---

**END OF DOCUMENT**

> **Reminder for Claude Code:** Start every session by reading `PROGRESS.md` to confirm what has been completed. Update `PROGRESS.md` at the end of every session. This document (010) is your north star — if you are unsure about any decision, refer back here before making assumptions.
