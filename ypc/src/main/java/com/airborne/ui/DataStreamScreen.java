package com.airborne.ui;

import com.airborne.fountain.FountainPacket;
import com.airborne.core.KeyMaterial;
import com.airborne.transport.QRGenerator;
import com.airborne.transport.TransferSession;
import com.google.gson.GsonBuilder;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * QR code display screen — two operating modes:
 *
 *   STREAM mode (folder transfers):
 *     Animated fountain-code QR stream. Each frame is held for DWELL_MS after it is
 *     fully painted before the next is generated. At 15 fps video capture this gives
 *     ≥ 5 video frames per QR code, making every packet reliably decodable.
 *
 *   GALLERY mode (single-file transfers):
 *     All 2 × K fountain packets are pre-generated and shown as a static slideshow.
 *     Each QR code stays on screen until the user clicks "Next". Individual frames
 *     can be saved as PNG files; all frames can be exported to a folder at once.
 *     The receiver imports the folder of PNGs instead of recording a video.
 */
class DataStreamScreen extends JPanel {

    /**
     * Milliseconds each QR frame is held after it is fully painted before advancing.
     * 333 ms = ~3 fps display rate → each QR appears in ≥ 5 video frames at 15 fps capture,
     * with headroom for VDI screen transmission latency.
     */
    static final int DWELL_MS = 333;

    private static final String VIEW_STREAM   = "stream";
    private static final String VIEW_GALLERY  = "gallery";
    private static final String VIEW_COMPLETE = "complete";

    private final AirborneApp app;
    private final QRGenerator generator = new QRGenerator();
    private final ExecutorService qrExecutor = Executors.newSingleThreadExecutor(r -> {
        Thread t = new Thread(r, "airborne-qr-gen");
        t.setDaemon(true);
        return t;
    });

    private TransferSession session;
    private volatile boolean running;
    private volatile BufferedImage currentImage;

    // -- Stream mode state --
    private final AtomicInteger frameCount = new AtomicInteger(0);
    private final AtomicInteger loopCount  = new AtomicInteger(0);
    private long startTime;
    private int totalPacketsPerLoop;
    private Timer statsTimer;

    // -- Gallery mode state --
    private List<FountainPacket> galleryPackets;
    private int galleryIndex = 0;

    // ---- Shared QR panel ----
    private final JPanel qrPanel;

    // ---- Stream view widgets ----
    private final JLabel frameLabel;
    private final JLabel loopLabel;
    private final JLabel elapsedLabel;
    private final JLabel etaLabel;

    // ---- Gallery view widgets ----
    private final JLabel galleryFrameLabel;
    private final JLabel galleryStatusLabel;
    private final JPanel galleryQrPanel;
    private final JButton galleryPrevBtn;
    private final JButton galleryNextBtn;
    private final JButton gallerySaveBtn;
    private final JButton galleryExportBtn;
    private volatile BufferedImage galleryCurrentImage;

    // ---- Completion view widgets ----
    private final JTextArea keyTextArea;

    // ---- Inner deck ----
    private final CardLayout innerCards = new CardLayout();
    private final JPanel     innerDeck  = new JPanel(innerCards);

    // -----------------------------------------------------------------------

