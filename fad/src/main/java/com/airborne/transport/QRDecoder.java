package com.airborne.transport;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import java.awt.image.BufferedImage;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Decodes a QR code from a BufferedImage (a single video frame).
 *
 * Returns null silently on any failure — unreadable/blank/noisy frames
 * are expected and handled gracefully.
 */
public class QRDecoder {

    private static final Map<DecodeHintType, Object> HINTS = new HashMap<>();

    static {
        // TRY_HARDER: use more time for detection (important for video frames)
        HINTS.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
        // PURE_BARCODE: the QR fills the frame — skip finder-pattern search, read bits directly.
        // This is the correct mode for OBS captures where the QR code dominates the frame.
        HINTS.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);
    }

    /**
     * Attempt to decode a QR code from the image and Base64-decode the payload.
     *
     * @param image A video frame (may contain a QR code or nothing).
     * @return Decoded byte array, or null if no valid QR code was found.
     */
    public byte[] decode(BufferedImage image) {
        String text = decodeText(image);
        if (text == null) return null;
        try {
            return Base64.getDecoder().decode(text);
        } catch (IllegalArgumentException e) {
            // Not valid Base64 — probably not a data packet QR
            return null;
        }
    }

    /**
     * Attempt to decode a QR code as a raw text string (for Key QR / JSON payloads).
     *
     * @param image A video frame or captured screen image.
     * @return Decoded text, or null if no valid QR code was found.
     */
    public String decodeText(BufferedImage image) {
        if (image == null) return null;

        // A fresh reader per call avoids stale internal state between attempts.
        QRCodeReader reader = new QRCodeReader();

        // Primary attempt: PURE_BARCODE mode (fast; works when QR fills the frame)
        String text = tryDecode(reader, image);
        if (text != null) return text;

        // Secondary attempt: inverted colors (handles dark backgrounds)
        text = tryDecodeInverted(reader, image);
        if (text != null) return text;

        // Tertiary attempt: normal detection without PURE_BARCODE (for screenshots
        // where the QR code is surrounded by UI elements and doesn't fill the image)
        return tryDecodeRelaxed(reader, image);
    }

    // -----------------------------------------------------------------------

    private static String tryDecode(QRCodeReader reader, BufferedImage image) {
        try {
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            return reader.decode(bitmap, HINTS).getText();
        } catch (NotFoundException | ChecksumException | FormatException e) {
            return null;
        }
    }

    private static String tryDecodeInverted(QRCodeReader reader, BufferedImage image) {
        try {
            LuminanceSource source = new InvertedLuminanceSource(
                    new BufferedImageLuminanceSource(image));
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            return reader.decode(bitmap, HINTS).getText();
        } catch (NotFoundException | ChecksumException | FormatException e) {
            return null;
        }
    }

    private static final Map<DecodeHintType, Object> HINTS_RELAXED = new HashMap<>();
    static {
        HINTS_RELAXED.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
        // No PURE_BARCODE — lets ZXing locate the QR within a larger screenshot image
    }

    private static String tryDecodeRelaxed(QRCodeReader reader, BufferedImage image) {
        try {
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            return reader.decode(bitmap, HINTS_RELAXED).getText();
        } catch (NotFoundException | ChecksumException | FormatException e) {
            return null;
        }
    }
}
