# Meeting Assistant

A local-first AI meeting recorder built in Rust for offline/restricted VDI environments. Captures system audio, transcribes speech with Whisper.cpp, summarizes with a local LLM, and enables semantic search — all without any cloud connection.

---

## Background

This tool was built specifically for VMware VDI (Virtual Desktop Infrastructure) environments that have no internet access and restrictive execution policies. Every component runs locally:

- **Audio capture** — Windows WASAPI loopback (records what the speakers play, not just the mic)
- **Transcription** — Whisper.cpp via whisper-rs, running on CPU
- **Summarization & extraction** — llama.cpp server with Phi-4-mini (3.8B, quantized)
- **Semantic search** — all-MiniLM-L6-v2 via ONNX Runtime + LanceDB vector database
- **Full-text search** — SQLite FTS5

No data ever leaves the machine.

---

## Features

- **One-hotkey recording** — Press `Alt+S` to start/stop; lives in the system tray
- **Post-call AI pipeline** — Auto-transcribes, summarizes, and extracts action items and decisions after each meeting
- **Dual search** — Keyword (FTS5) and semantic (vector) search across all past meetings
- **Graceful degradation** — If the LLM server is not running, the app still captures and transcribes
- **VDI-aware** — Listens for RDP disconnect/reconnect events to re-enumerate audio devices
- **Offline-first** — All models are bundled locally; zero network calls at runtime

---

## Architecture

```
Alt+S hotkey
     │
     ▼
SessionManager ──► cpal WASAPI loopback ──► hound WAV writer ──► .wav file
     │                                                                │
     │                                                    ┌──────────┘
     │                                                    ▼
     │                                          whisper-rs (small.en)
     │                                          Transcription segments
     │                                                    │
     │                              ┌─────────────────────┼──────────────────────┐
     │                              ▼                     ▼                      ▼
     │                       llama.cpp HTTP          ONNX Runtime           SQLite FTS5
     │                       (Phi-4-mini)         (all-MiniLM-L6-v2)
     │                       Summary, actions,    384-dim embeddings
     │                       decisions, risks     stored in LanceDB
     │
     ▼
System Tray + Daily Digest
```

### Tech Stack

| Layer | Technology |
|-------|------------|
| Language | Rust 2021, tokio async runtime |
| Audio | cpal 0.15 (WASAPI loopback), hound 3.5 (WAV I/O) |
| Transcription | whisper-rs 0.16 (Whisper.cpp bindings), Silero VAD |
| LLM | llama.cpp HTTP server via reqwest |
| Embeddings | ONNX Runtime (ort 2.0), all-MiniLM-L6-v2 |
| Vector DB | LanceDB 0.14 (embedded, disk-first) |
| Database | SQLite (rusqlite 0.31, bundled, WAL mode, FTS5) |
| UI | System tray (tray-item 0.9), Win32 global hotkeys |
| Config | TOML |
| Logging | tracing + rolling daily log files |

---

## Repository Structure

```
Meeting Assistant/
├── src/
│   ├── main.rs                  # Entry point; tray or --cli mode
│   ├── config.rs                # Config structs and defaults
│   ├── error.rs                 # Unified error type
│   ├── capture/
│   │   ├── audio.rs             # WASAPI loopback capture
│   │   ├── device.rs            # Device enumeration
│   │   └── writer.rs            # WAV file writer
│   ├── transcription/
│   │   ├── engine.rs            # whisper-rs model management
│   │   └── pipeline.rs          # Batch transcription orchestration
│   ├── ai/
│   │   ├── llm.rs               # llama.cpp HTTP client
│   │   ├── prompts.rs           # Prompt templates
│   │   ├── embedding.rs         # ONNX embedding inference
│   │   └── processor.rs         # Post-call AI orchestration
│   ├── storage/
│   │   ├── db.rs                # SQLite wrapper + migrations
│   │   ├── queue.rs             # Processing queue
│   │   └── vectors.rs           # LanceDB interface
│   ├── search/
│   │   ├── keyword.rs           # SQLite FTS5
│   │   └── semantic.rs          # LanceDB semantic search
│   ├── session/
│   │   ├── manager.rs           # Recording session lifecycle
│   │   └── windows.rs           # VDI session change listener
│   └── ui/
│       ├── tray.rs              # System tray menu
│       └── hotkey.rs            # Alt+S global hotkey
├── migrations/
│   └── 001_initial.sql          # Schema: meetings, segments, FTS, actions, decisions
├── docs/
│   ├── 000_PreBuild_Audit_VDI_Meeting_Assistant.md
│   └── 010_Claude_Code_Implementation_Guide.md
├── config.toml.example          # Annotated config template
├── start_meeting_assistant.bat  # VDI launcher (starts llama-server + app)
├── build.rs                     # Embeds tray icon into binary
└── Cargo.toml
```

---

## Prerequisites

### Build Machine (Windows, with internet)

