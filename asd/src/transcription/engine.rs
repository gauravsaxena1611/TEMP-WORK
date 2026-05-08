use anyhow::{Context, Result};
use std::path::Path;
use whisper_rs::{FullParams, SamplingStrategy, WhisperContext, WhisperContextParameters, WhisperVadParams};

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
        params.use_gpu(use_gpu);

        let ctx = WhisperContext::new_with_params(
            model_path.to_str().unwrap(),
            params,
        )
        .context("Failed to load Whisper model")?;

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
        let mut state = self.ctx.create_state().context("Failed to create Whisper state")?;

        let mut params = FullParams::new(SamplingStrategy::Greedy { best_of: 1 });
        params.set_language(Some(language));
        params.set_print_special(false);
        params.set_print_progress(false);
        params.set_print_realtime(false);
        params.set_print_timestamps(true);

        if enable_vad {
            params.enable_vad(true);
            let mut vad_params = WhisperVadParams::default();
            vad_params.set_threshold(0.6);
            params.set_vad_params(vad_params);
        }

        state.full(params, audio).context("Whisper transcription failed")?;

        // full_n_segments() returns i32 directly in whisper-rs 0.16
        let num_segments = state.full_n_segments();

        let mut results = Vec::new();
        for i in 0..num_segments {
            let Some(seg) = state.get_segment(i) else { continue };

            let text = seg.to_str().map_err(|e| anyhow::anyhow!("Segment text error: {:?}", e))?;
            // Whisper timestamps are in centiseconds — convert to ms
            let start_ms = seg.start_timestamp() * 10;
            let end_ms = seg.end_timestamp() * 10;

            let trimmed = text.trim().to_string();
            if !trimmed.is_empty() {
                results.push((start_ms, end_ms, trimmed, 1.0));
            }
        }

        tracing::info!("Transcribed {} segments", results.len());
        Ok(results)
    }

    /// Load a WAV file and return as 16kHz mono f32 samples, resampling if needed.
    pub fn load_wav(path: &Path) -> Result<Vec<f32>> {
        let mut reader = hound::WavReader::open(path)
            .with_context(|| format!("Cannot open WAV file: {:?}", path))?;

        let spec = reader.spec();

        // Read all samples as f32 regardless of source format
        let samples_f32: Vec<f32> = match spec.sample_format {
            hound::SampleFormat::Int => reader
                .samples::<i16>()
                .filter_map(|s| s.ok())
                .map(|s| s as f32 / i16::MAX as f32)
                .collect(),
            hound::SampleFormat::Float => reader
                .samples::<f32>()
                .filter_map(|s| s.ok())
                .collect(),
        };

        // Convert to mono by averaging channels
        let mono: Vec<f32> = if spec.channels > 1 {
            let ch = spec.channels as usize;
            samples_f32
                .chunks(ch)
                .map(|frame| frame.iter().sum::<f32>() / ch as f32)
                .collect()
        } else {
            samples_f32
        };

        // Resample to 16kHz using linear interpolation if needed
        let target_rate = 16000u32;
        let resampled = if spec.sample_rate != target_rate {
            tracing::info!(
                from_hz = spec.sample_rate,
                to_hz = target_rate,
                "Resampling audio"
            );
            resample_linear(&mono, spec.sample_rate, target_rate)
        } else {
            mono
        };

        Ok(resampled)
    }
}

/// Linear interpolation resampler. Good enough for speech; no external crate needed.
fn resample_linear(input: &[f32], from_hz: u32, to_hz: u32) -> Vec<f32> {
    let ratio = from_hz as f64 / to_hz as f64;
    let out_len = (input.len() as f64 / ratio).ceil() as usize;
    let mut output = Vec::with_capacity(out_len);

    for i in 0..out_len {
        let src = i as f64 * ratio;
        let idx = src as usize;
        let frac = (src - idx as f64) as f32;

        let a = input.get(idx).copied().unwrap_or(0.0);
        let b = input.get(idx + 1).copied().unwrap_or(a);
        output.push(a + frac * (b - a));
    }

    output
}
