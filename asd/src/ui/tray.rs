use anyhow::Result;
use crossbeam_channel::Sender;
use tray_item::{IconSource, TrayItem};

use crate::ui::TrayCommand;

pub struct TrayApp {
    tray: TrayItem,
    status_id: u32,
}

impl TrayApp {
    pub fn new(tx: Sender<TrayCommand>) -> Result<Self> {
        let mut tray = TrayItem::new("Meeting Assistant", IconSource::Resource("IDI_ICON1"))
            .map_err(|e| anyhow::anyhow!("Tray init failed: {:?}", e))?;

        // Non-clickable status display at position 0
        let status_id = tray
            .inner_mut()
            .add_label_with_id("○  Idle")
            .map_err(|e| anyhow::anyhow!("Status label: {:?}", e))?;

        // Toggle recording at position 1
        let tx1 = tx.clone();
        tray.inner_mut()
            .add_menu_item_with_id("Toggle Recording  [Alt+S]", move || {
                tx1.send(TrayCommand::ToggleRecording).ok();
            })
            .map_err(|e| anyhow::anyhow!("Toggle item: {:?}", e))?;

        tray.inner_mut()
            .add_separator()
            .map_err(|e| anyhow::anyhow!("Separator: {:?}", e))?;

        let tx2 = tx.clone();
        tray.inner_mut()
            .add_menu_item("Daily Digest", move || {
                tx2.send(TrayCommand::ShowDigest).ok();
            })
            .map_err(|e| anyhow::anyhow!("Digest item: {:?}", e))?;

        tray.inner_mut()
            .add_separator()
            .map_err(|e| anyhow::anyhow!("Separator: {:?}", e))?;

        let tx3 = tx;
        tray.inner_mut()
            .add_menu_item("Quit", move || {
                tx3.send(TrayCommand::Quit).ok();
            })
            .map_err(|e| anyhow::anyhow!("Quit item: {:?}", e))?;

        Ok(Self { tray, status_id })
    }

    pub fn set_recording(&mut self, recording: bool) -> Result<()> {
        let status = if recording { "●  Recording..." } else { "○  Idle" };
        self.tray
            .inner_mut()
            .set_label(status, self.status_id)
            .map_err(|e| anyhow::anyhow!("Status label update: {:?}", e))?;

        let tooltip = if recording {
            "Meeting Assistant — Recording"
        } else {
            "Meeting Assistant"
        };
        self.tray
            .inner_mut()
            .set_tooltip(tooltip)
            .map_err(|e| anyhow::anyhow!("Tooltip update: {:?}", e))?;

        Ok(())
    }

    pub fn set_tooltip(&mut self, text: &str) -> Result<()> {
        self.tray
            .inner_mut()
            .set_tooltip(text)
            .map_err(|e| anyhow::anyhow!("Tooltip: {:?}", e))
    }
}
