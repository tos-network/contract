package java.lang;

import java.math.BigInteger;
import java.util.Arrays;
import java.lang.rlp.RlpEncoder;
import java.lang.rlp.RlpString;
import java.lang.rlp.RlpList;

/**
 * A self-contained test class for RlpEncoder without using JUnit.
 * Examples taken from https://github.com/ethereum/wiki/wiki/RLP#examples
 */
public class TestRlpEncoder {

    public static void main(String[] args) {
        testEncodeString();
        testEncodeList();
        testEncodeEmpty();
        testEncodeInteger();
        testEncodeNestedList();
        testEncodeLongString();
        testEncodeZero();
        testEncodeSingleZero();
        testEncodeListWithZero();
        testEncodeBigPayload();
        
        System.out.println("All tests are executed!");
    }

    private static void assertEquals(boolean condition, String message) {
        if (!condition) {
            System.out.println("FAILED: " + message);
        } else {
            System.out.println("OK");
        }
    }

    private static void assertArrayEquals(byte[] expected, byte[] actual, String message) {
        assertEquals(Arrays.equals(expected, actual), message);
    }

    private static void testEncodeString() {
        // Test encoding "dog"
        assertArrayEquals(
            RlpEncoder.encode(RlpString.create("dog")),
            new byte[]{(byte) 0x83, 'd', 'o', 'g'},
            "Failed to encode string 'dog'"
        );
    }

    private static void testEncodeList() {
        // Test encoding ["cat", "dog"]
        assertArrayEquals(
            RlpEncoder.encode(new RlpList(RlpString.create("cat"), RlpString.create("dog"))),
            new byte[]{(byte) 0xc8, (byte) 0x83, 'c', 'a', 't', (byte) 0x83, 'd', 'o', 'g'},
            "Failed to encode list ['cat', 'dog']"
        );
    }

    private static void testEncodeEmpty() {
        // Test encoding empty string
        assertArrayEquals(
            RlpEncoder.encode(RlpString.create("")),
            new byte[]{(byte) 0x80},
            "Failed to encode empty string"
        );

        // Test encoding empty byte array
        assertArrayEquals(
            RlpEncoder.encode(RlpString.create(new byte[]{})),
            new byte[]{(byte) 0x80},
            "Failed to encode empty byte array"
        );

        // Test encoding empty list
        assertArrayEquals(
            RlpEncoder.encode(new RlpList()),
            new byte[]{(byte) 0xc0},
            "Failed to encode empty list"
        );
    }

    private static void testEncodeInteger() {
        // Test encoding 15 (0x0f)
        assertArrayEquals(
            RlpEncoder.encode(RlpString.create(BigInteger.valueOf(0x0f))),
            new byte[]{(byte) 0x0f},
            "Failed to encode integer 15"
        );

        // Test encoding 1024 (0x0400)
        assertArrayEquals(
            RlpEncoder.encode(RlpString.create(BigInteger.valueOf(0x0400))),
            new byte[]{(byte) 0x82, (byte) 0x04, (byte) 0x00},
            "Failed to encode integer 1024"
        );
    }

    private static void testEncodeNestedList() {
        // Test encoding [ [], [[]], [ [], [[]] ] ]
        assertArrayEquals(
            RlpEncoder.encode(
                new RlpList(
                    new RlpList(),
                    new RlpList(new RlpList()),
                    new RlpList(new RlpList(), new RlpList(new RlpList()))
                )
            ),
            new byte[]{
                (byte) 0xc7,
                (byte) 0xc0,
                (byte) 0xc1, (byte) 0xc0,
                (byte) 0xc3, (byte) 0xc0, (byte) 0xc1, (byte) 0xc0
            },
            "Failed to encode nested list structure"
        );
    }

    private static void testEncodeLongString() {
        // Test encoding long string
        String longStr = "Lorem ipsum dolor sit amet, consectetur adipisicing elit";
        assertArrayEquals(
            RlpEncoder.encode(RlpString.create(longStr)),
            new byte[]{
                (byte) 0xb8,
                (byte) 0x38,
                'L', 'o', 'r', 'e', 'm', ' ',
                'i', 'p', 's', 'u', 'm', ' ',
                'd', 'o', 'l', 'o', 'r', ' ',
                's', 'i', 't', ' ',
                'a', 'm', 'e', 't', ',', ' ',
                'c', 'o', 'n', 's', 'e', 'c', 't', 'e', 't', 'u', 'r', ' ',
                'a', 'd', 'i', 'p', 'i', 's', 'i', 'c', 'i', 'n', 'g', ' ',
                'e', 'l', 'i', 't'
            },
            "Failed to encode long string"
        );
    }

    private static void testEncodeZero() {
        // Test encoding zero as BigInteger
        assertArrayEquals(
            RlpEncoder.encode(RlpString.create(BigInteger.ZERO)),
            new byte[]{(byte) 0x80},
            "Failed to encode BigInteger.ZERO"
        );
    }

    private static void testEncodeSingleZero() {
        // Test encoding single zero byte
        assertArrayEquals(
            RlpEncoder.encode(RlpString.create(new byte[]{0})),
            new byte[]{(byte) 0x00},
            "Failed to encode single zero byte"
        );
    }

    private static void testEncodeListWithZero() {
        // Test encoding ["zw", [4], 1]
        assertArrayEquals(
            RlpEncoder.encode(
                new RlpList(
                    RlpString.create("zw"),
                    new RlpList(RlpString.create(4)),
                    RlpString.create(1)
                )
            ),
            new byte[]{
                (byte) 0xc6,
                (byte) 0x82, (byte) 0x7a, (byte) 0x77,
                (byte) 0xc1, (byte) 0x04,
                (byte) 0x01
            },
            "Failed to encode list with zero"
        );
    }

    private static void testEncodeBigPayload() {
        // Test encoding 55 bytes
        byte[] encodeMe = new byte[55];
        Arrays.fill(encodeMe, (byte) 0);
        byte[] expectedEncoding = new byte[56];
        expectedEncoding[0] = (byte) 0xb7;
        System.arraycopy(encodeMe, 0, expectedEncoding, 1, encodeMe.length);
        
        assertArrayEquals(
            RlpEncoder.encode(RlpString.create(encodeMe)),
            expectedEncoding,
            "Failed to encode 55 bytes payload"
        );
    }
} 