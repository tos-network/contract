package java.lang;

import java.math.BigInteger;
import java.lang.bytes.Arrays;

/**
 * Represents unsigned values less than {@code 2**64}.
 *
 * As indicated by the type signatures, arithmetic operations are not applicable
 * to types of other widths in this package. Copy constructors can be used to
 * explicitly promote or truncate values for the purposes of interoperability.
 */
public final class UInt64 extends UInt<UInt64> {
    static final int MAX_WIDTH = 2; // 64 bits = 2 * 32 bits

    /**
     * Maximum representable value.
     */
    public static UInt64 MAX_VALUE = new UInt64(Arrays.maxValue(MAX_WIDTH));

    public static UInt64 ZERO = new UInt64(Arrays.ZERO);
    public static UInt64 ONE = new UInt64(Arrays.ONE);
    public static UInt64 TWO = new UInt64(Arrays.TWO);

    /**
     * Construct from a big-endian {@code int} array.
     *
     * If {@code ints} exceeds {@link MAX_VALUE}, only the maximum prefix
     * will be considered. Leaves {@code ints} untouched.
     */
    public UInt64(final int[] ints) {
        super(ints, MAX_WIDTH);
    }

    /**
     * Construct from a big-endian {@code byte} array.
     *
     * If {@code bytes} exceeds {@link MAX_VALUE}, only the maximum prefix
     * will be considered. Leaves {@code bytes} untouched.
     */
    public UInt64(final byte[] bytes) {
        super(bytes, MAX_VALUE);
    }

    /**
     * Construct from a base ten string.
     *
     * Excessively wide numbers will be truncated.
     *
     * @throws NumberFormatException Negative, invalid or zero-length number.
     */
    public UInt64(final String s) {
        this(s, 10);
    }

    /**
     * Construct from a string in the given radix.
     *
     * Excessively wide numbers will be truncated.
     *
     * @throws NumberFormatException Negative, invalid or zero-length number.
     */
    public UInt64(final String s, final int radix) {
        super(s, radix, MAX_WIDTH);
    }

    /**
     * Construct from a {@link BigInteger}.
     *
     * If {@code b} exceeds {@link MAX_VALUE}, it's truncated.
     */
    public UInt64(final BigInteger b) {
        super(b, MAX_WIDTH);
    }

    /**
     * Construct from a {@code long}, when considered unsigned.
     *
     * For low values of {@code v}, an array cache may be used.
     */
    public UInt64(final long v) {
        super(v);
    }

    public UInt64 not() {
        return new UInt64(Arrays.not(ints, MAX_VALUE.ints));
    }

    public UInt64 and(final UInt64 other) {
        return new UInt64(Arrays.and(ints, other.ints));
    }

    public UInt64 or(final UInt64 other) {
        return new UInt64(Arrays.or(ints, other.ints));
    }

    public UInt64 xor(final UInt64 other) {
        return new UInt64(Arrays.xor(ints, other.ints));
    }

    public UInt64 setBit(final int bit) {
        if (bit < 0)
            throw new ArithmeticException("Negative bit address");
        return ((MAX_WIDTH <= bit >>> 5) ? this :
                new UInt64(Arrays.setBit(ints, bit)));
    }

    public UInt64 clearBit(final int bit) {
        if (bit < 0)
            throw new ArithmeticException("Negative bit address");
        return ((ints.length <= bit >>> 5) ? this :
                new UInt64(Arrays.clearBit(ints, bit)));
    }

    public UInt64 flipBit(final int bit) {
        if (bit < 0)
            throw new ArithmeticException("Negative bit address");
        return ((MAX_WIDTH <= bit >>> 5) ? this :
                new UInt64(Arrays.flipBit(ints, bit)));
    }

    public UInt64 shiftLeft(final int places) {
        return new UInt64(
                0 < places ?
                        Arrays.lshift(ints, places, MAX_WIDTH) :
                        Arrays.rshift(ints, -places, MAX_WIDTH));
    }

    public UInt64 shiftRight(final int places) {
        return new UInt64(
                0 < places ?
                        Arrays.rshift(ints, places, MAX_WIDTH) :
                        Arrays.lshift(ints, -places, MAX_WIDTH));
    }

    public UInt64 inc() {
        return new UInt64(Arrays.inc(ints, MAX_WIDTH));
    }

    public UInt64 dec() {
        return isZero() ? MAX_VALUE : new UInt64(Arrays.dec(ints));
    }

    public UInt64 add(final UInt64 other) {
        return (isZero() ? other :
                (other.isZero() ? this :
                        new UInt64(Arrays.add(ints, other.ints, MAX_WIDTH))));
    }

    public UInt64 addmod(final UInt64 add, final UInt64 mod) {
        if (mod.isZero())
            throw new ArithmeticException("div/mod by zero");
        if (isZero() && add.isZero())
            return ZERO;
        return new UInt64(Arrays.addmod(ints, add.ints, mod.ints));
    }

    public UInt64 subtract(final UInt64 other) {
        if (other.isZero())
            return this;
        final int cmp = compareTo(other);
        return (cmp == 0 ? ZERO :
                new UInt64(
                        cmp < 0 ?
                                Arrays.subgt(ints, other.ints, MAX_VALUE.ints) :
                                Arrays.sub(ints, other.ints)));
    }

    public UInt64 multiply(final UInt64 other) {
        if (ints.length == 0 || other.ints.length == 0)
            return ZERO;
        return new UInt64(Arrays.multiply(ints, other.ints, MAX_WIDTH));
    }

    public UInt64 mulmod(final UInt64 mul, final UInt64 mod) {
        if (mod.isZero())
            throw new ArithmeticException("div/mod by zero");
        return new UInt64(Arrays.mulmod(ints, mul.ints, mod.ints));
    }

    public UInt64 pow(final int exp) {
        if (exp < 0)
            throw new ArithmeticException("Negative exponent");
        if (exp == 0)
            return ONE;
        if (isZero())
            return this;
        return (exp == 1 ? this :
                new UInt64(Arrays.pow(ints, getLowestSetBit(), exp, MAX_WIDTH)));
    }

    public UInt64 divide(final UInt64 other) {
        if (other.isZero())
            throw new ArithmeticException("div/mod by zero");
        if (isZero())
            return ZERO;
        final int cmp = compareTo(other);
        return (cmp < 0 ? ZERO :
                (cmp == 0 ? ONE :
                        new UInt64(Arrays.divide(ints, other.ints))));
    }

    public UInt64 mod(final UInt64 other) {
        if (other.isZero())
            throw new ArithmeticException("div/mod by zero");
        if (isZero())
            return ZERO;
        final int cmp = compareTo(other);
        return (cmp < 0 ? this :
                (cmp == 0 ? ZERO :
                        new UInt64(Arrays.mod(ints, other.ints))));
    }

    public UInt64[] divmod(final UInt64 other) {
        if (other.isZero())
            throw new ArithmeticException("div/mod by zero");
        if (isZero())
            return new UInt64[]{ZERO, ZERO};
        final int cmp = compareTo(other);
        if (cmp < 0)
            return new UInt64[]{ZERO, this};
        if (cmp == 0)
            return new UInt64[]{ONE, ZERO};

        final int[][] qr = Arrays.divmod(ints, other.ints);
        return new UInt64[]{new UInt64(qr[0]), new UInt64(qr[1])};
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof BigInteger)
            return Arrays.compare(ints, (BigInteger) other, MAX_WIDTH) == 0;
        return super.equals(other);
    }
}