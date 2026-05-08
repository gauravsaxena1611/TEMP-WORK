package com.airborne.fountain;

import java.util.LinkedHashSet;
import java.util.Random;

/**
 * Robust Soliton degree distribution for LT fountain codes.
 *
 * Combines the ideal soliton distribution with a "spike" correction term
 * to ensure near-optimal decoding with high probability.
 *
 * Parameters: c = 0.03, delta = 0.05 (standard values from Luby 2002).
 */
public class RobustSoliton {

    private static final double C = 0.03;
    private static final double DELTA = 0.05;

    private final int k;
    private final double[] cdf;  // cumulative distribution, length k

    public RobustSoliton(int k) {
        if (k < 1) throw new IllegalArgumentException("k must be >= 1");
        this.k = k;
        this.cdf = buildCdf(k);
    }

    /**
     * Sample a degree from the Robust Soliton distribution using the given RNG.
     * Advances the RNG by exactly one nextDouble() call.
     */
    public int sampleDegree(Random rng) {
        double u = rng.nextDouble();
        // Binary search for u in CDF
        int lo = 0, hi = k - 1;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (cdf[mid] < u) lo = mid + 1;
            else hi = mid;
        }
        return lo + 1;  // degree is 1-indexed
    }

    /**
     * Sample `degree` distinct block indices from [0, k) using the given RNG.
     * Uses rejection sampling with a LinkedHashSet to preserve insertion order
     * (ensuring encoder and decoder produce the same sequence from the same seed).
     */
    public int[] sampleIndices(Random rng, int degree) {
        degree = Math.min(degree, k);  // safety cap
        LinkedHashSet<Integer> chosen = new LinkedHashSet<>(degree * 2);
        while (chosen.size() < degree) {
            chosen.add(rng.nextInt(k));
        }
        int[] result = new int[degree];
        int i = 0;
        for (int idx : chosen) result[i++] = idx;
        return result;
    }

    private static double[] buildCdf(int k) {
        // R = c * ln(k / delta) * sqrt(k)
        double r = C * Math.log((double) k / DELTA) * Math.sqrt(k);
        int s = Math.max(1, (int) Math.round((double) k / r));

        double[] pdf = new double[k];

        // Ideal soliton ρ(d)
        pdf[0] = 1.0 / k;                      // d=1
        for (int d = 2; d <= k; d++) {
            pdf[d - 1] = 1.0 / ((double) d * (d - 1));
        }

        // Robust correction τ(d)
        for (int d = 1; d <= k; d++) {
            if (d < s) {
                pdf[d - 1] += r / ((double) k * d);
            } else if (d == s) {
                pdf[d - 1] += (r * Math.log(r / DELTA)) / k;
            }
        }

        // Normalize and build CDF
        double sum = 0;
        for (double p : pdf) sum += p;
        double[] cdf = new double[k];
        double cumulative = 0;
        for (int i = 0; i < k; i++) {
            cumulative += pdf[i] / sum;
            cdf[i] = cumulative;
        }
        cdf[k - 1] = 1.0;  // clamp last bucket
        return cdf;
    }
}
