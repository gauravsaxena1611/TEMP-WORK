use std::sync::Arc;
use std::sync::atomic::{AtomicBool, Ordering};

use crossbeam_channel::Sender;

use crate::ui::TrayCommand;

pub fn spawn_hotkey_listener(tx: Sender<TrayCommand>, running: Arc<AtomicBool>) {
    std::thread::Builder::new()
        .name("hotkey".into())
        .spawn(move || {
            set_below_normal_priority();
            hotkey_loop(tx, running);
        })
        .expect("spawn hotkey thread");
}

fn hotkey_loop(tx: Sender<TrayCommand>, running: Arc<AtomicBool>) {
    #[cfg(windows)]
    unsafe {
        use windows_sys::Win32::UI::Input::KeyboardAndMouse::{
            RegisterHotKey, UnregisterHotKey, MOD_ALT, MOD_NOREPEAT,
        };
        use windows_sys::Win32::UI::WindowsAndMessaging::{
            PeekMessageW, MSG, PM_REMOVE, WM_HOTKEY,
        };

        const HOTKEY_RECORD: i32 = 1001;

        if RegisterHotKey(0, HOTKEY_RECORD, (MOD_ALT | MOD_NOREPEAT) as u32, 0x53) == 0 {
            tracing::warn!("RegisterHotKey Alt+S failed — hotkey unavailable");
            return;
        }

        tracing::info!("Hotkey Alt+S registered");

        let mut msg: MSG = std::mem::zeroed();
        while running.load(Ordering::Relaxed) {
            while PeekMessageW(&mut msg, 0, 0, 0, PM_REMOVE) != 0 {
                if msg.message == WM_HOTKEY && msg.wParam as i32 == HOTKEY_RECORD {
                    if tx.send(TrayCommand::ToggleRecording).is_err() {
                        UnregisterHotKey(0, HOTKEY_RECORD);
                        return;
                    }
                }
            }
            std::thread::sleep(std::time::Duration::from_millis(50));
        }

        UnregisterHotKey(0, HOTKEY_RECORD);
    }

    #[cfg(not(windows))]
    {
        let _ = (tx, running);
    }
}

fn set_below_normal_priority() {
    #[cfg(windows)]
    unsafe {
        use windows_sys::Win32::System::Threading::{
            GetCurrentThread, SetThreadPriority, THREAD_PRIORITY_BELOW_NORMAL,
        };
        let h = GetCurrentThread();
        SetThreadPriority(h, THREAD_PRIORITY_BELOW_NORMAL);
    }
}
