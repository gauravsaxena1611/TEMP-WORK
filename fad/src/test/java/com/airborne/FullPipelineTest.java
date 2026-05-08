package com.airborne;

import com.airborne.core.*;
import com.airborne.fountain.FountainPacket;
import com.airborne.fountain.LTDecoder;
import com.airborne.fountain.LTEncoder;
import com.airborne.transport.QRDecoder;
import com.airborne.transport.QRGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * End-to-end pipeline test:
 *   walk → serialize → compress → encrypt → LT encode → QR generate
 *   → QR decode → LT decode → decrypt → decompress → deserialize → folder
 *
 * Source and destination folders are verified SHA-256 identical on all files.
 * Run with 15% simulated packet loss.
 *
 * Uses ~500 KB of test data so K=500 and Robust Soliton works reliably.
 * This test intentionally runs QR encode+decode on every packet; expect ~1 min.
 */
class FullPipelineTest {

    private static final long SEED = 0xDEADBEEFL;

    // -----------------------------------------------------------------------
    // Main integration test
    // -----------------------------------------------------------------------

    @Test
    void fullPipelineWithFifteenPercentLoss(@TempDir Path tmpDir) throws Exception {
        // --- Build source folder (~500 KB, mixed content) ---
        Path srcDir = tmpDir.resolve("source");
        Files.createDirectories(srcDir);
        createTestTree(srcDir);

        // --- Sender side: walk → serialize → compress → encrypt → fountain encode ---
        DirectoryWalker walker = new DirectoryWalker();
        List<FileNode> nodes = walker.walk(srcDir);

        BlobSerializer serializer = new BlobSerializer();
        byte[] blob = serializer.serialize(srcDir, nodes);

        Compressor compressor = new Compressor();
        byte[] compressed = compressor.compress(blob);

        Encryptor encryptor = new Encryptor();
        Encryptor.EncryptionResult encrypted = encryptor.encrypt(compressed);
        KeyMaterial km = encrypted.keyMaterial;
        km.projectId = 12345L;

        LTEncoder encoder = new LTEncoder(encrypted.ciphertext, (int) km.projectId);
        int k = encoder.getK();
        assertTrue(k >= 100, "K should be at least 100 for reliable Robust Soliton (got " + k + ")");

        // --- Generate 2x overhead packets, encode as QR, decode QR back ---
        int totalToGenerate = k * 2;
        QRGenerator qrGenerator = new QRGenerator();
        QRDecoder qrDecoder = new QRDecoder();

        List<FountainPacket> receivedPackets = new ArrayList<>();
        Random lossRng = new Random(SEED);

        for (int i = 0; i < totalToGenerate; i++) {
            FountainPacket pkt = encoder.nextPacket();

            // Simulate 15% packet loss
            if (lossRng.nextDouble() < 0.15) continue;

            // QR encode → decode round-trip
            byte[] serializedPacket = pkt.serialize();
            BufferedImage qrImage = qrGenerator.generateDataQR(serializedPacket);
            byte[] decodedBytes = qrDecoder.decode(qrImage);
            assertNotNull(decodedBytes, "QR decode must not return null for packet " + i);

            FountainPacket recovered = FountainPacket.deserialize(decodedBytes);
            receivedPackets.add(recovered);
        }

        double actualLoss = 1.0 - ((double) receivedPackets.size() / totalToGenerate);
        assertTrue(actualLoss >= 0.10 && actualLoss <= 0.20,
                String.format("Simulated loss should be ~15%% (got %.1f%%)", actualLoss * 100));

        // --- Receiver side: fountain decode ---
        LTDecoder decoder = new LTDecoder(k, encoder.getBlockSize(), encrypted.ciphertext.length);
        for (FountainPacket pkt : receivedPackets) {
            if (decoder.addPacket(pkt)) break;
        }
        assertTrue(decoder.isComplete(),
                "Fountain decoder must complete with ~1.7x effective overhead and 15% simulated loss");

        byte[] reconstructedCiphertext = decoder.getReconstructedData();
        assertArrayEquals(encrypted.ciphertext, reconstructedCiphertext,
                "Reconstructed ciphertext must be byte-for-byte identical");

        // --- Receiver side: decrypt → decompress → deserialize ---
        Decryptor decryptor = new Decryptor();
        byte[] decryptedCompressed = decryptor.decrypt(reconstructedCiphertext, km);

        Decompressor decompressor = new Decompressor();
        byte[] deserializedBlob = decompressor.decompress(decryptedCompressed);

        Path destDir = tmpDir.resolve("destination");
        Files.createDirectories(destDir);

        BlobDeserializer blobDeserializer = new BlobDeserializer();
        Manifest manifest = blobDeserializer.deserialize(deserializedBlob, destDir);

        assertNotNull(manifest);
        assertTrue(manifest.totalFiles > 0, "Manifest must report files");

        // --- Verify: SHA-256 of every source file matches destination ---
        List<FileNode> sourceFiles = new ArrayList<>();
        for (FileNode n : nodes) {
            if (!n.isDirectory) sourceFiles.add(n);
        }

        for (FileNode node : sourceFiles) {
            Path srcFile = srcDir.getParent().resolve(node.relativePath.replace('/', java.io.File.separatorChar));
            Path dstFile = destDir.resolve(node.relativePath.replace('/', java.io.File.separatorChar));
            assertTrue(Files.exists(dstFile),
                    "Destination must contain: " + node.relativePath);
            assertArrayEquals(sha256(Files.readAllBytes(srcFile)),
                    sha256(Files.readAllBytes(dstFile)),
                    "SHA-256 mismatch for: " + node.relativePath);
        }
    }

