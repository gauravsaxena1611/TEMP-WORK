package com.airborne.fountain;

import java.util.*;

/**
 * Luby Transform (LT) fountain code decoder using belief propagation (peeling algorithm).
 *
 * Accepts packets in any order. Reconstruction succeeds when enough unique packets
 * have been received (typically K * 1.05 to 1.10, where K is the number of source blocks).
 *
 * Algorithm:
 *   1. For each received packet, reconstruct which source blocks it covers (from the seed).
 *   2. XOR any already-recovered blocks out of the packet payload, reducing its effective degree.
 *   3. Degree-1 packets directly reveal a source block.
 *   4. Recovering a block allows us to reduce the degree of all other packets covering it.
 *   5. Repeat until all K blocks are recovered or no progress can be made.
 */
public class LTDecoder {

    private final int k;
    private final int blockSize;
    private final int dataLength;
    private final RobustSoliton soliton;

    // Recovered source blocks. recovered[i] is non-null when block i has been decoded.
    private final byte[][] recovered;
    private int recoveredCount = 0;

    // Per-packet state (indexed by packet slot)
    private final List<List<Integer>> packetIndices;  // mutable list of remaining block indices
    private final List<byte[]> packetPayloads;         // current (partially XORed) payload

    // Inverted index: blockToPackets[i] = packet slots that still reference block i
    private final List<List<Integer>> blockToPackets;

    // Queue of packet slots with exactly 1 remaining block index (ready to peel)
    private final Queue<Integer> rippleQueue;

    // Deduplication: reject duplicate seeds
    private final Set<Integer> seenSeeds;

    /**
     * @param k          Number of source blocks (from FountainPacket.totalBlocks).
     * @param blockSize  Source block size in bytes (must match encoder).
     * @param dataLength Original data length in bytes (used to trim final output).
     */
    public LTDecoder(int k, int blockSize, int dataLength) {
        if (k < 1) throw new IllegalArgumentException("k must be >= 1");
        this.k = k;
        this.blockSize = blockSize;
        this.dataLength = dataLength;
        this.soliton = new RobustSoliton(k);

        this.recovered = new byte[k][];
        this.packetIndices = new ArrayList<>();
        this.packetPayloads = new ArrayList<>();
        this.blockToPackets = new ArrayList<>(k);
        for (int i = 0; i < k; i++) blockToPackets.add(new ArrayList<>());
        this.rippleQueue = new ArrayDeque<>();
        this.seenSeeds = new HashSet<>();
    }

    /**
     * Add a received packet to the decoder and attempt decoding.
     *
     * @param packet The received FountainPacket.
     * @return true if decoding is now complete.
     */
    public boolean addPacket(FountainPacket packet) {
        if (isComplete()) return true;
        if (seenSeeds.contains(packet.seed)) return false;  // duplicate
        seenSeeds.add(packet.seed);

        // Reconstruct which blocks this packet covers (same RNG as encoder)
        Random rng = new Random(packet.seed);
        int degree = soliton.sampleDegree(rng);
        int[] allIndices = soliton.sampleIndices(rng, degree);

        // Clone payload so we can mutate it
        byte[] payload = packet.payload.clone();

        // XOR out any already-recovered blocks, build list of remaining unknowns
        List<Integer> remaining = new ArrayList<>(degree);
        for (int idx : allIndices) {
            if (recovered[idx] != null) {
                xorInto(payload, recovered[idx]);
            } else {
                remaining.add(idx);
            }
        }

        if (remaining.isEmpty()) {
            // Packet contributed no new information (all its blocks are known)
            return isComplete();
        }

        int slot = packetIndices.size();
        packetIndices.add(remaining);
        packetPayloads.add(payload);

        for (int idx : remaining) {
            blockToPackets.get(idx).add(slot);
        }

        if (remaining.size() == 1) {
            rippleQueue.add(slot);
        }

        peel();
        return isComplete();
    }

    /**
     * Process the ripple queue: recover blocks from degree-1 packets and propagate.
     */
    private void peel() {
        while (!rippleQueue.isEmpty()) {
            int slot = rippleQueue.poll();
            List<Integer> indices = packetIndices.get(slot);

            if (indices.size() != 1) continue;  // was updated by a previous peel step

            int blockIdx = indices.get(0);
            if (recovered[blockIdx] != null) continue;  // block already recovered another way

            // Recover this source block
            recovered[blockIdx] = packetPayloads.get(slot).clone();
            recoveredCount++;

            if (recoveredCount == k) return;  // done

            // Reduce all other packets that cover this block
            for (int otherSlot : blockToPackets.get(blockIdx)) {
                if (otherSlot == slot) continue;
                List<Integer> otherIndices = packetIndices.get(otherSlot);
                boolean removed = otherIndices.remove(Integer.valueOf(blockIdx));
                if (removed) {
                    xorInto(packetPayloads.get(otherSlot), recovered[blockIdx]);
                    if (otherIndices.size() == 1) {
                        rippleQueue.add(otherSlot);
                    }
                }
            }
        }
    }

    /** @return true when all K source blocks have been recovered. */
    public boolean isComplete() {
        return recoveredCount == k;
    }

    /** @return How many unique source blocks have been recovered so far. */
    public int getRecoveredCount() { return recoveredCount; }

    /** @return Total blocks K required for full reconstruction. */
    public int getTotalBlocks() { return k; }

    /** @return Number of unique packets received (including duplicates rejected). */
    public int getPacketsReceived() { return seenSeeds.size(); }

    /**
     * Return the reconstructed data after successful decoding.
     * @throws IllegalStateException if decoding is not yet complete.
     */
    public byte[] getReconstructedData() {
        if (!isComplete()) {
            throw new IllegalStateException(
                    "Decoding is not complete. Recovered " + recoveredCount + " of " + k + " blocks.");
        }
        byte[] result = new byte[dataLength];
        for (int i = 0; i < k; i++) {
            int destOffset = i * blockSize;
            int copyLen = Math.min(blockSize, dataLength - destOffset);
            if (copyLen > 0) {
                System.arraycopy(recovered[i], 0, result, destOffset, copyLen);
            }
        }
        return result;
    }

    private static void xorInto(byte[] target, byte[] source) {
        int len = Math.min(target.length, source.length);
        for (int i = 0; i < len; i++) {
            target[i] ^= source[i];
        }
    }
}
