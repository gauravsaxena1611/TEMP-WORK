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
