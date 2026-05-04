# Airborne Copier

Transfer entire folder structures **or individual files** from a locked-down enterprise VDI to your personal laptop — using nothing but the screen.

No USB. No clipboard. No shared drives. No internet. No admin rights. Just Java and a camera.

---

## The Problem

Enterprise VDI environments disable every conventional transfer channel:

- USB redirection: disabled
- Clipboard sync between VDI and local machine: disabled
- Local drive mapping: disabled
- File downloads from browser: blocked
- No admin or install rights on VDI

The only output the VDI cannot restrict is its own screen. Airborne Copier treats that screen as the sole data channel.

---

## How It Works

The pipeline is identical for both folder and file transfers:

```
VDI (Sender)                              Personal Laptop (Receiver)
─────────────────────────────────         ────────────────────────────────
1. Select folder  OR  single file
2. Serialize all file content +
   metadata into one binary blob
3. Compress (GZip)
4. Encrypt (AES-256-GCM)
5. Encode as fountain-code
   (LT codes) packets

   ── FOLDER transfer ──────────────────────────────────────────────────
6. Animated QR stream displayed
   on VDI screen (3 fps — one     ──────► Record screen with OBS Studio
   QR per packet, held ≥333 ms           (lossless, any frame rate ≥15 fps)
   so every packet is captured
   in ≥5 video frames)
7. Stream completes one full loop;
   key JSON shown on screen ──────────── Note it down / send via phone

                                8. Paste mode → video file + key string
                                9. Extract frames, decode QR codes
                               10. Fountain decoder reconstructs blob
                               11. Decrypt → decompress → write files

   ── FILE transfer ────────────────────────────────────────────────────
6. All QR frames pre-generated
   and shown as a static gallery  ──────► Save each frame as PNG,
   (one QR at a time; stays on            OR export all frames to a
   screen until you click Next)           folder in one click
7. Key JSON shown on screen ────────────  Note it down / send via phone

                                8. Paste mode → PNG frames folder +
                                   key string
                                9. Decode each PNG, reconstruct blob
                               11. Decrypt → decompress → write file
```

The same JAR runs on both sides. One binary, all modes.

---

## Transfer Modes

### Copy a folder
Transfers an entire directory tree. Files, subdirectories, and timestamps are all preserved. Uses the animated QR stream; the receiver records it as a video.

### Copy a file
Transfers a single file of any type. Content and last-modified timestamp are preserved byte-for-byte. Uses the static QR gallery — no video recording required. The sender exports PNG frames; the receiver imports that folder of PNGs.

---

## Requirements

### Source machine (VDI)
- Java 11 or later (present in most Java dev VDIs)
- Git (to clone the repo)
- Maven or the included `mvnw` wrapper (no install needed)
- The VDI screen must be visible from your personal laptop

