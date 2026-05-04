package com.airborne.ui;

import com.airborne.transport.TransferSession;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.util.List;

/**
 * Main application window. Single JFrame with CardLayout — screens are swapped,
 * never recreated. All navigation goes through this class.
 */
public class AirborneApp extends JFrame {

    // -----------------------------------------------------------------------
    // Shared style constants (referenced by all screens)
    // -----------------------------------------------------------------------
    static final Color BG       = Color.WHITE;
    static final Color TEXT     = new Color(0x33, 0x33, 0x33);
    static final Color BLUE     = new Color(0x15, 0x65, 0xC0);
    static final Color BLUE_HOV = new Color(0x0D, 0x47, 0xA1);

    static final Font TITLE_FONT = new Font("SansSerif", Font.BOLD, 18);
    static final Font BODY_FONT  = new Font("SansSerif", Font.PLAIN, 12);
    static final Font MONO_FONT  = new Font("Monospaced", Font.PLAIN, 12);
    static final Font SMALL_FONT = new Font("SansSerif", Font.PLAIN, 11);

    // -----------------------------------------------------------------------
    // Screen names
    // -----------------------------------------------------------------------
    private static final String HOME       = "home";
    private static final String COPY       = "copy";
    private static final String COPY_FILE  = "copy_file";
    private static final String PROCESSING = "processing";
    private static final String STREAM     = "stream";
    private static final String PASTE      = "paste";
    private static final String DONE       = "done";

    // -----------------------------------------------------------------------
    // Layout
    // -----------------------------------------------------------------------
    private final CardLayout   cards = new CardLayout();
    private final JPanel       deck  = new JPanel(cards);

    // Screens (created once, reused)
    private final HomeScreen       homeScreen;
    private final CopyScreen       copyScreen;
    private final CopyFileScreen   copyFileScreen;
    private final ProcessingScreen processingScreen;
    private final DataStreamScreen dataStreamScreen;
    private final PasteScreen      pasteScreen;
    private final DoneScreen       doneScreen;

    // Shared transfer state
    TransferSession activeSession;

    // -----------------------------------------------------------------------

    public AirborneApp() {
        homeScreen       = new HomeScreen(this);
        copyScreen       = new CopyScreen(this);
        copyFileScreen   = new CopyFileScreen(this);
        processingScreen = new ProcessingScreen(this);
        dataStreamScreen = new DataStreamScreen(this);
        pasteScreen      = new PasteScreen(this);
        doneScreen       = new DoneScreen(this);

        deck.add(homeScreen,       HOME);
        deck.add(copyScreen,       COPY);
        deck.add(copyFileScreen,   COPY_FILE);
        deck.add(processingScreen, PROCESSING);
        deck.add(dataStreamScreen, STREAM);
        deck.add(pasteScreen,      PASTE);
        deck.add(doneScreen,       DONE);

        setContentPane(deck);
        setTitle("Airborne Copier  v1.0.0");
        setMinimumSize(new Dimension(600, 500));
        setPreferredSize(new Dimension(820, 680));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        show(HOME);
    }

    // -----------------------------------------------------------------------
    // Navigation — called by screens
    // -----------------------------------------------------------------------

    void show(String name) {
        cards.show(deck, name);
    }

    // -- Copy flow --

    void goCopy()     { show(COPY); }
    void goCopyFile() { show(COPY_FILE); }
    void goPaste()    { show(PASTE); }

    void startCopy(Path folder) {
        processingScreen.beginCopy(folder);
        show(PROCESSING);
    }

    void startCopyFile(Path filePath) {
        processingScreen.beginCopyFile(filePath);
        show(PROCESSING);
    }

    void onCopyDone(TransferSession session) {
        this.activeSession = session;
        dataStreamScreen.activate(session);
        show(STREAM);
    }

    void stopStream() {
        dataStreamScreen.stop();
        show(HOME);
    }

    // -- Paste flow --

    void startPaste(String keyJson, Path video, Path dest) {
        processingScreen.beginPaste(keyJson, video, dest);
        show(PROCESSING);
    }

    void startPasteImages(String keyJson, List<Path> imagePaths, Path dest) {
        processingScreen.beginPasteImages(keyJson, imagePaths, dest);
        show(PROCESSING);
    }

    void onPasteDone(int files, int dirs, long bytes, long elapsedMs, Path location) {
        doneScreen.setResults(files, dirs, bytes, elapsedMs, location);
        show(DONE);
    }

    void goHome() { show(HOME); }

    // -----------------------------------------------------------------------
    // Error display — always on EDT
    // -----------------------------------------------------------------------

    void showError(String message) {
        SwingUtilities.invokeLater(() ->
                JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE));
    }

    // -----------------------------------------------------------------------
    // Helpers used by screens
    // -----------------------------------------------------------------------

    /** Create a styled primary button (blue). */
    static JButton primaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(BLUE);
        btn.setForeground(Color.WHITE);
        btn.setFont(BODY_FONT);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        return btn;
    }

    /** Create a styled secondary button (outlined). */
    static JButton secondaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(BG);
        btn.setForeground(TEXT);
        btn.setFont(BODY_FONT);
        btn.setOpaque(true);
        btn.setBorderPainted(true);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xCC, 0xCC, 0xCC), 1),
                BorderFactory.createEmptyBorder(7, 18, 7, 18)));
        return btn;
    }

    /** Panel with white background and standard padding. */
    static JPanel padded(JPanel panel, int top, int left, int bottom, int right) {
        panel.setBackground(BG);
        panel.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
        return panel;
    }

    // -----------------------------------------------------------------------
    // Launch
    // -----------------------------------------------------------------------

    public static void launch() {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            new AirborneApp().setVisible(true);
        });
    }
}
