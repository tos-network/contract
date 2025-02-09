package java.lang.contract;

import java.lang.System;

/**
 * Represents a message with a sender and data.
 */
public class Storage {
  
    /**
     * Native method to calculate keccak256 hash.
     *
     * @param input The input byte array to hash
     * @return The keccak256 hash result
     */
    private native byte[] keccak256(byte[] input);

    /**
     * Native method to get the value of the storage.
     *
     * @param slot The slot to get the value for.
     * @return The value as a byte array.
     */
    private native byte[] storageGetFixed(int slot);

    /**
     * Native method to set the value of the storage.
     *
     * @param slot The slot to set the value for.
     * @param value The value to set.
     */
    private native void storageSetFixed(int slot, byte[] value);

    /**
     * Native method to compute the slot for a nested mapping.
     *
     * @param slot The base slot for the mapping
     * @param keyBytes The key bytes to compute the slot for
     * @return The computed slot
     */ 
    private native byte[] storageComputeNestedSlotMapping(byte[] slot, byte[] keyBytes);

    /**
     * Native method to get the value from mapping storage.
     *
     * @param slot The 32-byte storage slot
     * @param key The key in the mapping.
     * @return The value as a byte array.
     */
    private native byte[] storageGetMapping(byte[] slot, byte[] key);

    /**
     * Native method to set the value in mapping storage.
     *
     * @param slot The 32-byte storage slot
     * @param key The key in the mapping.
     * @param value The value to set.
     */
    private native void storageSetMapping(byte[] slot, byte[] key, byte[] value);

    /**
     * Native method to get the string value from storage.
     *
     * @param slot The slot to get the value for.
     * @return The value as a byte array.
     */
    private native byte[] storageGetString(int slot);

    /**
     * Native method to set the string value in storage.
     *
     * @param slot The slot to set the value for.
     * @param value The value to set.
     */
    private native void storageSetString(int slot, byte[] value);
    

    // Singleton pattern
    private static Storage storage;

    /**
     * Constructs a new Storage object.
     *
     */
    public Storage() {
    }

    /**
     * Returns a new Storage object.
     *
     * @return A new Storage object.
     */
    public static Storage getStorage() {
        if (storage == null) {
            storage = new Storage();
        }
        return storage;
    }

    /**
     * Retrieves a fixed value from the storage.
     *
     * @param slot The slot to retrieve the value from.
     * @return The value as a byte array.
     */
    public byte[] GetStorageFixedValue(int slot) {
        System.out.println("Getting fixed value from slot: " + slot);
        return storageGetFixed(slot);
    }

    /**
     * Sets a fixed value in the storage.
     *
     * @param slot The slot to set the value for.
     * @param value The value to set.
     */
    public void SetStorageFixedValue(int slot, byte[] value) {
        storageSetFixed(slot, value);
    }

    /**
     * Computes the slot for a nested mapping.
     *
     * @param slot The base slot for the mapping
     * @param keyBytes The key bytes to compute the slot for
     * @return The computed slot
     */
    public byte[] ComputeNestedMappingSlot(byte[] slot, byte[] keyBytes) {
        return storageComputeNestedSlotMapping(slot, keyBytes);
    }

    /**
     * Retrieves a mapping value from the storage.
     *
     * @param slot The slot to retrieve the value from.
     * @param key The key in the mapping.
     * @return The value as a byte array.
     */
    public byte[] GetStorageMappingValue(byte[] slot, byte[] key) {
        return storageGetMapping(slot, key);
    }

    /**
     * Sets a mapping value in the storage.
     *  
     * @param slot The slot to set the value for.
     * @param key The key in the mapping.
     * @param value The value to set.
     */
    public void SetStorageMappingValue(byte[] slot, byte[] key, byte[] value) {
        storageSetMapping(slot, key, value);
    }

    /**
     * Converts a byte array to an integer.
     *
     * @param bytes The byte array to convert.
     * @return The integer value.
     */
    private int bytesToInt(byte[] bytes) {
        int result = 0;
        for (int i = 0; i < 4; i++) {
            result = (result << 8) | (bytes[i] & 0xFF);
        }
        return result;
    }

    /**
     * Sets a dynamic array in the storage.
     *
     * @param arraySlot The slot to store the array.
     * @param values The array to store.
     */
    public void setDynamicArray(int arraySlot, byte[][] values) {
        // 1. storage length of arraySlot
        int length = values.length;
        byte[] lengthBytes = new byte[32];
        // convert length to 32 bytes
        for (int i = 0; i < 32; i++) {
            lengthBytes[31-i] = (byte)(length >> (i * 8));
        }
        storageSetFixed(arraySlot, lengthBytes);

        // 2. calculate data start position
        // dataSlot = keccak256(arraySlot)
        byte[] arraySlotBytes = new byte[32];
        for (int i = 0; i < 32; i++) {
            arraySlotBytes[31-i] = (byte)(arraySlot >> (i * 8));
        }
        byte[] dataSlotBytes = keccak256(arraySlotBytes);
        int dataSlot = bytesToInt(dataSlotBytes);

        // 3. storage each element
        for (int i = 0; i < length; i++) {
            int itemSlot = dataSlot + i;
            storageSetString(itemSlot, values[i]);  // use storageSetString
        }
    }

    /**
     * Retrieves a dynamic array from the storage.
     *
     * @param arraySlot The slot to retrieve the array from.
     * @return The retrieved array.
     */
    public byte[][] getDynamicArray(int arraySlot) {
        // 1. get array length
        byte[] lengthBytes = storageGetFixed(arraySlot);
        int length = bytesToInt(lengthBytes);

        // 2. calculate data start position
        byte[] arraySlotBytes = new byte[32];
        for (int i = 0; i < 32; i++) {
            arraySlotBytes[31-i] = (byte)(arraySlot >> (i * 8));
        }
        byte[] dataSlotBytes = keccak256(arraySlotBytes);
        int dataSlot = bytesToInt(dataSlotBytes);

        // 3. read each element
        byte[][] result = new byte[length][];
        for (int i = 0; i < length; i++) {
            int itemSlot = dataSlot + i;
            result[i] = storageGetString(itemSlot);  // use storageGetString
        }
        return result;
    }

    /**
     * Load the native library when the class is loaded.
     */
    static {
        System.loadLibrary("java.lang.contract.Storage"); // Load libstorage.so/storage.dll
    }

}