package com.airborne.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.nio.file.Path;

/**
 * Transfer complete screen — shows summary stats and a Done button.
 */
class DoneScreen extends JPanel {

    private final AirborneApp app;

    private final JLabel filesLabel;
    private final JLabel dirsLabel;
    private final JLabel sizeLabel;
    private final JLabel timeLabel;
    private final JLabel locationLabel;

    DoneScreen(AirborneApp app) {
        this.app = app;
        setBackground(AirborneApp.BG);
        setLayout(new GridBagLayout());

        JPanel content = new JPanel();
        content.setBackground(AirborneApp.BG);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(BorderFactory.createEmptyBorder(0, 60, 0, 60));

        // Success header
        JLabel check = new JLabel("✓  Transfer Complete");
        check.setFont(new Font("SansSerif", Font.BOLD, 22));
        check.setForeground(new Color(0x2E, 0x7D, 0x32)); // green
        check.setAlignmentX(LEFT_ALIGNMENT);

        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

        // Stats
        filesLabel    = statLine("Files restored:");
        dirsLabel     = statLine("Directories:");
        sizeLabel     = statLine("Total size:");
        timeLabel     = statLine("Time taken:");
        locationLabel = statLine("Location:");
        locationLabel.setFont(AirborneApp.MONO_FONT);

        // Copy-path button
        JButton copyPathBtn = AirborneApp.secondaryButton("Copy path");
        copyPathBtn.addActionListener(e -> {
            String path = locationLabel.getText();
            Toolkit.getDefaultToolkit().getSystemClipboard()
                    .setContents(new StringSelection(path), null);
        });
        copyPathBtn.setAlignmentX(LEFT_ALIGNMENT);

        // Done button
        JButton doneBtn = AirborneApp.primaryButton("Done");
        doneBtn.addActionListener(e -> app.goHome());
        doneBtn.setAlignmentX(LEFT_ALIGNMENT);

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        btnRow.setBackground(AirborneApp.BG);
        btnRow.setAlignmentX(LEFT_ALIGNMENT);
        btnRow.add(doneBtn);
        btnRow.add(copyPathBtn);

        content.add(check);
        content.add(Box.createVerticalStrut(16));
        content.add(sep);
        content.add(Box.createVerticalStrut(16));
        content.add(filesLabel);
        content.add(Box.createVerticalStrut(4));
        content.add(dirsLabel);
        content.add(Box.createVerticalStrut(4));
        content.add(sizeLabel);
        content.add(Box.createVerticalStrut(4));
        content.add(timeLabel);
        content.add(Box.createVerticalStrut(12));
        content.add(new JLabel("Location:") {{ setFont(AirborneApp.BODY_FONT); setForeground(AirborneApp.TEXT); setAlignmentX(LEFT_ALIGNMENT); }});
        content.add(Box.createVerticalStrut(2));
        content.add(locationLabel);
        content.add(Box.createVerticalStrut(24));
        content.add(btnRow);

        add(content);
    }

    void setResults(int files, int dirs, long bytes, long elapsedMs, Path location) {
        filesLabel.setText("Files restored:   " + files);
        dirsLabel.setText("Directories:      " + dirs);
        sizeLabel.setText("Total size:       " + formatSize(bytes));
        timeLabel.setText("Time taken:       " + ProcessingScreen.formatTime(elapsedMs));
        locationLabel.setText(location.toAbsolutePath().toString());
    }

    private static JLabel statLine(String text) {
        JLabel l = new JLabel(text);
        l.setFont(AirborneApp.BODY_FONT);
        l.setForeground(AirborneApp.TEXT);
        l.setAlignmentX(LEFT_ALIGNMENT);
        return l;
    }

    private static String formatSize(long bytes) {
        if (bytes < 1024 * 1024) return String.format("%.1f KB", bytes / 1024.0);
        return String.format("%.2f MB", bytes / (1024.0 * 1024));
    }
}
