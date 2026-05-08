package com.airborne.core;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Encrypts a byte array using AES-256-GCM.
 * Generates a fresh 256-bit key and 96-bit IV for each encryption.
 */
public class Encryptor {

    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final int KEY_BITS = 256;
    private static final int IV_BYTES = 12;   // 96-bit IV for GCM
    private static final int TAG_BITS = 128;  // 128-bit GCM authentication tag

    /**
     * Result of an encryption operation.
     */
    public static class EncryptionResult {
        public final byte[] ciphertext;     // encrypted bytes (tag appended by JCE)
        public final KeyMaterial keyMaterial;

        EncryptionResult(byte[] ciphertext, KeyMaterial keyMaterial) {
            this.ciphertext = ciphertext;
            this.keyMaterial = keyMaterial;
        }
    }

    /**
     * Encrypt plaintext with a freshly generated AES-256-GCM key.
     *
     * @param plaintext Bytes to encrypt.
     * @return EncryptionResult with ciphertext and key material.
     */
    public EncryptionResult encrypt(byte[] plaintext) {
        try {
            // Generate key and IV
            SecureRandom rng = SecureRandom.getInstanceStrong();
            byte[] keyBytes = new byte[KEY_BITS / 8];
            byte[] ivBytes = new byte[IV_BYTES];
            rng.nextBytes(keyBytes);
            rng.nextBytes(ivBytes);

            // Encrypt
            SecretKey key = new SecretKeySpec(keyBytes, "AES");
            GCMParameterSpec spec = new GCMParameterSpec(TAG_BITS, ivBytes);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key, spec);
            byte[] ciphertext = cipher.doFinal(plaintext);

            // SHA-256 of the ciphertext (for integrity pre-check before decryption)
            String hash = sha256Hex(ciphertext);

            KeyMaterial km = new KeyMaterial();
            km.key = Base64.getEncoder().encodeToString(keyBytes);
            km.iv = Base64.getEncoder().encodeToString(ivBytes);
            km.encryptedHash = hash;

            return new EncryptionResult(ciphertext, km);
        } catch (Exception e) {
            throw new RuntimeException("Encryption failed: " + e.getMessage(), e);
        }
    }

    /** Compute SHA-256 hex string of input bytes. */
    public static String sha256Hex(byte[] data) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(data);
            StringBuilder sb = new StringBuilder(digest.length * 2);
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("SHA-256 computation failed", e);
        }
    }
}
