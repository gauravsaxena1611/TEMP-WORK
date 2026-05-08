package com.airborne.core;

/**
 * Metadata model for a single file or directory entry in the transfer manifest.
 */
public class FileNode {
    public String relativePath;   // forward-slash separated, relative to selected root
    public String name;           // filename or directory name
    public String extension;      // file extension, empty string for dirs
    public boolean isDirectory;
    public boolean isSymlink;
    public String symlinkTarget;  // null if not a symlink
    public long sizeBytes;        // 0 for directories
    public long lastModified;     // epoch milliseconds
    public String mimeType;
    public boolean isBinary;
    public long dataOffset;       // byte offset in DATA_SECTION
    public long dataLength;       // byte length in DATA_SECTION (0 for dirs)

    public FileNode() {}

    public FileNode(String relativePath, String name, String extension,
                    boolean isDirectory, boolean isSymlink, String symlinkTarget,
                    long sizeBytes, long lastModified, String mimeType, boolean isBinary) {
        this.relativePath = relativePath;
        this.name = name;
        this.extension = extension;
        this.isDirectory = isDirectory;
        this.isSymlink = isSymlink;
        this.symlinkTarget = symlinkTarget;
        this.sizeBytes = sizeBytes;
        this.lastModified = lastModified;
        this.mimeType = mimeType;
        this.isBinary = isBinary;
        this.dataOffset = 0;
        this.dataLength = 0;
    }
}
