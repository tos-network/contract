package java.lang;

import java.lang.types.Arrays;
import java.math.BigInteger;

/**
 * Represents unsigned values less than {@code 2**64}.
 *
 * As indicated by the type signatures, arithmetic operations are not applicable
 * to types of other widths in this package. Copy constructors can be used to
 * explicitly promote or truncate values for the purposes of interoperability.
 */
public final class uint64 extends uintType<uint64> {
    static final int MAX_WIDTH = 2; // 64 bits = 2 * 32 bits

    /**
     * Maximum representable value.
     */
    public static uint64 MAX_VALUE = new uint64(Arrays.maxValue(MAX_WIDTH));

    public static uint64 ZERO = new uint64(Arrays.ZERO);
    public static uint64 ONE = new uint64(Arrays.ONE);
    public static uint64 TWO = new uint64(Arrays.TWO);

    /**
     * Construct from a big-endian {@code int} array.
     *
     * If {@code ints} exceeds {@link MAX_VALUE}, only the maximum prefix
     * will be considered. Leaves {@code ints} untouched.
     */
    public uint64(final int[] ints) {
        super(ints, MAX_WIDTH);
    }

    /**
     * Construct from a big-endian {@code byte} array.
     *
     * If {@code bytes} exceeds {@link MAX_VALUE}, only the maximum prefix
     * will be considered. Leaves {@code bytes} untouched.
     */
    public uint64(final byte[] bytes) {
        super(bytes, MAX_VALUE);
    }

    /**
     * Construct from a base ten string.
     *
     * Excessively wide numbers will be truncated.
     *
     * @throws NumberFormatException Negative, invalid or zero-length number.
     */
    public uint64(final String s) {
        this(s, 10);
    }

    /**
     * Construct from a string in the given radix.
     *
     * Excessively wide numbers will be truncated.
     *
     * @throws NumberFormatException Negative, invalid or zero-length number.
     */
    public uint64(final String s, final int radix) {
        super(s, radix, MAX_WIDTH);
    }

    /**
     * Construct from a {@link BigInteger}.
     *
     * If {@code b} exceeds {@link MAX_VALUE}, it's truncated.
     */
    public uint64(final BigInteger b) {
        super(b, MAX_WIDTH);
    }

    /**
     * Construct from a {@code long}, when considered unsigned.
     *
     * For low values of {@code v}, an array cache may be used.
     */
    public uint64(final long v) {
        super(v);
    }

    public uint64 not() {
        return new uint64(Arrays.not(ints, MAX_VALUE.ints));
    }

    public uint64 and(final uint64 other) {
        return new uint64(Arrays.and(ints, other.ints));
    }

    public uint64 or(final uint64 other) {
        return new uint64(Arrays.or(ints, other.ints));
    }

    public uint64 xor(final uint64 other) {
        return new uint64(Arrays.xor(ints, other.ints));
    }

    public uint64 setBit(final int bit) {
        if (bit < 0)
            throw new ArithmeticException("Negative bit address");
        return ((MAX_WIDTH <= bit >>> 5) ? this :
                new uint64(Arrays.setBit(ints, bit)));
    }

    public uint64 clearBit(final int bit) {
        if (bit < 0)
            throw new ArithmeticException("Negative bit address");
        return ((ints.length <= bit >>> 5) ? this :
                new uint64(Arrays.clearBit(ints, bit)));
    }

    public uint64 flipBit(final int bit) {
        if (bit < 0)
            throw new ArithmeticException("Negative bit address");
        return ((MAX_WIDTH <= bit >>> 5) ? this :
                new uint64(Arrays.flipBit(ints, bit)));
    }

    public uint64 shiftLeft(final int places) {
        return new uint64(
                0 < places ?
                        Arrays.lshift(ints, places, MAX_WIDTH) :
                        Arrays.rshift(ints, -places, MAX_WIDTH));
    }

    public uint64 shiftRight(final int places) {
        return new uint64(
                0 < places ?
                        Arrays.rshift(ints, places, MAX_WIDTH) :
                        Arrays.lshift(ints, -places, MAX_WIDTH));
    }

    public uint64 inc() {
        return new uint64(Arrays.inc(ints, MAX_WIDTH));
    }

    public uint64 dec() {
        return isZero() ? MAX_VALUE : new uint64(Arrays.dec(ints));
    }

    public uint64 add(final uint64 other) {
        return (isZero() ? other :
                (other.isZero() ? this :
                        new uint64(Arrays.add(ints, other.ints, MAX_WIDTH))));
    }

    public uint64 addmod(final uint64 add, final uint64 mod) {
        if (mod.isZero())
            throw new ArithmeticException("div/mod by zero");
        if (isZero() && add.isZero())
            return ZERO;
        return new uint64(Arrays.addmod(ints, add.ints, mod.ints));
    }

    public uint64 subtract(final uint64 other) {
        if (other.isZero())
            return this;
        final int cmp = compareTo(other);
        return (cmp == 0 ? ZERO :
                new uint64(
                        cmp < 0 ?
                                Arrays.subgt(ints, other.ints, MAX_VALUE.ints) :
                                Arrays.sub(ints, other.ints)));
    }

    public uint64 multiply(final uint64 other) {
        if (ints.length == 0 || other.ints.length == 0)
            return ZERO;
        return new uint64(Arrays.multiply(ints, other.ints, MAX_WIDTH));
    }

    public uint64 mulmod(final uint64 mul, final uint64 mod) {
        if (mod.isZero())
            throw new ArithmeticException("div/mod by zero");
        return new uint64(Arrays.mulmod(ints, mul.ints, mod.ints));
    }

    public uint64 pow(final int exp) {
        if (exp < 0)
            throw new ArithmeticException("Negative exponent");
        if (exp == 0)
            return ONE;
        if (isZero())
            return this;
        return (exp == 1 ? this :
                new uint64(Arrays.pow(ints, getLowestSetBit(), exp, MAX_WIDTH)));
    }

    public uint64 divide(final uint64 other) {
        if (other.isZero())
            throw new ArithmeticException("div/mod by zero");
        if (isZero())
            return ZERO;
        final int cmp = compareTo(other);
        return (cmp < 0 ? ZERO :
                (cmp == 0 ? ONE :
                        new uint64(Arrays.divide(ints, other.ints))));
    }

    public uint64 mod(final uint64 other) {
        if (other.isZero())
            throw new ArithmeticException("div/mod by zero");
        if (isZero())
            return ZERO;
        final int cmp = compareTo(other);
        return (cmp < 0 ? this :
                (cmp == 0 ? ZERO :
                        new uint64(Arrays.mod(ints, other.ints))));
    }

    public uint64[] divmod(final uint64 other) {
        if (other.isZero())
            throw new ArithmeticException("div/mod by zero");
        if (isZero())
            return new uint64[]{ZERO, ZERO};
        final int cmp = compareTo(other);
        if (cmp < 0)
            return new uint64[]{ZERO, this};
        if (cmp == 0)
            return new uint64[]{ONE, ZERO};

        final int[][] qr = Arrays.divmod(ints, other.ints);
        return new uint64[]{new uint64(qr[0]), new uint64(qr[1])};
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof BigInteger)
            return Arrays.compare(ints, (BigInteger) other, MAX_WIDTH) == 0;
        return super.equals(other);
    }

    @Override
    public uintType<uint64> getMaxValue() {
        return MAX_VALUE;
    }
}