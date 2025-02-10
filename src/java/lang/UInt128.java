package java.lang;

import java.lang.types.Arrays;
import java.math.BigInteger;
/**
 * Represents unsigned values less than {@code 2**128}.
 *
 * As indicated by the type signatures, arithmetic operations are not applicable
 * to types of other widths in this package.  Copy constructors can be used to
 * explicitly promote or truncate values for the purposes of interoperability.
 */
public final class uint128 extends uintType<uint128> {
  static final int MAX_WIDTH = 4;

  /**
   * Maximum representable value.
   */
  public static uint128 MAX_VALUE = new uint128(Arrays.maxValue(MAX_WIDTH));

  public static uint128 ZERO = new uint128(Arrays.ZERO);
  public static uint128 ONE  = new uint128(Arrays.ONE);
  public static uint128 TWO  = new uint128(Arrays.TWO);

  /**
   * Construct from a big-endian {@code int} array.
   *
   * If {@code ints} exceeds {@link MAX_VALUE}, only the maximum prefix
   * will be considered.  Leaves {@code ints} untouched.
   */
  public uint128(final int[] ints) {
    super(ints, MAX_WIDTH);
  }

  /**
   * Construct from a big-endian {@code byte} array.
   *
   * If {@code bytes} exceeds {@link MAX_VALUE}, only the maximum prefix
   * will be considered.  Leaves {@code bytes} untouched.
   */
  public uint128(final byte[] bytes) {
    super(bytes, MAX_VALUE);
  }

  /**
   * Construct from a {@link uint256}.
   *
   * Excessively wide numbers will be truncated.
   */
  public uint128(final uint256 other) {
    super(other.ints, MAX_WIDTH);
  }

  /**
   * Construct from a base ten string.
   *
   * Excessively wide numbers will be truncated.
   *
   * @throws NumberFormatException Negative, invalid or zero-length number.
   */
  public uint128(final String s) {
    this(s, 10);
  }

  /**
   * Construct from a string in the given radix.
   *
   * Excessively wide numbers will be truncated.
   *
   * @throws NumberFormatException Negative, invalid or zero-length number.
   */
  public uint128(final String s, final int radix) {
    super(s, radix, MAX_WIDTH);
  }

  /**
   * Construct from a {@link BigInteger}.
   *
   * If {@code b} exceeds {@link MAX_VALUE}, it's truncated.
   */
  public uint128(final BigInteger b) { super(b, MAX_WIDTH); }

  /**
   * Construct from a {@code long}, when considered unsigned.
   *
   * For low values of {@code v}, an array cache may be used.
   */
    public uint128(final long v) { super(v); }

  public uint128 not() {
    return new uint128(Arrays.not(ints, MAX_VALUE.ints));
  }

  public uint128 and(final uint128 other) {
    return new uint128(Arrays.and(ints, other.ints));
  }

  public uint128 or(final uint128 other) {
    return new uint128(Arrays.or(ints, other.ints));
  }

  public uint128 xor(final uint128 other) {
    return new uint128(Arrays.xor(ints, other.ints));
  }

  public uint128 setBit(final int bit) {
    if(bit < 0)
      throw new ArithmeticException("Negative bit address");
    return ((MAX_WIDTH <= bit >>> 5) ? this :
            new uint128(Arrays.setBit(ints, bit)));
  }

  public uint128 clearBit(final int bit) {
    if(bit < 0)
      throw new ArithmeticException("Negative bit address");
    return ((ints.length <= bit >>> 5) ? this :
            new uint128(Arrays.clearBit(ints, bit)));
  }

  public uint128 flipBit(final int bit) {
     if(bit < 0)
       throw new ArithmeticException("Negative bit address");
     return ((MAX_WIDTH <= bit >>> 5) ? this :
             new uint128(Arrays.flipBit(ints, bit)));
  }

  public uint128 shiftLeft(final int places) {
    return new uint128(
      0 < places ?
      Arrays.lshift(ints,  places, MAX_WIDTH) :
      Arrays.rshift(ints, -places, MAX_WIDTH));
  }

  public uint128 shiftRight(final int places) {
    return new uint128(
      0 < places ?
      Arrays.rshift(ints,  places, MAX_WIDTH) :
      Arrays.lshift(ints, -places, MAX_WIDTH));
  }

  public uint128 inc() {
    return new uint128(Arrays.inc(ints, MAX_WIDTH));
  }

  public uint128 dec() {
    return isZero() ? MAX_VALUE : new uint128(Arrays.dec(ints));
  }

  public uint128 add(final uint128 other) {
    return (isZero() ? other :
            (other.isZero() ? this :
             new uint128(Arrays.add(ints, other.ints, MAX_WIDTH))));
  }

  public uint128 addmod(final uint128 add, final uint128 mod) {
    if(mod.isZero())
      throw new ArithmeticException("div/mod by zero");
    return new uint128(Arrays.addmod(ints, add.ints, mod.ints));
  }

  public uint128 subtract(final uint128 other) {
    if(other.isZero())
      return this;
    final int cmp = compareTo(other);
    return (cmp == 0 ? ZERO :
            new uint128(
              cmp < 0 ?
              Arrays.subgt(ints, other.ints, MAX_VALUE.ints) :
              Arrays.sub  (ints, other.ints)));
  }

  public uint128 multiply(final uint128 other) {
    if(ints.length == 0 || other.ints.length == 0)
      return ZERO;

    return new uint128(Arrays.multiply(ints, other.ints, MAX_WIDTH));
  }

  public uint128 mulmod(final uint128 mul, final uint128 mod) {
    if(mod.isZero())
      throw new ArithmeticException("div/mod by zero");
    return new uint128(Arrays.mulmod(ints, mul.ints, mod.ints));
  }

  public uint128 pow(final int exp) {
    if(exp < 0)
      throw new ArithmeticException("Negative exponent");
    if(exp == 0)
      return ONE;
    if(isZero())
      return this;
    return (exp == 1 ? this :
            new uint128(Arrays.pow(ints, getLowestSetBit(), exp, MAX_WIDTH)));
  }

  public uint128 divide(final uint128 other) {
    if(other.isZero())
      throw new ArithmeticException("div/mod by zero");
    if(isZero())
      return ZERO;
    final int cmp = compareTo(other);
    return (cmp  <  0 ? ZERO :
            (cmp == 0 ? ONE  :
             new uint128(Arrays.divide(ints, other.ints))));
  }

  public uint128 mod(final uint128 other) {
    if(other.isZero())
      throw new ArithmeticException("div/mod by zero");
    if(isZero())
      return ZERO;
    final int cmp = compareTo(other);
    return (cmp  <  0 ? this :
            (cmp == 0 ? ZERO :
             new uint128(Arrays.mod(ints, other.ints))));
  }

  public uint128[] divmod(final uint128 other) {
    if(other.isZero())
      throw new ArithmeticException("div/mod by zero");
    if(isZero())
      return new uint128[]{ZERO, ZERO};
    final int cmp = compareTo(other);
    if(cmp < 0)
      return new uint128[]{ZERO, this};
    if(cmp == 0)
      return new uint128[]{ONE, ZERO};

    final int[][] qr = Arrays.divmod(ints, other.ints);
    return new uint128[]{new uint128(qr[0]), new uint128(qr[1])};
  }

  public boolean equals(final Object other) {
    if(other instanceof BigInteger)
      return Arrays.compare(ints, (BigInteger)other, MAX_WIDTH) == 0;
    return super.equals(other);
  }

  @Override
  public uintType<uint128> getMaxValue() {
    return MAX_VALUE;
  }
}
