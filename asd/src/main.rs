mod ai;
mod capture;
mod config;
mod error;
mod search;
mod session;
mod storage;
mod transcription;
mod ui;

use std::sync::Arc;
use std::sync::atomic::{AtomicBool, Ordering};

use anyhow::Result;
use tracing::info;

use crate::session::manager::SessionManager;

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

    // ── Session manager ────────────────────────────────────────────────────
    let mut session_mgr = SessionManager::new(db, config.clone(), data_dir.clone());

    // ── Mode selection ─────────────────────────────────────────────────────
    let args: Vec<String> = std::env::args().collect();
    if args.contains(&"--cli".to_string()) {
        run_cli(&mut session_mgr, &config, &base_dir, &data_dir).await
    } else {
        run_tray(&mut session_mgr, &data_dir)
    }
}

// ── Tray mode (default) ────────────────────────────────────────────────────

fn run_tray(
    session_mgr: &mut SessionManager,
    data_dir: &std::path::Path,
) -> Result<()> {
    let (cmd_tx, cmd_rx) = crossbeam_channel::unbounded::<ui::TrayCommand>();
    let running = Arc::new(AtomicBool::new(true));

    // Hotkey listener (Alt+S)
    ui::hotkey::spawn_hotkey_listener(cmd_tx.clone(), Arc::clone(&running));

    // VDI session change listener
    session::windows::spawn_wts_listener();

    // System tray
    let mut tray = ui::tray::TrayApp::new(cmd_tx)
        .map_err(|e| anyhow::anyhow!("Failed to create system tray: {}", e))?;

    info!("Meeting Assistant tray ready — right-click tray icon or press Alt+S");

    // Main command loop
    loop {
        match cmd_rx.recv() {
            Ok(ui::TrayCommand::ToggleRecording) => {
                if session_mgr.is_recording() {
                    match session_mgr.stop_session() {
                        Ok(Some(id)) => {
                            info!(meeting_id = %id, "Recording stopped");
                            tray.set_recording(false).ok();
                        }
                        Ok(None) => {}
                        Err(e) => tracing::error!("Stop session failed: {}", e),
                    }
                } else {
                    match session_mgr.start_session() {
                        Ok(id) => {
                            info!(meeting_id = %id, "Recording started");
                            tray.set_recording(true).ok();
                        }
                        Err(e) => tracing::error!("Start session failed: {}", e),
                    }
                }
            }
            Ok(ui::TrayCommand::ShowDigest) => {
                let db_path = data_dir.join("db").join("meetings.db");
                if let Ok(db) = storage::db::Database::open(&db_path) {
                    match db.daily_digest() {
                        Ok(ref meetings) if meetings.is_empty() => {
                            tray.set_tooltip("Meeting Assistant — No meetings today").ok();
                        }
                        Ok(ref meetings) => {
                            let msg = format!("Meeting Assistant — {} meeting(s) today", meetings.len());
                            tray.set_tooltip(&msg).ok();
                            for (id, title, started_at, duration, _) in meetings {
                                let dur = duration.map(|s| format!("{}m", s / 60)).unwrap_or_else(|| "?".into());
                                let t = if title.is_empty() { "(untitled)" } else { title.as_str() };
                                info!(meeting_id = %&id[..8], title = %t, started = %started_at, duration = %dur, "Digest entry");
                            }
                        }
                        Err(e) => tracing::error!("Daily digest failed: {}", e),
                    }
                }
            }
            Ok(ui::TrayCommand::Quit) | Err(_) => {
                running.store(false, Ordering::SeqCst);
                if session_mgr.is_recording() {
                    session_mgr.stop_session().ok();
                }
                break;
            }
        }
    }

    info!("Meeting Assistant shutting down");
    Ok(())
}

// ── CLI mode (--cli flag) ─────────────────────────────────────────────────