    // -----------------------------------------------------------------------
    // SHA-256 without QR — fast data-path verification
    // -----------------------------------------------------------------------

    @Test
    void dataPathSha256Identical(@TempDir Path tmpDir) throws Exception {
        Path srcDir = tmpDir.resolve("source");
        Files.createDirectories(srcDir);
        createTestTree(srcDir);

        // walk → serialize → compress → encrypt
        DirectoryWalker walker = new DirectoryWalker();
        List<FileNode> nodes = walker.walk(srcDir);
        BlobSerializer serializer = new BlobSerializer();
        byte[] blob = serializer.serialize(srcDir, nodes);
        Compressor compressor = new Compressor();
        byte[] compressed = compressor.compress(blob);
        Encryptor encryptor = new Encryptor();
        Encryptor.EncryptionResult encrypted = encryptor.encrypt(compressed);
        KeyMaterial km = encrypted.keyMaterial;

        // fountain encode → decode (no QR, 2x overhead, no loss)
        LTEncoder encoder = new LTEncoder(encrypted.ciphertext, 99);
        int k = encoder.getK();
        LTDecoder decoder = new LTDecoder(k, encoder.getBlockSize(), encrypted.ciphertext.length);

        for (int i = 0; i < k * 2; i++) {
            if (decoder.addPacket(encoder.nextPacket())) break;
        }
        assertTrue(decoder.isComplete(), "Fountain decoder must complete with 2x overhead");

        // decrypt → decompress → deserialize
        Decryptor decryptor = new Decryptor();
        byte[] plain = decryptor.decrypt(decoder.getReconstructedData(), km);
        Decompressor decompressor = new Decompressor();
        byte[] finalBlob = decompressor.decompress(plain);

        Path destDir = tmpDir.resolve("dest");
        BlobDeserializer blobDeserializer = new BlobDeserializer();
        blobDeserializer.deserialize(finalBlob, destDir);

        // verify every file
        for (FileNode node : nodes) {
            if (node.isDirectory) continue;
            Path srcFile = srcDir.getParent().resolve(node.relativePath.replace('/', java.io.File.separatorChar));
            Path dstFile = destDir.resolve(node.relativePath.replace('/', java.io.File.separatorChar));
            assertTrue(Files.exists(dstFile), "Missing: " + node.relativePath);
            assertArrayEquals(sha256(Files.readAllBytes(srcFile)),
                    sha256(Files.readAllBytes(dstFile)),
                    "SHA-256 mismatch: " + node.relativePath);
        }
    }

    // -----------------------------------------------------------------------
    // Helpers
    // -----------------------------------------------------------------------

    /**
     * Build a test folder tree totalling ~500 KB of mixed content.
     * Structure:
     *   source/
     *     readme.txt           (text)
     *     data/
     *       large_text.txt     (~200 KB text)
     *       binary_blob.bin    (~200 KB binary)
     *     sub/
     *       nested/
     *         small_a.txt      (1 KB)
     *         small_b.txt      (1 KB)
     *     config/
     *       settings.json      (~2 KB JSON)
     */
    private void createTestTree(Path root) throws Exception {
        Random rng = new Random(SEED);

        // readme.txt — short text
        Files.writeString(root.resolve("readme.txt"),
                "Airborne Copier test transfer\nThis file verifies the pipeline works.\n");

        // data/
        Path data = root.resolve("data");
        Files.createDirectories(data);

        // large_text.txt — ~200 KB of repetitive text
        StringBuilder sb = new StringBuilder(204_800);
        String line = "The quick brown fox jumps over the lazy dog. Pack my box with five dozen liquor jugs.\n";
        while (sb.length() < 200_000) sb.append(line);
        Files.writeString(data.resolve("large_text.txt"), sb);

        // binary_blob.bin — ~200 KB pseudo-random binary
        byte[] binary = new byte[204_800];
        rng.nextBytes(binary);
        Files.write(data.resolve("binary_blob.bin"), binary);

        // sub/nested/
        Path nested = root.resolve("sub").resolve("nested");
        Files.createDirectories(nested);
        byte[] small = new byte[1024];
        rng.nextBytes(small);
        Files.write(nested.resolve("small_a.txt"), small);
        rng.nextBytes(small);
        Files.write(nested.resolve("small_b.txt"), small);

        // config/
        Path config = root.resolve("config");
        Files.createDirectories(config);
        Files.writeString(config.resolve("settings.json"),
                "{\n  \"version\": \"1.0\",\n  \"transfer\": \"beacon\",\n"
                + "  \"encoding\": \"LT+AES256GCM\",\n  \"blockSize\": 1000,\n"
                + "  \"description\": \"Integration test configuration file\"\n}\n");
    }

    private static byte[] sha256(byte[] data) throws Exception {
        return MessageDigest.getInstance("SHA-256").digest(data);
    }
}
