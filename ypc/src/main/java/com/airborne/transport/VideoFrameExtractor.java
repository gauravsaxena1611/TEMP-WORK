package com.airborne.transport;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Extracts every video frame from an OBS-recorded MKV or MP4 file and delivers
 * them as BufferedImages via a callback.
 *
 * Duplicates and out-of-order frames are harmless — the fountain decoder deduplicates.
 */
public class VideoFrameExtractor {

    /**
     * Callback invoked for each extracted frame.
     */
    public interface FrameCallback {
        /**
         * @param image    The video frame.
         * @param frameNum 0-based frame counter (includes skipped/null frames).
         * @param total    Estimated total video frames, or -1 if unknown.
         * @return true to continue extraction, false to stop early.
         */
        boolean onFrame(BufferedImage image, int frameNum, int total);
    }

    /**
     * Extract all frames from a video file and deliver them to the callback.
     *
     * @param videoPath Path to the MKV or MP4 file.
     * @param callback  Receives each frame in sequence.
     * @throws IOException If the file cannot be opened or read.
     */
    public void extractFrames(Path videoPath, FrameCallback callback) throws IOException {
        if (!videoPath.toFile().exists()) {
            throw new IOException("Video file not found: " + videoPath);
        }

        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoPath.toFile());
        try {
            grabber.start();
            int total = grabber.getLengthInFrames();
            Java2DFrameConverter converter = new Java2DFrameConverter();

            Frame frame;
            int frameNum = 0;
            while ((frame = grabber.grabImage()) != null) {
                BufferedImage image = converter.convert(frame);
                if (image != null) {
                    boolean keepGoing = callback.onFrame(image, frameNum, total);
                    if (!keepGoing) break;
                }
                frameNum++;
            }
        } catch (org.bytedeco.javacv.FrameGrabber.Exception e) {
            throw new IOException("Failed to read video: " + videoPath.getFileName() + " — " + e.getMessage(), e);
        } finally {
            try { grabber.stop(); } catch (Exception ignored) {}
            try { grabber.release(); } catch (Exception ignored) {}
        }
    }

    /**
     * Count total frames in a video file without processing them.
     * Useful for progress reporting setup.
     *
     * @param videoPath Path to the video file.
     * @return Estimated frame count, or -1 if unavailable.
     * @throws IOException If the file cannot be opened.
     */
    public int countFrames(Path videoPath) throws IOException {
        if (!videoPath.toFile().exists()) {
            throw new IOException("Video file not found: " + videoPath);
        }
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoPath.toFile());
        try {
            grabber.start();
            return grabber.getLengthInFrames();
        } catch (org.bytedeco.javacv.FrameGrabber.Exception e) {
            return -1;
        } finally {
            try { grabber.stop(); } catch (Exception ignored) {}
            try { grabber.release(); } catch (Exception ignored) {}
        }
    }
}
