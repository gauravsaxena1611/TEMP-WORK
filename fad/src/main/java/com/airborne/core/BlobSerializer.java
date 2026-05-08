package com.airborne.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.List;

/**
 * Serializes a folder tree (as a list of FileNode objects) into a single binary blob.
 *
 * Blob layout:
 *   [5 bytes]  MAGIC "BCNV1"
 *   [4 bytes]  MANIFEST_LEN (big-endian int32)
 *   [N bytes]  MANIFEST_JSON (UTF-8)
 *   [M bytes]  DATA_SECTION (concatenated raw file bytes)
 */
public class BlobSerializer {

    public static final byte[] MAGIC = new byte[]{'B', 'C', 'N', 'V', '1'};
    private static final int BLOCK_SIZE = 65536; // 64 KB read buffer

    /**
     * Serialize the folder rooted at rootPath with the given file list into a byte array.
     *
     * @param rootPath Absolute path to the root folder being transferred.
     * @param nodes    List of FileNode in pre-order DFS order (from DirectoryWalker).
     * @return Binary blob as byte[].
     * @throws IOException If a file cannot be read.
     */
    public byte[] serialize(Path rootPath, List<FileNode> nodes) throws IOException {
        // Build manifest and compute DATA_SECTION in a first pass
        ByteArrayOutputStream dataSection = new ByteArrayOutputStream();
        int totalFiles = 0;
        int totalDirs = 0;
        long dataOffset = 0;

        for (FileNode node : nodes) {
            if (node.isDirectory) {
                totalDirs++;
                node.dataOffset = 0;
                node.dataLength = 0;
            } else {
                totalFiles++;
                Path filePath = rootPath.getParent().resolve(node.relativePath.replace('/', File.separatorChar));
                byte[] fileBytes = readFile(filePath, node.relativePath);
                node.dataOffset = dataOffset;
                node.dataLength = fileBytes.length;
                dataSection.write(fileBytes);
                dataOffset += fileBytes.length;
            }
        }

        // Build manifest
        Manifest manifest = new Manifest();
        manifest.version = "1.0";
        manifest.created = Instant.now().toString();
        manifest.sourceRoot = rootPath.getFileName().toString();
        manifest.totalFiles = totalFiles;
        manifest.totalDirs = totalDirs;
        manifest.totalDataBytes = dataOffset;
        manifest.entries = nodes;

        Gson gson = new GsonBuilder().serializeNulls().create();
        byte[] manifestJson = gson.toJson(manifest).getBytes("UTF-8");
        byte[] data = dataSection.toByteArray();

        // Assemble blob
        ByteArrayOutputStream out = new ByteArrayOutputStream(
                MAGIC.length + 4 + manifestJson.length + data.length);
        out.write(MAGIC);
        out.write(toBigEndianInt(manifestJson.length));
        out.write(manifestJson);
        out.write(data);

        return out.toByteArray();
    }

    /** Read a file to bytes. Throws IOException with the path in the message on failure. */
    private byte[] readFile(Path path, String relativePath) throws IOException {
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new IOException("Could not read file: " + relativePath + " — " + e.getMessage(), e);
        }
    }

    /**
     * Serialize a single file into a blob using the same format as {@link #serialize}.
     * The file's relativePath in the manifest is just its filename, so the deserializer
     * will write it directly into the chosen destination folder with the original name.
     * File content is stored byte-for-byte; the last-modified timestamp is preserved.
     *
     * @param filePath Absolute path to the single file to transfer.
     * @return Binary blob as byte[].
     * @throws IOException If the file cannot be read.
     */
    public byte[] serializeSingleFile(Path filePath) throws IOException {
        String name = filePath.getFileName().toString();
        int dot = name.lastIndexOf('.');
        String extension = (dot > 0 && dot < name.length() - 1) ? name.substring(dot + 1) : "";
        long lastModified = Files.getLastModifiedTime(filePath).toMillis();

        byte[] fileBytes;
        try {
            fileBytes = Files.readAllBytes(filePath);
        } catch (IOException e) {
            throw new IOException("Could not read file: " + name + " — " + e.getMessage(), e);
        }

        FileNode node = new FileNode(
                name, name, extension,
                false, false, null,
                fileBytes.length, lastModified,
                "application/octet-stream", true
        );
        node.dataOffset = 0;
        node.dataLength = fileBytes.length;

        Manifest manifest = new Manifest();
        manifest.version = "1.0";
        manifest.created = Instant.now().toString();
        manifest.sourceRoot = name;
        manifest.totalFiles = 1;
        manifest.totalDirs = 0;
        manifest.totalDataBytes = fileBytes.length;
        manifest.entries = List.of(node);

        Gson gson = new GsonBuilder().serializeNulls().create();
        byte[] manifestJson = gson.toJson(manifest).getBytes("UTF-8");

        ByteArrayOutputStream out = new ByteArrayOutputStream(
                MAGIC.length + 4 + manifestJson.length + fileBytes.length);
        out.write(MAGIC);
        out.write(toBigEndianInt(manifestJson.length));
        out.write(manifestJson);
        out.write(fileBytes);

        return out.toByteArray();
    }

    /** Encode int as 4-byte big-endian array. */
    static byte[] toBigEndianInt(int value) {
        return ByteBuffer.allocate(4).putInt(value).array();
    }
}
