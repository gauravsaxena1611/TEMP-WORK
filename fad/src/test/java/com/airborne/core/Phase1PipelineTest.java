package com.airborne.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.security.MessageDigest;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Phase 1 milestone test: walk → serialize → compress → encrypt → decrypt → decompress
 * → deserialize → verify byte-for-byte identity of all files.
 */
class Phase1PipelineTest {

    @Test
    void fullPipelineProducesIdenticalFiles(@TempDir Path src, @TempDir Path dst) throws Exception {
        // Build a realistic source tree
        buildTestTree(src);

        // ---- SENDER SIDE ----
        DirectoryWalker walker = new DirectoryWalker();
        List<FileNode> nodes = walker.walk(src);

        BlobSerializer serializer = new BlobSerializer();
        byte[] blob = serializer.serialize(src, nodes);

        Compressor compressor = new Compressor();
        byte[] compressed = compressor.compress(blob);

        Encryptor encryptor = new Encryptor();
        Encryptor.EncryptionResult encrypted = encryptor.encrypt(compressed);

        // Simulate KeyMaterial JSON transmission (phone scan → paste)
        String keyJson = encrypted.keyMaterial.toJson();

        // ---- RECEIVER SIDE ----
        KeyMaterial keyMaterial = KeyMaterial.fromJson(keyJson);

        Decryptor decryptor = new Decryptor();
        byte[] decrypted = decryptor.decrypt(encrypted.ciphertext, keyMaterial);

        Decompressor decompressor = new Decompressor();
        byte[] rawBlob = decompressor.decompress(decrypted);

        BlobDeserializer deserializer = new BlobDeserializer();
        Manifest manifest = deserializer.deserialize(rawBlob, dst);

        // ---- VERIFY ----
        assertNotNull(manifest);
        assertTrue(manifest.totalFiles > 0, "Manifest must report files");

        // Walk both trees and compare SHA-256 of each file
        String srcRoot = src.getFileName().toString();
        verifyFilesMatch(src, dst.resolve(srcRoot), src);
    }

    @Test
    void pipelinePreservesFileCount(@TempDir Path src, @TempDir Path dst) throws Exception {
        Files.writeString(src.resolve("one.txt"), "one");
        Files.writeString(src.resolve("two.txt"), "two");
        Path sub = src.resolve("sub");
        Files.createDirectory(sub);
        Files.writeString(sub.resolve("three.txt"), "three");

        DirectoryWalker walker = new DirectoryWalker();
        List<FileNode> nodes = walker.walk(src);
        byte[] blob = new BlobSerializer().serialize(src, nodes);
        byte[] compressed = new Compressor().compress(blob);
        Encryptor.EncryptionResult enc = new Encryptor().encrypt(compressed);
        byte[] decrypted = new Decryptor().decrypt(enc.ciphertext, enc.keyMaterial);
        byte[] raw = new Decompressor().decompress(decrypted);
        Manifest manifest = new BlobDeserializer().deserialize(raw, dst);

        assertEquals(3, manifest.totalFiles, "All 3 files must be accounted for in manifest");
    }

    private void buildTestTree(Path root) throws IOException {
        // Various file types
        Files.writeString(root.resolve("README.md"), "# Airborne Copier\nA file transfer tool.");
        Files.writeString(root.resolve("config.properties"), "key=value\nhost=localhost\nport=8080");

        // Binary-ish data
        byte[] binaryData = new byte[512];
        for (int i = 0; i < binaryData.length; i++) binaryData[i] = (byte) (i & 0xFF);
        Files.write(root.resolve("data.bin"), binaryData);

        // Subdirectory with Java source
        Path src = root.resolve("src");
        Files.createDirectory(src);
        Files.writeString(src.resolve("Main.java"),
                "package com.example;\npublic class Main {\n    public static void main(String[] args) {}\n}");

        // Nested structure
        Path deep = src.resolve("util");
        Files.createDirectory(deep);
        Files.writeString(deep.resolve("Helper.java"),
                "package com.example.util;\npublic class Helper {}");

        // Unicode filename
        Files.writeString(root.resolve("日本語.txt"), "テスト", StandardCharsets.UTF_8);

        // Empty directory
        Files.createDirectory(root.resolve("emptyDir"));
    }

    private void verifyFilesMatch(Path srcRoot, Path dstRoot, Path srcBase) throws Exception {
        List<Path> allFiles = new ArrayList<>();
        Files.walk(srcRoot).filter(Files::isRegularFile).forEach(allFiles::add);

        for (Path srcPath : allFiles) {
            Path relative = srcBase.getParent().relativize(srcPath);
            Path dstPath = dstRoot.getParent().resolve(relative);
            assertTrue(Files.exists(dstPath), "File must exist in destination: " + relative);
            byte[] srcBytes = Files.readAllBytes(srcPath);
            byte[] dstBytes = Files.readAllBytes(dstPath);
            assertArrayEquals(
                sha256(srcBytes), sha256(dstBytes),
                "SHA-256 must match for: " + relative
            );
        }
    }

    private byte[] sha256(byte[] data) throws Exception {
        return MessageDigest.getInstance("SHA-256").digest(data);
    }
}
