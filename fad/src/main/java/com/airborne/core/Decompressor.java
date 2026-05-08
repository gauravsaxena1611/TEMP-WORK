package com.airborne.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

/**
 * Decompresses a GZip-compressed byte array.
 */
public class Decompressor {

    /**
     * Decompress GZip-compressed bytes.
     *
     * @param input Compressed bytes.
     * @return Decompressed bytes.
     * @throws IOException If input is not valid GZip data.
     */
    public byte[] decompress(byte[] input) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream(input.length * 4);
        try (GZIPInputStream gzip = new GZIPInputStream(new ByteArrayInputStream(input))) {
            byte[] buf = new byte[65536];
            int n;
            while ((n = gzip.read(buf)) != -1) {
                out.write(buf, 0, n);
            }
        }
        return out.toByteArray();
    }
}
