package com.airborne.core;

import com.google.gson.Gson;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.Arrays;

/**
 * Deserializes a binary blob back into a folder tree on disk.
 * Validates magic bytes and enforces strict path safety rules.
 */
public class BlobDeserializer {

    /**
     * Reconstruct the folder tree from a binary blob at the given destination.
     *
     * @param blob        The binary blob (output of BlobSerializer or decrypt/decompress pipeline).
     * @param destination The root directory where files will be written.
     * @return The Manifest parsed from the blob.
     * @throws IOException              On I/O errors.
     * @throws SecurityException        If any path fails safety validation.
     * @throws IllegalArgumentException If the blob is malformed.
     */
    public Manifest deserialize(byte[] blob, Path destination) throws IOException {
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(blob));

        // 1. Verify magic bytes
        byte[] magic = new byte[BlobSerializer.MAGIC.length];
        in.readFully(magic);
        if (!Arrays.equals(magic, BlobSerializer.MAGIC)) {
            throw new IllegalArgumentException(
                    "Invalid blob: magic bytes not found. Expected BCNV1.");
        }

        // 2. Read manifest length
        int manifestLen = in.readInt();
        if (manifestLen <= 0 || manifestLen > 50 * 1024 * 1024) {
            throw new IllegalArgumentException(
                    "Invalid manifest length: " + manifestLen);
        }

        // 3. Read and parse manifest JSON
        byte[] manifestBytes = new byte[manifestLen];
        in.readFully(manifestBytes);
        String manifestJson = new String(manifestBytes, "UTF-8");
        Manifest manifest = new Gson().fromJson(manifestJson, Manifest.class);
        if (manifest == null || manifest.entries == null) {
            throw new IllegalArgumentException("Manifest JSON is invalid or empty.");
        }

        // 4. Read DATA_SECTION into memory
        byte[] dataSection = readRemaining(in);

        // 5. Validate ALL paths before writing anything (fail-fast, whole-transfer rejection)
        Path destNormalized = destination.toAbsolutePath().normalize();
        for (FileNode node : manifest.entries) {
            validatePath(node.relativePath, destNormalized);
        }

        // 6. Write entries
        Files.createDirectories(destination);
        for (FileNode node : manifest.entries) {
            Path target = destNormalized.resolve(node.relativePath.replace('/', File.separatorChar))
                                        .normalize();
            if (node.isDirectory) {
                Files.createDirectories(target);
            } else {
                Files.createDirectories(target.getParent());
                int offset = (int) node.dataOffset;
                int length = (int) node.dataLength;
                try (OutputStream fileOut = Files.newOutputStream(target)) {
                    fileOut.write(dataSection, offset, length);
                }
                // Restore last-modified timestamp
                Files.setLastModifiedTime(target, FileTime.fromMillis(node.lastModified));
            }
        }

        return manifest;
    }

    /**
     * Validate a relative path against directory traversal and other safety rules.
     * Rejects the entire transfer (throws SecurityException) on any violation.
     */
    private void validatePath(String relativePath, Path destRoot) {
        if (relativePath == null || relativePath.isEmpty()) {
            throw new SecurityException(
                    "Security alert: The transfer contains an unsafe file path. " +
                    "This transfer has been rejected. Do not use this transfer data.");
        }
        // Must not contain null bytes
        if (relativePath.contains("\0")) {
            throw new SecurityException(
                    "Security alert: The transfer contains an unsafe file path. " +
                    "This transfer has been rejected. Do not use this transfer data.");
        }
        // Must not be absolute
        if (relativePath.startsWith("/") || (relativePath.length() > 1 && relativePath.charAt(1) == ':')) {
            throw new SecurityException(
                    "Security alert: The transfer contains an unsafe file path. " +
                    "This transfer has been rejected. Do not use this transfer data.");
        }
        // Must not contain ".." components
        String[] parts = relativePath.replace('\\', '/').split("/");
        for (String part : parts) {
            if ("..".equals(part)) {
                throw new SecurityException(
                        "Security alert: The transfer contains an unsafe file path. " +
                        "This transfer has been rejected. Do not use this transfer data.");
            }
            if (part.length() > 255) {
                throw new SecurityException(
                        "Security alert: The transfer contains an unsafe file path. " +
                        "This transfer has been rejected. Do not use this transfer data.");
            }
        }
        // Canonical path must start with destination root
        Path resolved = destRoot.resolve(relativePath.replace('/', File.separatorChar)).normalize();
        if (!resolved.startsWith(destRoot)) {
            throw new SecurityException(
                    "Security alert: The transfer contains an unsafe file path. " +
                    "This transfer has been rejected. Do not use this transfer data.");
        }
    }

    /** Read all remaining bytes from a DataInputStream. */
    private byte[] readRemaining(DataInputStream in) throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        byte[] chunk = new byte[65536];
        int n;
        while ((n = in.read(chunk)) != -1) {
            buf.write(chunk, 0, n);
        }
        return buf.toByteArray();
    }
}
