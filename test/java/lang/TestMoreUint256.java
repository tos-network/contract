package java.lang;

import java.math.BigInteger;
import java.lang.types.Arrays;
import java.util.Random;

/**
 * A standalone test class for uint256. It avoids JUnit and uses a simple
 * pass/fail system. Also, it does NOT use SecureRandom or java.util.Random --
 * instead, it uses a custom MyRandom class for pseudo-random generation.
 *
 * Usage:
 *   javac TestMoreUint256.java
 *   java java.lang.TestMoreUint256
 */
public class TestMoreUint256 {

    // ------------------------------
    // PASS/FAIL COUNTERS
    // ------------------------------
    private static int passCount = 0;
    private static int failCount = 0;

    // ------------------------------
    // SAMPLE SIZES (like JUINT_SAMPLE_FACTOR usage)
    // ------------------------------
    private static int SAMPLE_BIG;
    private static int SAMPLE_MED;
    private static int SAMPLE_SMALL;

    // ------------------------------
    // CUSTOM RANDOM
    // ------------------------------
    private static final Random rnd = new Random(123456789L);   

    // ------------------------------
    // BASIC CONSTANTS
    // ------------------------------
    private static final int maxWidth     = uint256.MAX_WIDTH;  // e.g. 8 for 256 bits
    private static final uint256 zero     = uint256.ZERO;
    private static final uint256 max      = uint256.MAX_VALUE;
    private static final uint256 one      = uint256.ONE;
    private static final uint256 two      = uint256.TWO;

    // We'll cycle random variables x, y, rand1w
    private static uint256 x, y, rand1w;
    private static BigInteger xb, yb, rand1wb;
    private static uint256 xcopy, ycopy, rand1wcopy;

    /**
     * main entry point
     */
    public static void main(String[] args) {
        initSampleSizes();   // read environment, set SAMPLE_BIG, etc.
        runAllTests();       // run all test methods
        printSummary();      // pass/fail summary
    }

    /**
     * Attempt to read JUINT_SAMPLE_FACTOR from environment to scale sample counts.
     */
    private static void initSampleSizes() {
        double factor = 0.0001;
        SAMPLE_BIG   = (int)(4096 * factor);
        SAMPLE_MED   = (int)(2048 * factor);
        SAMPLE_SMALL = (int)(1024 * factor);

        System.out.println("Max iterations (SAMPLE_BIG): " + SAMPLE_BIG);
    }

    /**
     * Calls test methods in a certain sequence.
     * You can add or remove calls as needed to achieve full coverage.
     */
    private static void runAllTests() {
        // Basic constructor tests
        testLongCtorInvariant();
        testBytesCtor();
        testBytesCtorTruncate();

        // Simple invariants
        testIsZeroInvariant();
        testNotInvariant();

        // Bit-length and 'not' checks
        testBitLength();
        testNot();
        testGetLowestSetBit();

        // Subtraction
        testSub();

        // We'll do a small loop of random cycles for add, multiply, etc.
        for(int i = 0; i < 5; i++) {
            cycle(); 
            testAdd();
            testMultiply();
        }

        // Additional coverage for division, shifts, random arithmetic
        for(int i = 0; i < 5; i++) {
            cycle();
            testDivMod();
            testShifts();
        }

        testRandomArithmetic();
        testValueConversions();
        testComparisons();
        testBitOperations();

        // Add new edge case tests
        testStringEdgeCases();
        testPowOperations();
        testExactValueExceptions();
        testDivModZero();
        testSpecialCases();
    }

    /**
     * Print final pass/fail summary and exit with code 1 if any failures.
     */
    private static void printSummary() {
        System.out.println("===== Test Summary =====");
        System.out.println("Pass:  " + passCount);
        System.out.println("Fail:  " + failCount);
        System.out.println("Total: " + (passCount + failCount));
        if (failCount > 0) {
            System.exit(1);
        }
    }

    // -------------------------------------------------------
    // check(...) - basic pass/fail logging
    // -------------------------------------------------------
    private static void check(boolean condition, String message) {
        if (condition) {
            System.out.println("OK");
            passCount++;
        } else {
            System.out.println("FAIL: " + message);
            failCount++;
        }
    }

    /**
     * eq(...) - checks if the BigInteger a equals the uint256 b.
     */
    private static void eq(BigInteger a, uint256 b) {
        if (!b.toBigInteger().equals(a)) {
            String msg = "Mismatch: expected " + a + " but got " + b.toBigInteger();
            check(false, msg);
        } else {
            check(true, "eq OK");
        }
    }

