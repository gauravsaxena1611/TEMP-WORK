package com.airborne.core;

import java.util.List;

/**
 * Transfer manifest: describes all files and directories in the transfer.
 * Serialized as JSON inside the binary blob.
 */
public class Manifest {
    public String version = "1.0";
    public String created;           // ISO-8601 timestamp
    public String sourceRoot;        // name of the root folder
    public int totalFiles;
    public int totalDirs;
    public long totalDataBytes;
    public List<FileNode> entries;   // pre-order DFS, dirs before children
}
