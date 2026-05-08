package com.airborne;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Minimal file-based logger for Airborne Copier.
 *
 * Writes timestamped lines to {@code airborne.log} in the working directory.
 * Thread-safe via synchronized writes. Falls back to stderr if the log file
 * cannot be written (e.g. read-only VDI filesystem).
 *
 * Usage:
 * <pre>
 *   AirborneLogger.info("Transfer started");
 *   AirborneLogger.error("Failed to read file", exception);
 * </pre>
 */
public class AirborneLogger {

    private static final Path LOG_FILE = Paths.get("airborne.log");

    private static final DateTimeFormatter FMT = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(ZoneId.systemDefault());

    private AirborneLogger() {}

    public static void info(String message) {
        write("INFO ", message, null);
    }

    public static void warn(String message) {
        write("WARN ", message, null);
    }

    public static void error(String message) {
        write("ERROR", message, null);
    }

    public static void error(String message, Throwable t) {
        write("ERROR", message, t);
    }

    public static void debug(String message) {
        write("DEBUG", message, null);
    }

    // -----------------------------------------------------------------------

    private static synchronized void write(String level, String message, Throwable t) {
        String line = FMT.format(Instant.now()) + " [" + level + "] " + message;

        if (t != null) {
            StringWriter sw = new StringWriter();
            t.printStackTrace(new PrintWriter(sw));
            line = line + "\n" + sw;
        }

        try {
            Files.writeString(LOG_FILE, line + "\n",
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            // Can't write log — fall back to stderr so nothing is silently lost
            System.err.println("[AIRBORNE-LOG-FALLBACK] " + line);
        }
    }
}
