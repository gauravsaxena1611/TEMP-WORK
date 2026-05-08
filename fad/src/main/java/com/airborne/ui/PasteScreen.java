package com.airborne.ui;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Receiver screen — two input modes selectable via radio buttons:
 *
 *   VIDEO mode:  paste the key string, select an OBS video recording (MKV/MP4),
 *                choose destination folder. Used for folder transfers and large files.
 *
 *   IMAGES mode: paste the key string, select the exported PNG folder (produced by the
 *                sender's "Export All Frames" button), choose destination folder.
 *                Used for single-file transfers where the sender exported static QR images.
 */
class PasteScreen extends JPanel {

    private final AirborneApp app;

    private final JTextArea keyField;
    private JPanel keySection; // visible in video mode only

    // -- Video mode --
    private final JLabel videoPathLabel;
    private Path videoPath;

    // -- Images mode --
    private final JLabel imagesDirLabel;
    private Path imagesDirPath;
    private final JPanel videoRow;
    private final JPanel imagesRow;

    // -- Shared --
    private final JLabel destPathLabel;
    private final JButton startBtn;
    private Path destPath;

    private final JRadioButton videoRadio;
    private final JRadioButton imagesRadio;

    PasteScreen(AirborneApp app) {
        this.app = app;
        setBackground(AirborneApp.BG);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(24, 40, 24, 40));

        // ---- Header ----
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(AirborneApp.BG);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        JButton backBtn = AirborneApp.secondaryButton("← Home");
        backBtn.addActionListener(e -> app.goHome());
        JLabel title = new JLabel("Receive a transfer");
        title.setFont(AirborneApp.TITLE_FONT);
        title.setForeground(AirborneApp.TEXT);
        header.add(backBtn, BorderLayout.WEST);
        header.add(title, BorderLayout.CENTER);

        // ---- Mode selector ----
        videoRadio  = new JRadioButton("Video recording (OBS / screen capture)");
        imagesRadio = new JRadioButton("QR image files (exported from sender)");
        videoRadio.setFont(AirborneApp.BODY_FONT);
        imagesRadio.setFont(AirborneApp.BODY_FONT);
        videoRadio.setBackground(AirborneApp.BG);
        imagesRadio.setBackground(AirborneApp.BG);
        videoRadio.setSelected(true);

        ButtonGroup modeGroup = new ButtonGroup();
        modeGroup.add(videoRadio);
        modeGroup.add(imagesRadio);

        videoRadio.addActionListener(e  -> updateMode());
        imagesRadio.addActionListener(e -> updateMode());

        JPanel modePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 0));
        modePanel.setBackground(AirborneApp.BG);
        modePanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0xCC, 0xCC, 0xCC)),
                "Input source"));
        modePanel.add(videoRadio);
        modePanel.add(imagesRadio);

        // ---- Key string (video mode only — images mode reads key.png automatically) ----
        JLabel keyLabel = fieldLabel("Key string (paste text from phone after scanning Key QR):");
        keyField = new JTextArea(3, 40);
        keyField.setFont(new Font("Monospaced", Font.PLAIN, 10));
        keyField.setLineWrap(true);
        keyField.setWrapStyleWord(false);
        keyField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xCC, 0xCC, 0xCC)),
                BorderFactory.createEmptyBorder(4, 6, 4, 6)));
        JScrollPane keyScroll = new JScrollPane(keyField);
        keyScroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        keyScroll.setAlignmentX(LEFT_ALIGNMENT);

        keySection = new JPanel();
        keySection.setBackground(AirborneApp.BG);
        keySection.setLayout(new BoxLayout(keySection, BoxLayout.Y_AXIS));
        keySection.setAlignmentX(LEFT_ALIGNMENT);

        // ---- Video source row ----
        JLabel videoLabel  = fieldLabel("OBS recording (MKV or MP4):");
        videoPathLabel     = pathLabel("No file selected");
        JButton videoBrowse = AirborneApp.secondaryButton("Browse...");
        videoBrowse.addActionListener(e -> browseVideo());
        videoRow = browseRow(videoBrowse, videoPathLabel);

        JPanel videoSection = new JPanel();
        videoSection.setBackground(AirborneApp.BG);
        videoSection.setLayout(new BoxLayout(videoSection, BoxLayout.Y_AXIS));
        videoSection.add(videoLabel);
        videoSection.add(Box.createVerticalStrut(4));
        videoSection.add(videoRow);

        // ---- Images source row ----
        JLabel imagesLabel  = fieldLabel("QR frames folder (use \"Export All Frames\" on sender):");
        imagesDirLabel      = pathLabel("No folder selected");
        JButton imagesBrowse = AirborneApp.secondaryButton("Browse...");
        imagesBrowse.addActionListener(e -> browseImagesDir());
        imagesRow = browseRow(imagesBrowse, imagesDirLabel);

        JPanel imagesSection = new JPanel();
        imagesSection.setBackground(AirborneApp.BG);
        imagesSection.setLayout(new BoxLayout(imagesSection, BoxLayout.Y_AXIS));
        imagesSection.add(imagesLabel);
        imagesSection.add(Box.createVerticalStrut(4));
        imagesSection.add(imagesRow);

        // Wrap both in a CardLayout so only the active one is shown
        CardLayout sourceCards = new CardLayout();
        JPanel sourcePanel = new JPanel(sourceCards);
        sourcePanel.setBackground(AirborneApp.BG);
        sourcePanel.setAlignmentX(LEFT_ALIGNMENT);
        sourcePanel.add(videoSection,  "video");
        sourcePanel.add(imagesSection, "images");
        sourceCards.show(sourcePanel, "video");

        videoRadio.addActionListener(e  -> sourceCards.show(sourcePanel, "video"));
        imagesRadio.addActionListener(e -> sourceCards.show(sourcePanel, "images"));

        // ---- Destination folder ----
        JLabel destLabel   = fieldLabel("Destination folder:");
        destPathLabel      = pathLabel("No folder selected");
        JButton destBrowse = AirborneApp.secondaryButton("Browse...");
        destBrowse.addActionListener(e -> browseDest());
        JPanel destRow = browseRow(destBrowse, destPathLabel);

        // ---- Buttons ----
        startBtn = AirborneApp.primaryButton("Start Paste");
        startBtn.addActionListener(e -> doStart());

        JButton cancelBtn = AirborneApp.secondaryButton("Cancel");
        cancelBtn.addActionListener(e -> app.goHome());

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttons.setBackground(AirborneApp.BG);
        buttons.add(startBtn);
        buttons.add(cancelBtn);

        // ---- Body ----
        JPanel body = new JPanel();
        body.setBackground(AirborneApp.BG);
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));

        keySection.add(keyLabel);
        keySection.add(Box.createVerticalStrut(4));
        keySection.add(keyScroll);

        body.add(modePanel);
        body.add(Box.createVerticalStrut(16));
        body.add(keySection);
        body.add(Box.createVerticalStrut(16));
        body.add(sourcePanel);
        body.add(Box.createVerticalStrut(16));
        body.add(destLabel);
        body.add(Box.createVerticalStrut(4));
        body.add(destRow);
        body.add(Box.createVerticalStrut(24));
        body.add(buttons);

        add(header, BorderLayout.NORTH);
        add(body,   BorderLayout.CENTER);
    }

    private void updateMode() {
        boolean isVideo = videoRadio.isSelected();
        keySection.setVisible(isVideo);
        // Revalidate so the layout shrinks/expands correctly
        revalidate();
        repaint();
    }

    private void browseVideo() {
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new FileNameExtensionFilter("Video files (MKV, MP4)", "mkv", "mp4"));
        fc.setDialogTitle("Select OBS recording");
        if (fc.showOpenDialog(app) == JFileChooser.APPROVE_OPTION) {
            videoPath = fc.getSelectedFile().toPath();
            videoPathLabel.setText(videoPath.toString());
            videoPathLabel.setForeground(AirborneApp.TEXT);
        }
    }

    private void browseImagesDir() {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setDialogTitle("Select folder containing exported QR frames");
        if (fc.showOpenDialog(app) == JFileChooser.APPROVE_OPTION) {
            imagesDirPath = fc.getSelectedFile().toPath();
            imagesDirLabel.setText(imagesDirPath.toString());
            imagesDirLabel.setForeground(AirborneApp.TEXT);
        }
    }

    private void browseDest() {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setDialogTitle("Select destination folder");
        if (fc.showOpenDialog(app) == JFileChooser.APPROVE_OPTION) {
            destPath = fc.getSelectedFile().toPath();
            destPathLabel.setText(destPath.toString());
            destPathLabel.setForeground(AirborneApp.TEXT);
        }
    }

    private static final String[] IMAGE_EXTS = {"png", "jpg", "jpeg", "bmp", "gif", "webp", "tiff", "tif"};

    private void doStart() {
        if (destPath == null) {
            app.showError("Please select a destination folder.");
            return;
        }

        if (videoRadio.isSelected()) {
            String keyJson = keyField.getText().trim();
            if (keyJson.isEmpty()) {
                app.showError("Please paste the key string.");
                return;
            }
            if (videoPath == null) {
                app.showError("Please select the OBS recording video file.");
                return;
            }
            app.startPaste(keyJson, videoPath, destPath);

        } else {
            if (imagesDirPath == null) {
                app.showError("Please select the folder containing the exported QR frames.");
                return;
            }
            // Collect all image files sorted by name — includes any key QR screenshot
            File[] imageFiles = imagesDirPath.toFile().listFiles(f -> {
                if (!f.isFile()) return false;
                String name = f.getName().toLowerCase();
                for (String ext : IMAGE_EXTS) {
                    if (name.endsWith("." + ext)) return true;
                }
                return false;
            });
            if (imageFiles == null || imageFiles.length == 0) {
                app.showError("No image files found in the selected folder.");
                return;
            }
            java.util.Arrays.sort(imageFiles, java.util.Comparator.comparing(File::getName));
            List<Path> imagePaths = new ArrayList<>();
            for (File f : imageFiles) imagePaths.add(f.toPath());
            app.startPasteImages(imagePaths, destPath);
        }
    }

    // -----------------------------------------------------------------------
    // Helpers
    // -----------------------------------------------------------------------

    private static JLabel fieldLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(AirborneApp.BODY_FONT);
        l.setForeground(AirborneApp.TEXT);
        l.setAlignmentX(LEFT_ALIGNMENT);
        return l;
    }

    private static JLabel pathLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(AirborneApp.MONO_FONT);
        l.setForeground(new Color(0x88, 0x88, 0x88));
        return l;
    }

    private static JPanel browseRow(JButton btn, JLabel path) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        row.setBackground(AirborneApp.BG);
        row.setAlignmentX(LEFT_ALIGNMENT);
        row.add(btn);
        row.add(path);
        return row;
    }
}
