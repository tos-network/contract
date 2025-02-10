package java.lang;

import java.lang.types.Arrays;
import java.math.BigInteger;

/**
 * Represents unsigned values less than {@code 2**256}.
 *
 * As indicated by the type signatures, arithmetic operations are not applicable
 * to types of other widths in this package.  Copy constructors can be used to
 * explicitly promote or truncate values for the purposes of interoperability.
 */
public final class uint256 extends uintType<uint256> {
  static final int MAX_WIDTH = 8;

  /**
   * Maximum representable value.
   */
  public static uint256 MAX_VALUE = new uint256(Arrays.maxValue(MAX_WIDTH));

  public static uint256 ZERO = new uint256(Arrays.ZERO);
  public static uint256 ONE  = new uint256(Arrays.ONE);
  public static uint256 TWO  = new uint256(Arrays.TWO);

  /**
   * Construct from a big-endian {@code int} array.
   *
   * If {@code ints} exceeds {@link MAX_VALUE}, only the maximum prefix
   * will be considered.  Leaves {@code ints} untouched.
   */
  public uint256(final int[] ints) {
    super(ints, MAX_WIDTH);
  }

  /**
   * Construct from a big-endian {@code byte} array.
   *
   * If {@code bytes} exceeds {@link MAX_VALUE}, only the maximum prefix
   * will be considered.  Leaves {@code bytes} untouched.
   */
  public uint256(final byte[] bytes) {
    super(bytes, MAX_VALUE);
  }

  /**
   * Construct from a {@link uint128}.
   */
  public uint256(final uint128 other) {
    super(other.ints, MAX_WIDTH);
  }

  /**
   * Construct from a base ten string.
   *
   * Excessively wide numbers will be truncated.
   *
   * @throws NumberFormatException Negative, invalid or zero-length number.
   */
  public uint256(final String s) {
    this(s, 10);
  }

  /**
   * Construct from a string in the given radix.
   *
   * Excessively wide numbers will be truncated.
   *
   * @throws NumberFormatException Negative, invalid or zero-length number.
   */
  public uint256(final String s, final int radix) {
    super(s, radix, MAX_WIDTH);
  }

  /**
   * Construct from a {@link BigInteger}.
   *
   * If {@code b} exceeds {@link MAX_VALUE}, it's truncated.
   */
  public uint256(final BigInteger b) { super(b, MAX_WIDTH); }

  /**
   * Construct from a {@code long}, when considered unsigned.
   *
   * For low values of {@code v}, an array cache may be used.
   */
  public uint256(final long v) { super(v); }

  public uint256 not() {
    return new uint256(Arrays.not(ints, MAX_VALUE.ints));
  }

  public uint256 and(final uint256 other) {
    return new uint256(Arrays.and(ints, other.ints));
  }

  public uint256 or(final uint256 other) {
    return new uint256(Arrays.or(ints, other.ints));
  }

  public uint256 xor(final uint256 other) {
    return new uint256(Arrays.xor(ints, other.ints));
  }

  public uint256 setBit(final int bit) {
    if(bit < 0)
      throw new ArithmeticException("Negative bit address");
    return ((MAX_WIDTH <= bit >>> 5) ? this :
            new uint256(Arrays.setBit(ints, bit)));
  }

  public uint256 clearBit(final int bit) {
    if(bit < 0)
      throw new ArithmeticException("Negative bit address");
    return ((ints.length <= bit >>> 5) ? this :
            new uint256(Arrays.clearBit(ints, bit)));
  }

  public uint256 flipBit(final int bit) {
     if(bit < 0)
       throw new ArithmeticException("Negative bit address");
     return ((MAX_WIDTH <= bit >>> 5) ? this :
             new uint256(Arrays.flipBit(ints, bit)));
  }

  public uint256 shiftLeft(final int places) {
    return new uint256(
      0 < places ?
      Arrays.lshift(ints,  places, MAX_WIDTH) :
      Arrays.rshift(ints, -places, MAX_WIDTH));
  }

  public uint256 shiftRight(final int places) {
    return new uint256(
      0 < places ?
      Arrays.rshift(ints,  places, MAX_WIDTH) :
      Arrays.lshift(ints, -places, MAX_WIDTH));
  }

  public uint256 inc() {
    return new uint256(Arrays.inc(ints, MAX_WIDTH));
  }

  public uint256 dec() {
    return isZero() ? MAX_VALUE : new uint256(Arrays.dec(ints));
  }

  public uint256 add(final uint256 other) {
    return (isZero() ? other :
            (other.isZero() ? this :
             new uint256(Arrays.add(ints, other.ints, MAX_WIDTH))));
  }

  public uint256 addmod(final uint256 add, final uint256 mod) {
    if(mod.isZero())
      throw new ArithmeticException("div/mod by zero");
    if(isZero() && add.isZero())
      return ZERO;
    return new uint256(Arrays.addmod(ints, add.ints, mod.ints));
  }

  public uint256 subtract(final uint256 other) {
    if(other.isZero())
      return this;
    final int cmp = compareTo(other);
    return (cmp == 0 ? ZERO :
            new uint256(
              cmp < 0 ?
              Arrays.subgt(ints, other.ints, MAX_VALUE.ints) :
              Arrays.sub  (ints, other.ints)));
  }

  public uint256 multiply(final uint256 other) {
    if(ints.length == 0 || other.ints.length == 0)
      return ZERO;
    return new uint256(Arrays.multiply(ints, other.ints, MAX_WIDTH));
  }

  public uint256 mulmod(final uint256 mul, final uint256 mod) {
    if(mod.isZero())
      throw new ArithmeticException("div/mod by zero");
    return new uint256(Arrays.mulmod(ints, mul.ints, mod.ints));
  }

  public uint256 pow(final int exp) {
    if(exp < 0)
      throw new ArithmeticException("Negative exponent");
    if(exp == 0)
      return ONE;
    if(isZero())
      return this;
    return (exp == 1 ? this :
            new uint256(Arrays.pow(ints, getLowestSetBit(), exp, MAX_WIDTH)));
  }

  public uint256 divide(final uint256 other) {
    if(other.isZero())
      throw new ArithmeticException("div/mod by zero");
    if(isZero())
      return ZERO;
    final int cmp = compareTo(other);
    return (cmp  <  0 ? ZERO :
            (cmp == 0 ? ONE  :
             new uint256(Arrays.divide(ints, other.ints))));
  }

  public uint256 mod(final uint256 other) {
    if(other.isZero())
      throw new ArithmeticException("div/mod by zero");
    if(isZero())
      return ZERO;
    final int cmp = compareTo(other);
    return (cmp  <  0 ? this :
            (cmp == 0 ? ZERO :
             new uint256(Arrays.mod(ints, other.ints))));
  }

  public uint256[] divmod(final uint256 other) {
    if(other.isZero())
      throw new ArithmeticException("div/mod by zero");
    if(isZero())
      return new uint256[]{ZERO, ZERO};
    final int cmp = compareTo(other);
    if(cmp < 0)
      return new uint256[]{ZERO, this};
    if(cmp == 0)
      return new uint256[]{ONE, ZERO};

    final int[][] qr = Arrays.divmod(ints, other.ints);
    return new uint256[]{new uint256(qr[0]), new uint256(qr[1])};
  }

  public boolean equals(final Object other) {
    if(other instanceof BigInteger)
      return Arrays.compare(ints, (BigInteger)other, MAX_WIDTH) == 0;
    return super.equals(other);
  }

  @Override
  public uintType<uint256> getMaxValue() {
    return MAX_VALUE;
  }
}
