package com.airborne.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.GZIPOutputStream;

/**
 * Compresses a byte array using GZip at maximum compression level.
 */
public class Compressor {

    /**
     * Compress the input using GZip BEST_COMPRESSION (level 9).
     *
     * @param input Raw bytes to compress.
     * @return Compressed bytes.
     * @throws IOException If compression fails.
     */
    public byte[] compress(byte[] input) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream(input.length / 2);
        try (GZIPOutputStream gzip = new GZIPOutputStream(out) {{
            def.setLevel(Deflater.BEST_COMPRESSION);
        }}) {
            gzip.write(input);
        }
        return out.toByteArray();
    }
}
