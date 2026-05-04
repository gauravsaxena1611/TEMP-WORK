package com.airborne.core;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CompressorDecompressorTest {

    private final Compressor compressor = new Compressor();
    private final Decompressor decompressor = new Decompressor();

    @Test
    void compressThenDecompressProducesIdenticalBytes() throws IOException {
        byte[] original = "Hello, this is test data for compression round-trip!".repeat(100).getBytes();

        byte[] compressed = compressor.compress(original);
        byte[] restored = decompressor.decompress(compressed);

        assertArrayEquals(original, restored, "Decompressed data must match original");
    }

    @Test
    void compressReducesSizeForRepetitiveData() throws IOException {
        byte[] repetitive = new byte[10_000];
        Arrays.fill(repetitive, (byte) 'A');

        byte[] compressed = compressor.compress(repetitive);

        assertTrue(compressed.length < repetitive.length,
                "GZip must reduce size for highly repetitive data");
    }

    @Test
    void handlesBinaryData() throws IOException {
        byte[] binary = new byte[1024];
        for (int i = 0; i < binary.length; i++) binary[i] = (byte) i;

        byte[] compressed = compressor.compress(binary);
        byte[] restored = decompressor.decompress(compressed);

        assertArrayEquals(binary, restored, "Binary data must survive compression round-trip");
    }

    @Test
    void handlesEmptyArray() throws IOException {
        byte[] empty = new byte[0];

        byte[] compressed = compressor.compress(empty);
        byte[] restored = decompressor.decompress(compressed);

        assertArrayEquals(empty, restored, "Empty array must survive compression round-trip");
    }
}