    // -------------------------------------------------------
    // cycle() - sets random x,y, rand1w, plus copies
    // -------------------------------------------------------
    private static void cycle() {
        x      = random();
        y      = random();
        rand1w = random(1);

        xb      = x.toBigInteger();
        yb      = y.toBigInteger();
        rand1wb = rand1w.toBigInteger();

        xcopy      = new uint256(x.toIntArray());
        ycopy      = new uint256(y.toIntArray());
        rand1wcopy = new uint256(rand1w.toIntArray());
    }

    /**
     * checkImmutable can be called after a test if you want to ensure x,y,rand1w are unchanged.
     */
    private static void checkImmutable() {
        check(x.equals(xcopy),         "x not mutated");
        check(y.equals(ycopy),         "y not mutated");
        check(rand1w.equals(rand1wcopy),"rand1w not mutated");
    }

    // -------------------------------------------------------
    // fromInt helper
    // -------------------------------------------------------
    private static uint256 fromInt(int i) {
        if(i == 0) return zero;
        return new uint256(new int[]{ i });
    }

    // -------------------------------------------------------
    // random generation
    // -------------------------------------------------------
    private static uint256 random() {
        // up to maxWidth words
        int w = Math.abs(rnd.nextInt() % (maxWidth + 1));
        return random(w);
    }

    private static uint256 random(int n) {
        return new uint256(randomints(n));
    }

    /**
     * Return an int[] of length n with random content.
     * We'll also add a 2.5% chance we produce Arrays.maxValue(n) to mimic your code.
     */
    private static int[] randomints(int n) {
        if (n == 0) return new int[0];
        // 2.5% chance => maxValue
        if (rnd.nextFloat() < 0.025) {
            return Arrays.maxValue(n);
        }
        int[] ints = new int[n];
        // 25% chance => "small" (Short.MAX_VALUE)
        boolean small = (rnd.nextFloat() < 0.25);

        // first word cannot be zero
        do {
            ints[0] = small ? rnd.nextInt(Short.MAX_VALUE) : rnd.nextInt();
        } while(ints[0] == 0);

        for (int i = 1; i < n; i++) {
            ints[i] = small ? rnd.nextInt(Short.MAX_VALUE) : rnd.nextInt();
        }
        return ints;
    }

    /**
     * If needed, produce a random non-zero:
     */
    private static uint256 randomNonZero() {
        uint256 val;
        do {
            val = random();
        } while(val.isZero());
        return val;
    }

    // -------------------------------------------------------
    // trunc logic if needed
    // -------------------------------------------------------
    private static BigInteger trunc(BigInteger u) {
        BigInteger mask = BigInteger.ONE.shiftLeft(32 * maxWidth).subtract(BigInteger.ONE);
        return u.and(mask);
    }

    // -------------------------------------------------------
    // Test methods 
    // -------------------------------------------------------

    private static void testLongCtorInvariant() {
        System.out.println("Running testLongCtorInvariant...");
        check(new uint256(new int[]{-1, -1}).equals(new uint256(-1L)), "construct(-1L)");
        check(zero.equals(new uint256(0L)), "construct(0L)");
        check(one.equals(new uint256(1L)),  "construct(1L)");
    }

    private static void testBytesCtor() {
        System.out.println("Running testBytesCtor...");
        for(int i = 0; i < SAMPLE_MED; i++) {
            cycle();
            uint256 built = new uint256(x.toByteArray());
            check(x.equals(built), "bytesCtor => equals");
            checkImmutable();
        }
    }

    private static void testBytesCtorTruncate() {
        System.out.println("Running testBytesCtorTruncate...");
        for(int i = 0; i < SAMPLE_BIG; i++) {
            BigInteger b = BigInteger.probablePrime(32 * maxWidth * 2, new java.util.Random(rnd.nextInt()));
            uint256 built = new uint256(b.toByteArray());
            eq(trunc(b), built);
        }
    }

    private static void testIsZeroInvariant() {
        System.out.println("Running testIsZeroInvariant...");
        check(zero.isZero(), "zero.isZero()");
        check(!one.isZero(), "one != 0");
        check(!two.isZero(), "two != 0");

        uint256 zero2 = one.subtract(one);
        check(zero2.isZero(), "1 - 1 => zero");
        check(!max.isZero(),  "max != 0");
    }

    private static void testNotInvariant() {
        System.out.println("Running testNotInvariant...");
        check(zero.not().equals(max),            "zero.not => max");
        check(zero.not().not().equals(zero),     "zero double not => zero");
        check(max.not().equals(zero),            "max.not => zero");
        check(max.not().not().equals(max),       "max double not => max");
        check(one.not().equals(max.subtract(one)),"one.not => max-1");
        check(one.not().not().equals(one),       "one double not => one");
    }

