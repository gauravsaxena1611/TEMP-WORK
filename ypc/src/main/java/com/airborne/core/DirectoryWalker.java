package com.airborne.core;

import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * Walks a directory tree in pre-order DFS and produces a list of FileNode objects.
 * Parent directories appear before their children.
 */
public class DirectoryWalker {

    /**
     * Walk the given root directory and return all entries in pre-order DFS order.
     *
     * @param root The root directory to walk.
     * @return List of FileNode in DFS pre-order.
     * @throws IOException If the root cannot be read.
     */
    public List<FileNode> walk(Path root) throws IOException {
        if (!Files.isDirectory(root)) {
            throw new IllegalArgumentException("Root must be a directory: " + root);
        }

        List<FileNode> results = new ArrayList<>();

        Files.walkFileTree(root, new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                String relative = toRelative(root, dir);
                String name = dir.getFileName() != null ? dir.getFileName().toString() : dir.toString();
                FileNode node = new FileNode(
                        relative, name, "",
                        true, false, null,
                        0L, attrs.lastModifiedTime().toMillis(),
                        "inode/directory", false
                );
                results.add(node);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                String relative = toRelative(root, file);
                String name = file.getFileName().toString();
                String extension = getExtension(name);
                boolean symlink = Files.isSymbolicLink(file);
                String symlinkTarget = null;
                if (symlink) {
                    try {
                        symlinkTarget = Files.readSymbolicLink(file).toString();
                    } catch (IOException ignored) {}
                }
                String mimeType = detectMime(file, name);
                boolean binary = isBinary(file, mimeType);
                FileNode node = new FileNode(
                        relative, name, extension,
                        false, symlink, symlinkTarget,
                        attrs.size(), attrs.lastModifiedTime().toMillis(),
                        mimeType, binary
                );
                results.add(node);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) {
                // Log and skip unreadable files — caller decides on action
                System.err.println("Could not read file: " + file + " — " + exc.getMessage());
                return FileVisitResult.CONTINUE;
            }
        });

        return results;
    }

    /** Convert an absolute path to a forward-slash relative path from root. */
    private static String toRelative(Path root, Path target) {
        Path rel = root.getParent() != null ? root.getParent().relativize(target) : root.relativize(target);
        // Use the root's own name as prefix so the path starts with the folder name
        return rel.toString().replace('\\', '/');
    }

    /** Extract file extension, empty string if none. */
    static String getExtension(String name) {
        int dot = name.lastIndexOf('.');
        if (dot > 0 && dot < name.length() - 1) {
            return name.substring(dot + 1);
        }
        return "";
    }

    /** Detect MIME type using Java's built-in probe, with fallback to URLConnection. */
    private static String detectMime(Path file, String name) {
        try {
            String mime = Files.probeContentType(file);
            if (mime != null && !mime.isEmpty()) return mime;
        } catch (IOException ignored) {}

        String mime = URLConnection.guessContentTypeFromName(name);
        return mime != null ? mime : "application/octet-stream";
    }

    /**
     * Heuristic binary detection: check first 8KB for null bytes.
     * Also treats any non-text MIME prefix as binary.
     */
    private static boolean isBinary(Path file, String mimeType) {
        if (mimeType != null && !mimeType.startsWith("text/")) {
            // application/*, image/*, etc. — treat as binary unless we know better
            if (!mimeType.equals("application/json") &&
                !mimeType.equals("application/xml") &&
                !mimeType.equals("application/x-sh") &&
                !mimeType.equals("application/javascript")) {
                return true;
            }
        }
        // Scan first 8KB for null bytes
        try {
            byte[] buf = new byte[8192];
            int read;
            try (java.io.InputStream in = Files.newInputStream(file)) {
                read = in.read(buf);
            }
            for (int i = 0; i < read; i++) {
                if (buf[i] == 0) return true;
            }
        } catch (IOException ignored) {}
        return false;
    }
}