| Tool | Version | Notes |
|------|---------|-------|
| Rust | stable (≥1.78) | Install via [rustup.rs](https://rustup.rs) — select MSVC toolchain |
| Visual Studio Build Tools | 2022 | C++ workload required (whisper.cpp compiles via cmake) |
| LLVM | ≥17 | Required by whisper-rs bindgen; set `LIBCLANG_PATH=C:\Program Files\LLVM\bin` |
| Git | any | For cloning |

### VDI / Target Machine (offline)

| Requirement | Details |
|-------------|---------|
| OS | Windows 10/11 x86_64 |
| RAM | 8 GB minimum (16 GB recommended with LLM) |
| Disk | ~5 GB for models + data |
| CPU | Any modern x86_64 (no GPU required) |
| WASAPI | Built into Windows; no drivers needed |

### Model Files

Download these once on your build machine and transfer to the VDI:

| Model | File | Size | Purpose |
|-------|------|------|---------|
| Whisper base | `ggml-base.en.bin` | 145 MB | Live transcription |
| Whisper small | `ggml-small.en.bin` | 480 MB | Post-call accurate transcription |
| Sentence embeddings | `minilm.onnx` | 80 MB | Semantic search |
| Phi-4-mini LLM | `phi-4-mini-Q4_K_M.gguf` | ~3.5 GB | Summarization & extraction |

**Where to get them:**
- Whisper models: [huggingface.co/ggerganov/whisper.cpp](https://huggingface.co/ggerganov/whisper.cpp)
- MiniLM ONNX: [huggingface.co/sentence-transformers/all-MiniLM-L6-v2](https://huggingface.co/sentence-transformers/all-MiniLM-L6-v2) (ONNX export)
- Phi-4-mini GGUF: [huggingface.co/microsoft/Phi-4-mini-instruct-gguf](https://huggingface.co/microsoft/Phi-4-mini-instruct-gguf) — pick `Q4_K_M`
- llama.cpp server binary: [github.com/ggerganov/llama.cpp/releases](https://github.com/ggerganov/llama.cpp/releases) — `llama-server.exe`

---

## Building

### 1. Set environment variables

```powershell
$env:LIBCLANG_PATH = "C:\Program Files\LLVM\bin"
```

### 2. Clone and build

```powershell
git clone <repo-url> "Meeting Assistant"
cd "Meeting Assistant"
cargo build --release
```

First build takes ~10 minutes (whisper.cpp is compiled from C++ via cmake). Subsequent builds are faster.

Output binary: `target\release\meeting_assistant.exe` (~10–15 MB stripped)

---

## Deployment to VDI

### 1. Create the deployment directory

```
%LOCALAPPDATA%\MeetingAssistant\
├── meeting_assistant.exe
├── config.toml                    ← copy and edit from config.toml.example
├── start_meeting_assistant.bat    ← optional launcher
├── models\
│   ├── ggml-base.en.bin
│   ├── ggml-small.en.bin
│   └── minilm.onnx
└── llm\
    ├── llama-server.exe           ← optional; enables summarization
    └── phi-4-mini-Q4_K_M.gguf
```

The `data\` and `logs\` directories are created automatically on first run.

### 2. Configure

Copy `config.toml.example` to `config.toml` in the deployment directory and edit as needed:

```toml
[audio]
device = "auto"          # or set to exact WASAPI device name
capture_mode = "loopback"

[transcription]
model_dir = "./models"   # path to Whisper model files

[llm]
enabled = true
endpoint = "http://localhost:8080"

[embedding]
model_path = "./models/minilm.onnx"

[storage]
data_dir = "./data"
audio_retention_days = 30
```

### 3. Run

**Option A — Via launcher (recommended):**

Double-click `start_meeting_assistant.bat`. It will:
1. Check if `llama-server.exe` is already running; if not, start it with Phi-4-mini
2. Wait 3 seconds for the LLM server to initialize
3. Launch `meeting_assistant.exe` in the background

**Option B — Directly:**

```powershell
.\meeting_assistant.exe          # Tray mode (default)
.\meeting_assistant.exe --cli    # CLI mode for testing
```

---

## How to Use

### Recording a Meeting

1. **Start the app** — A tray icon appears in the system tray (bottom-right taskbar area)
2. **Press `Alt+S`** — Recording begins; tray shows `● Recording...`
3. **Join your meeting** — The app captures all audio playing through the speakers (WASAPI loopback)
4. **Press `Alt+S` again** — Recording stops; tray shows `○ Idle`

After stopping, the app automatically runs the AI pipeline in the background:
- Re-transcribes the audio with the more accurate `small.en` model
- If llama.cpp is running: generates a summary and extracts action items and decisions
- Embeds all transcript segments for semantic search
- Marks the meeting as complete

### Checking the Tray Menu

Right-click the tray icon:

| Menu Item | Action |
|-----------|--------|
| Toggle Recording [Alt+S] | Start or stop recording |
| Daily Digest | Shows count of today's meetings in the log |
| Quit | Closes the application cleanly |

### Searching Past Meetings

Currently accessible via CLI mode (`--cli` flag) or by querying the SQLite database directly:

**Keyword search (FTS5):**
```sql
SELECT m.title, s.text, s.start_ms
FROM segments_fts
JOIN segments s ON s.rowid = segments_fts.rowid
JOIN meetings m ON m.id = s.meeting_id
WHERE segments_fts MATCH 'budget approval'
ORDER BY rank;
```

**Direct DB access:**
```powershell
sqlite3 .\data\db\meetings.db
```

Semantic search is built in but exposed programmatically via the `search` module.

---

## Data Layout

All runtime data is written relative to the executable directory by default (configurable via `config.toml`):

```
<deploy-dir>/
├── data/
│   ├── db/
│   │   └── meetings.db          # SQLite: all meetings, segments, summaries, actions
│   ├── audio/
│   │   └── *.wav                # Raw recordings (auto-deleted after retention_days)
│   ├── vectors/
│   │   └── segments.lance/      # LanceDB vector store
│   └── queue/
│       └── meeting_*.json       # Processing queue (transient)
└── logs/
    └── meeting_assistant.YYYY-MM-DD.log
```

### Database Tables

| Table | Contents |
|-------|----------|
| `meetings` | One row per session: UUID, title, timestamps, audio path, status |
| `segments` | Transcribed text chunks with start/end times and confidence |
| `segments_fts` | FTS5 virtual table auto-synced from `segments` |
| `summaries` | LLM-generated summaries (brief / standard / detailed) |
| `actions` | Extracted action items with owner and due date |
| `decisions` | Extracted decisions with context |
| `consent_log` | Compliance: consent timestamps |

---

## Configuration Reference

Full annotated example in `config.toml.example`. Key options:

```toml
[audio]
device = "auto"                     # "auto" or exact device name (e.g. "VMware Virtual Audio DevTap")
capture_mode = "loopback"           # "loopback" | "microphone" | "both"
fallback_mode = "microphone"        # Used if loopback device is unavailable

[transcription]
live_model = "base.en"              # Model for live use (Phase 2)
batch_model = "small.en"           # Model for post-call transcription
enable_vad = true                   # Voice Activity Detection (Silero VAD)
vad_threshold = 0.5                 # 0.0 (sensitive) to 1.0 (selective)
model_dir = "./models"

[llm]
enabled = true                      # Set false to disable LLM features entirely
endpoint = "http://localhost:8080"
timeout_secs = 120
health_check_on_start = true        # Warn at startup if LLM unreachable

[embedding]
model_path = "./models/minilm.onnx"
dimensions = 384
batch_size = 32

[storage]
data_dir = "./data"
audio_retention_days = 30           # Auto-delete audio after N days (0 = never)
max_audio_file_mb = 500             # Stop if file exceeds this size

[ui]
hotkey_start_stop = "Alt+S"

[logging]
level = "info"                      # "debug" | "info" | "warn" | "error"
log_dir = "./logs"
max_log_files = 7
```

---

## Troubleshooting

### No audio is captured

1. Check that the correct WASAPI device is selected — in `config.toml` set `device = "auto"` first, then check the log for which device was chosen
2. On VDI, the device may be named `"VMware Virtual Audio DevTap"` — set it explicitly if auto-detection picks the wrong one
3. Ensure the meeting audio is playing through speakers/headphones (loopback captures speaker output, not mic input)

### Transcription is slow

- `small.en` runs on CPU and takes 2–5× real-time on typical VDI hardware
- Switch `batch_model = "base.en"` in config for faster (less accurate) post-call transcription
- Check that no other CPU-heavy process is competing

### LLM features not working (no summary/actions)

1. Verify llama-server is running: `tasklist | findstr llama-server`
2. Test the endpoint: `curl http://localhost:8080/health`
3. Check the log file in `.\logs\` for connection errors
4. If llama-server is not available, the app still captures and transcribes — only summarization and action extraction are skipped

### App doesn't start on VDI (.exe blocked by AppLocker)

Use `start_meeting_assistant.bat` as the launcher — batch files are typically allowed even when `.exe` execution is restricted. If `.bat` is also blocked, ask IT to whitelist `%LOCALAPPDATA%\MeetingAssistant\meeting_assistant.exe`.

### Alt+S hotkey not working

- Another application may have registered `Alt+S` globally — check for conflicts
- Run the app as the current user (not elevated); hotkeys registered by elevated processes don't fire for normal windows
- Check the log for `"Failed to register hotkey"` messages

### Database locked error

The app uses WAL mode to allow concurrent reads, but only one writer at a time. If you see a lock error, check that no other instance of the app is running: `tasklist | findstr meeting_assistant`

---

## Limitations (Phase 1)

- **No live transcription** — transcription runs post-call only (live streaming is planned for Phase 2)
- **No speaker diarization** — all speech is attributed to generic speaker labels
- **No GUI for search** — search requires direct SQLite queries or CLI mode
- **No export** — action items and summaries are in the DB; export templates are Phase 2
- **No encryption** — database and audio files are stored in plain format (SQLCipher is Phase 3)
- **LLM requires separate process** — `llama-server.exe` must be started separately (the launcher handles this)

---

## Build Profiles

```toml
[profile.release]
opt-level = 3
lto = "thin"
codegen-units = 1
strip = true           # ~10–15 MB final binary
```

Development builds (`cargo build`) are faster to compile but larger and unoptimized.
