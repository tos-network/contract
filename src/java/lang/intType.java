package java.lang;

import java.math.BigInteger;
import java.io.Storable;
import java.lang.contract.Storage;

/**
 * Abstract base class for various fixed-width signed integer types.
 *
 * <p>This class uses a {@code BigInteger} for internal storage, maintaining the value in the range
 * 0 ≤ value < 2^(bitSize()). When a signed interpretation is needed, the most significant bit is
 * checked to perform a two's complement conversion.</p>
 *
 * @param <T> the concrete subclass type (e.g. {@code int160}, {@code int256}, etc.)
 */
public abstract class intType<T extends intType<T>>
    extends Number
    implements Comparable<T>, Storable {

    /** Default radix used for {@link #toString()}. */
    protected static final int DEFAULT_RADIX = 10;

    /**
     * Internal storage of the number using a BigInteger, reduced modulo 2^(bitSize()).
     */
    protected BigInteger ints;

    /** Used by the Storable interface to track the storage slot. */
    private int slot = Storable.NO_SLOT;

    /* ====================================================== */
    /*                 Abstract Methods / Hooks               */
    /* ====================================================== */

    /**
     * Returns the number of bits for this type (e.g. 160 or 256).
     *
     * @return the bit width
     */
    public abstract int bitSize();

    /**
     * Returns the maximum representable value for this type (e.g. int160.MAX_VALUE).
     *
     * @return the maximum value
     */
    public abstract T getMaxValue();

    /**
     * Returns the minimum representable value for this type (e.g. int160.MIN_VALUE).
     *
     * @return the minimum value
     */
    public abstract T getMinValue();

    /**
     * Creates a new instance of the concrete subclass.
     */
    protected abstract T newInstance(BigInteger value);

    /**
     * Returns the maximum width in 32-bit words.
     */
    protected abstract int getMaxWidth();

    /* ====================================================== */
    /*                    Helper Methods                      */
    /* ====================================================== */

    /**
     * Returns the modulus 2^(bitSize()).
     *
     * @return the modulus as a BigInteger
     */
    protected BigInteger getMod() {
        return BigInteger.ONE.shiftLeft(bitSize());
    }

    /**
     * Reduces the given BigInteger into the range 0 ≤ x < 2^(bitSize()).
     *
     * @param x the BigInteger to reduce
     * @return the reduced value
     */
    protected BigInteger modValue(BigInteger x) {
        BigInteger mod = getMod();
        return x.mod(mod);
    }

    /**
     * Returns the signed BigInteger representation of the number.
     * If the most significant bit (bitSize()-1) is set, the value is interpreted as negative,
     * and the modulus is subtracted to obtain the proper two's complement value.
     *
     * @return the signed BigInteger value
     */
    public BigInteger toBigInteger() {
        BigInteger mod = getMod();
        if (ints.testBit(bitSize() - 1)) {
            return ints.subtract(mod);
        }
        return ints;
    }

    /**
     * Returns this value as T (used for operations returning self).
     *
     * @return this instance cast to T
     */
    @SuppressWarnings("unchecked")
    protected T self() {
        return (T) this;
    }

    /**
     * Fixes the radix to a valid range. If the provided radix is less than
     * {@link Character#MIN_RADIX} or greater than {@link Character#MAX_RADIX}, it returns {@link #DEFAULT_RADIX}.
     *
     * @param radix the input radix
     * @return a valid radix value
     */
    private static int fixRadix(int radix) {
        if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX) {
            return DEFAULT_RADIX;
        }
        return radix;
    }

    /* ====================================================== */
    /*                    Constructors                        */
    /* ====================================================== */

    /**
     * Constructs from a {@code long}, preserving its sign.
     *
     * @param l the long value
     */
    protected intType(final long l) {
        // Convert long to BigInteger and reduce modulo 2^(bitSize())
        this.ints = modValue(BigInteger.valueOf(l));
    }

    /**
     * Constructs from a BigInteger.
     *
     * @param b the BigInteger value
     */
    protected intType(final BigInteger b) {
        this.ints = modValue(b);
    }

    /**
     * Constructs from a string representation with the given radix.
     *
     * @param s the string representation
     * @param radix the radix to use
     */
    protected intType(final String s, final int radix) {
        this(new BigInteger(s, fixRadix(radix)));
    }

    /**
     * Constructs from a byte array with max/min value bounds.
     */
    protected intType(final byte[] bytes, final T maxValue, final T minValue) {
        this(new BigInteger(bytes));
        BigInteger value = toBigInteger();
        if (value.compareTo(maxValue.toBigInteger()) > 0 || 
            value.compareTo(minValue.toBigInteger()) < 0) {
            throw new ArithmeticException("Value out of range");
        }
    }

    /**
     * Constructs from an int array.
     */
    protected intType(final int[] ints) {
        this(new BigInteger(1, toByteArray(ints)));
    }

    /**
     * Convert int array to byte array.
     */
    private static byte[] toByteArray(int[] ints) {
        byte[] bytes = new byte[ints.length * 4];
        for (int i = 0; i < ints.length; i++) {
            int offset = i * 4;
            bytes[offset] = (byte)(ints[i] >> 24);
            bytes[offset + 1] = (byte)(ints[i] >> 16);
            bytes[offset + 2] = (byte)(ints[i] >> 8);
            bytes[offset + 3] = (byte)ints[i];
        }
        return bytes;
    }

    /* ====================================================== */
    /*                    Arithmetic Operations             */
    /* ====================================================== */

    /** {@code this + other} */
    public T add(T other) {
        BigInteger mod = getMod();
        BigInteger result = this.ints.add(other.ints).mod(mod);
        return newInstance(result);
    }

    /** {@code this - other} */
    public T subtract(T other) {
        BigInteger mod = getMod();
        BigInteger result = this.ints.subtract(other.ints).mod(mod);
        return newInstance(result);
    }

    /** {@code this * other} */
    public T multiply(T other) {
        if (isZero() || other.isZero()) {
            return createFromInt(0);
        }
        
        // Perform multiplication without modulo to check for overflow
        BigInteger rawResult = this.toBigInteger().multiply(other.toBigInteger());
        
        // Check if result exceeds the valid range for this type
        if (rawResult.compareTo(getMaxValue().toBigInteger()) > 0 ||
            rawResult.compareTo(getMinValue().toBigInteger()) < 0) {
            return createFromInt(0);
        }
        
        // If within range, apply modulo and return the result
        BigInteger result = modValue(rawResult);
        return newInstance(result);
    }

    /** {@code this / other}, throws ArithmeticException if {@code other} is zero */
    public T divide(T other) {
        if (other.isZero()) {
            throw new ArithmeticException("Division by zero");
        }
        // Use signed BigInteger for division
        BigInteger dividend = this.toBigInteger();
        BigInteger divisor = other.toBigInteger();
        BigInteger quotient = dividend.divide(divisor);
        if (quotient.compareTo(getMaxValue().toBigInteger()) > 0 ||
            quotient.compareTo(getMinValue().toBigInteger()) < 0) {
            return createFromInt(0);
        }
        quotient = modValue(quotient);
        return newInstance(quotient);
    }

    /** {@code this % other}, throws ArithmeticException if {@code other} is zero */
    public T mod(T other) {
        if (other.isZero()) {
            throw new ArithmeticException("Division by zero");
        }
        BigInteger dividend = this.toBigInteger();
        BigInteger divisor = other.toBigInteger();
        BigInteger result = dividend.mod(divisor);
        result = modValue(result);
        return newInstance(result);
    }

    /** {@code this << n} */
    public T shiftLeft(int n) {
        if (n == 0) return self();
        if (n < 0) return shiftRight(-n);
        BigInteger mod = getMod();
        BigInteger result = this.ints.shiftLeft(n).mod(mod);
        return newInstance(result);
    }

    /** {@code this >> n} (arithmetic right shift) */
    public T shiftRight(int n) {
        if (n == 0) return self();
        if (n < 0) return shiftLeft(-n);
        // Convert the internal value to its signed representation before shifting
        BigInteger shifted = toBigInteger().shiftRight(n);
        shifted = modValue(shifted);
        return newInstance(shifted);
    }

    /* ====================================================== */
    /*                    Bitwise Operations                */
    /* ====================================================== */

    /**
     * Returns the number of bits required to represent this number (in signed representation).
     *
     * @return the bit length
     */
    public int bitLength() {
        return toBigInteger().bitLength();
    }

    /**
     * Tests whether the bit at position n is set.
     *
     * <p>Note: Bit positions follow BigInteger's convention, where bit 0 is the least significant bit.</p>
     *
     * @param n the bit index
     * @return {@code true} if the bit is set, {@code false} otherwise
     * @throws ArithmeticException if the bit index is out of range
     */
    public boolean testBit(int n) {
        if (n < 0 || n >= bitSize()) {
            throw new ArithmeticException("Bit index out of range");
        }
        return ints.testBit(n);
    }

    /** {@code this & other} */
    public T and(T other) {
        BigInteger mod = getMod();
        BigInteger result = this.ints.and(other.ints).mod(mod);
        return newInstance(result);
    }

    /** {@code this | other} */
    public T or(T other) {
        BigInteger mod = getMod();
        BigInteger result = this.ints.or(other.ints).mod(mod);
        return newInstance(result);
    }

    /** {@code this ^ other} */
    public T xor(T other) {
        BigInteger mod = getMod();
        BigInteger result = this.ints.xor(other.ints).mod(mod);
        return newInstance(result);
    }

    /** {@code ~this} */
    public T not() {
        BigInteger mod = getMod();
        // Invert the bits and reduce to bitSize() bits (i.e., XOR with 2^(bitSize()) - 1)
        BigInteger result = this.ints.xor(mod.subtract(BigInteger.ONE));
        result = result.mod(mod);
        return newInstance(result);
    }

    /* ====================================================== */
    /*                 Comparison Operations                */
    /* ====================================================== */

    @Override
    public int compareTo(T other) {
        return this.toBigInteger().compareTo(other.toBigInteger());
    }

    /* ====================================================== */
    /*                 Storable Interface Implementation      */
    /* ====================================================== */

    @Override
    public void setSlot(int slot) {
        this.slot = slot;
    }

    @Override
    public int getSlot() {
        return this.slot;
    }

    @Override
    public boolean save() {
        if (this.slot == Storable.NO_SLOT) {
            return false;
        }
        Storage storage = Storage.getStorage();
        storage.SetStorageFixedValue(this.slot, toByteArray());
        return true;
    }

    @Override
    public boolean load() {
        if (this.slot == Storable.NO_SLOT) {
            return false;
        }
        Storage storage = Storage.getStorage();
        byte[] bytes = storage.GetStorageFixedValue(this.slot);
        if (bytes == null) {
            this.ints = BigInteger.ZERO;
            return false;
        }
        fromByteArray(bytes);
        return true;
    }

    /* ====================================================== */
    /*                    Number Methods                    */
    /* ====================================================== */

    @Override
    public int intValue() {
        return toBigInteger().intValue();
    }

    @Override
    public long longValue() {
        return toBigInteger().longValue();
    }

    @Override
    public float floatValue() {
        return toBigInteger().floatValue();
    }

    @Override
    public double doubleValue() {
        return toBigInteger().doubleValue();
    }

    /**
     * Converts the number to a big-endian byte array using its signed representation.
     *
     * @return the byte array representation
     */
    public byte[] toByteArray() {
        return toBigInteger().toByteArray();
    }

    /**
     * Restores the internal value from a byte array.
     *
     * @param data the byte array
     */
    public void fromByteArray(byte[] data) {
        this.ints = modValue(new BigInteger(data));
    }

    /**
     * Returns the signum of this number: -1 for negative, 0 for zero, 1 for positive.
     *
     * @return the signum value
     */
    public int signum() {
        return toBigInteger().signum();
    }

    /* ====================================================== */
    /*                 Object Method Overrides              */
    /* ====================================================== */

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        @SuppressWarnings("unchecked")
        T other = (T) obj;
        return this.toBigInteger().equals(other.toBigInteger());
    }

    @Override
    public int hashCode() {
        return toBigInteger().hashCode();
    }

    /**
     * Creates a new instance from an int value.
     *
     * @param value the int value
     * @return a new instance of T
     */
    protected T createFromInt(int value) {
        return newInstance(modValue(BigInteger.valueOf(value)));
    }

    /**
     * Creates a new instance representing zero.
     *
     * @return a new instance of T with value zero
     */
    protected T createNew() {
        return newInstance(BigInteger.ZERO);
    }

    /**
     * Sets the sign of this number.
     *
     * <p>This method adjusts the internal value to reflect the desired sign using two's complement conversion.</p>
     *
     * @param negative {@code true} to set the number as negative, {@code false} for positive
     */
    protected void setNegative(boolean negative) {
        BigInteger mod = getMod();
        BigInteger signed = toBigInteger();
        if (negative && signed.signum() >= 0) {
            signed = signed.subtract(mod);
        } else if (!negative && signed.signum() < 0) {
            signed = signed.add(mod);
        }
        this.ints = modValue(signed);
    }

    /**
     * Returns true if this number is zero.
     */
    public boolean isZero() {
        return this.ints.equals(BigInteger.ZERO);
    }

    /**
     * Returns true if this number is negative.
     */
    public boolean isNegative() {
        return this.ints.testBit(bitSize() - 1);
    }

    /**
     * Returns the negation of this value (-this).
     */
    public T negate() {
        if (isZero()) return self();
        BigInteger mod = getMod();
        return newInstance(mod.subtract(this.ints));
    }

    /**
     * Returns the absolute value of this number.
     */
    public T abs() {
        if (!isNegative() || isZero()) return self();
        return negate();
    }
}