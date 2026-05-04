package com.airborne.transport;

import com.airborne.fountain.FountainPacket;
import com.airborne.fountain.LTDecoder;
import com.airborne.fountain.LTEncoder;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class QRGeneratorDecoderTest {

    private final QRGenerator generator = new QRGenerator();
    private final QRDecoder decoder = new QRDecoder();

    // -----------------------------------------------------------------------
    // Round-trip: encode bytes → QR image → decode bytes
    // -----------------------------------------------------------------------

    @Test
    void roundTripArbitraryBytes() throws Exception {
        byte[] original = randomBytes(512);

        BufferedImage qr = generator.generateDataQR(original);
        assertNotNull(qr, "QR image must not be null");

        byte[] decoded = decoder.decode(qr);
        assertNotNull(decoded, "Decoded bytes must not be null");
        assertArrayEquals(original, decoded, "Decoded bytes must match original");
    }

    @Test
    void roundTripFountainPacket() throws Exception {
        byte[] data = randomBytes(10_000);
        LTEncoder enc = new LTEncoder(data, 42, 1000);
        FountainPacket packet = enc.nextPacket();

        byte[] serialized = packet.serialize();
        BufferedImage qr = generator.generateDataQR(serialized);
        byte[] decoded = decoder.decode(qr);

        assertNotNull(decoded);
        FountainPacket restored = FountainPacket.deserialize(decoded);
        assertEquals(packet.projectId, restored.projectId);
        assertEquals(packet.totalBlocks, restored.totalBlocks);
        assertEquals(packet.seed, restored.seed);
        assertArrayEquals(packet.payload, restored.payload);
    }

    @Test
    void roundTrip100Packets() throws Exception {
        byte[] data = randomBytes(50_000);
        LTEncoder enc = new LTEncoder(data, 99, 1000);

        int failCount = 0;
        for (int i = 0; i < 100; i++) {
            byte[] serialized = enc.nextPacket().serialize();
            BufferedImage qr = generator.generateDataQR(serialized);
            byte[] decoded = decoder.decode(qr);
            if (decoded == null) failCount++;
        }
        assertEquals(0, failCount, "All 100 QR codes must decode successfully");
    }

    @Test
    void keyQrRoundTrip() throws Exception {
        String json = "{\"version\":\"1.0\",\"key\":\"dGVzdGtleQ==\",\"iv\":\"dGVzdGl2\","
                + "\"encryptedHash\":\"abc123\",\"totalBlocks\":500}";

        BufferedImage qr = generator.generateKeyQR(json);
        assertNotNull(qr);

        String decoded = decoder.decodeText(qr);
        assertNotNull(decoded, "Key QR text must decode");
        assertEquals(json, decoded, "Key QR text must match original JSON");
    }

    // -----------------------------------------------------------------------
    // Graceful null on undecodable images
    // -----------------------------------------------------------------------

    @Test
    void blankWhiteImageReturnsNull() {
        BufferedImage blank = new BufferedImage(800, 800, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = blank.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 800, 800);
        g.dispose();

        assertNull(decoder.decode(blank), "Blank image must return null (no crash)");
    }

    @Test
    void blankBlackImageReturnsNull() {
        BufferedImage black = new BufferedImage(800, 800, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = black.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 800, 800);
        g.dispose();

        assertNull(decoder.decode(black), "Black image must return null (no crash)");
    }

    @Test
    void randomNoiseImageReturnsNull() {
        BufferedImage noise = new BufferedImage(800, 800, BufferedImage.TYPE_INT_RGB);
        Random rng = new Random(42);
        for (int y = 0; y < 800; y++) {
            for (int x = 0; x < 800; x++) {
                noise.setRGB(x, y, rng.nextInt(0xFFFFFF));
            }
        }
        assertNull(decoder.decode(noise), "Random noise image must return null (no crash)");
    }

    @Test
    void nullImageReturnsNull() {
        assertNull(decoder.decode(null), "Null image must return null (no crash)");
    }

    // -----------------------------------------------------------------------
    // Full Phase 3 pipeline: fountain encode → QR → QR decode → fountain decode
    // -----------------------------------------------------------------------

    @Test
    void fullQrFountainPipeline() throws Exception {
        // Use 50KB data with 4x overhead so soliton works for K=50
        byte[] original = randomBytes(50_000);
        int projectId = 7;
        LTEncoder enc = new LTEncoder(original, projectId, 1000);
        int k = enc.getK();  // 50

        // Generate 4x K packets, encode each as QR, decode QR, feed to fountain decoder
        int totalPackets = k * 4;
        LTDecoder dec = new LTDecoder(k, enc.getBlockSize(), original.length);
        List<byte[]> decoded = new ArrayList<>();

        for (int i = 0; i < totalPackets; i++) {
            byte[] serialized = enc.nextPacket().serialize();
            BufferedImage qr = generator.generateDataQR(serialized);
            byte[] qrDecoded = decoder.decode(qr);
            assertNotNull(qrDecoded, "QR decode must not return null for frame " + i);
            decoded.add(qrDecoded);
        }

        // Feed all packets to the fountain decoder
        for (byte[] packetBytes : decoded) {
            FountainPacket pkt = FountainPacket.deserialize(packetBytes);
            assertEquals(projectId, pkt.projectId, "projectId must survive QR round-trip");
            if (dec.addPacket(pkt)) break;
        }

        assertTrue(dec.isComplete(),
                "Fountain decoder must complete with 4x packet overhead via QR round-trip");
        assertArrayEquals(original, dec.getReconstructedData(),
                "Reconstructed data must be byte-for-byte identical to original");
    }

    // -----------------------------------------------------------------------
    // Helpers
    // -----------------------------------------------------------------------

    private byte[] randomBytes(int len) {
        byte[] buf = new byte[len];
        new Random(55555).nextBytes(buf);
        return buf;
    }
}
