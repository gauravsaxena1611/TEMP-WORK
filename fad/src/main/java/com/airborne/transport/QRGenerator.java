package com.airborne.transport;

import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.image.BufferedImage;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Generates QR code images from binary data or text strings.
 *
 * Two modes:
 *   - Data QR (800x800): encodes a serialized FountainPacket as Base64; used for the animated stream.
 *   - Key QR  (512x512): encodes the KeyMaterial JSON string; displayed once for phone scan.
 */
public class QRGenerator {

    /** Pixel size for animated data stream QR codes.
     *  A 1012-byte packet Base64-encodes to ~1352 chars, requiring QR Version 38
     *  (193×193 modules). At 1024px each module is ~5.3px — comfortably decodable
     *  from lossless OBS capture and ZXing software decoding. */
    public static final int DATA_QR_SIZE = 1024;

    /** Pixel size for the static key QR code. */
    public static final int KEY_QR_SIZE = 512;

    private final QRCodeWriter writer = new QRCodeWriter();

    /**
     * Encode binary packet bytes into an 800x800 QR image.
     * The bytes are Base64-encoded to produce a string payload.
     *
     * @param packetBytes Serialized FountainPacket bytes.
     * @return BufferedImage containing the QR code.
     */
    public BufferedImage generateDataQR(byte[] packetBytes) throws WriterException {
        String encoded = Base64.getEncoder().encodeToString(packetBytes);
        return generate(encoded, DATA_QR_SIZE, "ISO-8859-1");
    }

    /**
     * Encode binary packet bytes into a QR image at a custom pixel size.
     */
    public BufferedImage generateDataQR(byte[] packetBytes, int sizePixels) throws WriterException {
        String encoded = Base64.getEncoder().encodeToString(packetBytes);
        return generate(encoded, sizePixels, "ISO-8859-1");
    }

    /**
     * Encode a JSON string (KeyMaterial) into a 512x512 QR image.
     *
     * @param jsonString The KeyMaterial.toJson() output.
     * @return BufferedImage containing the QR code.
     */
    public BufferedImage generateKeyQR(String jsonString) throws WriterException {
        return generate(jsonString, KEY_QR_SIZE, "UTF-8");
    }

    // -----------------------------------------------------------------------

    private BufferedImage generate(String content, int sizePixels, String charset)
            throws WriterException {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.CHARACTER_SET, charset);
        hints.put(EncodeHintType.MARGIN, 2);  // 2-module quiet zone; PURE_BARCODE decode doesn't need finder patterns

        BitMatrix matrix = writer.encode(
                content, BarcodeFormat.QR_CODE, sizePixels, sizePixels, hints);
        return MatrixToImageWriter.toBufferedImage(matrix);
    }
}
