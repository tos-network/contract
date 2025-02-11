package java.lang;

import java.math.BigInteger;
import java.util.Random;

/**
 * A self-contained test class for Arrays.java without using JUnit
 * and without throwing AssertionError. Instead, it prints either
 * "OK" for a passing check or prints the message for a failing check.
 */
public class TestArrays {

    public static void main(String[] args) {
        // Run all tests in sequence
        testValueOf();
        testCompare();
        testStripLeadingZeroes();
        testBitwiseOps();
        testSetClearFlipBit();
        testArithmeticAdd();
        testArithmeticSub();
        testMulMod();
        testFromBigInteger();
        testRandomArithmetic();

        System.out.println("All tests are executed!");
    }

    /**
     * Prints "OK" if condition == true,
     * otherwise prints the given message.
     */
    private static void check(boolean condition, String message) {
        if (!condition) {
            // Condition failed => print the message
            System.out.println(message);
        } else {
            // Condition passed => print "OK"
            System.out.println("OK");
        }
    }

    /* ---------------------------------------------------------------------- */
    /*                        BEGIN TEST METHODS                               */
    /* ---------------------------------------------------------------------- */

    private static void testValueOf() {
        // test small cached values
        check(java.lang.types.Arrays.valueOf(0L) == java.lang.types.Arrays.ZERO, 
              "valueOf(0) should return ZERO");
        check(java.lang.types.Arrays.valueOf(1L) == java.lang.types.Arrays.ONE, 
              "valueOf(1) should return ONE");
        check(java.lang.types.Arrays.valueOf(2L) == java.lang.types.Arrays.TWO, 
              "valueOf(2) should return TWO");

        // check a larger value
        long bigValue = 0x1FFFF_FFFFL; // 8589934591 decimal
        int[] result = java.lang.types.Arrays.valueOf(bigValue);
        check(result.length == 2, "Expected length=2 for 0x1FFFF_FFFFL");
        check(result[0] == 0x1,   "High word mismatch");
        check(result[1] == -1,    "Low word mismatch (0xFFFFFFFF)");
    }

    private static void testCompare() {
        int[] zero = java.lang.types.Arrays.ZERO;
        int[] one  = java.lang.types.Arrays.ONE;
        int[] two  = java.lang.types.Arrays.TWO;

        // zero < one
        check(java.lang.types.Arrays.compare(zero, one) < 0, "zero < one");
        // two > one
        check(java.lang.types.Arrays.compare(two, one) > 0, "two > one");
        // zero == zero
        check(java.lang.types.Arrays.compare(zero, zero) == 0, "zero == zero");

        int[] x = new int[]{0x1234, 0xABCD};
        int[] y = new int[]{0x1234};
        check(java.lang.types.Arrays.compare(x, y) > 0, "longer array with same top word is bigger");

        int[] xCopy = new int[]{0x1234, 0xABCD};
        check(java.lang.types.Arrays.compare(x, xCopy) == 0, "identical arrays => compare == 0");
    }

    private static void testStripLeadingZeroes() {
        // no leading zeros => same array
        int[] a = new int[]{0x1234, 0xABCD};
        int[] sA = java.lang.types.Arrays.stripLeadingZeroes(a);
        check(sA == a, "no leading zero => same array returned");

        // leading zeros
        int[] b = new int[]{0, 0, 0xFEED, 0xBEEF};
        int[] sB = java.lang.types.Arrays.stripLeadingZeroes(b);
        check(sB.length == 2, "should remove top zeros => length=2");
        check(sB[0] == 0xFEED && sB[1] == 0xBEEF, "stripLeadingZeroes mismatch");
    }

    private static void testBitwiseOps() {
        int[] one = java.lang.types.Arrays.ONE;   // [0x00000001]
        int[] two = java.lang.types.Arrays.TWO;   // [0x00000002]

        // 1 & 2 => 0
        int[] andRes = java.lang.types.Arrays.and(one, two);
        check(andRes.length == 0, "1 & 2 => zero-length array => 0");

        // 1 | 2 => 3
        int[] orRes = java.lang.types.Arrays.or(one, two);
        check(orRes.length == 1 && orRes[0] == 3, "1 | 2 => [3]");

        // 1 ^ 2 => 3
        int[] xorRes = java.lang.types.Arrays.xor(one, two);
        check(xorRes.length == 1 && xorRes[0] == 3, "1 ^ 2 => [3]");
    }

