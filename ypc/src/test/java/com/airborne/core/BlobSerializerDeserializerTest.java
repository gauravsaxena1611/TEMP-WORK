package com.airborne.core;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class BlobSerializerDeserializerTest {

    private final DirectoryWalker walker = new DirectoryWalker();
    private final BlobSerializer serializer = new BlobSerializer();
    private final BlobDeserializer deserializer = new BlobDeserializer();

    @Test
    void magicBytesAtOffsetZero(@TempDir Path src) throws IOException {
        Files.writeString(src.resolve("hello.txt"), "hello");
        List<FileNode> nodes = walker.walk(src);
        byte[] blob = serializer.serialize(src, nodes);

        assertArrayEquals(BlobSerializer.MAGIC, Arrays.copyOf(blob, 5),
                "Magic bytes BCNV1 must appear at offset 0");
    }

    @Test
    void serializeThenDeserializeProducesIdenticalFiles(@TempDir Path src, @TempDir Path dst) throws IOException {
        Files.writeString(src.resolve("readme.txt"), "Airborne Copier");
        Path sub = src.resolve("config");
        Files.createDirectory(sub);
        Files.writeString(sub.resolve("app.properties"), "key=value\nfoo=bar");
        byte[] binaryData = new byte[256];
        for (int i = 0; i < 256; i++) binaryData[i] = (byte) i;
        Files.write(src.resolve("data.bin"), binaryData);

        List<FileNode> nodes = walker.walk(src);
        byte[] blob = serializer.serialize(src, nodes);
        deserializer.deserialize(blob, dst);

        // Verify each file's content matches
        String srcName = src.getFileName().toString();
        assertFileEquals(src.resolve("readme.txt"), dst.resolve(srcName).resolve("readme.txt"));
        assertFileEquals(sub.resolve("app.properties"),
                         dst.resolve(srcName).resolve("config").resolve("app.properties"));
        assertFileEquals(src.resolve("data.bin"), dst.resolve(srcName).resolve("data.bin"));
    }

    @Test
    void directoryEntriesHaveZeroLength(@TempDir Path src) throws IOException {
        Path sub = src.resolve("emptySubDir");
        Files.createDirectory(sub);
        Files.writeString(src.resolve("file.txt"), "data");

        List<FileNode> nodes = walker.walk(src);
        serializer.serialize(src, nodes);  // sets dataOffset/dataLength

        for (FileNode node : nodes) {
            if (node.isDirectory) {
                assertEquals(0, node.dataLength, "Directory entries must have dataLength == 0");
                assertEquals(0, node.dataOffset, "Directory entries must have dataOffset == 0");
            }
        }
    }

    @Test
    void fileOffsetsAndLengthsAreNonOverlapping(@TempDir Path src) throws IOException {
        Files.writeString(src.resolve("a.txt"), "AAAA");
        Files.writeString(src.resolve("b.txt"), "BBBBBB");
        Files.writeString(src.resolve("c.txt"), "CCCCCCCC");

        List<FileNode> nodes = walker.walk(src);
        serializer.serialize(src, nodes);

        List<FileNode> files = new ArrayList<>();
        for (FileNode n : nodes) {
            if (!n.isDirectory) files.add(n);
        }
        // Sort by offset and check no overlaps
        files.sort(Comparator.comparingLong(n -> n.dataOffset));
        for (int i = 1; i < files.size(); i++) {
            long prevEnd = files.get(i - 1).dataOffset + files.get(i - 1).dataLength;
            assertTrue(files.get(i).dataOffset >= prevEnd,
                    "File regions must not overlap");
        }
    }

    @Test
    void emptyDirectoryIsRestored(@TempDir Path src, @TempDir Path dst) throws IOException {
        Path empty = src.resolve("emptyDir");
        Files.createDirectory(empty);

        List<FileNode> nodes = walker.walk(src);
        byte[] blob = serializer.serialize(src, nodes);
        deserializer.deserialize(blob, dst);

        assertTrue(Files.isDirectory(dst.resolve(src.getFileName().toString()).resolve("emptyDir")),
                "Empty directory must be recreated at destination");
    }

    @Test
    void pathTraversalIsRejected(@TempDir Path dst) {
        // Craft a blob with a malicious path
        byte[] fakeBlob = craftBlobWithPath("../../evil/file.txt");
        assertThrows(SecurityException.class, () -> deserializer.deserialize(fakeBlob, dst),
                "Path traversal must throw SecurityException");
    }

    @Test
    void absolutePathIsRejected(@TempDir Path dst) {
        byte[] fakeBlob = craftBlobWithPath("/etc/passwd");
        assertThrows(SecurityException.class, () -> deserializer.deserialize(fakeBlob, dst),
                "Absolute path must throw SecurityException");
    }

    // Helper: build a minimal blob with a single file at a given (potentially evil) path
    private byte[] craftBlobWithPath(String relativePath) {
        FileNode node = new FileNode(relativePath, "file.txt", "txt",
                false, false, null, 4, 0L, "text/plain", false);
        node.dataOffset = 0;
        node.dataLength = 4;

        Manifest manifest = new Manifest();
        manifest.version = "1.0";
        manifest.created = "2026-01-01T00:00:00Z";
        manifest.sourceRoot = "root";
        manifest.totalFiles = 1;
        manifest.totalDirs = 0;
        manifest.totalDataBytes = 4;
        manifest.entries = Collections.singletonList(node);

        try {
            com.google.gson.Gson gson = new com.google.gson.GsonBuilder().serializeNulls().create();
            byte[] jsonBytes = gson.toJson(manifest).getBytes("UTF-8");
            byte[] data = "ABCD".getBytes();

            java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
            out.write(BlobSerializer.MAGIC);
            out.write(BlobSerializer.toBigEndianInt(jsonBytes.length));
            out.write(jsonBytes);
            out.write(data);
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void assertFileEquals(Path expected, Path actual) throws IOException {
        assertTrue(Files.exists(actual), "File must exist: " + actual);
        assertArrayEquals(Files.readAllBytes(expected), Files.readAllBytes(actual),
                "File contents must be identical: " + actual.getFileName());
    }
}
