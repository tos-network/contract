package java.lang;

import java.math.BigInteger;
import java.util.Random;

/**
 * A self-contained test class for uint256.java without using JUnit.
 * It prints "OK" if a check passes, or prints the failure message if it fails.
 * At the end, it summarizes pass/fail counts and exits with code 1 if any failed.
 */
public class TestUint256 {

    // Track how many checks pass/fail
    private static int passCount = 0;
    private static int failCount = 0;

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

    /**
     * A simple check method that prints "OK" if the condition is true,
     * otherwise prints the provided message. Also increments pass/fail counters.
     */
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

    /**
     * Verify that ZERO, ONE, TWO, and MAX_VALUE are as expected.
     */
    private static void testConstants() {
        uint256 zero = uint256.ZERO;
        uint256 one  = uint256.ONE;
        uint256 two  = uint256.TWO;

        check(zero.isZero(),           "ZERO should be zero");
        check(!one.isZero(),           "ONE should not be zero");
        check(!two.isZero(),           "TWO should not be zero");

        check(zero.compareTo(one) < 0, "ZERO < ONE");
        check(two.compareTo(one) > 0,  "TWO > ONE");

        uint256 max = uint256.MAX_VALUE;
        check(!max.isZero(),          "MAX_VALUE should not be zero");
        check(max.bitLength() == 256, "MAX_VALUE should have 256 bits (2^256-1)");
    }

    /**
     * Tests constructing uint256 from various data types (long, BigInteger, strings).
     */
    private static void testConstructors() {
        // From long
        uint256 fromLong = new uint256(123456789L);
        check(fromLong.toBigInteger().equals(BigInteger.valueOf(123456789L)),
              "Construct from long 123456789 => 123456789");

        // From BigInteger
        BigInteger largeVal = BigInteger.ONE.shiftLeft(200).add(BigInteger.valueOf(9999999));
        uint256 fromBI = new uint256(largeVal);
        // It's truncated to 256 bits, which is fine, but largeVal < 2^256 anyway
        check(fromBI.toBigInteger().equals(largeVal),
              "Construct from BigInteger < 2^256 => same value");

        // From decimal string
        uint256 fromDecimal = new uint256("987654321");
        check(fromDecimal.toBigInteger().equals(BigInteger.valueOf(987654321L)),
              "Construct from decimal string '987654321'");

        // From hex string
        uint256 fromHex = new uint256("ffffff", 16);
        check(fromHex.toBigInteger().equals(new BigInteger("ffffff", 16)),
              "Construct from hex string 'ffffff'");
    }

    /**
     * Test add / addmod logic.
     */
    private static void testAdd() {
        uint256 one = uint256.ONE;
        uint256 two = uint256.TWO;

        // 1 + 2 => 3
        uint256 three = one.add(two);
        check(three.toBigInteger().equals(BigInteger.valueOf(3)), "1 + 2 => 3");

        // addmod => (3+2)%5 => 0
        uint256 five = new uint256(5L);
        uint256 am   = three.addmod(two, five);
        check(am.isZero(), "(3+2) % 5 => 0");

        // bigger test
        BigInteger val128 = BigInteger.ONE.shiftLeft(128).subtract(BigInteger.ONE); // 2^128 -1
        uint256 a = new uint256(val128);
        uint256 b = new uint256(val128);
        uint256 sum = a.add(b); // => (2^128 -1)*2 => (2^129 -2)
        BigInteger expected = val128.add(val128).and(BigInteger.ONE.shiftLeft(256).subtract(BigInteger.ONE));
        check(sum.toBigInteger().equals(expected),
              "(2^128-1)+(2^128-1)=2^129-2 mod 2^256");
    }

    /**
     * Test subtract / wrap-around.
     */
    private static void testSubtract() {
        uint256 one = uint256.ONE;
        uint256 two = uint256.TWO;

        // 2 - 1 => 1
        uint256 diff = two.subtract(one);
        check(diff.toBigInteger().equals(BigInteger.ONE), "2 - 1 => 1");

        // 1 - 2 => wrap-around => 2^256 -1
        uint256 wrap = one.subtract(two);
        BigInteger wrapVal = wrap.toBigInteger();
        BigInteger two256  = BigInteger.ONE.shiftLeft(256);
        check(wrapVal.equals(two256.subtract(BigInteger.ONE)), "1 - 2 => 2^256 -1 wrap-around");
    }

    /**
     * Test multiply, mulmod.
     */
    private static void testMultiply() {
        uint256 three = new uint256(3L);
        uint256 four  = new uint256(4L);
        uint256 twelve= three.multiply(four);
        check(twelve.toBigInteger().equals(BigInteger.valueOf(12)), "3 * 4 => 12");

        // mulmod => (4*3)%5 => 12%5 => 2
        uint256 five  = new uint256(5L);
        uint256 mm    = four.mulmod(three, five);
        check(mm.toBigInteger().equals(BigInteger.valueOf(2)), "(4*3)%5 => 2");

        // large test
        BigInteger val1 = BigInteger.ONE.shiftLeft(200);
        BigInteger val2 = BigInteger.ONE.shiftLeft(100);
        uint256 u1 = new uint256(val1);
        uint256 u2 = new uint256(val2);
        BigInteger product = val1.multiply(val2).mod(BigInteger.ONE.shiftLeft(256));
        uint256 mul = u1.multiply(u2);
        check(mul.toBigInteger().equals(product), "2^200 * 2^100 => 2^300 mod 2^256 => 2^44");
    }

