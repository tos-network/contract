package java.lang;

import java.math.BigInteger;
import java.lang.types.Numeric;
import java.lang.rlp.RlpDecoder;
import java.lang.rlp.RlpString;
import java.lang.rlp.RlpList;

/**
 * A self-contained test class for RlpDecoder without using JUnit.
 * Examples taken from https://github.com/ethereum/wiki/wiki/RLP#examples
 */
public class TestRlpDecoder {

    public static void main(String[] args) {
        testBigPositiveNumber();
        testEmptyArray();
        testDog();
        testCatDogList();
        testEmptyString();
        testEmptyList();
        testIntegers();
        testSetTheory();
        testLongString();
        testParityTests();
        testLargePayload();
        testInvalidInput();
        
        System.out.println("All tests are executed!");
    }

    private static void assertEquals(boolean condition, String message) {
        if (!condition) {
            System.out.println("FAILED: " + message);
        } else {
            System.out.println("OK");
        }
    }

    private static void testBigPositiveNumber() {
        // Test that big positive number should stay positive after encoding-decoding
        long value = 3000000000L;
        assertEquals(
                RlpString.create(BigInteger.valueOf(value)).asPositiveBigInteger().longValue() == value,
                "Failed to handle big positive number"
        );
    }

    private static void testEmptyArray() {
        assertEquals(
            RlpDecoder.decode(new byte[] {}).getValues().isEmpty(),
            "Empty array should decode to empty values"
        );
    }

    private static void testDog() {
        assertEquals(
            RlpDecoder.decode(new byte[] {(byte) 0x83, 'd', 'o', 'g'})
                .getValues().get(0).equals(RlpString.create(new String("dog"))),
            "Failed to decode 'dog'"
        );
    }

    private static void testCatDogList() {
        RlpList result = (RlpList) RlpDecoder.decode(
            new byte[] {
                (byte) 0xc8, (byte) 0x83, 'c', 'a', 't',
                (byte) 0x83, 'd', 'o', 'g'
            }
        ).getValues().get(0);

        assertEquals(
            result.getValues().get(0).equals(RlpString.create("cat")) &&
            result.getValues().get(1).equals(RlpString.create("dog")),
            "Failed to decode ['cat', 'dog']"
        );
    }

    private static void testEmptyString() {
        RlpList result = RlpDecoder.decode(new byte[] {(byte) 0x80});
        assertEquals(
            result.getValues().get(0).equals(RlpString.create(new String(""))) &&
            result.getValues().get(0).equals(RlpString.create(new byte[] {})) &&
            result.getValues().get(0).equals(RlpString.create(BigInteger.ZERO)),
            "Failed to decode empty string"
        );
    }

    private static void testEmptyList() {
        RlpList result = RlpDecoder.decode(new byte[] {(byte) 0xc0});
        assertEquals(
            result.getValues().get(0) instanceof RlpList &&
            ((RlpList) result.getValues().get(0)).getValues().isEmpty(),
            "Failed to decode empty list"
        );
    }

    private static void testIntegers() {
        // Test zero
        assertEquals(
            RlpDecoder.decode(new byte[] {(byte) 0x00})
                .getValues().get(0).equals(RlpString.create(new byte[] {0})),
            "Failed to decode zero"
        );

        // Test 15
        assertEquals(
            RlpDecoder.decode(new byte[] {(byte) 0x0f})
                .getValues().get(0).equals(RlpString.create((byte) 15)),
            "Failed to decode 15"
        );

        // Test 1024
        assertEquals(
            RlpDecoder.decode(new byte[] {(byte) 0x82, (byte) 0x04, (byte) 0x00})
                .getValues().get(0).equals(RlpString.create(BigInteger.valueOf(0x0400))),
            "Failed to decode 1024"
        );

    }

