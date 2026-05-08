package com.airborne;

import com.airborne.core.*;
import com.airborne.fountain.LTDecoder;
import com.airborne.fountain.LTEncoder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Edge-case and robustness tests for Airborne Copier.
 *
 * Note on paths: DirectoryWalker produces paths relative to the root's parent,
 * so the root folder name is always part of relative paths. After deserialization
 * to dest, files appear at dest/{srcDirName}/...
 *
 * Covers:
 *   - Empty folder
 *   - Binary files (all 256 byte values, null-heavy files)
 *   - Deep nesting (10 levels)
 *   - Many small files
 *   - Destination already exists (should overwrite cleanly)
 *   - Wrong key string (should throw on decrypt)
 *   - Unicode filenames (skipped on Windows)
 *   - SHA-256 integrity after full data pipeline
 */
class Phase5EdgeCaseTest {

    // -----------------------------------------------------------------------
    // Empty folder
    // -----------------------------------------------------------------------

    @Test
    void emptyFolderRoundTrip(@TempDir Path tmpDir) throws Exception {
        Path src = tmpDir.resolve("empty_src");
        Files.createDirectories(src);

        DirectoryWalker walker = new DirectoryWalker();
        List<FileNode> nodes = walker.walk(src);

        BlobSerializer serializer = new BlobSerializer();
        byte[] blob = serializer.serialize(src, nodes);

        Path dest = tmpDir.resolve("empty_dest");
        BlobDeserializer deserializer = new BlobDeserializer();
        Manifest m = deserializer.deserialize(blob, dest);

        assertNotNull(m);
        assertEquals(0, m.totalFiles, "Empty folder should have 0 files");
        assertTrue(Files.isDirectory(dest), "Destination directory must exist");
    }

    // -----------------------------------------------------------------------
    // Binary files — ensure no corruption through the pipeline
    // -----------------------------------------------------------------------

    @Test
    void binaryFileRoundTrip(@TempDir Path tmpDir) throws Exception {
        Path src = tmpDir.resolve("bin_src");
        Files.createDirectories(src);

        // Every byte value 0x00–0xFF
        byte[] allBytes = new byte[256];
        for (int i = 0; i < 256; i++) allBytes[i] = (byte) i;
        Files.write(src.resolve("all_bytes.bin"), allBytes);

        // File with null bytes (common binary corruption trigger)
        byte[] withNulls = new byte[512];
        Arrays.fill(withNulls, (byte) 0x00);
        withNulls[0]   = (byte) 0xFF;
        withNulls[511] = (byte) 0xFE;
        Files.write(src.resolve("nulls.bin"), withNulls);

        Path dest = tmpDir.resolve("bin_dest");
        runDataPipeline(src, dest);

        // Files land at dest/bin_src/...
        Path destSrc = dest.resolve(src.getFileName());
        assertArrayEquals(allBytes,  Files.readAllBytes(destSrc.resolve("all_bytes.bin")));
        assertArrayEquals(withNulls, Files.readAllBytes(destSrc.resolve("nulls.bin")));
    }

    // -----------------------------------------------------------------------
    // Deep nesting — 10 levels
    // -----------------------------------------------------------------------

    @Test
    void deeplyNestedDirectoryRoundTrip(@TempDir Path tmpDir) throws Exception {
        Path src = tmpDir.resolve("deep_src");
        Files.createDirectories(src);

        // Create 10 levels deep: src/a/b/c/d/e/f/g/h/i/j/leaf.txt
        Path current = src;
        String[] segments = {"a","b","c","d","e","f","g","h","i","j"};
        for (String seg : segments) {
            current = current.resolve(seg);
            Files.createDirectories(current);
        }
        Files.writeString(current.resolve("leaf.txt"), "I am deeply nested.");

        Path dest = tmpDir.resolve("deep_dest");
        runDataPipeline(src, dest);

        // Files land at dest/deep_src/a/b/.../leaf.txt
        Path expected = dest.resolve(src.getFileName());
        for (String seg : segments) expected = expected.resolve(seg);
        expected = expected.resolve("leaf.txt");

        assertTrue(Files.exists(expected), "Deeply nested file must survive round-trip");
        assertEquals("I am deeply nested.", Files.readString(expected));
    }

    // -----------------------------------------------------------------------
    // Many small files — stress test the manifest
    // -----------------------------------------------------------------------

    @Test
    void manySmallFilesRoundTrip(@TempDir Path tmpDir) throws Exception {
        Path src = tmpDir.resolve("many_src");
        Files.createDirectories(src);

        int fileCount = 200;
        for (int i = 0; i < fileCount; i++) {
            Files.writeString(src.resolve(String.format("file_%04d.txt", i)),
                    "Content of file " + i + "\n");
        }

        Path dest = tmpDir.resolve("many_dest");
        runDataPipeline(src, dest);

        Path destSrc = dest.resolve(src.getFileName());
        for (int i = 0; i < fileCount; i++) {
            Path f = destSrc.resolve(String.format("file_%04d.txt", i));
            assertTrue(Files.exists(f), "File " + i + " must exist in destination");
            assertEquals("Content of file " + i + "\n", Files.readString(f));
        }
    }

    // -----------------------------------------------------------------------
    // Destination already exists — overwrite cleanly
    // -----------------------------------------------------------------------

