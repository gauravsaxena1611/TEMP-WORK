use anyhow::{Context, Result};
use serde::{Deserialize, Serialize};
use std::path::{Path, PathBuf};

/// A processing queue item written to data/queue/ as a JSON file.
/// The transcription pipeline watches this directory for new items.
#[derive(Debug, Serialize, Deserialize)]
pub struct QueueItem {
    pub meeting_id: String,
    pub wav_path: String,
    pub queued_at: String,
}

impl QueueItem {
    pub fn new(meeting_id: &str, wav_path: &Path) -> Self {
        Self {
            meeting_id: meeting_id.to_string(),
            wav_path: wav_path.to_string_lossy().to_string(),
            queued_at: chrono::Utc::now().to_rfc3339(),
        }
    }

    /// Write this item to queue_dir/<meeting_id>.json
    pub fn enqueue(&self, queue_dir: &Path) -> Result<PathBuf> {
        std::fs::create_dir_all(queue_dir)
            .with_context(|| format!("Cannot create queue dir: {:?}", queue_dir))?;

        let item_path = queue_dir.join(format!("{}.json", self.meeting_id));
        let json = serde_json::to_string_pretty(self).context("Failed to serialize queue item")?;
        std::fs::write(&item_path, json)
            .with_context(|| format!("Failed to write queue item: {:?}", item_path))?;

        tracing::info!(meeting_id = %self.meeting_id, "Enqueued for transcription");
        Ok(item_path)
    }

    /// Load a queue item from a JSON file.
    pub fn load(path: &Path) -> Result<Self> {
        let json = std::fs::read_to_string(path)
            .with_context(|| format!("Cannot read queue item: {:?}", path))?;
        serde_json::from_str(&json).context("Failed to deserialize queue item")
    }

    /// Move item to failed/ subdirectory with an error annotation.
    pub fn move_to_failed(item_path: &Path, reason: &str) -> Result<()> {
        let failed_dir = item_path
            .parent()
            .unwrap_or(Path::new("."))
            .join("failed");
        std::fs::create_dir_all(&failed_dir)?;

        let dest = failed_dir.join(item_path.file_name().unwrap_or_default());
        std::fs::rename(item_path, &dest)?;

        // Write error sidecar
        let err_path = dest.with_extension("error.txt");
        std::fs::write(&err_path, reason)?;

        tracing::warn!(reason = %reason, "Queue item moved to failed/");
        Ok(())
    }
}

/// Drain all pending .json files from queue_dir and return their paths, sorted by modified time.
pub fn pending_items(queue_dir: &Path) -> Vec<PathBuf> {
    let Ok(entries) = std::fs::read_dir(queue_dir) else {
        return vec![];
    };

    let mut items: Vec<(PathBuf, std::time::SystemTime)> = entries
        .filter_map(|e| e.ok())
        .filter(|e| e.path().extension().map_or(false, |x| x == "json"))
        .filter_map(|e| {
            let path = e.path();
            let mtime = e.metadata().ok()?.modified().ok()?;
            Some((path, mtime))
        })
        .collect();

    items.sort_by_key(|(_, t)| *t);
    items.into_iter().map(|(p, _)| p).collect()
}
