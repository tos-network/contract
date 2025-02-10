package java.lang;

import java.math.BigInteger;
import static java.lang.types.Arrays.LONG;
import java.io.Storable;
import java.lang.contract.Storage;
import java.lang.types.Arrays;
import java.lang.types.StringUtil;

/**
 * An abstract base class for various unsigned integer types.
 *
 * @param <T> the concrete subclass type (e.g. {@code uint160}, {@code uint256}, etc.)
 */
public abstract class uintType<T extends uintType<T>>
    extends Number
    implements Comparable<T>, Storable {

  /** Default string radix for {@link #toString()}. */
  protected static final int DEFAULT_RADIX = 10;

  /**
   * The underlying array of 32-bit words, stored in big-endian format:
   *   index 0 => most significant 32 bits,
   *   index (length - 1) => least significant 32 bits.
   */
  protected int[] ints;

  /** Used for {@link Storable} interface to track the storage slot. */
  private int slot = Storable.NO_SLOT;


  /* ====================================================== */
  /*                 Abstract / Hook Methods                */
  /* ====================================================== */

  /**
   * Returns the maximum representable value for this type
   * (e.g. {@code uint160.MAX_VALUE} or {@code uint256.MAX_VALUE}).
   */
  public abstract T getMaxValue();

  /**
   * Returns the maximum number of 32-bit words this type can hold.
   * For example, {@code uint160} = 5, {@code uint256} = 8, etc.
   */
  protected abstract int getMaxWidth();

  /**
   * Creates a new instance of the concrete subclass, using the given
   * big-endian {@code int[]} array. This is used internally by the base class
   * to produce new instances of type {@code T}.
   *
   * @param ints the big-endian array to wrap or truncate
   * @return a new instance of T
   */
  protected abstract T newInstance(int[] ints);


  /* ====================================================== */
  /*                     Constructors                       */
  /* ====================================================== */

  /**
   * Constructs from a {@code long}, treating it as unsigned.
   */
  protected uintType(final long l) {
    this.ints = Arrays.valueOf(l);
    // Strip leading zeroes if it exceeds the max width
    this.ints = Arrays.stripLeadingZeroes(this.ints,
        Math.max(0, this.ints.length - getMaxWidth()));
  }

  /**
   * Constructs from a big-endian {@code int[]} array.
   * Truncates to {@link #getMaxWidth()} if needed and removes leading zeros.
   */
  protected uintType(final int[] ints) {
    this.ints = Arrays.stripLeadingZeroes(
        ints, Math.max(0, ints.length - getMaxWidth())
    );
  }

  /**
   * Internal constructor that takes both an array and a maxWidth,
   * used by other constructors to handle truncation.
   */
  protected uintType(final int[] ints, final int maxWidth) {
    this.ints = Arrays.stripLeadingZeroes(
        ints, Math.max(0, ints.length - maxWidth)
    );
  }

  /**
   * Constructs from another {@code uintType<?>}, possibly of a different width,
   * and truncates if needed.
   */
  protected uintType(final uintType<?> other) {
    this(other.ints, other.getMaxWidth());
  }

  /**
   * Constructs from a {@link BigInteger}, converting it to unsigned representation.
   * If it is negative, it is turned positive (or you could throw an exception).
   */
  protected uintType(final BigInteger b) {
    BigInteger nonNeg = (b.signum() < 0) ? b.negate() : b;
    // This presupposes you have Arrays.from(BigInteger, int)
    this.ints = Arrays.from(nonNeg, getMaxWidth());
  }

  /**
   * Constructs from a string {@code s} with the given {@code radix}.
   * If out of range, it will use base 10. If it exceeds the max width, it truncates.
   */
  protected uintType(final String s, final int radix) {
    this.ints = StringUtil.fromString(s, fixRadix(radix), getMaxWidth());
  }

  /**
   * A constructor for byte[] that references a maximum value.
   * This uses Arrays.from(byte[], maxValue.ints) then truncates if needed.
   * Typically called by child classes' constructors that do `super(bytes, MAX_VALUE)`.
   */
  protected uintType(final byte[] bytes, final T maxValue) {
    // Convert from big-endian bytes to an int[] up to maxValue.ints length
    this(Arrays.from(bytes, maxValue.ints), maxValue.ints.length);
  }

  /**
   * Fixes the radix to a valid range. If out of {@link Character#MIN_RADIX} or
   * {@link Character#MAX_RADIX}, returns {@link #DEFAULT_RADIX}.
   */
  private static int fixRadix(int radix) {
    if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX) {
      return DEFAULT_RADIX;
    }
    return radix;
  }


  /* ====================================================== */
  /*                    Arithmetic Ops                      */
  /* ====================================================== */

  /** {@code ~this} */
  public T not() {
    // effectively: this ^ getMaxValue()
    return newInstance(Arrays.not(this.ints, getMaxValue().ints));
  }

  /** {@code this & other} */
  public T and(T other) {
    return newInstance(Arrays.and(this.ints, other.ints));
  }

  /** {@code this | other} */
  public T or(T other) {
    return newInstance(Arrays.or(this.ints, other.ints));
  }

  /** {@code this ^ other} */
  public T xor(T other) {
    return newInstance(Arrays.xor(this.ints, other.ints));
  }

  /**
   * {@code this | (1 << bit)}
   */
  @SuppressWarnings("unchecked")
  public T setBit(int bit) {
    if (bit < 0) {
      throw new ArithmeticException("Negative bit index");
    }
    // If bit is beyond the max width, return this unchanged
    return (getMaxWidth() <= (bit >>> 5))
        ? (T) this
        : newInstance(Arrays.setBit(this.ints, bit));
  }

  /**
   * {@code this & ~(1 << bit)}
   */
  @SuppressWarnings("unchecked")
  public T clearBit(int bit) {
    if (bit < 0) {
      throw new ArithmeticException("Negative bit index");
    }
    // If bit is beyond the current array length, no change
    return (this.ints.length <= (bit >>> 5))
        ? (T) this
        : newInstance(Arrays.clearBit(this.ints, bit));
  }

  /**
   * {@code this ^ (1 << bit)}
   */
  @SuppressWarnings("unchecked")
  public T flipBit(int bit) {
    if (bit < 0) {
      throw new ArithmeticException("Negative bit index");
    }
    // If bit is beyond the max width, no change
    return (getMaxWidth() <= (bit >>> 5))
        ? (T) this
        : newInstance(Arrays.flipBit(this.ints, bit));
  }

  /**
   * Shift left by {@code places} if {@code places > 0},
   * otherwise shift right by {@code -places}.
   */
  public T shiftLeft(int places) {
    if (places == 0) {
      return self();
    }
    if (places < 0) {
      return shiftRight(-places);
    }
    return newInstance(Arrays.lshift(this.ints, places, getMaxWidth()));
  }

  /**
   * Shift right by {@code places} if {@code places > 0},
   * otherwise shift left by {@code -places}.
   */
  public T shiftRight(int places) {
    if (places == 0) {
      return self();
    }
    if (places < 0) {
      return shiftLeft(-places);
    }
    return newInstance(Arrays.rshift(this.ints, places, getMaxWidth()));
  }

  /**
   * {@code this + 1}
   */
  public T inc() {
    return newInstance(Arrays.inc(this.ints, getMaxWidth()));
  }

  /**
   * {@code this - 1}, wrapping to {@code getMaxValue()} if underflow occurs.
   */
  public T dec() {
    if (isZero()) {
      return getMaxValue();
    }
    return newInstance(Arrays.dec(this.ints));
  }

  /**
   * {@code this + other} (mod 2^(getMaxWidth()*32)).
   */
  public T add(T other) {
    if (this.isZero()) {
      return other;
    }
    if (other.isZero()) {
      return self();
    }
    return newInstance(Arrays.add(this.ints, other.ints, getMaxWidth()));
  }

  /**
   * {@code (this + add) % mod}, throws if {@code mod.isZero()}.
   */
  public T addmod(T add, T mod) {
    if (mod.isZero()) {
      throw new ArithmeticException("div/mod by zero");
    }
    if (this.isZero() && add.isZero()) {
      return newInstance(Arrays.ZERO);
    }
    return newInstance(Arrays.addmod(this.ints, add.ints, mod.ints));
  }

  /**
   * {@code this - other}, wrapping to (this - other + 2^N) if this < other.
   */
  public T subtract(T other) {
    if (other.isZero()) {
      return self();
    }
    int cmp = compareTo(other);
    if (cmp == 0) {
      return newInstance(Arrays.ZERO);
    }
    if (cmp < 0) {
      // subgt: (this - other + 2^N)
      return newInstance(Arrays.subgt(this.ints, other.ints, getMaxValue().ints));
    }
    // normal subtraction
    return newInstance(Arrays.sub(this.ints, other.ints));
  }

  /**
   * {@code this * other}
   */
  public T multiply(T other) {
    if (this.ints.length == 0 || other.ints.length == 0) {
      return newInstance(Arrays.ZERO);
    }
    return newInstance(Arrays.multiply(this.ints, other.ints, getMaxWidth()));
  }

  /**
   * {@code (this * mul) % mod}, throws if {@code mod.isZero()}.
   */
  public T mulmod(T mul, T mod) {
    if (mod.isZero()) {
      throw new ArithmeticException("div/mod by zero");
    }
    return newInstance(Arrays.mulmod(this.ints, mul.ints, mod.ints));
  }

  /**
   * {@code this ** exp}, throws if {@code exp < 0}.
   */
  public T pow(int exp) {
    if (exp < 0) {
      throw new ArithmeticException("Negative exponent");
    }
    if (exp == 0) {
      // x^0 = 1
      return newInstance(Arrays.ONE);
    }
    if (isZero()) {
      // 0^exp = 0
      return self();
    }
    if (exp == 1) {
      return self();
    }
    return newInstance(Arrays.pow(this.ints, getLowestSetBit(), exp, getMaxWidth()));
  }

  /**
   * {@code this / other}, throws if {@code other.isZero()}.
   */
  public T divide(T other) {
    if (other.isZero()) {
      throw new ArithmeticException("div/mod by zero");
    }
    if (isZero()) {
      return newInstance(Arrays.ZERO);
    }
    int cmp = compareTo(other);
    if (cmp < 0) {
      return newInstance(Arrays.ZERO);
    }
    if (cmp == 0) {
      // equals => quotient is 1
      return newInstance(Arrays.ONE);
    }
    return newInstance(Arrays.divide(this.ints, other.ints));
  }

  /**
   * {@code this % other}, throws if {@code other.isZero()}.
   */
  public T mod(T other) {
    if (other.isZero()) {
      throw new ArithmeticException("div/mod by zero");
    }
    if (isZero()) {
      return newInstance(Arrays.ZERO);
    }
    int cmp = compareTo(other);
    if (cmp < 0) {
      return self();
    }
    if (cmp == 0) {
      return newInstance(Arrays.ZERO);
    }
    return newInstance(Arrays.mod(this.ints, other.ints));
  }

  /**
   * {@code (this / other, this % other)} => an array of two elements: [quotient, remainder].
   */
  @SuppressWarnings("unchecked")
  public T[] divmod(T other) {
    if (other.isZero()) {
      throw new ArithmeticException("div/mod by zero");
    }
    if (isZero()) {
      return (T[]) new uintType<?>[] {
          newInstance(Arrays.ZERO),
          newInstance(Arrays.ZERO)
      };
    }
    int cmp = compareTo(other);
    if (cmp < 0) {
      return (T[]) new uintType<?>[] {
          newInstance(Arrays.ZERO),
          self()
      };
    }
    if (cmp == 0) {
      return (T[]) new uintType<?>[] {
          newInstance(Arrays.ONE),
          newInstance(Arrays.ZERO)
      };
    }
    int[][] qr = Arrays.divmod(this.ints, other.ints);
    return (T[]) new uintType<?>[] {
        newInstance(qr[0]),   // quotient
        newInstance(qr[1])    // remainder
    };
  }


  /* ====================================================== */
  /*               Common Utility Methods                   */
  /* ====================================================== */

  /** Checks if this value is 0. */
  public final boolean isZero() {
    return (this.ints.length == 0);
  }

  /**
   * Returns the index of the lowest set bit, or -1 if none.
   * Bits are counted from right (LSB) to left (MSB), starting at 0.
   */
  public final int getLowestSetBit() {
    int start = this.ints.length - 1;
    for (int i = start; i >= 0; i--) {
      if (this.ints[i] != 0) {
        return (start - i) * 32
            + Integer.numberOfTrailingZeros(this.ints[i]);
      }
    }
    return -1;
  }

  /** Returns the number of bits needed to represent this value in binary. */
  public final int bitLength() {
    return Arrays.bitLength(this.ints);
  }

  /**
   * Helper to cast {@code this} to T.
   */
  @SuppressWarnings("unchecked")
  private T self() {
    return (T) this;
  }


  /* ====================================================== */
  /*                   compare / equals                     */
  /* ====================================================== */

  @Override
  public final int compareTo(T other) {
    return Arrays.compare(this.ints, other.ints);
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other == null) {
      return false;
    }
    // compare with another uintType
    if (other instanceof uintType) {
      uintType<?> o = (uintType<?>) other;
      return (Arrays.compare(this.ints, o.ints) == 0);
    }
    // compare with BigInteger
    if (other instanceof BigInteger) {
      return Arrays.compare(this.ints, (BigInteger) other, getMaxWidth()) == 0;
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 0;
    for (int val : this.ints) {
      // incorporate each 32-bit value
      h = 31 * h + (val & (int) LONG);
    }
    return h;
  }


  /* ====================================================== */
  /*                Number interface methods                */
  /* ====================================================== */

  @Override
  public final int intValue() {
    return (this.ints.length == 0)
        ? 0
        : this.ints[this.ints.length - 1];
  }

  @Override
  public final long longValue() {
    int len = this.ints.length;
    if (len == 0) {
      return 0L;
    }
    long low = this.ints[len - 1] & LONG;
    if (len == 1) {
      return low;
    }
    long high = (this.ints[len - 2] & LONG);
    return (high << 32) | low;
  }

  @Override
  public final float floatValue() {
    // Simple approach: parse from string
    return Float.parseFloat(this.toString());
  }

  @Override
  public final double doubleValue() {
    // Simple approach: parse from string
    return Double.parseDouble(this.toString());
  }

  public final int intValueExact() {
    if (this.ints.length <= 1 && bitLength() < 32) {
      return intValue();
    }
    throw new ArithmeticException("Out of int range");
  }

  public final long longValueExact() {
    if (this.ints.length <= 2 && bitLength() < 64) {
      return longValue();
    }
    throw new ArithmeticException("Out of long range");
  }

  public final short shortValueExact() {
    int v = intValueExact();
    if (v >= Short.MIN_VALUE && v <= Short.MAX_VALUE) {
      return (short) v;
    }
    throw new ArithmeticException("Out of short range");
  }

  public final byte byteValueExact() {
    int v = intValueExact();
    if (v >= Byte.MIN_VALUE && v <= Byte.MAX_VALUE) {
      return (byte) v;
    }
    throw new ArithmeticException("Out of byte range");
  }


  /* ====================================================== */
  /*                    Conversions                         */
  /* ====================================================== */

  /**
   * Converts to a {@link BigInteger} (always non-negative).
   */
  public final BigInteger toBigInteger() {
    BigInteger out = BigInteger.ZERO;
    for (int i = 0; i < this.ints.length; i++) {
      out = out.shiftLeft(32).or(BigInteger.valueOf(this.ints[i] & LONG));
    }
    return out;
  }

  /**
   * Converts to a big-endian byte array.
   */
  public final byte[] toByteArray() {
    final int bits = bitLength();
    final int byteCount = (int) Math.ceil(bits / 8.0);
    final byte[] out = new byte[byteCount];

    int intIndex = this.ints.length - 1;
    int current = 0;

    for (int outIdx = byteCount - 1, copied = 0; outIdx >= 0; outIdx--, copied++) {
      if (copied % 4 == 0) {
        current = (intIndex >= 0) ? this.ints[intIndex--] : 0;
      }
      out[outIdx] = (byte) (current & 0xFF);
      current >>>= 8;
    }
    return out;
  }

  /**
   * Re-initializes this object from a big-endian byte array,
   * using the same approach as the 2-arg constructor.
   * Truncates if needed.
   */
  public final void fromByteArray(byte[] bytes) {
    // Convert with respect to the maxValue.ints
    int[] array = Arrays.from(bytes, getMaxValue().ints);
    // Then strip if it exceeds getMaxWidth()
    array = Arrays.stripLeadingZeroes(
        array, Math.max(0, array.length - getMaxWidth())
    );
    this.ints = array;
  }

  /**
   * Returns a copy of the underlying {@code int[]} array in big-endian format.
   */
  public final int[] toIntArray() {
    return java.util.Arrays.copyOf(this.ints, this.ints.length);
  }

  @Override
  public final String toString() {
    return toString(DEFAULT_RADIX);
  }

  /**
   * Returns a string representation in the specified radix.
   * If {@code radix} is out of range, defaults to base 10.
   */
  public final String toString(int radix) {
    if (isZero()) {
      return "0";
    }
    radix = fixRadix(radix);

    // For very small arrays, use built-in methods
    if (this.ints.length == 1) {
      return Integer.toUnsignedString(this.ints[0], radix);
    }
    if (this.ints.length == 2) {
      long val = ((long) (this.ints[0] & LONG) << 32)
               |  ((long) (this.ints[1] & LONG));
      return Long.toUnsignedString(val, radix);
    }
    // Otherwise use StringUtil
    return StringUtil.toString(this.ints, radix);
  }


  /* ====================================================== */
  /*                Storable Interface                      */
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
      // No valid slot, cannot save
      return false;
    }
    Storage storage = Storage.getStorage();
    storage.SetStorageFixedValue(this.slot, toByteArray());
    return true;
  }

  @Override
  public boolean load() {
    if (this.slot == Storable.NO_SLOT) {
      // No valid slot, cannot load
      return false;
    }
    Storage storage = Storage.getStorage();
    byte[] bytes = storage.GetStorageFixedValue(this.slot);
    if (bytes == null) {
      // Nothing stored => set to zero
      this.ints = new int[0];
      return false;
    }
    // Same logic as fromByteArray:
    int[] array = Arrays.from(bytes, getMaxValue().ints);
    array = Arrays.stripLeadingZeroes(
        array, Math.max(0, array.length - getMaxWidth())
    );
    this.ints = array;
    return true;
  }
}
