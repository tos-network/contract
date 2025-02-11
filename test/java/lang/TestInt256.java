package java.lang;

import java.math.BigInteger;

/**
 * A self-contained test class for int256.java without using JUnit.
 * It prints "OK" if a check passes, or prints the failure message if it fails.
 * At the end, it summarizes pass/fail counts and exits with code 1 if any failed.
 */
public class TestInt256 {
    // Track how many checks pass/fail
    private static int passCount = 0;
    private static int failCount = 0;

    // A custom pseudo-random generator (replaces java.util.Random)
    private static final MyRandom rnd = new MyRandom();

    public static void main(String[] args) {
        // Run all tests
        testConstants();
        testConstructors();
        testAdd();
        testSubtract();
        testMultiply();
        testDivMod();
        testBitwiseOps();
        testShifts();
        testRandomArithmetic();
        testSignedSpecificOperations();

        System.out.println("All tests are executed!");

        // Print summary
        System.out.println("===== Test Summary =====");
        System.out.println("Pass:  " + passCount);
        System.out.println("Fail:  " + failCount);
        System.out.println("Total: " + (passCount + failCount));

        // Exit with code 1 if any checks failed
        if (failCount > 0) {
            System.exit(1);
        }
    }

    /** Simple checker method. Prints "OK" on pass, otherwise prints message. */
    private static void check(boolean condition, String message) {
        if (condition) {
            System.out.println("OK");
            passCount++;
        } else {
            System.out.println(message);
            failCount++;
        }
    }

    /* ---------------------------------------------------------------------- */
    /*                            TEST METHODS                                */
    /* ---------------------------------------------------------------------- */

    private static void testConstants() {
        // Test MAX_VALUE (2^255 - 1)
        check(int256.MAX_VALUE.bitLength() == 255, 
              "MAX_VALUE should have 255 bits");
        check(!int256.MAX_VALUE.isNegative(), 
              "MAX_VALUE should be positive");
        check(int256.MAX_VALUE.signum() == 1,
              "MAX_VALUE signum should be 1");

        // Test MIN_VALUE (-2^255)
        check(int256.MIN_VALUE.bitLength() == 255, 
              "MIN_VALUE should have 255 bits");
        check(int256.MIN_VALUE.isNegative(), 
              "MIN_VALUE should be negative");
        check(int256.MIN_VALUE.signum() == -1,
              "MIN_VALUE signum should be -1");

 
        // Test ZERO
        check(int256.ZERO.isZero(), 
              "ZERO should be zero");
        check(!int256.ZERO.isNegative(), 
              "ZERO should not be negative");
        check(int256.ZERO.signum() == 0,
              "ZERO signum should be 0");

        // Test ONE
        check(int256.ONE.equals(new int256(1)), 
              "ONE should equal 1");
        check(!int256.ONE.isNegative(), 
              "ONE should be positive");
        check(int256.ONE.signum() == 1,
              "ONE signum should be 1");

        // Test MINUS_ONE
        check(int256.MINUS_ONE.equals(new int256(-1)), 
              "MINUS_ONE should equal -1");
        check(int256.MINUS_ONE.isNegative(), 
              "MINUS_ONE should be negative");
        check(int256.MINUS_ONE.signum() == -1,
              "MINUS_ONE signum should be -1");
    }

    private static void testConstructors() {
        // From positive long
        int256 fromPosLong = new int256(123456789L);
        check(fromPosLong.toBigInteger().equals(BigInteger.valueOf(123456789L)),
              "Construct from positive long");
        check(!fromPosLong.isNegative(),
              "Positive long should create positive value");

        // From negative long
        int256 fromNegLong = new int256(-123456789L);
        check(fromNegLong.toBigInteger().equals(BigInteger.valueOf(-123456789L)),
              "Construct from negative long");
        check(fromNegLong.isNegative(),
              "Negative long should create negative value");

        // From BigInteger
        BigInteger largePos = BigInteger.ONE.shiftLeft(200);
        BigInteger largeNeg = largePos.negate();
        int256 fromPosBI = new int256(largePos);
        int256 fromNegBI = new int256(largeNeg);
        check(fromPosBI.toBigInteger().equals(largePos), 
              "Construct from positive BigInteger");
        check(fromNegBI.toBigInteger().equals(largeNeg), 
              "Construct from negative BigInteger");

        // From strings
        int256 fromPosDecimal = new int256("987654321");
        int256 fromNegDecimal = new int256("-987654321");
        check(fromPosDecimal.toBigInteger().equals(BigInteger.valueOf(987654321L)),
              "Construct from positive decimal string");
        check(fromNegDecimal.toBigInteger().equals(BigInteger.valueOf(-987654321L)),
              "Construct from negative decimal string");

        // Test string with explicit positive sign
        int256 fromSignedPosDecimal = new int256("+987654321");
        check(fromSignedPosDecimal.equals(fromPosDecimal),
              "String with + sign should equal unsigned string");
    }

