pub mod hotkey;
pub mod tray;

#[derive(Debug, Clone)]
pub enum TrayCommand {
    ToggleRecording,
    ShowDigest,
    Quit,
}
