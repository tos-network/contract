package java.lang;

import java.io.Storable;
import java.lang.contract.Storage;
import java.util.HashMap;
import java.util.Map;

/**
 * A generic mapping implementation that mimics Solidity's mapping type.
 * Supports both simple and nested mappings with infinite nesting levels.
 *
 * @param <K> Type of the key (must implement Storable)
 * @param <V> Type of the value (must implement Storable)
 */
public class Mapping<K extends Storable, V extends Storable> implements java.io.Storable {
    private int slot = Storable.NO_SLOT;
    private final Map<String, Object> cache;  // Cache for both values and nested mappings
    private final Class<K> keyType;
    private final Class<V> valueType;
    private final boolean isNestedMapping;

    /**
     * Creates a new mapping instance with the specified key and value types
     *
     * @param keyType The class type of the key
     * @param valueType The class type of the value
     * @return A new mapping instance
     */
    public static <K extends Storable, V extends Storable> Mapping<K, V> of(Class<K> keyType, Class<V> valueType) {
        return new Mapping<>(keyType, valueType);
    }
    
    /**
     * Creates a new nested mapping instance with the specified key and value types
     *
     * @param keyType The class type of the key
     * @param valueType The class type of the value
     * @return A new nested mapping instance
     */
    @SuppressWarnings("unchecked")
    public static <K extends Storable, V extends Storable> Mapping<K, Mapping<K, V>> ofNested(Class<K> keyType, Class<V> valueType) {
        return new Mapping<>(keyType, (Class<Mapping<K, V>>) (Class<?>) Mapping.class);
    }

    /**
     * Constructor for creating a new mapping
     */
    public Mapping(Class<K> keyType, Class<V> valueType) {
        this.slot = Storable.NO_SLOT;
        this.cache = new HashMap<>();
        this.keyType = keyType;
        this.valueType = valueType;
        this.isNestedMapping = Mapping.class.isAssignableFrom(valueType);
    }

    @Override
    public void setSlot(int slot) {
        this.slot = slot;
    }

    @Override
    public int getSlot() {
        return slot;
    }

    /**
     * Generates a cache key from byte array
     */
    private String getCacheKey(byte[] keyBytes) {
        return java.util.Base64.getEncoder().encodeToString(keyBytes);
    }

    /**
     * Retrieves a value from the mapping using variable number of keys for nested access
     *
     * @param keys Variable number of keys for nested mapping access
     * @return The value associated with the key(s)
     * @throws IllegalStateException if slot is not set
     * @throws IllegalArgumentException if no keys are provided
     */
    @SafeVarargs
    @SuppressWarnings("unchecked")
    public final V get(K... keys) {
        if (slot == Storable.NO_SLOT) {
            throw new IllegalStateException("Mapping slot not set");
        }
        if (keys.length == 0) {
            throw new IllegalArgumentException("At least one key must be provided");
        }

        // Build cache key
        StringBuilder cacheKeyBuilder = new StringBuilder();
        for (K key : keys) {
            cacheKeyBuilder.append(getCacheKey(key.toByteArray())).append(":");
        }
        String fullCacheKey = cacheKeyBuilder.toString();

        // Check cache first
        Object cachedValue = cache.get(fullCacheKey);
        if (cachedValue != null) {
            return (V) cachedValue;
        }

        // Calculate final storage slot for nested mappings
        byte[] currentSlot = new byte[32];
        currentSlot[31] = (byte) slot;
        byte[] finalKeyBytes = null;
        
        for (int i = 0; i < keys.length; i++) {
            byte[] keyBytes = keys[i].toByteArray();
            if (i == keys.length - 1) {
                finalKeyBytes = keyBytes;
            } else {
                currentSlot = Storage.getStorage().ComputeNestedMappingSlot(currentSlot, keyBytes);
            }
        }

        // Retrieve from storage
        byte[] data = Storage.getStorage().GetStorageMappingValue(currentSlot, finalKeyBytes);
        
        // If no data found, return default value
        if (data == null || data.length == 0) {
            try {
                V defaultValue = valueType.getDeclaredConstructor().newInstance();
                cache.put(fullCacheKey, defaultValue);
                return defaultValue;
            } catch (Exception e) {
                throw new RuntimeException("Failed to create default value for type: " + valueType, e);
            }
        }

        // Create value from data
        V value = (V) Storable.newInstanceFromByteArray(data);
        cache.put(fullCacheKey, value);
        return value;
    }

