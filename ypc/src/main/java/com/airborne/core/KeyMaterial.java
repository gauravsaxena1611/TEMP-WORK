package com.airborne.core;

import com.google.gson.Gson;

import java.util.Base64;

/**
 * Encryption key material transmitted via the Key QR code.
 * This JSON is scanned by phone, sent to laptop, pasted into the receiver app.
 */
public class KeyMaterial {
    public String version = "1.0";
    public long projectId;          // random int32 as long, identifies transfer
    public String key;              // Base64-encoded 32-byte AES-256 key
    public String iv;               // Base64-encoded 12-byte GCM IV
    public String encryptedHash;    // SHA-256 hex of the encrypted blob
    public int totalBlocks;         // K — number of fountain code source blocks
    public int sourceFiles;
    public int sourceDirs;
    public long uncompressedBytes;
    public long compressedBytes;
    public long encryptedBytes;
    public String timestamp;        // ISO-8601

    public KeyMaterial() {}

    /** Decode Base64 key field to raw bytes. */
    public byte[] getKeyBytes() {
        return Base64.getDecoder().decode(key);
    }

    /** Decode Base64 IV field to raw bytes. */
    public byte[] getIvBytes() {
        return Base64.getDecoder().decode(iv);
    }

    /** Serialize to compact JSON string (for QR encoding). */
    public String toJson() {
        return new Gson().toJson(this);
    }

    /** Parse from JSON string (from QR scan). */
    public static KeyMaterial fromJson(String json) {
        return new Gson().fromJson(json, KeyMaterial.class);
    }
}
