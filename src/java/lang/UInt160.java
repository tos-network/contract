package java.lang;

import java.lang.types.Arrays;
import java.math.BigInteger;

/**
 * Represents unsigned values less than {@code 2**160}.
 *
 * As indicated by the type signatures, arithmetic operations are not applicable
 * to types of other widths in this package.  Copy constructors can be used to
 * explicitly promote or truncate values for the purposes of interoperability.
 */
public final class uint160 extends uintType<uint160> {
  static final int MAX_WIDTH = 5;

  /**
   * Maximum representable value.
   */
  public static uint160 MAX_VALUE = new uint160(Arrays.maxValue(MAX_WIDTH));

  public static uint160 ZERO = new uint160(Arrays.ZERO);
  public static uint160 ONE  = new uint160(Arrays.ONE);
  public static uint160 TWO  = new uint160(Arrays.TWO);

  /**
   * Construct from a big-endian {@code int} array.
   *
   * If {@code ints} exceeds {@link MAX_VALUE}, only the maximum prefix
   * will be considered.  Leaves {@code ints} untouched.
   */
  public uint160(final int[] ints) {
    super(ints, MAX_WIDTH);
  }

  /**
   * Construct from a big-endian {@code byte} array.
   *
   * If {@code bytes} exceeds {@link MAX_VALUE}, only the maximum prefix
   * will be considered.  Leaves {@code bytes} untouched.
   */
  public uint160(final byte[] bytes) {
    super(bytes, MAX_VALUE);
  }

  /**
   * Construct from a {@link uint128}.
   */
  public uint160(final uint128 other) {
    super(other.ints, MAX_WIDTH);
  }

  /**
   * Construct from a base ten string.
   *
   * Excessively wide numbers will be truncated.
   *
   * @throws NumberFormatException Negative, invalid or zero-length number.
   */
  public uint160(final String s) {
    this(s, 10);
  }

  /**
   * Construct from a string in the given radix.
   *
   * Excessively wide numbers will be truncated.
   *
   * @throws NumberFormatException Negative, invalid or zero-length number.
   */
  public uint160(final String s, final int radix) {
    super(s, radix, MAX_WIDTH);
  }

  /**
   * Construct from a {@link BigInteger}.
   *
   * If {@code b} exceeds {@link MAX_VALUE}, it's truncated.
   */
  public uint160(final BigInteger b) { super(b, MAX_WIDTH); }

  /**
   * Construct from a {@code long}, when considered unsigned.
   *
   * For low values of {@code v}, an array cache may be used.
   */
  public uint160(final long v) { super(v); }

  public uint160 not() {
    return new uint160(Arrays.not(ints, MAX_VALUE.ints));
  }

  public uint160 and(final uint160 other) {
    return new uint160(Arrays.and(ints, other.ints));
  }

  public uint160 or(final uint160 other) {
    return new uint160(Arrays.or(ints, other.ints));
  }

  public uint160 xor(final uint160 other) {
    return new uint160(Arrays.xor(ints, other.ints));
  }

  public uint160 setBit(final int bit) {
    if(bit < 0)
      throw new ArithmeticException("Negative bit address");
    return ((MAX_WIDTH <= bit >>> 5) ? this :
            new uint160(Arrays.setBit(ints, bit)));
  }

  public uint160 clearBit(final int bit) {
    if(bit < 0)
      throw new ArithmeticException("Negative bit address");
    return ((ints.length <= bit >>> 5) ? this :
            new uint160(Arrays.clearBit(ints, bit)));
  }

  public uint160 flipBit(final int bit) {
     if(bit < 0)
       throw new ArithmeticException("Negative bit address");
     return ((MAX_WIDTH <= bit >>> 5) ? this :
             new uint160(Arrays.flipBit(ints, bit)));
  }

  public uint160 shiftLeft(final int places) {
    return new uint160(
      0 < places ?
      Arrays.lshift(ints,  places, MAX_WIDTH) :
      Arrays.rshift(ints, -places, MAX_WIDTH));
  }

  public uint160 shiftRight(final int places) {
    return new uint160(
      0 < places ?
      Arrays.rshift(ints,  places, MAX_WIDTH) :
      Arrays.lshift(ints, -places, MAX_WIDTH));
  }

  public uint160 inc() {
    return new uint160(Arrays.inc(ints, MAX_WIDTH));
  }

  public uint160 dec() {
    return isZero() ? MAX_VALUE : new uint160(Arrays.dec(ints));
  }

  public uint160 add(final uint160 other) {
    return (isZero() ? other :
            (other.isZero() ? this :
             new uint160(Arrays.add(ints, other.ints, MAX_WIDTH))));
  }

  public uint160 addmod(final uint160 add, final uint160 mod) {
    if(mod.isZero())
      throw new ArithmeticException("div/mod by zero");
    if(isZero() && add.isZero())
      return ZERO;
    return new uint160(Arrays.addmod(ints, add.ints, mod.ints));
  }

  public uint160 subtract(final uint160 other) {
    if(other.isZero())
      return this;
    final int cmp = compareTo(other);
    return (cmp == 0 ? ZERO :
            new uint160(
              cmp < 0 ?
              Arrays.subgt(ints, other.ints, MAX_VALUE.ints) :
              Arrays.sub  (ints, other.ints)));
  }

  public uint160 multiply(final uint160 other) {
    if(ints.length == 0 || other.ints.length == 0)
      return ZERO;
    return new uint160(Arrays.multiply(ints, other.ints, MAX_WIDTH));
  }

  public uint160 mulmod(final uint160 mul, final uint160 mod) {
    if(mod.isZero())
      throw new ArithmeticException("div/mod by zero");
    return new uint160(Arrays.mulmod(ints, mul.ints, mod.ints));
  }

  public uint160 pow(final int exp) {
    if(exp < 0)
      throw new ArithmeticException("Negative exponent");
    if(exp == 0)
      return ONE;
    if(isZero())
      return this;
    return (exp == 1 ? this :
            new uint160(Arrays.pow(ints, getLowestSetBit(), exp, MAX_WIDTH)));
  }

  public uint160 divide(final uint160 other) {
    if(other.isZero())
      throw new ArithmeticException("div/mod by zero");
    if(isZero())
      return ZERO;
    final int cmp = compareTo(other);
    return (cmp  <  0 ? ZERO :
            (cmp == 0 ? ONE  :
             new uint160(Arrays.divide(ints, other.ints))));
  }

  public uint160 mod(final uint160 other) {
    if(other.isZero())
      throw new ArithmeticException("div/mod by zero");
    if(isZero())
      return ZERO;
    final int cmp = compareTo(other);
    return (cmp  <  0 ? this :
            (cmp == 0 ? ZERO :
             new uint160(Arrays.mod(ints, other.ints))));
  }

  public uint160[] divmod(final uint160 other) {
    if(other.isZero())
      throw new ArithmeticException("div/mod by zero");
    if(isZero())
      return new uint160[]{ZERO, ZERO};
    final int cmp = compareTo(other);
    if(cmp < 0)
      return new uint160[]{ZERO, this};
    if(cmp == 0)
      return new uint160[]{ONE, ZERO};

    final int[][] qr = Arrays.divmod(ints, other.ints);
    return new uint160[]{new uint160(qr[0]), new uint160(qr[1])};
  }

  public boolean equals(final Object other) {
    if(other instanceof BigInteger)
      return Arrays.compare(ints, (BigInteger)other, MAX_WIDTH) == 0;
    return super.equals(other);
  }

  @Override
  public uintType<uint160> getMaxValue() {
    return MAX_VALUE;
  }
}