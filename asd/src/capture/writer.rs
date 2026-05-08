use anyhow::Result;
use hound::{SampleFormat, WavSpec, WavWriter};
use std::io::BufWriter;
use std::path::Path;
use std::sync::Mutex;

pub struct AudioWriter {
    writer: Mutex<Option<WavWriter<BufWriter<std::fs::File>>>>,
    path: std::path::PathBuf,
}

impl AudioWriter {
    pub fn new(path: &Path, sample_rate: u32, channels: u16) -> Result<Self> {
        let spec = WavSpec {
            channels,
            sample_rate,
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

    /// Write f32 samples, converting to i16 for WAV storage.
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
            writer
                .finalize()
                .map_err(|e| anyhow::anyhow!("Failed to finalize WAV: {}", e))?;
        }
        tracing::info!("WAV file saved: {:?}", self.path);
        Ok(self.path)
    }
}