async fn run_cli(
    session_mgr: &mut SessionManager,
    config: &config::AppConfig,
    base_dir: &std::path::Path,
    data_dir: &std::path::Path,
) -> Result<()> {
    println!("Meeting Assistant ready (CLI mode).");
    println!("Commands: [s] Start/Stop  [d] Devices  [t] Transcribe queue  [a] AI process");
    println!("          [k] Keyword search  [v] Semantic search  [g] Daily digest  [q] Quit");
    println!("Search usage: k <query>  or  v <query>");

    let stdin = std::io::stdin();
    loop {
        let mut input = String::new();
        stdin.read_line(&mut input)?;
        let trimmed = input.trim();
        let cmd = trimmed.split_whitespace().next().unwrap_or("").to_lowercase();
        let args = trimmed.splitn(2, char::is_whitespace).nth(1).unwrap_or("").trim();

        match cmd.as_str() {
            "s" => {
                if session_mgr.is_recording() {
                    match session_mgr.stop_session()? {
                        Some(id) => println!("Recording stopped. Meeting ID: {}", id),
                        None => println!("No active session"),
                    }
                } else {
                    match session_mgr.start_session() {
                        Ok(id) => println!("Recording started. Meeting ID: {}\nPress [s] to stop.", id),
                        Err(e) => println!("Failed to start recording: {}", e),
                    }
                }
            }
            "d" => {
                let devices = capture::device::list_devices();
                println!("Output devices ({}):", devices.len());
                for d in &devices {
                    println!("  - {}", d);
                }
            }
            "t" => {
                let model_dir = base_dir.join(&config.transcription.model_dir);
                let batch_model = model_dir.join(format!("ggml-{}.bin", config.transcription.batch_model));
                let queue_dir = data_dir.join("queue");
                match transcription::pipeline::TranscriptionPipeline::new(
                    &batch_model,
                    config.transcription.language.clone(),
                    config.transcription.enable_vad,
                    queue_dir,
                ) {
                    Ok(pipeline) => {
                        let db_path = data_dir.join("db").join("meetings.db");
                        match storage::db::Database::open(&db_path) {
                            Ok(db) => match pipeline.drain_queue(&db) {
                                Ok(n) => println!("Transcribed {} recording(s)", n),
                                Err(e) => println!("Transcription error: {}", e),
                            },
                            Err(e) => println!("DB error: {}", e),
                        }
                    }
                    Err(e) => println!("Failed to load Whisper model: {}", e),
                }
            }
            "a" => {
                let db_path = data_dir.join("db").join("meetings.db");
                match storage::db::Database::open(&db_path) {
                    Ok(db) => match db.last_completed_meeting() {
                        Ok(Some(meeting_id)) => {
                            println!("Running AI pipeline for meeting {}...", meeting_id);
                            let mut processor = ai::processor::AiProcessor::new(config, base_dir, data_dir);
                            match processor.process_meeting(&meeting_id, &db).await {
                                ai::processor::ProcessingResult::Success { segments } =>
                                    println!("Done. {} segments embedded and summarised.", segments),
                                ai::processor::ProcessingResult::TranscribeOnly { segments } =>
                                    println!("Transcribe-only mode (LLM/embedding unavailable). {} segments.", segments),
                            }
                        }
                        Ok(None) => println!("No completed meetings found."),
                        Err(e) => println!("DB error: {}", e),
                    },
                    Err(e) => println!("DB error: {}", e),
                }
            }
            "k" => {
                if args.is_empty() {
                    println!("Usage: k <search query>");
                } else {
                    let db_path = data_dir.join("db").join("meetings.db");
                    match storage::db::Database::open(&db_path) {
                        Ok(db) => {
                            let searcher = search::keyword::KeywordSearch::new(&db);
                            match searcher.search(args, 10) {
                                Ok(results) if results.is_empty() => println!("No results for: {}", args),
                                Ok(results) => {
                                    println!("Keyword results for \"{}\" ({} found):", args, results.len());
                                    for r in &results {
                                        println!("  [{}] {:.4}  {}", &r.meeting_id[..8], r.score, r.text);
                                    }
                                }
                                Err(e) => println!("Search error: {}", e),
                            }
                        }
                        Err(e) => println!("DB error: {}", e),
                    }
                }
            }
            "v" => {
                if args.is_empty() {
                    println!("Usage: v <search query>");
                } else {
                    let model_dir = base_dir.join(&config.embedding.model_path);
                    let tokenizer_path = model_dir
                        .parent()
                        .unwrap_or(std::path::Path::new("."))
                        .join("tokenizer.json");

                    match ai::embedding::EmbeddingModel::new(&model_dir, &tokenizer_path) {
                        Ok(embedding) => {
                            let vectors = storage::vectors::VectorStore::new(
                                data_dir.join("db").join("vectors"),
                            );
                            let mut searcher = search::semantic::SemanticSearch::new(embedding, vectors);
                            match searcher.search(args, 5).await {
                                Ok(results) if results.is_empty() => println!("No semantic results for: {}", args),
                                Ok(results) => {
                                    println!("Semantic results for \"{}\" ({} found):", args, results.len());
                                    for r in &results {
                                        println!("  [{}] dist={:.4}  {}", &r.meeting_id[..8], r.distance, r.text);
                                    }
                                }
                                Err(e) => println!("Semantic search error: {}", e),
                            }
                        }
                        Err(e) => println!("Embedding model unavailable ({})\nRun [a] first to embed meetings.", e),
                    }
                }
            }
            "g" => {
                let db_path = data_dir.join("db").join("meetings.db");
                match storage::db::Database::open(&db_path) {
                    Ok(db) => match db.daily_digest() {
                        Ok(meetings) if meetings.is_empty() => println!("No meetings recorded today."),
                        Ok(meetings) => {
                            println!("Daily digest — {} meeting(s) today:", meetings.len());
                            for (id, title, started_at, duration, summary) in &meetings {
                                let dur = duration.map(|s| format!("{}m", s / 60)).unwrap_or_else(|| "?".to_string());
                                let display_title = if title.is_empty() { "(untitled)" } else { title.as_str() };
                                println!("\n  [{}] {} — {} ({})", &id[..8], display_title, started_at, dur);
                                if let Some(s) = summary {
                                    println!("  {}", s);
                                }
                            }
                        }
                        Err(e) => println!("DB error: {}", e),
                    },
                    Err(e) => println!("DB error: {}", e),
                }
            }
            "q" => {
                if session_mgr.is_recording() {
                    println!("Stopping active recording...");
                    session_mgr.stop_session()?;
                }
                break;
            }
            "" => {}
            _ => println!("Unknown command. Use: [s] [d] [t] [a] [k] [v] [g] [q]"),
        }
    }

    info!("Meeting Assistant shutting down");
    Ok(())
}