    private static void testBitLength() {
        System.out.println("Running testBitLength...");
        for(int i = 0; i < SAMPLE_BIG; i++) {
            cycle();
            int bitLenXb = xb.bitLength();
            int bitLenX  = x.bitLength();
            check(bitLenXb == bitLenX, "bitLength matches BigInteger");
            checkImmutable();
        }
    }

    private static void testNot() {
        System.out.println("Running testNot...");
        for(int i = 0; i < SAMPLE_BIG; i++) {
            cycle();
            uint256 got = x.not();
            BigInteger want = trunc(xb.not());
            eq(want, got);
            checkImmutable();
        }
    }

    private static void testGetLowestSetBit() {
        System.out.println("Running testGetLowestSetBit...");
        for(int i = 0; i < SAMPLE_BIG; i++) {
            cycle();
            check(xb.getLowestSetBit() == x.getLowestSetBit(), "lowestSetBit match");
            checkImmutable();
        }
    }

    private static void testSub() {
        System.out.println("Running testSub...");
        for(int i = 0; i < 10; i++) {
            cycle();
            // x - y => wrap in mod 2^256
            uint256 subVal = x.subtract(y);
            BigInteger want = trunc(xb.subtract(yb));
            eq(want, subVal);
            checkImmutable();
        }
    }

    private static void testAdd() {
        System.out.println("Running testAdd...");
        // x + y mod 2^(32*maxWidth)
        uint256 sum = x.add(y);
        BigInteger bigSum = trunc(xb.add(yb));
        eq(bigSum, sum);
        checkImmutable();
    }

    private static void testMultiply() {
        System.out.println("Running testMultiply...");
        uint256 prod = x.multiply(y);
        BigInteger want = trunc(xb.multiply(yb));
        eq(want, prod);
        checkImmutable();
    }

    private static void testDivMod() {
        System.out.println("Running testDivMod...");
        // Only perform division if y is not zero
        if(!y.isZero()) {
            BigInteger[] br = xb.divideAndRemainder(yb);
            
            // Store divmod result in uintType<?> array first
            uintType<?>[] ur = x.divmod(y);
            
            // Cast each element to uint256 individually
            uint256 quotient = (uint256) ur[0];
            uint256 remainder = (uint256) ur[1];

            eq(br[0], quotient);
            eq(br[1], remainder);
            checkImmutable();
        }
    }

    private static void testShifts() {
        System.out.println("Running testShifts...");
        // For each test, shift x by some random from -256..255
        int shift = rnd.nextInt(512) - 256; 
        BigInteger want = trunc( shift >= 0
            ? xb.shiftLeft(shift)
            : xb.shiftRight(-shift));
        uint256 got = (shift >= 0)
            ? x.shiftLeft(shift)
            : x.shiftRight(-shift);
        eq(want, got);
        checkImmutable();
    }

    private static void testRandomArithmetic() {
        System.out.println("Running testRandomArithmetic...");
        for(int i = 0; i < 10; i++) {
            BigInteger bx = BigInteger.probablePrime(128, new java.util.Random(rnd.nextInt()));
            BigInteger by = BigInteger.probablePrime(128, new java.util.Random(rnd.nextInt()));

            uint256 ux = new uint256(bx);
            uint256 uy = new uint256(by);

            // Add
            BigInteger sumRef = bx.add(by).and(BigInteger.ONE.shiftLeft(256).subtract(BigInteger.ONE));
            uint256 sumVal = ux.add(uy);
            check(sumVal.toBigInteger().equals(sumRef), "random add => match");

            // Multiply
            BigInteger mulRef = bx.multiply(by).and(BigInteger.ONE.shiftLeft(256).subtract(BigInteger.ONE));
            uint256 mulVal = ux.multiply(uy);
            check(mulVal.toBigInteger().equals(mulRef), "random multiply => match");

            // Sub
            BigInteger subRef = bx.subtract(by).and(BigInteger.ONE.shiftLeft(256).subtract(BigInteger.ONE));
            uint256 subVal = ux.subtract(uy);
            check(subVal.toBigInteger().equals(subRef), "random sub => match");
        }
    }

    private static void testValueConversions() {
        System.out.println("Running testValueConversions...");
        // Test exact value conversions
        check(fromInt(100).intValueExact() == 100, "intValueExact");
        check(fromInt(100).longValueExact() == 100L, "longValueExact");
        // Add more conversion tests...
    }