    private static void testAdd() {
        int256 pos = new int256(100);
        int256 neg = new int256(-50);

        // Positive + Positive
        check(pos.add(pos).toBigInteger().equals(BigInteger.valueOf(200)),
              "100 + 100 => 200");

        // Positive + Negative
        check(pos.add(neg).toBigInteger().equals(BigInteger.valueOf(50)),
              "100 + (-50) => 50");

        // Negative + Negative
        check(neg.add(neg).toBigInteger().equals(BigInteger.valueOf(-100)),
              "-50 + (-50) => -100");

        // Test overflow
        int256 almostMax = int256.MAX_VALUE.subtract(new int256(1));
        int256 result = almostMax.add(new int256(2));
        check(result.equals(int256.MIN_VALUE),
              "overflow should wrap to MIN_VALUE");

        // Test original values unchanged
        check(pos.toBigInteger().equals(BigInteger.valueOf(100)), 
              "original value unchanged after add");
        check(neg.toBigInteger().equals(BigInteger.valueOf(-50)), 
              "original value unchanged after add");
    }

    private static void testSubtract() {
        int256 pos = new int256(100);
        int256 neg = new int256(-50);

        // Positive - Positive
        check(pos.subtract(new int256(30)).toBigInteger().equals(BigInteger.valueOf(70)),
              "100 - 30 => 70");

        // Positive - Negative
        check(pos.subtract(neg).toBigInteger().equals(BigInteger.valueOf(150)),
              "100 - (-50) => 150");

        // Negative - Positive
        check(neg.subtract(pos).toBigInteger().equals(BigInteger.valueOf(-150)),
              "-50 - 100 => -150");

        // Test underflow
        int256 almostMin = int256.MIN_VALUE.add(new int256(1));
        int256 result = almostMin.subtract(new int256(2));
        check(result.equals(int256.MAX_VALUE),
              "underflow should wrap to MAX_VALUE");

        // Test original values unchanged
        check(pos.toBigInteger().equals(BigInteger.valueOf(100)), 
              "original value unchanged after subtract");
        check(neg.toBigInteger().equals(BigInteger.valueOf(-50)), 
              "original value unchanged after subtract");
    }

    private static void testMultiply() {
        int256 pos = new int256(100);
        int256 neg = new int256(-50);

        // Positive * Positive
        check(pos.multiply(new int256(3)).toBigInteger().equals(BigInteger.valueOf(300)),
              "100 * 3 => 300");

        // Positive * Negative
        check(pos.multiply(neg).toBigInteger().equals(BigInteger.valueOf(-5000)),
              "100 * (-50) => -5000");

        // Negative * Negative
        check(neg.multiply(neg).toBigInteger().equals(BigInteger.valueOf(2500)),
              "-50 * (-50) => 2500");

        // Test overflow
        int256 large = new int256(BigInteger.ONE.shiftLeft(254));
        check(large.multiply(new int256(2)).equals(int256.ZERO),
              "overflow should wrap to zero");

        // Test original values unchanged
        check(pos.toBigInteger().equals(BigInteger.valueOf(100)), 
              "original value unchanged after multiply");
        check(neg.toBigInteger().equals(BigInteger.valueOf(-50)), 
              "original value unchanged after multiply");
    }

    private static void testDivMod() {
        int256 pos = new int256(100);
        int256 neg = new int256(-50);

        // Positive / Positive
        check(pos.divide(new int256(2)).toBigInteger().equals(BigInteger.valueOf(50)),
              "100 / 2 => 50");

        // Positive / Negative
        check(pos.divide(neg).toBigInteger().equals(BigInteger.valueOf(-2)),
              "100 / (-50) => -2");

        // Test division by zero
        try {
            pos.divide(new int256(0));
            check(false, "Division by zero should throw ArithmeticException");
        } catch (ArithmeticException e) {
            check(true, "Division by zero correctly throws");
        }
    }

    private static void testBitwiseOps() {
        int256 pos = new int256(12);  // 1100 in binary
        int256 neg = new int256(-12); // ... two's complement representation

        // Test AND
        check(pos.and(new int256(10)).toBigInteger().equals(BigInteger.valueOf(8)),
              "12 & 10 => 8");

        // Test OR
        check(pos.or(new int256(10)).toBigInteger().equals(BigInteger.valueOf(14)),
              "12 | 10 => 14");

        // Test XOR
        check(pos.xor(new int256(10)).toBigInteger().equals(BigInteger.valueOf(6)),
              "12 ^ 10 => 6");

        // Test NOT => ~x + 1 = -x in two's complement
        check(pos.not().add(new int256(1)).toBigInteger().equals(pos.negate().toBigInteger()),
              "~x + 1 == -x for positive x");
    }

