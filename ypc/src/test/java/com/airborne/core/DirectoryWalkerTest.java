package com.airborne.core;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class DirectoryWalkerTest {

    private final DirectoryWalker walker = new DirectoryWalker();

    @Test
    void walksSimpleFolderInDfsPreOrder(@TempDir Path root) throws IOException {
        // root/
        //   a.txt
        //   subdir/
        //     b.txt
        Files.writeString(root.resolve("a.txt"), "hello");
        Path sub = root.resolve("subdir");
        Files.createDirectory(sub);
        Files.writeString(sub.resolve("b.txt"), "world");

        List<FileNode> nodes = walker.walk(root);

        // Pre-order: root dir first, then entries
        List<String> paths = nodes.stream().map(n -> n.relativePath).collect(Collectors.toList());
        assertTrue(paths.indexOf(root.getFileName().toString()) <
                   paths.indexOf(paths.stream().filter(p -> p.endsWith("subdir")).findFirst().orElse("")),
                   "root dir should appear before subdir");
        String subdirRelative = paths.stream().filter(p -> p.endsWith("subdir")).findFirst().orElse(null);
        String bTxt = paths.stream().filter(p -> p.endsWith("b.txt")).findFirst().orElse(null);
        assertNotNull(subdirRelative);
        assertNotNull(bTxt);
        assertTrue(paths.indexOf(subdirRelative) < paths.indexOf(bTxt),
                   "subdir should appear before its child b.txt");
    }

    @Test
    void handlesEmptyDirectory(@TempDir Path root) throws IOException {
        Path empty = root.resolve("emptyDir");
        Files.createDirectory(empty);

        List<FileNode> nodes = walker.walk(root);

        assertTrue(nodes.stream().anyMatch(n -> n.isDirectory && n.name.equals("emptyDir")),
                   "Empty directory must appear in results");
    }

    @Test
    void handlesUnicodeFilenames(@TempDir Path root) throws IOException {
        String unicodeName = "日本語ファイル.txt";
        Files.writeString(root.resolve(unicodeName), "content", StandardCharsets.UTF_8);

        List<FileNode> nodes = walker.walk(root);

        assertTrue(nodes.stream().anyMatch(n -> n.name.equals(unicodeName)),
                   "Unicode filename must be preserved");
    }

    @Test
    void handlesDeepNesting(@TempDir Path root) throws IOException {
        // 5-level deep structure
        Path current = root;
        for (int i = 1; i <= 5; i++) {
            current = current.resolve("level" + i);
            Files.createDirectory(current);
        }
        Files.writeString(current.resolve("deep.txt"), "deep content");

        List<FileNode> nodes = walker.walk(root);

        assertTrue(nodes.stream().anyMatch(n -> n.name.equals("deep.txt")),
                   "File at depth 5 must be found");
        // Verify dirs appear before their children
        List<String> paths = nodes.stream().map(n -> n.relativePath).collect(Collectors.toList());
        for (int i = 0; i < paths.size() - 1; i++) {
            String p = paths.get(i);
            // Any later path that starts with p (and has more segments) must come after p
            for (int j = i + 1; j < paths.size(); j++) {
                String q = paths.get(j);
                if (q.startsWith(p + "/")) {
                    assertTrue(i < j, "Parent " + p + " must appear before child " + q);
                }
            }
        }
    }

    @Test
    void rootNodeIsFirstEntry(@TempDir Path root) throws IOException {
        Files.writeString(root.resolve("file.txt"), "data");

        List<FileNode> nodes = walker.walk(root);

        assertEquals(root.getFileName().toString(), nodes.get(0).name,
                     "First node must be the root directory itself");
        assertTrue(nodes.get(0).isDirectory);
    }

    @Test
    void fileMetadataIsCorrect(@TempDir Path root) throws IOException {
        byte[] content = "test content".getBytes(StandardCharsets.UTF_8);
        Files.write(root.resolve("sample.txt"), content);

        List<FileNode> nodes = walker.walk(root);
        FileNode file = nodes.stream().filter(n -> n.name.equals("sample.txt")).findFirst().orElse(null);

        assertNotNull(file);
        assertFalse(file.isDirectory);
        assertEquals("txt", file.extension);
        assertEquals(content.length, file.sizeBytes);
        assertTrue(file.lastModified > 0);
    }
}
