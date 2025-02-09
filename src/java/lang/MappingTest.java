package java.lang;

import java.io.Storable;
import java.lang.contract.Storage;

/**
 * Simple test class for Mapping implementation
 */
public class MappingTest {
    
    public static void main(String[] args) {
        testSimpleMapping();
        testNestedMapping();
        testDelete();
        testCacheClearing();
        testSlotNotSet();
        testNoKeysProvided();
        
        System.out.println("All tests passed!");
    }
    
    private static void testSimpleMapping() {
        // Create a simple mapping
        Mapping<TestKey, TestValue> mapping = Mapping.of(TestKey.class, TestValue.class);
        mapping.setSlot(1);
        
        // Test data
        TestKey key = new TestKey(123);
        TestValue value = new TestValue("test");
        
        // Validate key and value serialization
        byte[] keyBytes = key.toByteArray();
        byte[] valueBytes = value.toByteArray();
        assertNotNull(keyBytes);
        assertNotNull(valueBytes);
        assertTrue(keyBytes.length > 0, "Key serialization produced empty byte array");
        assertTrue(valueBytes.length > 0, "Value serialization produced empty byte array");
        
        // Test setting value
        mapping.set(value, key);
        
        // Test retrieving value
        TestValue retrieved = mapping.get(key);
        assertNotNull(retrieved);
        assertEquals("test", retrieved.getValue());
    }
    
    private static void assertEquals(Object expected, Object actual) {
        if (!expected.equals(actual)) {
            throw new AssertionError("Expected " + expected + " but got " + actual);
        }
    }
    
    private static void assertNotNull(Object obj) {
        if (obj == null) {
            throw new AssertionError("Expected non-null value");
        }
    }
    
    private static void assertNull(Object obj) {
        if (obj != null) {
            throw new AssertionError("Expected null value but got " + obj);
        }
    }
    
    private static void testNestedMapping() {
        // Create a nested mapping
        Mapping<TestKey, Mapping<TestKey, TestValue>> nestedMapping = 
            Mapping.ofNested(TestKey.class, TestValue.class);
        nestedMapping.setSlot(2);
        
        // Test data
        TestKey key1 = new TestKey(1);
        TestKey key2 = new TestKey(2);
        TestValue value = new TestValue("nested");
        
        // Get inner mapping and set value
        Mapping<TestKey, TestValue> innerMapping = nestedMapping.get(key1);
        innerMapping.set(value, key2);
        
        // Test retrieving nested value
        TestValue retrieved = nestedMapping.get(key1).get(key2);
        assertEquals("nested", retrieved.getValue());
    }
    
    private static void testDelete() {
        Mapping<TestKey, TestValue> mapping = Mapping.of(TestKey.class, TestValue.class);
        mapping.setSlot(3);
        
        TestKey key = new TestKey(456);
        TestValue value = new TestValue("delete-test");
        
        // Set value
        mapping.set(value, key);
        
        // Delete value
        mapping.delete(key);
        
        // Verify default value is returned
        TestValue retrieved = mapping.get(key);
        assertNotNull(retrieved);
        assertNull(retrieved.getValue());
    }
    
    private static void testCacheClearing() {
        Mapping<TestKey, TestValue> mapping = Mapping.of(TestKey.class, TestValue.class);
        mapping.setSlot(4);
        
        TestKey key = new TestKey(789);
        TestValue value = new TestValue("cache-test");
        
        // Set value
        mapping.set(value, key);
        
        // Clear cache
        mapping.clearCache();
        
        // Verify value is reloaded from storage
        TestValue retrieved = mapping.get(key);
        assertEquals("cache-test", retrieved.getValue());
    }
    
    private static void testSlotNotSet() {
        Mapping<TestKey, TestValue> mapping = Mapping.of(TestKey.class, TestValue.class);
        mapping.get(new TestKey(1));
    }
    
    private static void testNoKeysProvided() {
        Mapping<TestKey, TestValue> mapping = Mapping.of(TestKey.class, TestValue.class);
        mapping.setSlot(5);
        mapping.get();
    }
    
    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
    
    /**
     * Test key class implementing Storable interface
     */
    private static class TestKey implements Storable {
        private final int id;
        
        public TestKey(int id) {
            this.id = id;
        }
        
        @Override
        public byte[] toByteArray() {
            byte[] bytes = new byte[4];
            bytes[0] = (byte) (id >> 24);
            bytes[1] = (byte) (id >> 16);
            bytes[2] = (byte) (id >> 8);
            bytes[3] = (byte) id;
            return bytes;
        }
        
        @Override
        public void fromByteArray(byte[] data) {
            // Not needed for testing
        }
        
        @Override
        public void setSlot(int slot) {
            // Not needed for testing
        }
        
        @Override
        public int getSlot() {
            return Storable.NO_SLOT;
        }
        
        @Override
        public boolean save() {
            return true;
        }
        
        @Override
        public boolean load() {
            return true;
        }
    }
    
    /**
     * Test value class implementing Storable interface
     */
    private static class TestValue implements Storable {
        private String value;
        
        public TestValue() {
            this.value = null;
        }
        
        public TestValue(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
        
        @Override
        public byte[] toByteArray() {
            return value != null ? value.getBytes() : new byte[0];
        }
        
        @Override
        public void fromByteArray(byte[] data) {
            this.value = data.length > 0 ? new String(data) : null;
        }
        
        @Override
        public void setSlot(int slot) {
            // Not needed for testing
        }
        
        @Override
        public int getSlot() {
            return Storable.NO_SLOT;
        }
        
        @Override
        public boolean save() {
            return true;
        }
        
        @Override
        public boolean load() {
            return true;
        }
    }
} 