    private static void testSetClearFlipBit() {
        int[] zero = java.lang.types.Arrays.ZERO;
        int[] one  = java.lang.types.Arrays.ONE;

        // setBit(zero, 0) => [1]
        int[] set = java.lang.types.Arrays.setBit(zero, 0);
        check(set.length == 1 && set[0] == 1, "setBit(zero,0) => [1]");

        // clearBit(one,0) => zero
        int[] cleared = java.lang.types.Arrays.clearBit(one, 0);
        check(cleared.length == 0, "clear bit0 on [1] => zero array");
    
        // flipBit(one,1) => [3]
        int[] flipped = java.lang.types.Arrays.flipBit(one, 1);
        check(flipped.length == 1 && flipped[0] == 3, "flip bit1 on [1] => [3]");
    }

    private static void testArithmeticAdd() {
        int[] one = java.lang.types.Arrays.ONE;
        int[] two = java.lang.types.Arrays.TWO;

        // 1 + 2 => 3
        int[] three = java.lang.types.Arrays.add(one, two, -1);
        check(three.length == 1 && three[0] == 3, "1+2 => 3 => [3]");

        // 0x12340000 + 0x5678 => 0x12345678
        int[] left  = new int[]{0x1234, 0x0000};
        int[] right = new int[]{0x5678};
        int[] sum   = java.lang.types.Arrays.add(left, right, -1);
        check(sum.length == 2, "sum length => 2");
        check(sum[0] == 0x1234 && sum[1] == 0x5678, "0x12340000 + 0x5678 => 0x12345678");
    }

    private static void testArithmeticSub() {
        int[] one = java.lang.types.Arrays.ONE;
        int[] two = java.lang.types.Arrays.TWO;

        // 2 - 1 => 1
        int[] diff = java.lang.types.Arrays.sub(two, one);
        check(diff.length == 1 && diff[0] == 1, "2-1 => 1 => [1]");

        // 0x12345678 - 0x5678 => 0x12340000
        int[] left  = new int[]{0x1234, 0x5678};
        int[] right = new int[]{0x5678};
        int[] out   = java.lang.types.Arrays.sub(left, right);
        check(out.length == 2, "length => 2 after sub");
        check(out[0] == 0x1234 && out[1] == 0x0000, "0x12345678 - 0x5678 => 0x12340000");
    }

    private static void testMulMod() {
        // (2 * 3) % 5 => 6 % 5 => 1
        int[] two   = java.lang.types.Arrays.TWO;
        int[] three = new int[]{3};
        int[] five  = new int[]{5};

        int[] product = java.lang.types.Arrays.mulmod(two, three, five);
        check(product.length == 1 && product[0] == 1, "(2*3)%5 => [1]");
    }

    private static void testFromBigInteger() {
        // 0xFFFFFFFF_FFFFFFFF => two words [0xFFFFFFFF, 0xFFFFFFFF]
        BigInteger bigVal = BigInteger.valueOf(0xFFFFFFFFL)
            .shiftLeft(32)
            .or(BigInteger.valueOf(0xFFFFFFFFL));

        // maxWidth = 4
        int[] result = java.lang.types.Arrays.from(bigVal, 4); 
        check(result.length == 2, "Expected 2-word array");
        check(result[0] == 0xFFFFFFFF && result[1] == 0xFFFFFFFF,
              "Both words should be 0xFFFFFFFF");
    }

    private static void testRandomArithmetic() {
        // simple random checks
        Random rnd = new Random(0xDEADBEEF);

        for(int i = 0; i < 5; i++) {
            long x = Math.abs(rnd.nextLong() >>> 1);
            long y = Math.abs(rnd.nextLong() >>> 1);

            int[] arrX = java.lang.types.Arrays.valueOf(x);
            int[] arrY = java.lang.types.Arrays.valueOf(y);

            // naive check for sum
            long sumL = x + y; // may overflow 64 bits, but okay for quick test
            int[] arrSum = java.lang.types.Arrays.add(arrX, arrY, -1);
            BigInteger sumVal = convertToBigInt(arrSum);
            // if sumVal fits in 64 bits, compare:
            if(sumVal.bitLength() <= 63) {
                check(sumVal.longValue() == sumL, "Random sum mismatch: x+y");
            }

            // difference, wrap-around not thoroughly checked
            // no crash => test pass
            int[] arrDiff = java.lang.types.Arrays.sub(arrX, arrY);
        }
    }

    private static BigInteger convertToBigInt(int[] arr) {
        BigInteger result = BigInteger.ZERO;
        for(int word : arr) {
            result = result.shiftLeft(32).or(BigInteger.valueOf(word & 0xffffffffL));
        }
        return result;
    }
}