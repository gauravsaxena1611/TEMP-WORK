use anyhow::{anyhow, Result};
use cpal::traits::{DeviceTrait, StreamTrait};
use cpal::{Device, Stream, StreamConfig};
use crossbeam_channel::{bounded, Receiver, Sender};
use std::sync::atomic::{AtomicBool, Ordering};
use std::sync::Arc;

pub struct AudioCapture {
    device: Device,
    config: StreamConfig,
    // Stream is !Send, so it must live here (same thread as creator)
    stream: Option<Stream>,
    is_running: Arc<AtomicBool>,
}

impl AudioCapture {
    pub fn new(device: Device) -> Result<Self> {
        // Use the device's native output config — hardcoding 16kHz mono is rejected by most
        // devices in WASAPI shared mode. Session 3 resamples to 16kHz mono for Whisper.
        let supported = device
            .default_output_config()
            .map_err(|e| anyhow!("Cannot get device output config: {}", e))?;

        let config = supported.config();

        tracing::info!(
            device = %device.name().unwrap_or_default(),
            sample_rate = config.sample_rate.0,
            channels = config.channels,
            "Audio capture using device native format"
        );

        Ok(Self {
            device,
            config,
            stream: None,
            is_running: Arc::new(AtomicBool::new(false)),
        })
    }

    pub fn sample_rate(&self) -> u32 {
        self.config.sample_rate.0
    }

    pub fn channels(&self) -> u16 {
        self.config.channels
    }

    /// Start capturing audio. Returns a channel receiver for f32 sample chunks.
    /// build_input_stream on a WASAPI output device = loopback capture.
    pub fn start(&mut self) -> Result<Receiver<Vec<f32>>> {
        if self.stream.is_some() {
            return Err(anyhow!("Capture already running"));
        }

        let (tx, rx): (Sender<Vec<f32>>, Receiver<Vec<f32>>) = bounded(100);
        self.is_running.store(true, Ordering::SeqCst);

        let tx_clone = tx.clone();
        let is_running = Arc::clone(&self.is_running);

        let stream = self
            .device
            .build_input_stream(
                &self.config,
                move |data: &[f32], _: &cpal::InputCallbackInfo| {
                    if is_running.load(Ordering::Relaxed) {
                        if tx_clone.try_send(data.to_vec()).is_err() {
                            tracing::warn!("Audio buffer full — dropping chunk");
                        }
                    }
                },
                |err| tracing::error!("Audio stream error: {:?}", err),
                None,
            )
            .map_err(|e| anyhow!("Failed to build audio stream: {}", e))?;

        stream
            .play()
            .map_err(|e| anyhow!("Failed to start audio stream: {}", e))?;

        // Stream stored here; dropping it (in stop()) closes the callback and the channel sender
        self.stream = Some(stream);

        tracing::info!(
            device = %self.device.name().unwrap_or_default(),
            sample_rate = self.config.sample_rate.0,
            channels = self.config.channels,
            "Audio capture started"
        );

        Ok(rx)
    }

    /// Stop capturing. Drops the stream, which closes the channel sender.
    pub fn stop(&mut self) {
        self.is_running.store(false, Ordering::SeqCst);
        self.stream = None;
        tracing::info!("Audio capture stopped");
    }
}