    private static void testShifts() {
        int256 pos = new int256(16);
        int256 neg = new int256(-16);

        // Left shifts
        check(pos.shiftLeft(2).toBigInteger().equals(BigInteger.valueOf(64)),
              "16 << 2 => 64");
        check(neg.shiftLeft(2).toBigInteger().equals(BigInteger.valueOf(-64)),
              "-16 << 2 => -64");

        // Right shifts (arithmetic)
        check(pos.shiftRight(2).toBigInteger().equals(BigInteger.valueOf(4)),
              "16 >> 2 => 4");
        check(neg.shiftRight(2).toBigInteger().equals(BigInteger.valueOf(-4)),
              "-16 >> 2 => -4");
    }

    /**
     * Replaces the original "random" usage with a custom approach.
     * We'll generate random 254-bit BigIntegers, possibly negating them.
     */
    private static void testRandomArithmetic() {

        for (int i = 0; i < 5; i++) {
            // random 254-bit BigInteger
            BigInteger bx = randomBigInt(254);
            BigInteger by = randomBigInt(254);

            // randomly negate
            if (rnd.nextBoolean()) bx = bx.negate();
            if (rnd.nextBoolean()) by = by.negate();

            int256 ix = new int256(bx);
            int256 iy = new int256(by);

            // Add
            BigInteger sumRef = bx.add(by);
            int256 sumVal = ix.add(iy);
            check(sumVal.toBigInteger().equals(sumRef), "random add => match");

            // Multiply
            BigInteger mulRef = bx.multiply(by);
            if (mulRef.compareTo(int256.MAX_VALUE.toBigInteger()) > 0) {
                mulRef = BigInteger.valueOf(0);
            }
            int256 mulVal = ix.multiply(iy);
            check(mulVal.toBigInteger().equals(mulRef), "random multiply => match");

            // Sub
            BigInteger subRef = bx.subtract(by);
            int256 subVal = ix.subtract(iy);
            check(subVal.toBigInteger().equals(subRef), "random subtract => match");
        }
    }

    /**
     * Additional tests that focus on signed operations:
     * signum, abs, negate, etc.
     */
    private static void testSignedSpecificOperations() {
        int256 pos = new int256(42);
        int256 neg = new int256(-42);
        int256 zero = int256.ZERO;

        // signum
        check(pos.signum() == 1, "positive signum should be 1");
        check(neg.signum() == -1, "negative signum should be -1");
        check(zero.signum() == 0, "zero signum should be 0");

        // abs
        check(pos.abs().equals(pos), "abs(42) => 42");
        check(neg.abs().equals(pos), "abs(-42) => 42");
        check(zero.abs().equals(zero), "abs(0) => 0");

        // negate
        check(pos.negate().equals(neg), "negate(42) => -42");
        check(neg.negate().equals(pos), "negate(-42) => 42");
        check(zero.negate().equals(zero), "negate(0) => 0");

        // MIN_VALUE edge cases
        check(int256.MIN_VALUE.abs().equals(int256.MIN_VALUE), 
              "abs(MIN_VALUE) should return MIN_VALUE");
        check(int256.MIN_VALUE.negate().equals(int256.MIN_VALUE), 
              "negate(MIN_VALUE) should return MIN_VALUE");
    }

    /* ===================================================== */
    /*  CUSTOM SIMPLE PRNG "MyRandom" (like a mini-LCG)       */
    /* ===================================================== */
    private static class MyRandom {
        private int seed = 0xDEADBEEF; // default seed

        // Linear Congruential Generator
        public int nextInt() {
            seed = seed * 1103515245 + 12345;
            // keep it non-negative
            return (seed >>> 1) & 0x7fffffff;
        }

        public int nextInt(int bound) {
            if (bound <= 1) return 0;
            return nextInt() % bound;
        }

        public boolean nextBoolean() {
            // e.g. if LSB is 1 => true, else false
            return (nextInt() & 1) == 1;
        }

        public float nextFloat() {
            return nextInt() / (float)(0x7fffffff);
        }
    }

    /**
     * Generate a random BigInteger with up to 'bitCount' bits using MyRandom.
     * We'll set bits by shifting.
     */
    private static BigInteger randomBigInt(int bitCount) {
        if (bitCount <= 0) {
            return BigInteger.ZERO;
        }
        // We'll assemble the bits in a loop
        // up to bitCount bits
        BigInteger result = BigInteger.ZERO;
        for (int bitsUsed = 0; bitsUsed < bitCount;) {
            // get up to 16 random bits
            int chunkBits = Math.min(16, bitCount - bitsUsed);
            int r = rnd.nextInt() & ((1 << chunkBits) - 1);
            result = result.shiftLeft(chunkBits).add(BigInteger.valueOf(r));
            bitsUsed += chunkBits;
        }
        return result;
    }
}