    /**
     * Test divide, mod, divmod.
     */
    private static void testDivMod() {
        uint256 six  = new uint256(6L);
        uint256 five = new uint256(5L);

        // 6 / 5 => 1 remainder 1
        check(six.divide(five).toBigInteger().equals(BigInteger.ONE), "6 / 5 => 1");
        check(six.mod(five).toBigInteger().equals(BigInteger.ONE), "6 % 5 => 1");

        // The fix: store divmod(...) result in a uintType<?>[] array
        // and cast each element to uint256 individually, to avoid ClassCastException
        uintType<?>[] qrGeneric = six.divmod(five);
        check(qrGeneric.length == 2, "divmod => array of length 2");
        uint256 q = (uint256) qrGeneric[0];
        uint256 r = (uint256) qrGeneric[1];
        check(q.toBigInteger().equals(BigInteger.ONE), "quotient => 1");
        check(r.toBigInteger().equals(BigInteger.ONE), "remainder => 1");

        // bigger test
        BigInteger bigVal = new BigInteger("98765432198765432123456789");
        BigInteger bigDiv = new BigInteger("1234567890123456789");
        uint256 a = new uint256(bigVal);
        uint256 b = new uint256(bigDiv);
        uint256 quo = a.divide(b);
        uint256 rem = a.mod(b);

        // Compare w/ BigInteger
        BigInteger bigQ = bigVal.divide(bigDiv);
        BigInteger bigR = bigVal.mod(bigDiv);
        check(quo.toBigInteger().equals(bigQ), "Quotient matches BigInteger");
        check(rem.toBigInteger().equals(bigR), "Remainder matches BigInteger");
    }

    /**
     * Test bitwise: not, and, or, xor, setBit, clearBit, flipBit, etc.
     */
    private static void testBitwiseOps() {
        uint256 zero = uint256.ZERO;
        uint256 one  = uint256.ONE;
        uint256 two  = uint256.TWO;

        check(one.and(two).isZero(), "1 & 2 => 0");

        uint256 orVal = one.or(two);
        check(orVal.toBigInteger().equals(BigInteger.valueOf(3)), "1 | 2 => 3");

        uint256 xorVal = one.xor(two);
        check(xorVal.toBigInteger().equals(BigInteger.valueOf(3)), "1 ^ 2 => 3");

        // setBit(0) on zero => 1
        check(zero.setBit(0).equals(one), "setBit(0) => 1");

        // clearBit(0) on 1 => 0
        check(one.clearBit(0).isZero(), "clearBit(0) on 1 => 0");

        // flipBit(1) on 1 => 3
        uint256 flip = one.flipBit(1);
        check(flip.toBigInteger().equals(BigInteger.valueOf(3)), "flipBit(1) => 3");

        // not(0) => all bits => 2^256 -1
        uint256 notZero = zero.not();
        BigInteger two256 = BigInteger.ONE.shiftLeft(256);
        check(notZero.toBigInteger().equals(two256.subtract(BigInteger.ONE)), "~0 => 2^256-1");
    }

    /**
     * Test shifting logic: shiftLeft, shiftRight, negative shifts, etc.
     */
    private static void testShifts() {
        uint256 one = uint256.ONE;

        // 1 << 1 => 2
        uint256 sl1 = one.shiftLeft(1);
        check(sl1.toBigInteger().equals(BigInteger.valueOf(2)), "1<<1 => 2");

        // 1 << 255 => 2^255
        uint256 sl255 = one.shiftLeft(255);
        check(sl255.bitLength() == 256, "bitLength => 256 for 1<<255");

        // shiftRight
        uint256 sr255 = sl255.shiftRight(255);
        check(sr255.equals(one), " (1<<255)>>255 => 1");

        // negative shift => do opposite
        // 2^5 => 32, shiftLeft(-1) => shiftRight(1) => 16
        uint256 thirtyTwo = new uint256(32L);
        uint256 st = thirtyTwo.shiftLeft(-1); // => 16
        check(st.toBigInteger().equals(BigInteger.valueOf(16)), "32<<(-1) => 16");
    }

    /**
     * Test random arithmetic for some coverage.
     */
    private static void testRandomArithmetic() {
        Random rnd = new Random(0xBEEF_CAFE);

        for (int i = 0; i < 5; i++) {
            // random 128-bit
            BigInteger bx = new BigInteger(128, rnd);
            BigInteger by = new BigInteger(128, rnd);

            uint256 ux = new uint256(bx);
            uint256 uy = new uint256(by);

            // add
            BigInteger sumRef = bx.add(by).and(BigInteger.ONE.shiftLeft(256).subtract(BigInteger.ONE));
            uint256 sumVal = ux.add(uy);
            check(sumVal.toBigInteger().equals(sumRef), "random add => match BigInteger mod 2^256");

            // multiply
            BigInteger mulRef = bx.multiply(by).and(BigInteger.ONE.shiftLeft(256).subtract(BigInteger.ONE));
            uint256 mulVal = ux.multiply(uy);
            check(mulVal.toBigInteger().equals(mulRef), "random multiply => match BigInteger mod 2^256");
        }
    }
}