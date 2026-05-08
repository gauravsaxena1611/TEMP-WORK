package com.airborne.transport;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for VideoFrameExtractor.
 *
 * Full end-to-end video processing (OBS recording → frame extraction → QR decode)
 * is covered in Phase 5 manual system testing with a real video file.
 */
class VideoFrameExtractorTest {

    private final VideoFrameExtractor extractor = new VideoFrameExtractor();

    @Test
    void nonExistentFileThrowsIOException(@TempDir Path tmp) {
        Path missing = tmp.resolve("nonexistent.mkv");
        assertThrows(IOException.class,
                () -> extractor.extractFrames(missing, (img, f, t) -> true),
                "Missing video file must throw IOException");
    }

    @Test
    void countFramesOnNonExistentFileThrowsIOException(@TempDir Path tmp) {
        Path missing = tmp.resolve("missing.mp4");
        assertThrows(IOException.class,
                () -> extractor.countFrames(missing),
                "Missing video file must throw IOException on countFrames");
    }
}
