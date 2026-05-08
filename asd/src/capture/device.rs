use anyhow::{anyhow, Result};
use cpal::traits::{DeviceTrait, HostTrait};

/// Find the best audio capture device.
/// Priority: (1) exact match from config, (2) VMware Virtual Audio Device,
/// (3) default output device.
pub fn select_device(preferred: &str) -> Result<cpal::Device> {
    let host = cpal::host_from_id(cpal::HostId::Wasapi)
        .map_err(|e| anyhow!("WASAPI host not available: {}", e))?;

    let output_devices: Vec<cpal::Device> = host
        .output_devices()
        .map_err(|e| anyhow!("Cannot enumerate output devices: {}", e))?
        .collect();

    if output_devices.is_empty() {
        return Err(anyhow!("No audio output devices found"));
    }

    tracing::info!("Available output devices:");
    for dev in &output_devices {
        tracing::info!("  - {}", dev.name().unwrap_or_default());
    }

    if preferred != "auto" {
        let matched = output_devices
            .iter()
            .find(|d| d.name().unwrap_or_default().contains(preferred));
        if let Some(device) = matched {
            tracing::info!("Using configured device: {}", device.name().unwrap_or_default());
            return Ok(device.clone());
        }
        tracing::warn!(
            "Configured device '{}' not found; falling back to auto-select",
            preferred
        );
    }

    let vmware = output_devices.iter().find(|d| {
        let name = d.name().unwrap_or_default().to_lowercase();
        name.contains("vmware") || name.contains("devtap") || name.contains("virtual audio")
    });

    if let Some(device) = vmware {
        tracing::info!(
            "Auto-selected VMware Virtual Audio: {}",
            device.name().unwrap_or_default()
        );
        return Ok(device.clone());
    }

    host.default_output_device()
        .ok_or_else(|| anyhow!("No default output device available"))
}

/// List all available output device names.
pub fn list_devices() -> Vec<String> {
    let Ok(host) = cpal::host_from_id(cpal::HostId::Wasapi) else {
        return vec![];
    };
    host.output_devices()
        .map(|devs| devs.filter_map(|d| d.name().ok()).collect())
        .unwrap_or_default()
}