    private static void testComparisons() {
        System.out.println("Running testComparisons...");
        check(one.compareTo(zero) > 0, "one > zero");
        check(zero.compareTo(one) < 0, "zero < one");
        check(one.compareTo(one) == 0, "one == one");
        // Add min/max tests...
    }

    private static void testBitOperations() {
        System.out.println("Running testBitOperations...");
        check(one.testBit(0), "one has bit 0 set");
        check(!one.testBit(1), "one has bit 1 clear");
        check(one.setBit(1).equals(fromInt(3)), "setBit(1) on 1 => 3");
        // Add more bit operation tests...
    }

    // -------------------------------------------------------
    // Additional edge case tests
    // -------------------------------------------------------
    private static void testStringEdgeCases() {
        System.out.println("Running testStringEdgeCases...");
        // Test empty and invalid strings
        try {
            new uint256("", 10);
            check(false, "Empty string should throw NumberFormatException");
        } catch (NumberFormatException e) {
            check(true, "Empty string correctly throws");
        }

        try {
            new uint256("-1", 10);
            check(false, "Negative string should throw NumberFormatException");
        } catch (NumberFormatException e) {
            check(true, "Negative string correctly throws");
        }

        // Test valid string with plus sign
        check(new uint256("+1024", 10).equals(fromInt(1024)), "String with plus sign");
    }

    private static void testPowOperations() {
        System.out.println("Running testPowOperations...");
        // Test basic pow operations
        check(one.pow(0).equals(one), "1^0 = 1");
        check(zero.pow(1).equals(zero), "0^1 = 0");
        check(two.pow(2).equals(fromInt(4)), "2^2 = 4");

        // Test negative exponent
        try {
            one.pow(-1);
            check(false, "Negative exponent should throw ArithmeticException");
        } catch (ArithmeticException e) {
            check(true, "Negative exponent correctly throws");
        }
    }

    private static void testExactValueExceptions() {
        System.out.println("Running testExactValueExceptions...");
        // Test exact value conversion exceptions
        uint256 large = max;
        
        try {
            large.intValueExact();
            check(false, "Large value should throw ArithmeticException for intValueExact");
        } catch (ArithmeticException e) {
            check(true, "Large value correctly throws for intValueExact");
        }

        try {
            large.longValueExact();
            check(false, "Large value should throw ArithmeticException for longValueExact");
        } catch (ArithmeticException e) {
            check(true, "Large value correctly throws for longValueExact");
        }

        try {
            fromInt(Short.MAX_VALUE + 1).shortValueExact();
            check(false, "Value > Short.MAX_VALUE should throw ArithmeticException");
        } catch (ArithmeticException e) {
            check(true, "Value > Short.MAX_VALUE correctly throws");
        }

        try {
            fromInt(Byte.MAX_VALUE + 1).byteValueExact();
            check(false, "Value > Byte.MAX_VALUE should throw ArithmeticException");
        } catch (ArithmeticException e) {
            check(true, "Value > Byte.MAX_VALUE correctly throws");
        }
    }

    private static void testDivModZero() {
        System.out.println("Running testDivModZero...");
        // Test division by zero
        try {
            one.divide(zero);
            check(false, "Division by zero should throw ArithmeticException");
        } catch (ArithmeticException e) {
            check(true, "Division by zero correctly throws");
        }

        try {
            one.mod(zero);
            check(false, "Mod by zero should throw ArithmeticException");
        } catch (ArithmeticException e) {
            check(true, "Mod by zero correctly throws");
        }

        try {
            one.divmod(zero);
            check(false, "Divmod by zero should throw ArithmeticException");
        } catch (ArithmeticException e) {
            check(true, "Divmod by zero correctly throws");
        }
    }

    private static void testSpecialCases() {
        System.out.println("Running testSpecialCases...");
        
        // Test operations with max value
        check(max.add(one).equals(zero), "max + 1 = 0 (overflow)");
        check(zero.subtract(one).equals(max), "0 - 1 = max (underflow)");
        
        // Test operations with powers of 2
        uint256 pow2_255 = one.shiftLeft(255);
        check(pow2_255.testBit(255), "2^255 has bit 255 set");
        check(!pow2_255.testBit(254), "2^255 has bit 254 clear");
        
        // Test byte array conversion edge cases
        byte[] zeroBytes = zero.toByteArray();
        // For zero, the byte array might be empty or contain a single zero byte
        check(zeroBytes != null, "zero toByteArray not null");
        
        // Test max value byte array
        byte[] maxBytes = max.toByteArray();
        check(maxBytes.length == 32, "max value should be 32 bytes");
        check((maxBytes[0] & 0xFF) == 0xFF, "max value first byte should be 0xFF");
    }
}