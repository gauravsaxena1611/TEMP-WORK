use anyhow::{anyhow, Result};
use chrono::Utc;
use std::path::PathBuf;
use std::thread::JoinHandle;
use tracing::{error, info};
use uuid::Uuid;

use crate::capture::audio::AudioCapture;
use crate::capture::device;
use crate::capture::writer::AudioWriter;
use crate::config::AppConfig;
use crate::storage::db::Database;
use crate::storage::queue::QueueItem;

pub struct SessionManager {
    db: Database,
    config: AppConfig,
    data_dir: PathBuf,
    queue_dir: PathBuf,
    active: Option<ActiveSession>,
}

struct ActiveSession {
    meeting_id: String,
    wav_path: PathBuf,
    capture: AudioCapture,
    writer_thread: JoinHandle<()>,
}

impl SessionManager {
    pub fn new(db: Database, config: AppConfig, data_dir: PathBuf) -> Self {
        let queue_dir = data_dir.join("queue");
        Self {
            db,
            config,
            data_dir,
            queue_dir,
            active: None,
        }
    }

    pub fn is_recording(&self) -> bool {
        self.active.is_some()
    }

    pub fn start_session(&mut self) -> Result<String> {
        if self.active.is_some() {
            return Err(anyhow!("A recording session is already active"));
        }

        let meeting_id = Uuid::new_v4().to_string();
        let started_at = Utc::now().to_rfc3339();

        self.db.insert_meeting(&meeting_id, &started_at)?;

        let device = device::select_device(&self.config.audio.device)?;
        let mut capture = AudioCapture::new(device)?;
        let sample_rate = capture.sample_rate();
        let channels = capture.channels();
        let rx = capture.start()?;

        let audio_dir = self.data_dir.join("audio");
        std::fs::create_dir_all(&audio_dir)?;

        // Write to .tmp first; rename to .wav on stop (atomic write pattern from spec §13.3)
        let wav_path = audio_dir.join(format!("{}.wav", meeting_id));
        let tmp_path = wav_path.with_extension("tmp");

        let tmp_clone = tmp_path.clone();
        let wav_clone = wav_path.clone();
        let writer_thread = std::thread::spawn(move || {
            match AudioWriter::new(&tmp_clone, sample_rate, channels) {
                Ok(writer) => {
                    for samples in rx {
                        if let Err(e) = writer.write_samples(&samples) {
                            error!("WAV write error: {}", e);
                            return;
                        }
                    }
                    match writer.finalise() {
                        Ok(_) => {
                            if let Err(e) = std::fs::rename(&tmp_clone, &wav_clone) {
                                error!("Failed to rename WAV to final path: {}", e);
                            }
                        }
                        Err(e) => error!("Failed to finalize WAV: {}", e),
                    }
                }
                Err(e) => error!("Failed to create WAV writer: {}", e),
            }
        });

        info!(meeting_id = %meeting_id, "Recording started");

        self.active = Some(ActiveSession {
            meeting_id: meeting_id.clone(),
            wav_path,
            capture,
            writer_thread,
        });

        Ok(meeting_id)
    }

    pub fn stop_session(&mut self) -> Result<Option<String>> {
        let Some(mut session) = self.active.take() else {
            return Ok(None);
        };

        // Signal capture to stop — background thread drops stream, closing channel sender
        session.capture.stop();

        // Wait for writer thread to flush remaining samples and finalize WAV
        if session.writer_thread.join().is_err() {
            error!("Writer thread panicked during session stop");
        }

        let ended_at = Utc::now().to_rfc3339();
        self.db
            .complete_meeting(&session.meeting_id, &ended_at, 0)?;

        // Enqueue for transcription if the WAV was written successfully
        if session.wav_path.exists() {
            let item = QueueItem::new(&session.meeting_id, &session.wav_path);
            if let Err(e) = item.enqueue(&self.queue_dir) {
                error!("Failed to enqueue recording for transcription: {}", e);
            }
        } else {
            error!(
                meeting_id = %session.meeting_id,
                "WAV file missing after stop — transcription skipped"
            );
        }

        info!(
            meeting_id = %session.meeting_id,
            wav_path = %session.wav_path.display(),
            "Recording stopped"
        );

        Ok(Some(session.meeting_id))
    }
}