    private static void testSetTheory() {
        // [ [], [[]], [ [], [[]] ] ]
        RlpList result = RlpDecoder.decode(new byte[] {
            (byte) 0xc7,
            (byte) 0xc0,
            (byte) 0xc1, (byte) 0xc0,
            (byte) 0xc3, (byte) 0xc0, (byte) 0xc1, (byte) 0xc0
        });

        assertEquals(
            result.getValues().size() == 1 &&
            ((RlpList) result.getValues().get(0)).getValues().size() == 3 &&
            ((RlpList) ((RlpList) result.getValues().get(0)).getValues().get(0)).getValues().size() == 0 &&
            ((RlpList) ((RlpList) result.getValues().get(0)).getValues().get(1)).getValues().size() == 1 &&
            ((RlpList) ((RlpList) result.getValues().get(0)).getValues().get(2)).getValues().size() == 2 &&
            ((RlpList) ((RlpList) ((RlpList) result.getValues().get(0)).getValues().get(2)).getValues().get(0)).getValues().size() == 0 &&
            ((RlpList) ((RlpList) ((RlpList) result.getValues().get(0)).getValues().get(2)).getValues().get(1)).getValues().size() == 1,
            "Failed to decode set theory example with correct nested structure"
        );
    }

    private static void testLongString() {
        byte[] encoded = new byte[] {
            (byte) 0xb8, (byte) 0x38, 'L', 'o', 'r', 'e', 'm', ' ',
            'i', 'p', 's', 'u', 'm', ' ', 'd', 'o', 'l', 'o', 'r', ' ',
            's', 'i', 't', ' ', 'a', 'm', 'e', 't', ',', ' ',
            'c', 'o', 'n', 's', 'e', 'c', 't', 'e', 't', 'u', 'r', ' ',
            'a', 'd', 'i', 'p', 'i', 's', 'i', 'c', 'i', 'n', 'g', ' ',
            'e', 'l', 'i', 't'
        };

        assertEquals(
            RlpDecoder.decode(encoded).getValues().get(0)
                .equals(RlpString.create("Lorem ipsum dolor sit amet, consectetur adipisicing elit")),
            "Failed to decode long string"
        );
    }

    private static void testParityTests() {
        RlpList result = RlpDecoder.decode(new byte[] {
            (byte) 0xc6, (byte) 0x82, (byte) 0x7a, (byte) 0x77,
            (byte) 0xc1, (byte) 0x04, (byte) 0x01
        });

        assertEquals(
            ((RlpList) result.getValues().get(0)).getValues().size() == 3 &&
            ((RlpList) result.getValues().get(0)).getValues().get(0).equals(RlpString.create("zw")) &&
            ((RlpList) ((RlpList) result.getValues().get(0)).getValues().get(1)).getValues().get(0).equals(RlpString.create(4)) &&
            ((RlpList) result.getValues().get(0)).getValues().get(2).equals(RlpString.create(1)),
            "Failed parity test case with complete structure verification"
        );
    }

    private static void testLargePayload() {
        String data = "F86E12F86B80881BC16D674EC8000094CD2A3D9F938E13CD947EC05ABC7FE734D"
                + "F8DD8268609184E72A00064801BA0C52C114D4F5A3BA904A9B3036E5E118FE0DBB987"
                + "FE3955DA20F2CD8F6C21AB9CA06BA4C2874299A55AD947DBC98A25EE895AABF6B625C"
                + "26C435E84BFD70EDF2F69";

        byte[] payload = Numeric.hexStringToByteArray(data);
        RlpList result = RlpDecoder.decode(payload);

        // First verify the size
        assertEquals(
            ((RlpList) result.getValues().get(0)).getValues().size() == 2,
            "Failed to decode large payload: incorrect top level size"
        );

        // Then verify the types
        assertEquals(
            ((RlpList) result.getValues().get(0)).getValues().get(0).getClass() == RlpString.class &&
            ((RlpList) result.getValues().get(0)).getValues().get(1).getClass() == RlpList.class,
            "Failed to decode large payload: incorrect element types"
        );

        // Finally verify the nested list size
        assertEquals(
            ((RlpList) ((RlpList) result.getValues().get(0)).getValues().get(1)).getValues().size() == 9,
            "Failed to decode large payload: incorrect nested list size"
        );
    }

    private static void testInvalidInput() {
        boolean threw = false;
        try {
            RlpDecoder.decode(new byte[] {
                (byte) 0xbb, (byte) 0x7f, (byte) 0xff, (byte) 0xff, (byte) 0xff
            });
        } catch (RuntimeException e) {
            threw = true;
        }
        assertEquals(threw, "Failed to catch invalid input");
    }
} 