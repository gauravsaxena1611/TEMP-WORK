package com.airborne.ui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Sender screen — browse a single file and start the copy pipeline.
 * Any file type is accepted. The file is transferred byte-for-byte;
 * its name and last-modified timestamp are preserved on the receiver.
 */
class CopyFileScreen extends JPanel {

    private final AirborneApp app;

    private Path selectedFile;
    private final JLabel pathLabel;
    private final JLabel statsLabel;
    private final JButton startBtn;

    CopyFileScreen(AirborneApp app) {
        this.app = app;

        setBackground(AirborneApp.BG);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // ---- Header ----
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(AirborneApp.BG);
        JButton backBtn = AirborneApp.secondaryButton("← Home");
        backBtn.addActionListener(e -> app.goHome());
        JLabel title = new JLabel("Select file to copy");
        title.setFont(AirborneApp.TITLE_FONT);
        title.setForeground(AirborneApp.TEXT);
        header.add(backBtn, BorderLayout.WEST);
        header.add(title, BorderLayout.CENTER);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 24, 0));

        // ---- Browse row ----
        JButton browseBtn = AirborneApp.secondaryButton("Browse...");
        pathLabel = new JLabel("No file selected");
        pathLabel.setFont(AirborneApp.MONO_FONT);
        pathLabel.setForeground(new Color(0x66, 0x66, 0x66));
        browseBtn.addActionListener(e -> browseFile());

        JPanel browseRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        browseRow.setBackground(AirborneApp.BG);
        browseRow.add(browseBtn);
        browseRow.add(pathLabel);

        // ---- Stats ----
        statsLabel = new JLabel(" ");
        statsLabel.setFont(AirborneApp.BODY_FONT);
        statsLabel.setForeground(AirborneApp.TEXT);
        statsLabel.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));

        // ---- Buttons ----
        startBtn = AirborneApp.primaryButton("Start Copy");
        startBtn.setEnabled(false);
        startBtn.addActionListener(e -> {
            if (selectedFile != null) app.startCopyFile(selectedFile);
        });

        JButton cancelBtn = AirborneApp.secondaryButton("Cancel");
        cancelBtn.addActionListener(e -> app.goHome());

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttons.setBackground(AirborneApp.BG);
        buttons.add(startBtn);
        buttons.add(cancelBtn);

        // ---- Assemble ----
        JPanel body = new JPanel();
        body.setBackground(AirborneApp.BG);
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.add(browseRow);
        body.add(statsLabel);
        body.add(Box.createVerticalStrut(8));
        body.add(buttons);

        add(header, BorderLayout.NORTH);
        add(body, BorderLayout.CENTER);
    }

    private void browseFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setAcceptAllFileFilterUsed(true);
        chooser.setDialogTitle("Select file to copy");
        if (chooser.showOpenDialog(app) == JFileChooser.APPROVE_OPTION) {
            selectedFile = chooser.getSelectedFile().toPath();
            pathLabel.setFont(AirborneApp.MONO_FONT);
            pathLabel.setForeground(AirborneApp.TEXT);
            pathLabel.setText(selectedFile.toString());
            showFileStats();
        }
    }

    private void showFileStats() {
        try {
            long bytes = Files.size(selectedFile);
            statsLabel.setText("File size: " + formatSize(bytes));
            startBtn.setEnabled(true);
        } catch (IOException e) {
            statsLabel.setText("Could not read file: " + e.getMessage());
            startBtn.setEnabled(false);
        }
    }

    private static String formatSize(long bytes) {
        if (bytes < 1024) return bytes + " B";
        if (bytes < 1024 * 1024) return String.format("%.1f KB", bytes / 1024.0);
        return String.format("%.2f MB", bytes / (1024.0 * 1024));
    }
}