    DataStreamScreen(AirborneApp app) {
        this.app = app;
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        // ================================================================
        // STREAM VIEW — animated fountain QR stream
        // ================================================================
        qrPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, getWidth(), getHeight());
                BufferedImage img = currentImage;
                if (img != null) {
                    int size = Math.min(getWidth(), getHeight()) - 20;
                    if (size > 0) {
                        int x = (getWidth()  - size) / 2;
                        int y = (getHeight() - size) / 2;
                        g.drawImage(img, x, y, size, size, null);
                    }
                }
            }
        };
        qrPanel.setBackground(Color.BLACK);

        Color barBg = new Color(0x1A, 0x1A, 0x1A);
        frameLabel   = styledLabel("Frame: 0");
        loopLabel    = styledLabel("Loop: 0");
        elapsedLabel = styledLabel("Elapsed: 0:00");
        etaLabel     = styledLabel("Est 1st loop: calculating...");

        JPanel statsRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 24, 0));
        statsRow.setBackground(barBg);
        statsRow.add(frameLabel);
        statsRow.add(loopLabel);
        statsRow.add(elapsedLabel);
        statsRow.add(etaLabel);

        JButton streamStopBtn = darkButton("Stop");
        streamStopBtn.setForeground(new Color(0xFF, 0x66, 0x66));
        streamStopBtn.addActionListener(e -> confirmStop());

        JPanel streamBtnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        streamBtnRow.setBackground(barBg);
        streamBtnRow.add(streamStopBtn);

        JPanel streamBar = new JPanel(new BorderLayout());
        streamBar.setBackground(barBg);
        streamBar.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        streamBar.add(statsRow,     BorderLayout.CENTER);
        streamBar.add(streamBtnRow, BorderLayout.EAST);

        JPanel streamView = new JPanel(new BorderLayout());
        streamView.setBackground(Color.BLACK);
        streamView.add(qrPanel,   BorderLayout.CENTER);
        streamView.add(streamBar, BorderLayout.SOUTH);

        // ================================================================
        // GALLERY VIEW — static slideshow for single-file transfers
        // ================================================================
        Color galleryBg = new Color(0x0A, 0x0A, 0x0A);

        // QR display area for gallery
        galleryQrPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(galleryBg);
                g.fillRect(0, 0, getWidth(), getHeight());
                BufferedImage img = galleryCurrentImage;
                if (img != null) {
                    int size = Math.min(getWidth(), getHeight()) - 20;
                    if (size > 0) {
                        int x = (getWidth()  - size) / 2;
                        int y = (getHeight() - size) / 2;
                        g.drawImage(img, x, y, size, size, null);
                    }
                }
            }
        };
        galleryQrPanel.setBackground(galleryBg);

        // Gallery controls bar
        galleryFrameLabel  = styledLabel("Frame 0 / 0");
        galleryStatusLabel = styledLabel("Preparing frames...");

        galleryPrevBtn = darkButton("← Prev");
        galleryNextBtn = darkButton("Next →");
        gallerySaveBtn = darkButton("Save Frame as PNG");
        galleryExportBtn = darkButton("Export All Frames");

        galleryPrevBtn.addActionListener(e -> galleryNavigate(-1));
        galleryNextBtn.addActionListener(e -> galleryNavigate(+1));
        gallerySaveBtn.addActionListener(e -> gallerySaveCurrentFrame());
        galleryExportBtn.addActionListener(e -> galleryExportAll());

        JButton galleryStopBtn = darkButton("Done — Go Home");
        galleryStopBtn.setForeground(new Color(0xFF, 0x66, 0x66));
        galleryStopBtn.addActionListener(e -> confirmStop());

        JPanel galleryNavRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        galleryNavRow.setBackground(barBg);
        galleryNavRow.add(galleryPrevBtn);
        galleryNavRow.add(galleryFrameLabel);
        galleryNavRow.add(galleryNextBtn);

        JPanel galleryActRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        galleryActRow.setBackground(barBg);
        galleryActRow.add(gallerySaveBtn);
        galleryActRow.add(galleryExportBtn);
        galleryActRow.add(galleryStatusLabel);

        JPanel galleryStopRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        galleryStopRow.setBackground(barBg);
        galleryStopRow.add(galleryStopBtn);

        JPanel galleryBar = new JPanel();
        galleryBar.setBackground(barBg);
        galleryBar.setLayout(new BoxLayout(galleryBar, BoxLayout.Y_AXIS));
        galleryBar.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        galleryBar.add(galleryNavRow);
        galleryBar.add(Box.createVerticalStrut(6));
        galleryBar.add(galleryActRow);
        galleryBar.add(galleryStopRow);

        JLabel galleryTitle = new JLabel("Scan or save each QR frame — all frames required to receive the file");
        galleryTitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        galleryTitle.setForeground(new Color(0xAA, 0xAA, 0xAA));
        galleryTitle.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel galleryHeader = new JPanel(new BorderLayout());
        galleryHeader.setBackground(new Color(0x15, 0x15, 0x15));
        galleryHeader.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        galleryHeader.add(galleryTitle, BorderLayout.CENTER);

        JPanel galleryView = new JPanel(new BorderLayout());
        galleryView.setBackground(galleryBg);
        galleryView.add(galleryHeader,  BorderLayout.NORTH);
        galleryView.add(galleryQrPanel, BorderLayout.CENTER);
        galleryView.add(galleryBar,     BorderLayout.SOUTH);

        // ================================================================
        // COMPLETION VIEW — stream finished; show key JSON
        // ================================================================
        Color completeBg = new Color(0x0D, 0x0D, 0x0D);

        JLabel doneTitle = new JLabel("Loop Complete");
        doneTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
        doneTitle.setForeground(new Color(0x66, 0xFF, 0x66));
        doneTitle.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel doneSubtitle = new JLabel("Note down or copy this key string — paste it into the laptop app");
        doneSubtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        doneSubtitle.setForeground(new Color(0xAA, 0xAA, 0xAA));
        doneSubtitle.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel doneHeader = new JPanel();
        doneHeader.setBackground(completeBg);
        doneHeader.setLayout(new BoxLayout(doneHeader, BoxLayout.Y_AXIS));
        doneHeader.setBorder(BorderFactory.createEmptyBorder(28, 40, 16, 40));
        doneTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        doneSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        doneHeader.add(doneTitle);
        doneHeader.add(Box.createVerticalStrut(6));
        doneHeader.add(doneSubtitle);

        keyTextArea = new JTextArea();
        keyTextArea.setEditable(false);
        keyTextArea.setFont(new Font("Monospaced", Font.PLAIN, 15));
        keyTextArea.setBackground(new Color(0x16, 0x16, 0x16));
        keyTextArea.setForeground(new Color(0xFF, 0xF0, 0x80));
        keyTextArea.setCaretColor(new Color(0xFF, 0xF0, 0x80));
        keyTextArea.setLineWrap(false);
        keyTextArea.setWrapStyleWord(false);
        keyTextArea.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));
        keyTextArea.setSelectionColor(new Color(0x33, 0x55, 0x33));
        keyTextArea.setSelectedTextColor(Color.WHITE);

        JScrollPane keyScroll = new JScrollPane(keyTextArea);
        keyScroll.setBorder(BorderFactory.createLineBorder(new Color(0x33, 0x33, 0x33), 1));
        keyScroll.setBackground(new Color(0x16, 0x16, 0x16));
        keyScroll.getViewport().setBackground(new Color(0x16, 0x16, 0x16));
        keyScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JPanel keyWrapper = new JPanel(new BorderLayout());
        keyWrapper.setBackground(completeBg);
        keyWrapper.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 40));
        keyWrapper.add(keyScroll, BorderLayout.CENTER);

        JButton restartBtn = darkButton("Restart Stream");
        restartBtn.setForeground(new Color(0x88, 0xFF, 0x88));
        restartBtn.addActionListener(e -> restartStream());

        JButton doneStopBtn = darkButton("Done — Go Home");
        doneStopBtn.setForeground(new Color(0xFF, 0x66, 0x66));
        doneStopBtn.addActionListener(e -> confirmStop());

        JPanel completeBtnRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 0));
        completeBtnRow.setBackground(completeBg);
        completeBtnRow.add(restartBtn);
        completeBtnRow.add(doneStopBtn);

        JPanel completeBottom = new JPanel();
        completeBottom.setBackground(completeBg);
        completeBottom.setBorder(BorderFactory.createEmptyBorder(16, 40, 24, 40));
        completeBottom.add(completeBtnRow);

        JPanel completeView = new JPanel(new BorderLayout());
        completeView.setBackground(completeBg);
        completeView.add(doneHeader,     BorderLayout.NORTH);
        completeView.add(keyWrapper,     BorderLayout.CENTER);
        completeView.add(completeBottom, BorderLayout.SOUTH);

        // ================================================================
        // Assemble inner deck
        // ================================================================
        innerDeck.setBackground(Color.BLACK);
        innerDeck.add(streamView,   VIEW_STREAM);
        innerDeck.add(galleryView,  VIEW_GALLERY);
        innerDeck.add(completeView, VIEW_COMPLETE);

        add(innerDeck, BorderLayout.CENTER);
    }

    // -----------------------------------------------------------------------
    // Lifecycle
    // -----------------------------------------------------------------------

    void activate(TransferSession s) {
        this.session = s;
        this.running = true;

        if (s.isSingleFile()) {
            activateGallery(s);
        } else {
            activateStream(s);
        }
    }

    // -----------------------------------------------------------------------
    // STREAM MODE
    // -----------------------------------------------------------------------

    private void activateStream(TransferSession s) {
        frameCount.set(0);
        loopCount.set(0);
        startTime = System.currentTimeMillis();
        totalPacketsPerLoop = s.getEncoder().getK();
        currentImage = null;
        innerCards.show(innerDeck, VIEW_STREAM);
        startStatsTimer();
        scheduleNextStreamFrame();
    }

    private void startStatsTimer() {
        if (statsTimer != null) statsTimer.stop();
        statsTimer = new Timer(1000, e -> updateStreamStats());
        statsTimer.start();
    }

    /**
     * Self-chaining frame loop.
     * Generates the next QR, paints it, then waits DWELL_MS before generating the next.
     * This guarantees each QR is fully visible for DWELL_MS (≥5 video frames at 15fps)
     * regardless of how long generation or Swing painting takes.
     */
    private void scheduleNextStreamFrame() {
        if (!running || session == null) return;

        FountainPacket packet = session.nextPacket();
        qrExecutor.submit(() -> {
            try {
                BufferedImage img = generator.generateDataQR(packet.serialize());
                SwingUtilities.invokeLater(() -> {
                    currentImage = img;
                    qrPanel.repaint();

                    int fc = frameCount.incrementAndGet();
                    if (totalPacketsPerLoop > 0 && fc % totalPacketsPerLoop == 0) {
                        loopCount.incrementAndGet();
                        onStreamLoopComplete();
                        return; // do not schedule next frame — loop is done
                    }
                    updateStreamStats();

                    // Dwell: hold this QR for DWELL_MS after it is painted, then advance
                    Timer dwell = new Timer(DWELL_MS, evt -> {
                        ((Timer) evt.getSource()).stop();
                        scheduleNextStreamFrame();
                    });
                    dwell.setRepeats(false);
                    dwell.start();
                });
            } catch (Exception ex) {
                // On error, pause briefly and retry to keep the stream alive
                SwingUtilities.invokeLater(() -> {
                    Timer retry = new Timer(DWELL_MS, evt -> {
                        ((Timer) evt.getSource()).stop();
                        scheduleNextStreamFrame();
                    });
                    retry.setRepeats(false);
                    retry.start();
                });
            }
        });
    }

    private void onStreamLoopComplete() {
        if (statsTimer != null) statsTimer.stop();
        currentImage = null;
        qrPanel.repaint();
        updateStreamStats();
        showCompletionView();
    }

    private void restartStream() {
        if (session == null) return;
        frameCount.set(0);
        loopCount.set(0);
        startTime = System.currentTimeMillis();
        currentImage = null;
        qrPanel.repaint();
        running = true;
        innerCards.show(innerDeck, VIEW_STREAM);
        startStatsTimer();
        scheduleNextStreamFrame();
    }

    private void updateStreamStats() {
        int fc       = frameCount.get();
        int lc       = loopCount.get();
        long elapsed = System.currentTimeMillis() - startTime;

        frameLabel.setText("Frame: " + String.format("%,d", fc));
        loopLabel.setText("Loop: " + lc);
        elapsedLabel.setText("Elapsed: " + ProcessingScreen.formatTime(elapsed));

        if (lc == 0 && elapsed > 2000 && fc > 0) {
            double fps = fc / (elapsed / 1000.0);
            if (fps > 0) {
                long remaining = (long) ((totalPacketsPerLoop - fc % totalPacketsPerLoop) / fps * 1000);
                etaLabel.setText("Est 1st loop: ~" + ProcessingScreen.formatTime(elapsed + remaining));
            }
        } else {
            etaLabel.setText(" ");
        }
    }

    // -----------------------------------------------------------------------
    // GALLERY MODE (single-file transfers)
    // -----------------------------------------------------------------------

    /**
     * Pre-generate 2×K fountain packets for reliable decoding and show as a static slideshow.
     * Generation is off-EDT; the gallery is revealed once all frames are ready.
     */
    private void activateGallery(TransferSession s) {
        galleryPackets = null;
        galleryIndex   = 0;
        galleryCurrentImage = null;
        galleryFrameLabel.setText("Generating frames...");
        galleryStatusLabel.setText("Please wait...");
        galleryPrevBtn.setEnabled(false);
        galleryNextBtn.setEnabled(false);
        gallerySaveBtn.setEnabled(false);
        galleryExportBtn.setEnabled(false);
        innerCards.show(innerDeck, VIEW_GALLERY);
        galleryQrPanel.repaint();

        int overdrive = Math.max(2 * s.getEncoder().getK(), 10); // at least 10 frames

        qrExecutor.submit(() -> {
            List<FountainPacket> packets = new ArrayList<>(overdrive);
            for (int i = 0; i < overdrive; i++) {
                packets.add(s.nextPacket());
            }
            SwingUtilities.invokeLater(() -> {
                galleryPackets = packets;
                galleryIndex   = 0;
                int total      = packets.size();
                galleryFrameLabel.setText("Frame 1 / " + total);
                galleryStatusLabel.setText(total + " frames — scan ALL of them to receive the file");
                galleryPrevBtn.setEnabled(false);
                galleryNextBtn.setEnabled(total > 1);
                gallerySaveBtn.setEnabled(true);
                galleryExportBtn.setEnabled(true);
                galleryShowFrame(0);
            });
        });
    }

    private void galleryShowFrame(int index) {
        if (galleryPackets == null || index < 0 || index >= galleryPackets.size()) return;
        FountainPacket packet = galleryPackets.get(index);
        qrExecutor.submit(() -> {
            try {
                BufferedImage img = generator.generateDataQR(packet.serialize());
                SwingUtilities.invokeLater(() -> {
                    galleryCurrentImage = img;
                    galleryQrPanel.repaint();
                    int total = galleryPackets.size();
                    galleryFrameLabel.setText("Frame " + (index + 1) + " / " + total);
                    galleryPrevBtn.setEnabled(index > 0);
                    galleryNextBtn.setEnabled(index < total - 1);
                });
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() ->
                        galleryStatusLabel.setText("Error rendering frame: " + ex.getMessage()));
            }
        });
    }

    private void galleryNavigate(int delta) {
        if (galleryPackets == null) return;
        int next = galleryIndex + delta;
        if (next < 0 || next >= galleryPackets.size()) return;
        galleryIndex = next;
        galleryShowFrame(galleryIndex);
    }

    private void gallerySaveCurrentFrame() {
        if (galleryCurrentImage == null) return;
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Save QR frame as PNG");
        fc.setSelectedFile(new File("qr_frame_" + (galleryIndex + 1) + ".png"));
        fc.setFileFilter(new FileNameExtensionFilter("PNG image", "png"));
        if (fc.showSaveDialog(app) == JFileChooser.APPROVE_OPTION) {
            Path out = fc.getSelectedFile().toPath();
            qrExecutor.submit(() -> {
                try {
                    // Re-generate at full DATA_QR_SIZE for best scan quality
                    BufferedImage fullRes = generator.generateDataQR(
                            galleryPackets.get(galleryIndex).serialize(), QRGenerator.DATA_QR_SIZE);
                    ImageIO.write(fullRes, "PNG", out.toFile());
                    SwingUtilities.invokeLater(() ->
                            galleryStatusLabel.setText("Saved: " + out.getFileName()));
                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() ->
                            app.showError("Could not save frame: " + ex.getMessage()));
                }
            });
        }
    }

    private void galleryExportAll() {
        if (galleryPackets == null) return;
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setDialogTitle("Choose folder to export all QR frames");
        if (fc.showOpenDialog(app) != JFileChooser.APPROVE_OPTION) return;
        Path dir = fc.getSelectedFile().toPath();

        int total = galleryPackets.size();
        galleryExportBtn.setEnabled(false);
        gallerySaveBtn.setEnabled(false);
        galleryStatusLabel.setText("Exporting 0 / " + total + "...");

        qrExecutor.submit(() -> {
            for (int i = 0; i < total; i++) {
                final int idx = i;
                try {
                    BufferedImage img = generator.generateDataQR(
                            galleryPackets.get(i).serialize(), QRGenerator.DATA_QR_SIZE);
                    String name = String.format("qr_%03d.png", i + 1);
                    ImageIO.write(img, "PNG", dir.resolve(name).toFile());
                    SwingUtilities.invokeLater(() ->
                            galleryStatusLabel.setText("Exporting " + (idx + 1) + " / " + total + "..."));
                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() -> {
                        app.showError("Export failed at frame " + (idx + 1) + ": " + ex.getMessage());
                        galleryExportBtn.setEnabled(true);
                        gallerySaveBtn.setEnabled(true);
                    });
                    return;
                }
            }
            SwingUtilities.invokeLater(() -> {
                galleryStatusLabel.setText("Exported all " + total + " frames to: " + dir.getFileName());
                galleryExportBtn.setEnabled(true);
                gallerySaveBtn.setEnabled(true);
            });
        });
    }

    // -----------------------------------------------------------------------
    // COMPLETION VIEW
    // -----------------------------------------------------------------------

    private void showCompletionView() {
        if (session == null) return;
        KeyMaterial km = session.getKeyMaterial();
        String prettyJson = new GsonBuilder().setPrettyPrinting().create().toJson(km);
        keyTextArea.setText(prettyJson);
        keyTextArea.setCaretPosition(0);
        innerCards.show(innerDeck, VIEW_COMPLETE);
    }

    // -----------------------------------------------------------------------
    // Stop / cleanup
    // -----------------------------------------------------------------------

    void stop() {
        running = false;
        if (statsTimer != null) { statsTimer.stop(); statsTimer = null; }
        currentImage = null;
        galleryCurrentImage = null;
        galleryPackets = null;
        session = null;
        qrPanel.repaint();
        galleryQrPanel.repaint();
    }

    private void confirmStop() {
        int confirm = JOptionPane.showConfirmDialog(app,
                "Stop and return to home?", "Stop", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) app.stopStream();
    }

    // -----------------------------------------------------------------------
    // Widget factories
    // -----------------------------------------------------------------------

    private static JLabel styledLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("SansSerif", Font.PLAIN, 11));
        l.setForeground(new Color(0xCC, 0xCC, 0xCC));
        return l;
    }

    private static JButton darkButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(new Color(0x2A, 0x2A, 0x2A));
        btn.setForeground(new Color(0xCC, 0xCC, 0xCC));
        btn.setFont(new Font("SansSerif", Font.PLAIN, 13));
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 24, 10, 24));
        return btn;
    }
}
