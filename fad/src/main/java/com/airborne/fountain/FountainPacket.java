package com.airborne.fountain;

import java.nio.ByteBuffer;

/**
 * A single fountain code packet: 12-byte header + variable-length payload.
 *
 * Binary layout:
 *   bytes 0-3:    projectId   (big-endian int32)
 *   bytes 4-7:    totalBlocks (big-endian int32)  — K
 *   bytes 8-11:   seed        (big-endian int32)
 *   bytes 12-end: payload     (XOR of selected source blocks)
 */
public class FountainPacket {

    public static final int HEADER_SIZE = 12;

    public int projectId;
    public int totalBlocks;
    public int seed;
    public byte[] payload;

    public FountainPacket() {}

    public FountainPacket(int projectId, int totalBlocks, int seed, byte[] payload) {
        this.projectId = projectId;
        this.totalBlocks = totalBlocks;
        this.seed = seed;
        this.payload = payload;
    }

    /** Serialize to bytes for QR encoding. */
    public byte[] serialize() {
        byte[] out = new byte[HEADER_SIZE + payload.length];
        ByteBuffer buf = ByteBuffer.wrap(out);
        buf.putInt(projectId);
        buf.putInt(totalBlocks);
        buf.putInt(seed);
        buf.put(payload);
        return out;
    }

    /**
     * Deserialize from bytes (from QR decoding).
     * @throws IllegalArgumentException if data is too short.
     */
    public static FountainPacket deserialize(byte[] data) {
        if (data == null || data.length < HEADER_SIZE) {
            throw new IllegalArgumentException(
                    "Packet data too short: " + (data == null ? 0 : data.length));
        }
        ByteBuffer buf = ByteBuffer.wrap(data);
        FountainPacket p = new FountainPacket();
        p.projectId = buf.getInt();
        p.totalBlocks = buf.getInt();
        p.seed = buf.getInt();
        p.payload = new byte[data.length - HEADER_SIZE];
        buf.get(p.payload);
        return p;
    }
}
