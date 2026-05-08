fn main() {
    if std::env::var("CARGO_CFG_TARGET_OS").as_deref() != Ok("windows") {
        return;
    }

    let out_dir = std::env::var("OUT_DIR").expect("OUT_DIR not set");
    let out_path = std::path::PathBuf::from(out_dir);

    // Generate a minimal 16×16 32 bpp ICO file
    let ico_path = out_path.join("meeting_assistant.ico");
    std::fs::write(&ico_path, generate_icon_ico()).expect("Failed to write icon");

    // Write resources.rc referencing the ICO with an absolute path
    let ico_str = ico_path.to_str().expect("path is UTF-8").replace('\\', "/");
    let rc_path = out_path.join("resources.rc");
    std::fs::write(&rc_path, format!("IDI_ICON1 ICON \"{}\"\n", ico_str))
        .expect("Failed to write resources.rc");

    embed_resource::compile(&rc_path, embed_resource::NONE)
        .manifest_optional()
        .unwrap();
}

fn generate_icon_ico() -> Vec<u8> {
    // ICO layout: ICONDIR (6) + ICONDIRENTRY (16) + BITMAPINFOHEADER (40)
    //             + XOR mask 16×16×4 BGRA (1024) + AND mask 16×4 (64) = 1150 bytes
    let xor_len: u32 = 256 * 4; // 1024
    let and_len: u32 = 16 * 4;  // 64 — 1bpp rows padded to DWORD
    let bmp_hdr_len: u32 = 40;
    let bytes_in_res = bmp_hdr_len + xor_len + and_len; // 1128
    let image_offset: u32 = 22; // 6 (ICONDIR) + 16 (ICONDIRENTRY)

    let mut v = Vec::with_capacity((image_offset + bytes_in_res) as usize);

    // ICONDIR
    v.extend_from_slice(&[0u8, 0, 1, 0, 1, 0]); // reserved, type=1 (ICO), count=1

    // ICONDIRENTRY
    v.push(16u8); // width
    v.push(16u8); // height
    v.push(0u8);  // color count (0 for >8bpp)
    v.push(0u8);  // reserved
    v.extend_from_slice(&1u16.to_le_bytes());             // planes
    v.extend_from_slice(&32u16.to_le_bytes());            // bit count
    v.extend_from_slice(&bytes_in_res.to_le_bytes());
    v.extend_from_slice(&image_offset.to_le_bytes());

    // BITMAPINFOHEADER
    v.extend_from_slice(&40u32.to_le_bytes());  // biSize
    v.extend_from_slice(&16i32.to_le_bytes());  // biWidth
    v.extend_from_slice(&32i32.to_le_bytes());  // biHeight (doubled: XOR + AND masks)
    v.extend_from_slice(&1u16.to_le_bytes());   // biPlanes
    v.extend_from_slice(&32u16.to_le_bytes());  // biBitCount
    v.extend_from_slice(&0u32.to_le_bytes());   // biCompression (BI_RGB)
    v.extend_from_slice(&0u32.to_le_bytes());   // biSizeImage (0 = auto for BI_RGB)
    v.extend_from_slice(&0i32.to_le_bytes());   // biXPelsPerMeter
    v.extend_from_slice(&0i32.to_le_bytes());   // biYPelsPerMeter
    v.extend_from_slice(&0u32.to_le_bytes());   // biClrUsed
    v.extend_from_slice(&0u32.to_le_bytes());   // biClrImportant

    // XOR mask — 256 pixels, teal fill, fully opaque (BGRA order)
    for _ in 0..256 {
        v.extend_from_slice(&[0x40u8, 0x80, 0x40, 0xFF]); // B=64 G=128 R=64 A=255
    }

    // AND mask — 64 bytes of zeros (all pixels opaque)
    v.extend_from_slice(&[0u8; 64]);

    v
}
