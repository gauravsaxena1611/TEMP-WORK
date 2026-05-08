package com.airborne.transport;

import com.airborne.core.*;
import com.airborne.fountain.LTEncoder;
import com.airborne.fountain.FountainPacket;

import java.io.IOException;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.util.List;

/**
 * Coordinates the sender-side pipeline: walk → serialize → compress → encrypt → fountain encode.
 *
 * Produces:
 *   - A KeyMaterial object (to be displayed as Key QR)
 *   - An LTEncoder that generates an infinite stream of FountainPackets (to be displayed as data QR)
 *
 * Used by the UI in Phase 4.
 */
public class TransferSession {

    private final int projectId;
    private final KeyMaterial keyMaterial;
    private final LTEncoder encoder;
    private final boolean singleFile;

    /**
     * Build a complete transfer session from the given source folder.
     *
     * @param rootPath Path to the folder to transfer.
     * @param listener Optional progress listener (may be null).
     * @throws IOException If any file cannot be read.
     */
    public TransferSession(Path rootPath, ProgressListener listener) throws IOException {
        this.singleFile = false;
        this.projectId = new SecureRandom().nextInt();

        report(listener, "Walking directory...", 0);
        DirectoryWalker walker = new DirectoryWalker();
        List<FileNode> nodes = walker.walk(rootPath);

        report(listener, "Serializing files...", 10);
        BlobSerializer serializer = new BlobSerializer();
        byte[] blob = serializer.serialize(rootPath, nodes);

        report(listener, "Compressing...", 30);
        Compressor compressor = new Compressor();
        byte[] compressed = compressor.compress(blob);

        report(listener, "Encrypting...", 60);
        Encryptor encryptor = new Encryptor();
        Encryptor.EncryptionResult encrypted = encryptor.encrypt(compressed);

        // Populate key material with transfer metadata
        KeyMaterial km = encrypted.keyMaterial;
        km.projectId = projectId;
        km.version = "1.0";
        km.sourceFiles = countFiles(nodes);
        km.sourceDirs = countDirs(nodes);
        km.uncompressedBytes = blob.length;
        km.compressedBytes = compressed.length;
        km.encryptedBytes = encrypted.ciphertext.length;
        km.timestamp = java.time.Instant.now().toString();

        report(listener, "Preparing fountain codes...", 85);
        this.encoder = new LTEncoder(encrypted.ciphertext, projectId);
        km.totalBlocks = encoder.getK();

        this.keyMaterial = km;
        report(listener, "Ready.", 100);
    }

    /**
     * Build a transfer session from a single file.
     * The file is serialized, compressed, encrypted, and fountain-coded exactly like a
     * folder transfer. On the receiver side the paste flow is identical — the file is
     * written into the chosen destination folder with its original name and timestamp.
     *
     * @param filePath Path to the single file to transfer.
     * @param listener Optional progress listener (may be null).
     * @throws IOException If the file cannot be read.
     */
    public TransferSession(Path filePath, boolean singleFile, ProgressListener listener) throws IOException {
        this.singleFile = true;
        this.projectId = new SecureRandom().nextInt();

        report(listener, "Reading file...", 5);
        BlobSerializer serializer = new BlobSerializer();
        byte[] blob = serializer.serializeSingleFile(filePath);

        report(listener, "Compressing...", 30);
        Compressor compressor = new Compressor();
        byte[] compressed = compressor.compress(blob);

        report(listener, "Encrypting...", 60);
        Encryptor encryptor = new Encryptor();
        Encryptor.EncryptionResult encrypted = encryptor.encrypt(compressed);

        KeyMaterial km = encrypted.keyMaterial;
        km.projectId = projectId;
        km.version = "1.0";
        km.sourceFiles = 1;
        km.sourceDirs = 0;
        km.uncompressedBytes = blob.length;
        km.compressedBytes = compressed.length;
        km.encryptedBytes = encrypted.ciphertext.length;
        km.timestamp = java.time.Instant.now().toString();

        report(listener, "Preparing fountain codes...", 85);
        this.encoder = new LTEncoder(encrypted.ciphertext, projectId);
        km.totalBlocks = encoder.getK();

        this.keyMaterial = km;
        report(listener, "Ready.", 100);
    }

    /** @return true if this session was created from a single file (not a folder). */
    public boolean isSingleFile() { return singleFile; }

    /** @return The KeyMaterial to be displayed as the Key QR code. */
    public KeyMaterial getKeyMaterial() { return keyMaterial; }

    /** @return The project ID for this transfer. */
    public int getProjectId() { return projectId; }

    /** @return The LTEncoder; call nextPacket() repeatedly to drive the animated QR loop. */
    public LTEncoder getEncoder() { return encoder; }

    /** @return The next fountain packet to be displayed as a QR frame. */
    public FountainPacket nextPacket() { return encoder.nextPacket(); }

    // -----------------------------------------------------------------------

    private static int countFiles(List<FileNode> nodes) {
        int n = 0;
        for (FileNode node : nodes) if (!node.isDirectory) n++;
        return n;
    }

    private static int countDirs(List<FileNode> nodes) {
        int n = 0;
        for (FileNode node : nodes) if (node.isDirectory) n++;
        return n;
    }

    private static void report(ProgressListener listener, String message, int pct) {
        if (listener != null) listener.onProgress(message, pct);
    }

    /** Progress callback for the UI. */
    public interface ProgressListener {
        void onProgress(String message, int percentComplete);
    }
}