    @Test
    void destinationAlreadyExistsIsOverwritten(@TempDir Path tmpDir) throws Exception {
        Path src = tmpDir.resolve("ow_src");
        Files.createDirectories(src);
        Files.writeString(src.resolve("hello.txt"), "Hello from beacon.");

        Path dest = tmpDir.resolve("ow_dest");
        Files.createDirectories(dest);
        // Pre-populate destination with a stale file
        Files.writeString(dest.resolve("stale.txt"), "This should not interfere.");

        runDataPipeline(src, dest);

        Path destSrc = dest.resolve(src.getFileName());
        assertTrue(Files.exists(destSrc.resolve("hello.txt")));
        assertEquals("Hello from beacon.", Files.readString(destSrc.resolve("hello.txt")));
        // Stale file at dest root is left in place (deserializer only adds, does not clean)
        assertTrue(Files.exists(dest.resolve("stale.txt")),
                "Pre-existing files in destination are not deleted by the deserializer");
    }

    // -----------------------------------------------------------------------
    // Wrong key string — decrypt must throw
    // -----------------------------------------------------------------------

    @Test
    void wrongKeyThrowsOnDecrypt(@TempDir Path tmpDir) throws Exception {
        Path src = tmpDir.resolve("wk_src");
        Files.createDirectories(src);
        Files.writeString(src.resolve("secret.txt"), "Secret data.");

        DirectoryWalker walker = new DirectoryWalker();
        List<FileNode> nodes = walker.walk(src);
        BlobSerializer serializer = new BlobSerializer();
        byte[] blob = serializer.serialize(src, nodes);
        Compressor compressor = new Compressor();
        byte[] compressed = compressor.compress(blob);
        Encryptor encryptor = new Encryptor();
        Encryptor.EncryptionResult encrypted = encryptor.encrypt(compressed);

        // Build a second encryption with a different key
        Encryptor.EncryptionResult wrongKeyResult = encryptor.encrypt(new byte[32]);
        KeyMaterial wrongKm = wrongKeyResult.keyMaterial;

        Decryptor decryptor = new Decryptor();
        assertThrows(Exception.class,
                () -> decryptor.decrypt(encrypted.ciphertext, wrongKm),
                "Decryption with wrong key must throw");
    }

    // -----------------------------------------------------------------------
    // Unicode filenames (skipped on Windows — some chars forbidden by NTFS)
    // -----------------------------------------------------------------------

    @Test
    @DisabledOnOs(OS.WINDOWS)
    void unicodeFilenamesRoundTrip(@TempDir Path tmpDir) throws Exception {
        Path src = tmpDir.resolve("unicode_src");
        Files.createDirectories(src);

        Files.writeString(src.resolve("café.txt"),     "café");
        Files.writeString(src.resolve("résumé.txt"),   "résumé");
        Files.writeString(src.resolve("日本語.txt"),     "日本語テスト");
        Files.writeString(src.resolve("emoji_🚀.txt"), "launch");

        Path dest = tmpDir.resolve("unicode_dest");
        runDataPipeline(src, dest);

        Path destSrc = dest.resolve(src.getFileName());
        assertEquals("café",        Files.readString(destSrc.resolve("café.txt")));
        assertEquals("résumé",      Files.readString(destSrc.resolve("résumé.txt")));
        assertEquals("日本語テスト",  Files.readString(destSrc.resolve("日本語.txt")));
        assertEquals("launch",      Files.readString(destSrc.resolve("emoji_🚀.txt")));
    }

    // -----------------------------------------------------------------------
    // SHA-256 integrity — file contents identical after full data pipeline
    // -----------------------------------------------------------------------

    @Test
    void sha256IntegrityAfterPipeline(@TempDir Path tmpDir) throws Exception {
        Path src = tmpDir.resolve("sha_src");
        Files.createDirectories(src);

        byte[] payload = new byte[50_000];
        new java.util.Random(0xCAFEBABEL).nextBytes(payload);
        Files.write(src.resolve("payload.bin"), payload);

        Path dest = tmpDir.resolve("sha_dest");
        runDataPipeline(src, dest);

        byte[] restored = Files.readAllBytes(dest.resolve(src.getFileName()).resolve("payload.bin"));
        assertArrayEquals(sha256(payload), sha256(restored),
                "SHA-256 must match after full pipeline");
    }

    // -----------------------------------------------------------------------
    // Helper
    // -----------------------------------------------------------------------

    /**
     * Run the data pipeline (no QR) on the given source directory.
     * Writes the reconstructed folder tree into dest.
     * Uses generous overhead (10x K + 100) to work even for tiny K.
     */
    private void runDataPipeline(Path src, Path dest) throws Exception {
        DirectoryWalker walker = new DirectoryWalker();
        List<FileNode> nodes = walker.walk(src);

        BlobSerializer serializer = new BlobSerializer();
        byte[] blob = serializer.serialize(src, nodes);

        Compressor compressor = new Compressor();
        byte[] compressed = compressor.compress(blob);

        Encryptor encryptor = new Encryptor();
        Encryptor.EncryptionResult encrypted = encryptor.encrypt(compressed);
        KeyMaterial km = encrypted.keyMaterial;

        LTEncoder encoder = new LTEncoder(encrypted.ciphertext, 1);
        int k = encoder.getK();
        LTDecoder decoder = new LTDecoder(k, encoder.getBlockSize(), encrypted.ciphertext.length);

        for (int i = 0; i < k * 10 + 100; i++) {
            if (decoder.addPacket(encoder.nextPacket())) break;
        }
        assertTrue(decoder.isComplete(), "Fountain decoder must complete");

        Decryptor decryptor = new Decryptor();
        byte[] plain = decryptor.decrypt(decoder.getReconstructedData(), km);

        Decompressor decompressor = new Decompressor();
        byte[] finalBlob = decompressor.decompress(plain);

        BlobDeserializer blobDeserializer = new BlobDeserializer();
        blobDeserializer.deserialize(finalBlob, dest);
    }

    private static byte[] sha256(byte[] data) throws Exception {
        return MessageDigest.getInstance("SHA-256").digest(data);
    }
}
