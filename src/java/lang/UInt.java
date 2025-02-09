package java.lang;

import java.math.BigInteger;
import java.lang.bytes.Arrays;

/**
 * Represents unsigned values less than {@code 2**256}.
 *
 * As indicated by the type signatures, arithmetic operations are not applicable
 * to types of other widths in this package.  Copy constructors can be used to
 * explicitly promote or truncate values for the purposes of interoperability.
 */
public final class UInt extends UIntType<UInt> {
  static final int MAX_WIDTH = 8;

  /**
   * Maximum representable value.
   */
  public static UInt MAX_VALUE = new UInt(Arrays.maxValue(MAX_WIDTH));

  public static UInt ZERO = new UInt(Arrays.ZERO);
  public static UInt ONE  = new UInt(Arrays.ONE);
  public static UInt TWO  = new UInt(Arrays.TWO);

  /**
   * Construct from a big-endian {@code int} array.
   *
   * If {@code ints} exceeds {@link MAX_VALUE}, only the maximum prefix
   * will be considered.  Leaves {@code ints} untouched.
   */
  public UInt(final int[] ints) {
    super(ints, MAX_WIDTH);
  }

  /**
   * Construct from a big-endian {@code byte} array.
   *
   * If {@code bytes} exceeds {@link MAX_VALUE}, only the maximum prefix
   * will be considered.  Leaves {@code bytes} untouched.
   */
  public UInt(final byte[] bytes) {
    super(bytes, MAX_VALUE);
  }

  /**
   * Construct from a {@link UInt128}.
   */
  public UInt(final UInt128 other) {
    super(other.ints, MAX_WIDTH);
  }

  /**
   * Construct from a base ten string.
   *
   * Excessively wide numbers will be truncated.
   *
   * @throws NumberFormatException Negative, invalid or zero-length number.
   */
  public UInt(final String s) {
    this(s, 10);
  }

  /**
   * Construct from a string in the given radix.
   *
   * Excessively wide numbers will be truncated.
   *
   * @throws NumberFormatException Negative, invalid or zero-length number.
   */
  public UInt(final String s, final int radix) {
    super(s, radix, MAX_WIDTH);
  }

  /**
   * Construct from a {@link BigInteger}.
   *
   * If {@code b} exceeds {@link MAX_VALUE}, it's truncated.
   */
  public UInt(final BigInteger b) { super(b, MAX_WIDTH); }

  /**
   * Construct from a {@code long}, when considered unsigned.
   *
   * For low values of {@code v}, an array cache may be used.
   */
  public UInt(final long v) { super(v); }

  public UInt not() {
    return new UInt(Arrays.not(ints, MAX_VALUE.ints));
  }

  public UInt and(final UInt other) {
    return new UInt(Arrays.and(ints, other.ints));
  }

  public UInt or(final UInt other) {
    return new UInt(Arrays.or(ints, other.ints));
  }

  public UInt xor(final UInt other) {
    return new UInt(Arrays.xor(ints, other.ints));
  }

  public UInt setBit(final int bit) {
    if(bit < 0)
      throw new ArithmeticException("Negative bit address");
    return ((MAX_WIDTH <= bit >>> 5) ? this :
            new UInt(Arrays.setBit(ints, bit)));
  }

  public UInt clearBit(final int bit) {
    if(bit < 0)
      throw new ArithmeticException("Negative bit address");
    return ((ints.length <= bit >>> 5) ? this :
            new UInt(Arrays.clearBit(ints, bit)));
  }

  public UInt flipBit(final int bit) {
     if(bit < 0)
       throw new ArithmeticException("Negative bit address");
     return ((MAX_WIDTH <= bit >>> 5) ? this :
             new UInt(Arrays.flipBit(ints, bit)));
  }

  public UInt shiftLeft(final int places) {
    return new UInt(
      0 < places ?
      Arrays.lshift(ints,  places, MAX_WIDTH) :
      Arrays.rshift(ints, -places, MAX_WIDTH));
  }

  public UInt shiftRight(final int places) {
    return new UInt(
      0 < places ?
      Arrays.rshift(ints,  places, MAX_WIDTH) :
      Arrays.lshift(ints, -places, MAX_WIDTH));
  }

  public UInt inc() {
    return new UInt(Arrays.inc(ints, MAX_WIDTH));
  }

  public UInt dec() {
    return isZero() ? MAX_VALUE : new UInt(Arrays.dec(ints));
  }

  public UInt add(final UInt other) {
    return (isZero() ? other :
            (other.isZero() ? this :
             new UInt(Arrays.add(ints, other.ints, MAX_WIDTH))));
  }

  public UInt addmod(final UInt add, final UInt mod) {
    if(mod.isZero())
      throw new ArithmeticException("div/mod by zero");
    if(isZero() && add.isZero())
      return ZERO;
    return new UInt(Arrays.addmod(ints, add.ints, mod.ints));
  }

  public UInt subtract(final UInt other) {
    if(other.isZero())
      return this;
    final int cmp = compareTo(other);
    return (cmp == 0 ? ZERO :
            new UInt(
              cmp < 0 ?
              Arrays.subgt(ints, other.ints, MAX_VALUE.ints) :
              Arrays.sub  (ints, other.ints)));
  }

  public UInt multiply(final UInt other) {
    if(ints.length == 0 || other.ints.length == 0)
      return ZERO;
    return new UInt(Arrays.multiply(ints, other.ints, MAX_WIDTH));
  }

  public UInt mulmod(final UInt mul, final UInt mod) {
    if(mod.isZero())
      throw new ArithmeticException("div/mod by zero");
    return new UInt(Arrays.mulmod(ints, mul.ints, mod.ints));
  }

  public UInt pow(final int exp) {
    if(exp < 0)
      throw new ArithmeticException("Negative exponent");
    if(exp == 0)
      return ONE;
    if(isZero())
      return this;
    return (exp == 1 ? this :
            new UInt(Arrays.pow(ints, getLowestSetBit(), exp, MAX_WIDTH)));
  }

  public UInt divide(final UInt other) {
    if(other.isZero())
      throw new ArithmeticException("div/mod by zero");
    if(isZero())
      return ZERO;
    final int cmp = compareTo(other);
    return (cmp  <  0 ? ZERO :
            (cmp == 0 ? ONE  :
             new UInt(Arrays.divide(ints, other.ints))));
  }

  public UInt mod(final UInt other) {
    if(other.isZero())
      throw new ArithmeticException("div/mod by zero");
    if(isZero())
      return ZERO;
    final int cmp = compareTo(other);
    return (cmp  <  0 ? this :
            (cmp == 0 ? ZERO :
             new UInt(Arrays.mod(ints, other.ints))));
  }

  public UInt[] divmod(final UInt other) {
    if(other.isZero())
      throw new ArithmeticException("div/mod by zero");
    if(isZero())
      return new UInt[]{ZERO, ZERO};
    final int cmp = compareTo(other);
    if(cmp < 0)
      return new UInt[]{ZERO, this};
    if(cmp == 0)
      return new UInt[]{ONE, ZERO};

    final int[][] qr = Arrays.divmod(ints, other.ints);
    return new UInt[]{new UInt(qr[0]), new UInt(qr[1])};
  }

  public boolean equals(final Object other) {
    if(other instanceof BigInteger)
      return Arrays.compare(ints, (BigInteger)other, MAX_WIDTH) == 0;
    return super.equals(other);
  }

  @Override
  public UIntType<UInt> getMaxValue() {
    return MAX_VALUE;
  }
}
