package java.crypto;

import java.lang.System;

/**
 * Represents a Keccak256 hash function.
 */
public class Keccak256 {
  
    /**
     * Native method to calculate keccak256 hash.
     *
     * @param input The input byte array to hash
     * @return The keccak256 hash result
     */
    public native byte[] keccak256(byte[] input);

    // Singleton pattern
    private static Keccak256 keccak256;

    /**
     * Constructs a new Keccak256 object.
     *
     */
    public Keccak256() {
    }

    /**
     * Returns a new Keccak256 object.
     *
     * @return A new Keccak256 object.
     */
    public static Keccak256 getKeccak256() {
        if (keccak256 == null) {
            keccak256 = new Keccak256();
        }
        return keccak256;
    }


    /**
     * sha3 hash
     * @param input
     * @return
     */
    public byte[] sha3(byte[] input) {
        return keccak256(input);
    }

    /**
     * Load the native library when the class is loaded.
     */
    static {
        System.loadLibrary("java.crypto.Keccak256"); // Load libkeccak256.so/keccak256.dll
    }

}