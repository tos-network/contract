package java.lang;

import java.math.BigInteger;
import java.lang.bytes.Arrays;

/**
 * Represents unsigned values less than {@code 2**160}.
 *
 * As indicated by the type signatures, arithmetic operations are not applicable
 * to types of other widths in this package.  Copy constructors can be used to
 * explicitly promote or truncate values for the purposes of interoperability.
 */
public final class UInt160 extends UIntType<UInt160> {
  static final int MAX_WIDTH = 5;

  /**
   * Maximum representable value.
   */
  public static UInt160 MAX_VALUE = new UInt160(Arrays.maxValue(MAX_WIDTH));

  public static UInt160 ZERO = new UInt160(Arrays.ZERO);
  public static UInt160 ONE  = new UInt160(Arrays.ONE);
  public static UInt160 TWO  = new UInt160(Arrays.TWO);

  /**
   * Construct from a big-endian {@code int} array.
   *
   * If {@code ints} exceeds {@link MAX_VALUE}, only the maximum prefix
   * will be considered.  Leaves {@code ints} untouched.
   */
  public UInt160(final int[] ints) {
    super(ints, MAX_WIDTH);
  }

  /**
   * Construct from a big-endian {@code byte} array.
   *
   * If {@code bytes} exceeds {@link MAX_VALUE}, only the maximum prefix
   * will be considered.  Leaves {@code bytes} untouched.
   */
  public UInt160(final byte[] bytes) {
    super(bytes, MAX_VALUE);
  }

  /**
   * Construct from a {@link UInt128}.
   */
  public UInt160(final UInt128 other) {
    super(other.ints, MAX_WIDTH);
  }

  /**
   * Construct from a base ten string.
   *
   * Excessively wide numbers will be truncated.
   *
   * @throws NumberFormatException Negative, invalid or zero-length number.
   */
  public UInt160(final String s) {
    this(s, 10);
  }

  /**
   * Construct from a string in the given radix.
   *
   * Excessively wide numbers will be truncated.
   *
   * @throws NumberFormatException Negative, invalid or zero-length number.
   */
  public UInt160(final String s, final int radix) {
    super(s, radix, MAX_WIDTH);
  }

  /**
   * Construct from a {@link BigInteger}.
   *
   * If {@code b} exceeds {@link MAX_VALUE}, it's truncated.
   */
  public UInt160(final BigInteger b) { super(b, MAX_WIDTH); }

  /**
   * Construct from a {@code long}, when considered unsigned.
   *
   * For low values of {@code v}, an array cache may be used.
   */
  public UInt160(final long v) { super(v); }

  public UInt160 not() {
    return new UInt160(Arrays.not(ints, MAX_VALUE.ints));
  }

  public UInt160 and(final UInt160 other) {
    return new UInt160(Arrays.and(ints, other.ints));
  }

  public UInt160 or(final UInt160 other) {
    return new UInt160(Arrays.or(ints, other.ints));
  }

  public UInt160 xor(final UInt160 other) {
    return new UInt160(Arrays.xor(ints, other.ints));
  }

  public UInt160 setBit(final int bit) {
    if(bit < 0)
      throw new ArithmeticException("Negative bit address");
    return ((MAX_WIDTH <= bit >>> 5) ? this :
            new UInt160(Arrays.setBit(ints, bit)));
  }

  public UInt160 clearBit(final int bit) {
    if(bit < 0)
      throw new ArithmeticException("Negative bit address");
    return ((ints.length <= bit >>> 5) ? this :
            new UInt160(Arrays.clearBit(ints, bit)));
  }

  public UInt160 flipBit(final int bit) {
     if(bit < 0)
       throw new ArithmeticException("Negative bit address");
     return ((MAX_WIDTH <= bit >>> 5) ? this :
             new UInt160(Arrays.flipBit(ints, bit)));
  }

  public UInt160 shiftLeft(final int places) {
    return new UInt160(
      0 < places ?
      Arrays.lshift(ints,  places, MAX_WIDTH) :
      Arrays.rshift(ints, -places, MAX_WIDTH));
  }

  public UInt160 shiftRight(final int places) {
    return new UInt160(
      0 < places ?
      Arrays.rshift(ints,  places, MAX_WIDTH) :
      Arrays.lshift(ints, -places, MAX_WIDTH));
  }

  public UInt160 inc() {
    return new UInt160(Arrays.inc(ints, MAX_WIDTH));
  }

  public UInt160 dec() {
    return isZero() ? MAX_VALUE : new UInt160(Arrays.dec(ints));
  }

  public UInt160 add(final UInt160 other) {
    return (isZero() ? other :
            (other.isZero() ? this :
             new UInt160(Arrays.add(ints, other.ints, MAX_WIDTH))));
  }

  public UInt160 addmod(final UInt160 add, final UInt160 mod) {
    if(mod.isZero())
      throw new ArithmeticException("div/mod by zero");
    if(isZero() && add.isZero())
      return ZERO;
    return new UInt160(Arrays.addmod(ints, add.ints, mod.ints));
  }

  public UInt160 subtract(final UInt160 other) {
    if(other.isZero())
      return this;
    final int cmp = compareTo(other);
    return (cmp == 0 ? ZERO :
            new UInt160(
              cmp < 0 ?
              Arrays.subgt(ints, other.ints, MAX_VALUE.ints) :
              Arrays.sub  (ints, other.ints)));
  }

  public UInt160 multiply(final UInt160 other) {
    if(ints.length == 0 || other.ints.length == 0)
      return ZERO;
    return new UInt160(Arrays.multiply(ints, other.ints, MAX_WIDTH));
  }

  public UInt160 mulmod(final UInt160 mul, final UInt160 mod) {
    if(mod.isZero())
      throw new ArithmeticException("div/mod by zero");
    return new UInt160(Arrays.mulmod(ints, mul.ints, mod.ints));
  }

  public UInt160 pow(final int exp) {
    if(exp < 0)
      throw new ArithmeticException("Negative exponent");
    if(exp == 0)
      return ONE;
    if(isZero())
      return this;
    return (exp == 1 ? this :
            new UInt160(Arrays.pow(ints, getLowestSetBit(), exp, MAX_WIDTH)));
  }

  public UInt160 divide(final UInt160 other) {
    if(other.isZero())
      throw new ArithmeticException("div/mod by zero");
    if(isZero())
      return ZERO;
    final int cmp = compareTo(other);
    return (cmp  <  0 ? ZERO :
            (cmp == 0 ? ONE  :
             new UInt160(Arrays.divide(ints, other.ints))));
  }

  public UInt160 mod(final UInt160 other) {
    if(other.isZero())
      throw new ArithmeticException("div/mod by zero");
    if(isZero())
      return ZERO;
    final int cmp = compareTo(other);
    return (cmp  <  0 ? this :
            (cmp == 0 ? ZERO :
             new UInt160(Arrays.mod(ints, other.ints))));
  }

  public UInt160[] divmod(final UInt160 other) {
    if(other.isZero())
      throw new ArithmeticException("div/mod by zero");
    if(isZero())
      return new UInt160[]{ZERO, ZERO};
    final int cmp = compareTo(other);
    if(cmp < 0)
      return new UInt160[]{ZERO, this};
    if(cmp == 0)
      return new UInt160[]{ONE, ZERO};

    final int[][] qr = Arrays.divmod(ints, other.ints);
    return new UInt160[]{new UInt160(qr[0]), new UInt160(qr[1])};
  }

  public boolean equals(final Object other) {
    if(other instanceof BigInteger)
      return Arrays.compare(ints, (BigInteger)other, MAX_WIDTH) == 0;
    return super.equals(other);
  }
}