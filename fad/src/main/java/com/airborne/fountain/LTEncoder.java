package com.airborne.fountain;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Luby Transform (LT) fountain code encoder.
 *
 * Splits input data into K source blocks of `blockSize` bytes and produces
 * an infinite stream of encoded packets. Each packet encodes the XOR of a
 * degree-d subset of source blocks, where d is drawn from the Robust Soliton
 * distribution. Each packet has a unique random seed.
 */
public class LTEncoder {

    public static final int DEFAULT_BLOCK_SIZE = 1000;

    private final int projectId;
    private final int k;            // number of source blocks
    private final int blockSize;
    private final int dataLength;   // original data length before padding
    private final byte[][] blocks;  // source blocks, all exactly blockSize bytes
    private final RobustSoliton soliton;
    private final SecureRandom secureRandom;
    private final Set<Integer> usedSeeds;

    /**
     * @param data      The encrypted blob to encode.
     * @param projectId Transfer identifier (random int32).
     */
    public LTEncoder(byte[] data, int projectId) {
        this(data, projectId, DEFAULT_BLOCK_SIZE);
    }

    public LTEncoder(byte[] data, int projectId, int blockSize) {
        this.projectId = projectId;
        this.blockSize = blockSize;
        this.dataLength = data.length;
        this.k = (int) Math.ceil((double) data.length / blockSize);
        this.soliton = new RobustSoliton(k);
        this.secureRandom = new SecureRandom();
        this.usedSeeds = new HashSet<>();

        // Split data into k blocks, zero-pad the last block if needed
        this.blocks = new byte[k][blockSize];
        for (int i = 0; i < k; i++) {
            int srcOffset = i * blockSize;
            int copyLen = Math.min(blockSize, data.length - srcOffset);
            System.arraycopy(data, srcOffset, blocks[i], 0, copyLen);
            // Remaining bytes in blocks[i] are already zero (Java default)
        }
    }

    /** Return the number of source blocks K. */
    public int getK() { return k; }

    /** Return the block size in bytes. */
    public int getBlockSize() { return blockSize; }

    /** Return the original data length in bytes. */
    public int getDataLength() { return dataLength; }

    /** Return the project ID for this encoder. */
    public int getProjectId() { return projectId; }

    /**
     * Generate the next unique fountain code packet.
     * Thread-safe (synchronized on usedSeeds).
     */
    public FountainPacket nextPacket() {
        int seed = uniqueSeed();
        Random rng = new Random(seed);

        int degree = soliton.sampleDegree(rng);
        int[] indices = soliton.sampleIndices(rng, degree);

        // XOR selected source blocks
        byte[] payload = new byte[blockSize];
        for (int idx : indices) {
            xorInto(payload, blocks[idx]);
        }

        return new FountainPacket(projectId, k, seed, payload);
    }

    /**
     * Generate a fresh unique seed not used in this session.
     * Uses SecureRandom to avoid repeats in reasonable session sizes.
     */
    private synchronized int uniqueSeed() {
        int seed;
        do {
            seed = secureRandom.nextInt();
        } while (usedSeeds.contains(seed));
        usedSeeds.add(seed);
        return seed;
    }

    private static void xorInto(byte[] target, byte[] source) {
        for (int i = 0; i < target.length; i++) {
            target[i] ^= source[i];
        }
    }
}
