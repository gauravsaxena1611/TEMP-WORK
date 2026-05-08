package com.airborne.core;

import javax.crypto.AEADBadTagException;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Decrypts AES-256-GCM ciphertext using a KeyMaterial object.
 */
public class Decryptor {

    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final int TAG_BITS = 128;

    /**
     * Decrypt ciphertext using the key material from the Key QR.
     *
     * @param ciphertext   Encrypted bytes (with GCM tag appended).
     * @param keyMaterial  Key material parsed from the Key QR JSON string.
     * @return Decrypted plaintext bytes.
     * @throws IllegalArgumentException If the hash does not match (integrity pre-check).
     * @throws SecurityException        If AES-GCM tag verification fails (tamper detected).
     */
    public byte[] decrypt(byte[] ciphertext, KeyMaterial keyMaterial) {
        // Pre-check: verify hash of encrypted blob before attempting decryption
        String actualHash = Encryptor.sha256Hex(ciphertext);
        if (!actualHash.equals(keyMaterial.encryptedHash)) {
            throw new IllegalArgumentException(
                    "The video data does not match the key. This usually means the video was " +
                    "recorded from a different transfer session. Try scanning the Key QR again " +
                    "or ask the sender to display the stream again.");
        }

        try {
            byte[] keyBytes = keyMaterial.getKeyBytes();
            byte[] ivBytes = keyMaterial.getIvBytes();

            SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
            GCMParameterSpec spec = new GCMParameterSpec(TAG_BITS, ivBytes);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key, spec);
            return cipher.doFinal(ciphertext);

        } catch (AEADBadTagException e) {
            throw new SecurityException(
                    "Decryption failed. The data may be corrupted or the wrong key was used. " +
                    "Verify the key string is complete and try again.", e);
        } catch (Exception e) {
            throw new SecurityException(
                    "Decryption failed. The data may be corrupted or the wrong key was used. " +
                    "Verify the key string is complete and try again.", e);
        }
    }
}
