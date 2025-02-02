package java.lang;

import java.util.HashMap;
import java.util.Map;

public class Mapping<K, V> {
    private final Map<K, V> map = new HashMap<>();

    public Mapping() {
    }

    // set key value
    public void put(K key, V value) {
        map.put(key, value);
    }

    // get value
    public V get(K key) {
        return map.get(key);
    }

    // set key value
    public V set(K key, V value) {
        return map.put(key, value);
    }

    // check if key exists
    public boolean containsKey(K key) {
        return map.containsKey(key);
    }

    public V getOrDefault(K key, V defaultValue) {
        return map.getOrDefault(key, defaultValue);
    }
}
