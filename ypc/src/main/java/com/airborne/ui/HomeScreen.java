package com.airborne.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Home screen — two large mode-selection buttons.
 *
 *  ┌──────────────────────────────┐
 *  │      PROJECT BEACON          │
 *  │  Air-gap file transfer       │
 *  │                              │
 *  │  [  Copy a folder  ]         │
 *  │  [  Paste a folder ]         │
 *  │                              │
 *  │  v1.0.0                      │
 *  └──────────────────────────────┘
 */
class HomeScreen extends JPanel {

    HomeScreen(AirborneApp app) {
        setBackground(AirborneApp.BG);
        setLayout(new GridBagLayout());

        JPanel content = new JPanel();
        content.setBackground(AirborneApp.BG);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        // Title
        JLabel title = new JLabel("PROJECT BEACON");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(AirborneApp.TEXT);
        title.setAlignmentX(CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Air-gap file transfer via QR codes");
        subtitle.setFont(AirborneApp.BODY_FONT);
        subtitle.setForeground(new Color(0x66, 0x66, 0x66));
        subtitle.setAlignmentX(CENTER_ALIGNMENT);

        // Section label — Send
        JLabel sendLabel = new JLabel("SEND");
        sendLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
        sendLabel.setForeground(new Color(0x99, 0x99, 0x99));
        sendLabel.setAlignmentX(CENTER_ALIGNMENT);

        // Mode buttons (large)
        JButton copyBtn = AirborneApp.primaryButton("  Copy a folder  →  ");
        copyBtn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        copyBtn.setAlignmentX(CENTER_ALIGNMENT);
        copyBtn.setMaximumSize(new Dimension(260, 50));
        copyBtn.addActionListener(e -> app.goCopy());

        JButton copyFileBtn = AirborneApp.primaryButton("  Copy a file  →  ");
        copyFileBtn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        copyFileBtn.setAlignmentX(CENTER_ALIGNMENT);
        copyFileBtn.setMaximumSize(new Dimension(260, 50));
        copyFileBtn.addActionListener(e -> app.goCopyFile());

        // Section label — Receive
        JLabel receiveLabel = new JLabel("RECEIVE");
        receiveLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
        receiveLabel.setForeground(new Color(0x99, 0x99, 0x99));
        receiveLabel.setAlignmentX(CENTER_ALIGNMENT);

        JButton pasteBtn = AirborneApp.secondaryButton("  Paste  ←  ");
        pasteBtn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        pasteBtn.setAlignmentX(CENTER_ALIGNMENT);
        pasteBtn.setMaximumSize(new Dimension(260, 50));
        pasteBtn.addActionListener(e -> app.goPaste());

        JLabel version = new JLabel("v1.0.0");
        version.setFont(AirborneApp.SMALL_FONT);
        version.setForeground(new Color(0x99, 0x99, 0x99));
        version.setAlignmentX(CENTER_ALIGNMENT);

        content.add(Box.createVerticalStrut(10));
        content.add(title);
        content.add(Box.createVerticalStrut(8));
        content.add(subtitle);
        content.add(Box.createVerticalStrut(32));
        content.add(sendLabel);
        content.add(Box.createVerticalStrut(10));
        content.add(copyBtn);
        content.add(Box.createVerticalStrut(10));
        content.add(copyFileBtn);
        content.add(Box.createVerticalStrut(24));
        content.add(receiveLabel);
        content.add(Box.createVerticalStrut(10));
        content.add(pasteBtn);
        content.add(Box.createVerticalStrut(40));
        content.add(version);

        add(content);
    }
}