### Target machine (personal laptop)
- Java 11 or later
- **For folder transfers**: OBS Studio ([obsproject.com](https://obsproject.com)) to record the VDI screen
- **For file transfers**: no OBS needed — import the exported PNG frames directly
- The same JAR file (built on the VDI or locally)

---

## Build

```bash
# Clone the repo
git clone <repo-url>
cd airborne-copier

# Build the fat JAR (includes all dependencies)
./mvnw clean package -DskipTests

# JAR is at:
target/airborne-1.0.0.jar
```

The fat JAR bundles all dependencies including FFmpeg binaries (~150 MB). No separate install needed on either machine.

---

## Usage — Sender (VDI)

### Step 1 — Build the JAR inside the VDI

```bash
git clone <repo-url>
cd airborne-copier
./mvnw clean package -DskipTests
```

### Step 2 — Launch the app

```bash
java -jar target/airborne-1.0.0.jar
```

---

### Sending a folder

1. **Home screen** → click **Copy a folder**
2. **Browse** to the folder you want to transfer
3. **Click Start Copy** — the app serializes, compresses, and encrypts the payload
4. **Stream screen** — the animated QR stream starts. Each QR frame is held on screen for 333 ms (≥5 video frames at 15 fps) before advancing. The frame counter and ETA are shown at the bottom.
5. **Let the stream run one full loop** — it stops automatically
6. **Key JSON screen** — the encryption key is shown as formatted JSON. Write it down or send it to your laptop via phone/message. This is the only information that travels separately from the video.
7. **Stop OBS** on your laptop after the loop completes
8. Hit **Restart** to replay the stream if needed, or **Done — Go Home** when finished

---

### Sending a file

1. **Home screen** → click **Copy a file**
2. **Browse** to the file you want to transfer (any file type)
3. **Click Start Copy** — the app serializes, compresses, and encrypts the file
4. **Gallery screen** — all QR frames are pre-generated and shown one at a time. The header shows how many frames there are in total. **All frames must be transferred** for the receiver to reconstruct the file.
5. Transfer the frames to your laptop using one of these methods:
   - **Export All Frames** — saves every frame as a numbered PNG (`qr_001.png`, `qr_002.png`, …) to a folder you choose. Copy that folder to your laptop.
   - **Save Frame as PNG** — saves the currently displayed frame individually. Use this to manually capture selected frames.
6. **Key JSON screen** — shown after clicking Done. Write down or send the key string to your laptop.

---

## Usage — Receiver (Personal Laptop)

### Step 1 — Launch the app

```bash
java -jar airborne-1.0.0.jar
```

### Step 2 — Click Paste on the Home screen

The Paste screen supports two input modes selectable at the top:

---

### Receiving a folder (Video mode)

#### OBS Setup (do once, then reuse)

See the [full OBS setup guide](docs/OBS_SETUP.md). Critical settings:

| Setting | Value |
|---|---|
| Recording format | MKV |
| Encoder | x264 |
| Rate control | CRF |
| CRF value | **0** (lossless) |
| Frame rate | 15 fps or higher |
| Output resolution | Same as base (no downscaling) |

**CRF 0 is non-negotiable.** Any lossy compression destroys QR readability. 15 fps is sufficient since each QR frame is displayed for 333 ms; higher frame rates give more redundancy.

#### Recording

1. Open OBS → add a **Window Capture** source pointing at your VDI client window
2. Click **Start Recording** before the sender starts the QR stream
3. Let OBS record until the VDI stream stops (one full loop)
4. Click **Stop Recording** — note the saved file path

#### Paste walkthrough

1. **Input source** — select **Video recording**
2. **Key field** — paste the full JSON key string shown at the end of the VDI stream
3. **OBS recording** — browse to the `.mkv` or `.mp4` file
4. **Destination folder** — choose where the folder tree should land
5. **Click Start Paste** — the app extracts frames, decodes QR codes, runs the fountain decoder, decrypts, decompresses, and writes the full folder tree
6. **Done screen** — shows file count, directory count, total bytes, and time taken

---

### Receiving a file (Image files mode)

1. **Input source** — select **QR image files**
2. **Key field** — paste the JSON key string
3. **QR frames folder** — browse to the folder of PNG files exported by the sender
4. **Destination folder** — choose where the file should land
5. **Click Start Paste** — the app decodes each PNG, reconstructs the encrypted blob, decrypts, decompresses, and writes the file
6. **Done screen** — confirms the file was written

The resulting file is byte-for-byte identical to the original, including the last-modified timestamp.

---

## Supported Transfer Size

### Folder transfers (video mode)

| Payload | Approx. stream duration |
|---|---|
| 1 MB uncompressed | ~1–2 minutes |
| 10 MB uncompressed | ~10–15 minutes |
| 50 MB uncompressed | ~50–70 minutes |

Duration is longer than before because each QR frame is now held for 333 ms (down from 100 ms) to ensure reliable capture. This trades speed for zero dropped frames.

### File transfers (image mode)

| File size (compressed) | QR frames generated | Typical export time |
|---|---|---|
| < 10 KB | ~20 frames | < 5 seconds |
| ~100 KB | ~200 frames | ~20 seconds |
| ~500 KB | ~1000 frames | ~90 seconds |

The export is a one-time operation on the VDI. Importing on the laptop is fast — a few seconds per 100 frames.

---

## Architecture Overview

```
com.airborne/
├── core/
│   ├── DirectoryWalker       Walk source folder tree (pre-order DFS)
│   ├── BlobSerializer        Pack files + manifest into one binary blob
│   │                           serializeSingleFile() for single-file mode
│   ├── BlobDeserializer      Unpack blob back to folder tree or single file
│   ├── Compressor            GZip compression (level 9)
│   ├── Decompressor          GZip decompression
│   ├── Encryptor             AES-256-GCM encryption
│   ├── Decryptor             AES-256-GCM decryption
│   ├── Manifest              JSON file manifest (paths, sizes, offsets)
│   ├── FileNode              Metadata for one file or directory
│   └── KeyMaterial           AES key + IV + hash, serialized as JSON
│
├── fountain/
│   ├── LTEncoder             Luby Transform fountain code encoder
│   ├── LTDecoder             LT belief-propagation decoder
│   ├── RobustSoliton         Degree distribution for optimal overhead
│   └── FountainPacket        One encoded packet (seed + XOR payload)
│
├── transport/
│   ├── QRGenerator           ZXing QR code frame generator (1024 px)
│   ├── QRDecoder             ZXing QR frame decoder
│   ├── VideoFrameExtractor   JavaCV/FFmpeg frame-by-frame video extraction
│   └── TransferSession       Coordinates the full sender-side pipeline
│                               isSingleFile() drives stream vs gallery mode
│
└── ui/
    ├── AirborneApp           Main JFrame — CardLayout screen manager
    ├── HomeScreen            Entry point — Copy folder / Copy file / Paste
    ├── CopyScreen            Folder selector + scan stats
    ├── CopyFileScreen        Single-file selector + size display
    ├── ProcessingScreen      Progress bar (encode, decode, image-decode paths)
    ├── DataStreamScreen      Stream mode: animated QR at 333 ms/frame
    │                           Gallery mode: static slideshow + PNG export
    ├── PasteScreen           Video mode OR image-files mode selector
    └── DoneScreen            Transfer summary
```

**Blob format** — identical for folder and single-file transfers:

```
[MAGIC: "BCNV1"        5 bytes]
[MANIFEST LENGTH       4 bytes big-endian]
[MANIFEST              JSON UTF-8 — file paths, sizes, offsets, timestamps]
[DATA SECTION          raw file bytes concatenated in manifest order]
```

For single-file transfers the manifest has exactly one entry; `relativePath` is the bare filename. The deserializer writes `destination/filename` — preserving the original name and timestamp exactly as in a normal copy-paste.

**QR frame timing** — each packet is 1000 bytes of encrypted data. Base64-encoded it becomes ~1360 characters, requiring QR version ~38 (193×193 modules) at error correction level M. At 1024 px, each module is ~5.3 px — comfortably decodable from a 15 fps lossless recording. The 333 ms dwell guarantees ≥5 video frames per QR code even with VDI screen transmission latency.

**Security** — the encryption key is never stored in the encrypted blob. It is displayed on screen only after the stream completes and travels separately. The video (or PNG export) alone cannot be decrypted without the key string.

---

## Troubleshooting

**Paste fails: "not enough packets"**
The fountain decoder did not collect enough unique QR frames.
- *Video mode*: Re-run Paste on the same video — the decoder is deterministic. If still failing, ask the sender to Restart the stream, record a second video, and run Paste again.
- *Image mode*: Verify all exported PNG files are present in the folder and none are corrupted (zero-byte or truncated).

**Paste fails: "GCM tag failure" or "hash mismatch"**
The key string was transcribed incorrectly, or the video/images are corrupted.
- Double-check the key string matches what the sender displayed.
- In video mode: verify OBS was recording at CRF 0. Lossy encoding destroys QR payloads.

**OBS shows a black capture window**
Try different Capture Method options in the Window Capture source properties. "Windows Graphics Capture" usually works on Windows. Run OBS as Administrator if needed.

**QR stream looks blurry in the recording**
CRF must be exactly 0 in OBS Settings → Output → Recording. Also verify Output Resolution matches Base Resolution with no downscaling applied.

**Gallery export is slow for large files**
Each PNG is generated at full 1024 px resolution for maximum scan reliability. This is intentional — a smaller QR is harder to decode. The export runs on a background thread; the UI stays responsive.

**App is slow to start on first launch**
The JavaCV/FFmpeg bundle unpacks native libraries on first run (~10 seconds). Subsequent launches are instant.

---

## Project Layout

```
Airborne Copier/
├── src/                        Java source (Maven standard layout)
├── pom.xml                     Maven build — produces target/airborne-1.0.0.jar
├── mvnw / mvnw.cmd             Maven wrapper (no Maven install needed)
├── docs/
│   └── OBS_SETUP.md            Detailed OBS configuration guide
├── Project Documents/          Design docs, architecture, research
└── README.md                   This file
```

---

## Updating the App on the VDI

```bash
git pull
./mvnw clean package -DskipTests
java -jar target/airborne-1.0.0.jar
```

No admin rights needed. No installer. Pull, build, run.
