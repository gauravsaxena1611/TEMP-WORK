package com.airborne.ui;

import com.airborne.core.DirectoryWalker;
import com.airborne.core.FileNode;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Sender screen — browse a folder and start the copy pipeline.
 */
class CopyScreen extends JPanel {

    private final AirborneApp app;

    private Path selectedFolder;
    private final JLabel pathLabel;
    private final JLabel statsLabel;
    private final JButton startBtn;

    CopyScreen(AirborneApp app) {
        this.app = app;

        setBackground(AirborneApp.BG);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // ---- Header ----
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(AirborneApp.BG);
        JButton backBtn = AirborneApp.secondaryButton("← Home");
        backBtn.addActionListener(e -> app.goHome());
        JLabel title = new JLabel("Select folder to copy");
        title.setFont(AirborneApp.TITLE_FONT);
        title.setForeground(AirborneApp.TEXT);
        header.add(backBtn, BorderLayout.WEST);
        header.add(title, BorderLayout.CENTER);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 24, 0));

        // ---- Browse row ----
        JButton browseBtn = AirborneApp.secondaryButton("Browse...");
        pathLabel = new JLabel("No folder selected");
        pathLabel.setFont(AirborneApp.MONO_FONT);
        pathLabel.setForeground(new Color(0x66, 0x66, 0x66));
        browseBtn.addActionListener(e -> browseFolder());

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
            if (selectedFolder != null) app.startCopy(selectedFolder);
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

    private void browseFolder() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setDialogTitle("Select folder to copy");
        if (chooser.showOpenDialog(app) == JFileChooser.APPROVE_OPTION) {
            selectedFolder = chooser.getSelectedFile().toPath();
            pathLabel.setFont(AirborneApp.MONO_FONT);
            pathLabel.setForeground(AirborneApp.TEXT);
            pathLabel.setText(selectedFolder.toString());
            scanFolder();
        }
    }

    private void scanFolder() {
        statsLabel.setText("Scanning...");
        startBtn.setEnabled(false);
        SwingWorker<String, Void> scanner = new SwingWorker<>() {
            @Override
            protected String doInBackground() throws Exception {
                DirectoryWalker walker = new DirectoryWalker();
                List<FileNode> nodes = walker.walk(selectedFolder);
                int files = 0;
                int dirs = 0;
                long bytes = 0;
                for (FileNode n : nodes) {
                    if (n.isDirectory) dirs++;
                    else { files++; bytes += n.sizeBytes; }
                }
                return String.format("Found:  %d files,  %d directories,  %s",
                        files, dirs, formatSize(bytes));
            }

            @Override
            protected void done() {
                try {
                    statsLabel.setText(get());
                    startBtn.setEnabled(true);
                } catch (Exception e) {
                    statsLabel.setText("Could not read folder: " + e.getMessage());
                }
            }
        };
        scanner.execute();
    }

    private static String formatSize(long bytes) {
        if (bytes < 1024) return bytes + " B";
        if (bytes < 1024 * 1024) return String.format("%.1f KB", bytes / 1024.0);
        return String.format("%.1f MB", bytes / (1024.0 * 1024));
    }
}