    /**
     * Sets a value in the mapping using variable number of keys for nested access
     *
     * @param value The value to set
     * @param keys Variable number of keys for nested mapping access
     * @throws IllegalStateException if slot is not set
     * @throws IllegalArgumentException if no keys are provided
     */
    @SafeVarargs
    public final void set(V value, K... keys) {
        if (slot == Storable.NO_SLOT) {
            throw new IllegalStateException("Mapping slot not set");
        }
        if (keys.length == 0) {
            throw new IllegalArgumentException("At least one key must be provided");
        }

        // Build cache key
        StringBuilder cacheKeyBuilder = new StringBuilder();
        for (K key : keys) {
            cacheKeyBuilder.append(getCacheKey(key.toByteArray())).append(":");
        }
        String fullCacheKey = cacheKeyBuilder.toString();

        // Calculate final storage slot for nested mappings
        byte[] currentSlot = new byte[32];  // 32 bytes for ethereum storage slot
        currentSlot[31] = (byte) slot;      // Set initial slot in last byte
        byte[] finalKeyBytes = null;
        
        for (int i = 0; i < keys.length; i++) {
            byte[] keyBytes = keys[i].toByteArray();
            if (i == keys.length - 1) {
                finalKeyBytes = keyBytes;
            } else {
                currentSlot = Storage.getStorage().ComputeNestedMappingSlot(currentSlot, keyBytes);
            }
        }

        // Update storage
        byte[] data = value.toByteArray();
        Storage.getStorage().SetStorageMappingValue(currentSlot, finalKeyBytes, data);

        // Update cache
        cache.put(fullCacheKey, value);
    }

    /**
     * Deletes a value from the mapping by setting it to its default value
     * This mimics Solidity's delete keyword behavior
     *
     * @param keys Variable number of keys for nested mapping access
     * @throws IllegalStateException if slot is not set
     * @throws IllegalArgumentException if no keys are provided
     */
    @SafeVarargs
    public final void delete(K... keys) {
        if (slot == Storable.NO_SLOT) {
            throw new IllegalStateException("Mapping slot not set");
        }
        if (keys.length == 0) {
            throw new IllegalArgumentException("At least one key must be provided");
        }

        // Build cache key
        StringBuilder cacheKeyBuilder = new StringBuilder();
        for (K key : keys) {
            cacheKeyBuilder.append(getCacheKey(key.toByteArray())).append(":");
        }
        String fullCacheKey = cacheKeyBuilder.toString();

        // Calculate final storage slot
        byte[] currentSlot = new byte[32];  // 32 bytes for ethereum storage slot
        currentSlot[31] = (byte) slot;      // Set initial slot in last byte
        byte[] finalKeyBytes = null;
        
        for (int i = 0; i < keys.length; i++) {
            byte[] keyBytes = keys[i].toByteArray();
            if (i == keys.length - 1) {
                finalKeyBytes = keyBytes;
            } else {
                currentSlot = Storage.getStorage().ComputeNestedMappingSlot(currentSlot, keyBytes);
            }
        }

        // Set storage to default value (empty byte array)
        Storage.getStorage().SetStorageMappingValue(currentSlot, finalKeyBytes, new byte[0]);

        // Remove from cache
        cache.remove(fullCacheKey);
    }

    /**
     * Creates a new nested mapping instance
     *
     * @return A new nested mapping instance
     * @throws IllegalStateException if the value type is not a Mapping
     */
    public Mapping<K, V> createNestedMapping() {
        if (!isNestedMapping) {
            throw new IllegalStateException("Value type is not a Mapping");
        }
        return new Mapping<>(keyType, valueType);
    }

    /**
     * Clears the entire cache
     */
    public void clearCache() {
        cache.clear();
    }

    @Override
    public void fromByteArray(byte[] data) {
        // Mappings don't need to be reconstructed from byte arrays
    }

    @Override
    public byte[] toByteArray() {
        // Mappings don't need to be serialized directly
        return new byte[0];
    }

    @Override
    public boolean save() {
        // Mappings don't need to be saved directly since their values 
        // are saved individually through set() method
        return true;
    }

    @Override
    public boolean load() {
        // Mappings don't need to be loaded directly since their values
        // are loaded individually through get() method
        return true;
    }
}
