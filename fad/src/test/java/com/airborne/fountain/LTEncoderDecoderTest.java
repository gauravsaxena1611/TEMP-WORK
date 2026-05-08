package com.airborne.fountain;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class LTEncoderDecoderTest {

    private static final int PROJECT_ID = 42;

    // -----------------------------------------------------------------------
    // Encoder tests
    // -----------------------------------------------------------------------

    @Test
    void encoderReportsCorrectBlockCount() {
        byte[] data = new byte[5000];
        LTEncoder enc = new LTEncoder(data, PROJECT_ID, 1000);
        assertEquals(5, enc.getK(), "5000 bytes / 1000 block size = 5 blocks");
    }

    @Test
    void encoderHandlesDataNotMultipleOfBlockSize() {
        byte[] data = new byte[3500];
        LTEncoder enc = new LTEncoder(data, PROJECT_ID, 1000);
        assertEquals(4, enc.getK(), "ceil(3500/1000) = 4 blocks");
    }

    @Test
    void packetsHaveUniqueSeeds() {
        byte[] data = randomBytes(10_000);
        LTEncoder enc = new LTEncoder(data, PROJECT_ID, 1000);

        Set<Integer> seeds = new HashSet<>();
        for (int i = 0; i < 200; i++) {
            FountainPacket pkt = enc.nextPacket();
            assertTrue(seeds.add(pkt.seed), "Seed must be unique: " + pkt.seed);
        }
    }

    @Test
    void packetHeaderFieldsAreCorrect() {
        byte[] data = randomBytes(3000);
        LTEncoder enc = new LTEncoder(data, PROJECT_ID, 1000);
        FountainPacket pkt = enc.nextPacket();

        assertEquals(PROJECT_ID, pkt.projectId);
        assertEquals(3, pkt.totalBlocks);  // ceil(3000/1000)
        assertEquals(1000, pkt.payload.length);
    }

    @Test
    void packetSerializeDeserializeRoundTrip() {
        byte[] data = randomBytes(5000);
        LTEncoder enc = new LTEncoder(data, PROJECT_ID, 1000);
        FountainPacket original = enc.nextPacket();

        byte[] bytes = original.serialize();
        FountainPacket restored = FountainPacket.deserialize(bytes);

        assertEquals(original.projectId, restored.projectId);
        assertEquals(original.totalBlocks, restored.totalBlocks);
        assertEquals(original.seed, restored.seed);
        assertArrayEquals(original.payload, restored.payload);
    }

    // -----------------------------------------------------------------------
    // Decoder tests — encode/decode round trips with various loss rates
    // -----------------------------------------------------------------------

    // c=0.03 in RobustSoliton works well for K >= 500 (production targets K >= 1000).
    // Tests use 500KB data = 500 blocks so the soliton operates in its intended range.

    @Test
    void decodeWithZeroLoss() throws Exception {
        byte[] original = randomBytes(500_000);   // K=500 blocks
        byte[] result = roundTrip(original, 0.0);
        assertArrayEquals(original, result, "0% loss: must reconstruct exactly");
    }

    @Test
    void decodeWithTenPercentLoss() throws Exception {
        byte[] original = randomBytes(500_000);   // K=500 blocks
        byte[] result = roundTrip(original, 0.10);
        assertArrayEquals(original, result, "10% loss: must reconstruct exactly");
    }

    @Test
    void decodeWithTwentyPercentLoss() throws Exception {
        byte[] original = randomBytes(500_000);   // K=500 blocks
        byte[] result = roundTrip(original, 0.20);
        assertArrayEquals(original, result, "20% loss: must reconstruct exactly");
    }

    @Test
    void decodeWithThirtyPercentLossReportsInsufficient() {
        // Feed only 70% of K packets (far below the K*1.05 threshold) and verify failure.
        // Use K=500 for realistic K range; 70% of 500 = 350 packets << 525 needed.
        byte[] original = randomBytes(500_000);
        LTEncoder enc = new LTEncoder(original, PROJECT_ID, 1000);
        int k = enc.getK();  // 500

        int packetsToCollect = (int) (k * 0.70);  // 350
        LTDecoder dec = new LTDecoder(k, enc.getBlockSize(), original.length);

        for (int i = 0; i < packetsToCollect; i++) {
            dec.addPacket(enc.nextPacket());
        }

        assertFalse(dec.isComplete(),
                "With only 70% of required packets, decoding must NOT be complete");
        assertThrows(IllegalStateException.class, dec::getReconstructedData,
                "Calling getReconstructedData() before completion must throw");
    }

    @Test
    void smallDataSingleBlock() throws Exception {
        byte[] original = randomBytes(500);  // K=1 block — trivial decode
        byte[] result = roundTrip(original, 0.0);
        assertArrayEquals(original, result, "Sub-block data must round-trip exactly");
    }

    @Test
    void largishData() throws Exception {
        byte[] original = randomBytes(1_000_000);  // 1 MB = K=1000 blocks
        byte[] result = roundTrip(original, 0.15);
        assertArrayEquals(original, result, "1MB with 15% loss must reconstruct exactly");
    }

    @Test
    void duplicatePacketsAreIgnored() throws Exception {
        byte[] original = randomBytes(500_000);   // K=500, soliton works reliably
        LTEncoder enc = new LTEncoder(original, PROJECT_ID, 1000);
        int k = enc.getK();
        LTDecoder dec = new LTDecoder(k, enc.getBlockSize(), original.length);

        // Generate enough packets then send each one twice
        List<FountainPacket> packets = new ArrayList<>();
        for (int i = 0; i < (int) (k * 1.5); i++) {
            packets.add(enc.nextPacket());
        }
        for (FountainPacket p : packets) dec.addPacket(p);
        for (FountainPacket p : packets) dec.addPacket(p);  // duplicates

        assertTrue(dec.isComplete(), "Decoding must succeed even when packets are sent twice");
        assertArrayEquals(original, dec.getReconstructedData());
    }

    // -----------------------------------------------------------------------
    // Helpers
    // -----------------------------------------------------------------------

    /**
     * Full encode → simulate loss → decode round trip.
     * Generates 1.5 * K packets then discards `lossRate` fraction at random.
     */
    private byte[] roundTrip(byte[] data, double lossRate) {
        LTEncoder enc = new LTEncoder(data, PROJECT_ID, 1000);
        int k = enc.getK();

        // Generate 2x K packets — generous overhead ensures reliable reconstruction with loss.
        // Production uses ~1.05–1.10x; 2x here ensures deterministic test outcomes.
        int totalPackets = (int) (k * 2.0);
        List<FountainPacket> packets = new ArrayList<>(totalPackets);
        for (int i = 0; i < totalPackets; i++) {
            packets.add(enc.nextPacket());
        }

        // Simulate packet loss
        if (lossRate > 0) {
            Random rng = new Random(12345);
            Iterator<FountainPacket> it = packets.iterator();
            while (it.hasNext()) {
                it.next();
                if (rng.nextDouble() < lossRate) it.remove();
            }
        }

        // Decode
        LTDecoder dec = new LTDecoder(k, enc.getBlockSize(), data.length);
        for (FountainPacket pkt : packets) {
            if (dec.addPacket(pkt)) break;
        }

        assertTrue(dec.isComplete(),
                "Decoding must complete with " + (int)(lossRate * 100) + "% loss. " +
                "Recovered " + dec.getRecoveredCount() + " of " + k);
        return dec.getReconstructedData();
    }

    private byte[] randomBytes(int len) {
        byte[] buf = new byte[len];
        new Random(98765).nextBytes(buf);
        return buf;
    }
}
