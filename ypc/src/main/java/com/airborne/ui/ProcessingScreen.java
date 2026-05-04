package com.airborne.ui;

import com.airborne.core.*;
import com.airborne.fountain.FountainPacket;
import com.airborne.fountain.LTDecoder;
import com.airborne.fountain.LTEncoder;
import com.airborne.transport.QRDecoder;
import com.airborne.transport.TransferSession;
import com.airborne.transport.VideoFrameExtractor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Shared progress screen for both Copy and Paste pipelines.
 * Shows a status label and progress bar. All heavy work is on SwingWorker threads.
 */
class ProcessingScreen extends JPanel {

    private final AirborneApp app;

    private final JLabel titleLabel;
    private final JLabel statusLabel;
    private final JProgressBar progressBar;
    private final JLabel subLabel;

    private SwingWorker<?, ?> currentWorker;
    private long pipelineStartTime;

    ProcessingScreen(AirborneApp app) {
        this.app = app;
        setBackground(AirborneApp.BG);
        setLayout(new GridBagLayout());

        JPanel content = new JPanel();
        content.setBackground(AirborneApp.BG);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(BorderFactory.createEmptyBorder(0, 60, 0, 60));

        titleLabel = new JLabel("Processing...");
        titleLabel.setFont(AirborneApp.TITLE_FONT);
        titleLabel.setForeground(AirborneApp.TEXT);
        titleLabel.setAlignmentX(LEFT_ALIGNMENT);

        statusLabel = new JLabel("Starting...");
        statusLabel.setFont(AirborneApp.BODY_FONT);
        statusLabel.setForeground(AirborneApp.TEXT);
        statusLabel.setAlignmentX(LEFT_ALIGNMENT);

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setAlignmentX(LEFT_ALIGNMENT);
        progressBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 24));

        subLabel = new JLabel(" ");
        subLabel.setFont(AirborneApp.SMALL_FONT);
        subLabel.setForeground(new Color(0x66, 0x66, 0x66));
        subLabel.setAlignmentX(LEFT_ALIGNMENT);

        content.add(titleLabel);
        content.add(Box.createVerticalStrut(20));
        content.add(statusLabel);
        content.add(Box.createVerticalStrut(10));
        content.add(progressBar);
        content.add(Box.createVerticalStrut(8));
        content.add(subLabel);

        add(content);
    }

    // -----------------------------------------------------------------------
    // Copy pipeline
    // -----------------------------------------------------------------------

    void beginCopy(Path folder) {
        pipelineStartTime = System.currentTimeMillis();
        titleLabel.setText("Preparing transfer...");
        progressBar.setValue(0);
        statusLabel.setText("Starting...");
        subLabel.setText(" ");

        SwingWorker<TransferSession, Object[]> worker = new SwingWorker<>() {
            @Override
            protected TransferSession doInBackground() throws Exception {
                return new TransferSession(folder, (msg, pct) ->
                        publish(new Object[]{msg, pct, null}));
            }

            @Override
            protected void process(List<Object[]> chunks) {
                Object[] latest = chunks.get(chunks.size() - 1);
                statusLabel.setText((String) latest[0]);
                progressBar.setValue((int) latest[1]);
                updateElapsed();
            }

            @Override
            protected void done() {
                try {
                    app.onCopyDone(get());
                } catch (Exception e) {
                    app.showError(unwrap(e));
                    app.goHome();
                }
            }
        };
        currentWorker = worker;
        worker.execute();
    }

    // -----------------------------------------------------------------------
    // Copy-file pipeline
    // -----------------------------------------------------------------------

    void beginCopyFile(Path filePath) {
        pipelineStartTime = System.currentTimeMillis();
        titleLabel.setText("Preparing file transfer...");
        progressBar.setValue(0);
        statusLabel.setText("Starting...");
        subLabel.setText(" ");

        SwingWorker<TransferSession, Object[]> worker = new SwingWorker<>() {
            @Override
            protected TransferSession doInBackground() throws Exception {
                return new TransferSession(filePath, true, (msg, pct) ->
                        publish(new Object[]{msg, pct, null}));
            }

            @Override
            protected void process(List<Object[]> chunks) {
                Object[] latest = chunks.get(chunks.size() - 1);
                statusLabel.setText((String) latest[0]);
                progressBar.setValue((int) latest[1]);
                updateElapsed();
            }

            @Override
            protected void done() {
                try {
                    app.onCopyDone(get());
                } catch (Exception e) {
                    app.showError(unwrap(e));
                    app.goHome();
                }
            }
        };
        currentWorker = worker;
        worker.execute();
    }

    // -----------------------------------------------------------------------
    // Paste pipeline
    // -----------------------------------------------------------------------

    void beginPaste(String keyJson, Path videoPath, Path destination) {
        pipelineStartTime = System.currentTimeMillis();
        titleLabel.setText("Restoring transfer...");
        progressBar.setValue(0);
        statusLabel.setText("Parsing key...");
        subLabel.setText(" ");

        SwingWorker<Manifest, Object[]> worker = new SwingWorker<>() {
            @Override
            protected Manifest doInBackground() throws Exception {
                // Parse key material
                KeyMaterial km;
                try {
                    km = KeyMaterial.fromJson(keyJson);
                    if (km.key == null || km.iv == null) throw new IllegalArgumentException("null fields");
                } catch (Exception e) {
                    throw new IllegalArgumentException(
                            "The key string could not be parsed. Make sure you copied the complete text " +
                            "from your phone after scanning the Key QR code.");
                }

                publish(status("Extracting video frames...", 2));

                // Fountain decoder
                int blockSize = LTEncoder.DEFAULT_BLOCK_SIZE;
                LTDecoder ltDecoder = new LTDecoder(km.totalBlocks, blockSize, (int) km.encryptedBytes);
                QRDecoder qrDecoder = new QRDecoder();

                AtomicLong lastReport = new AtomicLong(0);

                VideoFrameExtractor extractor = new VideoFrameExtractor();
                extractor.extractFrames(videoPath, (image, frameNum, total) -> {
                    if (ltDecoder.isComplete()) return false;

                    byte[] raw = qrDecoder.decode(image);
                    if (raw != null) {
                        try {
                            FountainPacket pkt = FountainPacket.deserialize(raw);
                            if (pkt.projectId == (int) km.projectId) {
                                ltDecoder.addPacket(pkt);
                            }
                        } catch (Exception ignored) {}
                    }

                    long now = System.currentTimeMillis();
                    if (now - lastReport.get() > 200) {
                        lastReport.set(now);
                        int recovered = ltDecoder.getRecoveredCount();
                        int needed    = km.totalBlocks;
                        int pct = Math.min(60, total > 0
                                ? (int) (frameNum * 60.0 / total) : 0);
                        String msg = String.format("Decoding frames... %d / %d packets collected",
                                recovered, needed);
                        publish(status(msg, pct));
                    }
                    return true;
                });

                if (!ltDecoder.isComplete()) {
                    int got    = ltDecoder.getRecoveredCount();
                    int needed = km.totalBlocks;
                    int pct    = needed > 0 ? got * 100 / needed : 0;
                    throw new IllegalStateException(
                            "Not enough data was captured from the video. Collected " + got +
                            " of " + needed + " required packets (" + pct + "%). " +
                            "Try reprocessing the same video, or ask the sender to display " +
                            "the QR stream again and record another pass.");
                }

                publish(status("Reconstructing data...", 65));
                byte[] ciphertext = ltDecoder.getReconstructedData();

                publish(status("Decrypting...", 75));
                byte[] compressed = new Decryptor().decrypt(ciphertext, km);

                publish(status("Decompressing...", 85));
                byte[] blob = new Decompressor().decompress(compressed);

                publish(status("Writing files...", 92));
                return new BlobDeserializer().deserialize(blob, destination);
            }

            @Override
            protected void process(List<Object[]> chunks) {
                Object[] latest = chunks.get(chunks.size() - 1);
                statusLabel.setText((String) latest[0]);
                progressBar.setValue((int) latest[1]);
                updateElapsed();
            }

            @Override
            protected void done() {
                try {
                    Manifest m = get();
                    long elapsed = System.currentTimeMillis() - pipelineStartTime;
                    app.onPasteDone(m.totalFiles, m.totalDirs, m.totalDataBytes, elapsed, destination);
                } catch (Exception e) {
                    app.showError(unwrap(e));
                    app.goHome();
                }
            }
        };
        currentWorker = worker;
        worker.execute();
    }

    // -----------------------------------------------------------------------
    // Paste-images pipeline (single-file mode: PNG frames from sender export)
    // -----------------------------------------------------------------------

    void beginPasteImages(String keyJson, List<Path> imagePaths, Path destination) {
        pipelineStartTime = System.currentTimeMillis();
        titleLabel.setText("Restoring file from images...");
        progressBar.setValue(0);
        statusLabel.setText("Parsing key...");
        subLabel.setText(" ");

        SwingWorker<Manifest, Object[]> worker = new SwingWorker<>() {
            @Override
            protected Manifest doInBackground() throws Exception {
                KeyMaterial km;
                try {
                    km = KeyMaterial.fromJson(keyJson);
                    if (km.key == null || km.iv == null) throw new IllegalArgumentException("null fields");
                } catch (Exception e) {
                    throw new IllegalArgumentException(
                            "The key string could not be parsed. Make sure you copied the complete " +
                            "text from your phone after scanning the Key QR code.");
                }

                int blockSize = LTEncoder.DEFAULT_BLOCK_SIZE;
                LTDecoder ltDecoder = new LTDecoder(km.totalBlocks, blockSize, (int) km.encryptedBytes);
                QRDecoder qrDecoder = new QRDecoder();
                int total = imagePaths.size();

                publish(status("Decoding " + total + " QR image frames...", 5));

                for (int i = 0; i < total; i++) {
                    if (ltDecoder.isComplete()) break;
                    Path imgPath = imagePaths.get(i);
                    try {
                        BufferedImage image = ImageIO.read(imgPath.toFile());
                        if (image == null) continue; // unreadable — skip
                        byte[] raw = qrDecoder.decode(image);
                        if (raw != null) {
                            FountainPacket pkt = FountainPacket.deserialize(raw);
                            if (pkt.projectId == (int) km.projectId) {
                                ltDecoder.addPacket(pkt);
                            }
                        }
                    } catch (Exception ignored) { /* skip bad frame */ }

                    int pct = 5 + (int) (i * 60.0 / total);
                    publish(status(String.format("Decoded %d / %d frames (%d packets)",
                            i + 1, total, ltDecoder.getRecoveredCount()), pct));
                }

                if (!ltDecoder.isComplete()) {
                    int got    = ltDecoder.getRecoveredCount();
                    int needed = km.totalBlocks;
                    int pct    = needed > 0 ? got * 100 / needed : 0;
                    throw new IllegalStateException(
                            "Not enough packets decoded from the image files. Got " + got +
                            " of " + needed + " required (" + pct + "%). " +
                            "Make sure all exported PNG frames are in the folder and were not corrupted.");
                }

                publish(status("Reconstructing data...", 68));
                byte[] ciphertext = ltDecoder.getReconstructedData();

                publish(status("Decrypting...", 78));
                byte[] compressed = new Decryptor().decrypt(ciphertext, km);

                publish(status("Decompressing...", 88));
                byte[] blob = new Decompressor().decompress(compressed);

                publish(status("Writing file...", 93));
                return new BlobDeserializer().deserialize(blob, destination);
            }

            @Override
            protected void process(List<Object[]> chunks) {
                Object[] latest = chunks.get(chunks.size() - 1);
                statusLabel.setText((String) latest[0]);
                progressBar.setValue((int) latest[1]);
                updateElapsed();
            }

            @Override
            protected void done() {
                try {
                    Manifest m = get();
                    long elapsed = System.currentTimeMillis() - pipelineStartTime;
                    app.onPasteDone(m.totalFiles, m.totalDirs, m.totalDataBytes, elapsed, destination);
                } catch (Exception e) {
                    app.showError(unwrap(e));
                    app.goHome();
                }
            }
        };
        currentWorker = worker;
        worker.execute();
    }

    // -----------------------------------------------------------------------
    // Helpers
    // -----------------------------------------------------------------------

    private static Object[] status(String msg, int pct) {
        return new Object[]{msg, pct};
    }

    private void updateElapsed() {
        long ms = System.currentTimeMillis() - pipelineStartTime;
        subLabel.setText("Elapsed: " + formatTime(ms));
    }

    static String formatTime(long ms) {
        long s = ms / 1000;
        return String.format("%d:%02d", s / 60, s % 60);
    }

    private static String unwrap(Exception e) {
        Throwable cause = e.getCause() != null ? e.getCause() : e;
        return cause.getMessage() != null ? cause.getMessage() : cause.getClass().getSimpleName();
    }
}
