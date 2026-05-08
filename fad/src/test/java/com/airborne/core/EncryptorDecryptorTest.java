package com.airborne.core;

import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

class EncryptorDecryptorTest {

    private final Encryptor encryptor = new Encryptor();
    private final Decryptor decryptor = new Decryptor();

    @Test
    void encryptThenDecryptProducesIdenticalPlaintext() {
        byte[] original = "Hello, Airborne Copier! This is a test payload.".getBytes();

        Encryptor.EncryptionResult result = encryptor.encrypt(original);
        byte[] decrypted = decryptor.decrypt(result.ciphertext, result.keyMaterial);

        assertArrayEquals(original, decrypted, "Decrypted bytes must match original plaintext");
    }

    @Test
    void encryptThenDecryptLargePayload() {
        byte[] original = new byte[500_000];
        for (int i = 0; i < original.length; i++) original[i] = (byte) (i & 0xFF);

        Encryptor.EncryptionResult result = encryptor.encrypt(original);
        byte[] decrypted = decryptor.decrypt(result.ciphertext, result.keyMaterial);

        assertArrayEquals(original, decrypted, "Large payload must round-trip identically");
    }

    @Test
    void tamperedCiphertextFailsDecryption() {
        byte[] original = "sensitive data".getBytes();
        Encryptor.EncryptionResult result = encryptor.encrypt(original);

        // Flip a bit in the middle of the ciphertext
        byte[] tampered = result.ciphertext.clone();
        tampered[tampered.length / 2] ^= 0xFF;

        // Hash will mismatch first (IllegalArgumentException) — that's fine, still a failure
        assertThrows(Exception.class,
                () -> decryptor.decrypt(tampered, result.keyMaterial),
                "Tampered ciphertext must throw an exception");
    }

    @Test
    void wrongKeyFailsDecryption() {
        byte[] original = "secret content".getBytes();
        Encryptor.EncryptionResult result = encryptor.encrypt(original);

        // Generate a fresh key (different)
        Encryptor.EncryptionResult other = encryptor.encrypt("other".getBytes());

        KeyMaterial wrongKey = result.keyMaterial;
        wrongKey.key = other.keyMaterial.key;  // swap key, keep correct hash
        // The hash will match but GCM tag will fail
        wrongKey.encryptedHash = Encryptor.sha256Hex(result.ciphertext);  // restore correct hash

        assertThrows(SecurityException.class,
                () -> decryptor.decrypt(result.ciphertext, wrongKey),
                "Wrong AES key must cause GCM tag failure / SecurityException");
    }

    @Test
    void hashMismatchIsDetectedBeforeDecryption() {
        byte[] original = "data".getBytes();
        Encryptor.EncryptionResult result = encryptor.encrypt(original);

        // Corrupt the hash in key material (simulates mismatched transfer session)
        result.keyMaterial.encryptedHash = "0000000000000000000000000000000000000000000000000000000000000000";

        assertThrows(IllegalArgumentException.class,
                () -> decryptor.decrypt(result.ciphertext, result.keyMaterial),
                "Hash mismatch must throw IllegalArgumentException before decryption");
    }

    @Test
    void keyMaterialJsonRoundTrip() {
        byte[] original = "round-trip test".getBytes();
        Encryptor.EncryptionResult result = encryptor.encrypt(original);

        String json = result.keyMaterial.toJson();
        KeyMaterial parsed = KeyMaterial.fromJson(json);

        // Verify decryption still works after JSON round-trip
        byte[] decrypted = decryptor.decrypt(result.ciphertext, parsed);
        assertArrayEquals(original, decrypted, "KeyMaterial JSON round-trip must preserve decryptability");
    }
}
