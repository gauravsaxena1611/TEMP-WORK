/// Listens for WTS session change events (VDI reconnect/disconnect).
/// On reconnect, logs the event so operators can observe the session lifecycle.
/// Full audio-device restart on reconnect is a Phase 2 feature.
pub fn spawn_wts_listener() {
    #[cfg(windows)]
    std::thread::Builder::new()
        .name("wts-listener".into())
        .spawn(wts_loop)
        .expect("spawn WTS listener");
}

#[cfg(windows)]
fn wts_loop() {
    unsafe {
        use std::mem;
        use std::ptr;

        use windows_sys::Win32::Foundation::{HWND, LPARAM, LRESULT, WPARAM};
        use windows_sys::Win32::System::LibraryLoader::GetModuleHandleW;
        use windows_sys::Win32::System::RemoteDesktop::{
            WTSRegisterSessionNotification, WTSUnRegisterSessionNotification,
        };
        use windows_sys::Win32::UI::WindowsAndMessaging::{
            CreateWindowExW, DefWindowProcW, DestroyWindow, DispatchMessageW, GetMessageW,
            PostQuitMessage, RegisterClassW, TranslateMessage, CW_USEDEFAULT, MSG, WNDCLASSW,
            WM_DESTROY,
        };

        const WM_WTSSESSION_CHANGE: u32 = 0x02B1;
        const WTS_REMOTE_CONNECT: usize = 3;
        const WTS_REMOTE_DISCONNECT: usize = 4;
        const NOTIFY_FOR_THIS_SESSION: u32 = 0;
        const HWND_MESSAGE: HWND = -3isize;

        unsafe extern "system" fn wnd_proc(
            hwnd: HWND,
            msg: u32,
            w: WPARAM,
            l: LPARAM,
        ) -> LRESULT {
            if msg == WM_DESTROY {
                PostQuitMessage(0);
                return 0;
            }
            DefWindowProcW(hwnd, msg, w, l)
        }

        let class_name: Vec<u16> = "MeetingAssistantWts\0".encode_utf16().collect();
        let window_title: Vec<u16> = "MeetingAssistantWts\0".encode_utf16().collect();

        let hinstance = GetModuleHandleW(ptr::null());

        let mut wc: WNDCLASSW = mem::zeroed();
        wc.lpfnWndProc = Some(wnd_proc);
        wc.hInstance = hinstance;
        wc.lpszClassName = class_name.as_ptr();

        if RegisterClassW(&wc) == 0 {
            tracing::warn!("WTS listener: RegisterClassW failed — reconnect detection unavailable");
            return;
        }

        let hwnd = CreateWindowExW(
            0,
            class_name.as_ptr(),
            window_title.as_ptr(),
            0,
            CW_USEDEFAULT,
            CW_USEDEFAULT,
            0,
            0,
            HWND_MESSAGE,
            0,
            hinstance,
            ptr::null(),
        );
        if hwnd == 0 {
            tracing::warn!("WTS listener: CreateWindowExW failed — reconnect detection unavailable");
            return;
        }

        WTSRegisterSessionNotification(hwnd, NOTIFY_FOR_THIS_SESSION);
        tracing::info!("WTS session change listener active");

        let mut msg: MSG = mem::zeroed();
        loop {
            let ret = GetMessageW(&mut msg, 0, 0, 0);
            if ret <= 0 {
                break;
            }
            if msg.message == WM_WTSSESSION_CHANGE {
                match msg.wParam {
                    WTS_REMOTE_CONNECT => {
                        tracing::info!("VDI session reconnected — audio devices may have changed");
                    }
                    WTS_REMOTE_DISCONNECT => {
                        tracing::info!("VDI session disconnected");
                    }
                    _ => {}
                }
            }
            TranslateMessage(&msg);
            DispatchMessageW(&msg);
        }

        WTSUnRegisterSessionNotification(hwnd);
        DestroyWindow(hwnd);
    }
}
