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
public final class uint extends uintType<uint> {
  static final int MAX_WIDTH = 8;

  /**
   * Maximum representable value.
   */
  public static uint MAX_VALUE = new uint(Arrays.maxValue(MAX_WIDTH));

  public static uint ZERO = new uint(Arrays.ZERO);
  public static uint ONE  = new uint(Arrays.ONE);
  public static uint TWO  = new uint(Arrays.TWO);

  /**
   * Construct from a big-endian {@code int} array.
   *
   * If {@code ints} exceeds {@link MAX_VALUE}, only the maximum prefix
   * will be considered.  Leaves {@code ints} untouched.
   */
  public uint(final int[] ints) {
    super(ints, MAX_WIDTH);
  }

  /**
   * Construct from a big-endian {@code byte} array.
   *
   * If {@code bytes} exceeds {@link MAX_VALUE}, only the maximum prefix
   * will be considered.  Leaves {@code bytes} untouched.
   */
  public uint(final byte[] bytes) {
    super(bytes, MAX_VALUE);
  }

  /**
   * Construct from a {@link uint128}.
   */
  public uint(final uint128 other) {
    super(other.ints, MAX_WIDTH);
  }

  /**
   * Construct from a base ten string.
   *
   * Excessively wide numbers will be truncated.
   *
   * @throws NumberFormatException Negative, invalid or zero-length number.
   */
  public uint(final String s) {
    this(s, 10);
  }

  /**
   * Construct from a string in the given radix.
   *
   * Excessively wide numbers will be truncated.
   *
   * @throws NumberFormatException Negative, invalid or zero-length number.
   */
  public uint(final String s, final int radix) {
    super(s, radix, MAX_WIDTH);
  }

  /**
   * Construct from a {@link BigInteger}.
   *
   * If {@code b} exceeds {@link MAX_VALUE}, it's truncated.
   */
  public uint(final BigInteger b) { super(b, MAX_WIDTH); }

  /**
   * Construct from a {@code long}, when considered unsigned.
   *
   * For low values of {@code v}, an array cache may be used.
   */
  public uint(final long v) { super(v); }

  public uint not() {
    return new uint(Arrays.not(ints, MAX_VALUE.ints));
  }

  public uint and(final uint other) {
    return new uint(Arrays.and(ints, other.ints));
  }

  public uint or(final uint other) {
    return new uint(Arrays.or(ints, other.ints));
  }

  public uint xor(final uint other) {
    return new uint(Arrays.xor(ints, other.ints));
  }

  public uint setBit(final int bit) {
    if(bit < 0)
      throw new ArithmeticException("Negative bit address");
    return ((MAX_WIDTH <= bit >>> 5) ? this :
            new uint(Arrays.setBit(ints, bit)));
  }

  public uint clearBit(final int bit) {
    if(bit < 0)
      throw new ArithmeticException("Negative bit address");
    return ((ints.length <= bit >>> 5) ? this :
            new uint(Arrays.clearBit(ints, bit)));
  }

  public uint flipBit(final int bit) {
     if(bit < 0)
       throw new ArithmeticException("Negative bit address");
     return ((MAX_WIDTH <= bit >>> 5) ? this :
             new uint(Arrays.flipBit(ints, bit)));
  }

  public uint shiftLeft(final int places) {
    return new uint(
      0 < places ?
      Arrays.lshift(ints,  places, MAX_WIDTH) :
      Arrays.rshift(ints, -places, MAX_WIDTH));
  }

  public uint shiftRight(final int places) {
    return new uint(
      0 < places ?
      Arrays.rshift(ints,  places, MAX_WIDTH) :
      Arrays.lshift(ints, -places, MAX_WIDTH));
  }

  public uint inc() {
    return new uint(Arrays.inc(ints, MAX_WIDTH));
  }

  public uint dec() {
    return isZero() ? MAX_VALUE : new uint(Arrays.dec(ints));
  }

  public uint add(final uint other) {
    return (isZero() ? other :
            (other.isZero() ? this :
             new uint(Arrays.add(ints, other.ints, MAX_WIDTH))));
  }

  public uint addmod(final uint add, final uint mod) {
    if(mod.isZero())
      throw new ArithmeticException("div/mod by zero");
    if(isZero() && add.isZero())
      return ZERO;
    return new uint(Arrays.addmod(ints, add.ints, mod.ints));
  }

  public uint subtract(final uint other) {
    if(other.isZero())
      return this;
    final int cmp = compareTo(other);
    return (cmp == 0 ? ZERO :
            new uint(
              cmp < 0 ?
              Arrays.subgt(ints, other.ints, MAX_VALUE.ints) :
              Arrays.sub  (ints, other.ints)));
  }

  public uint multiply(final uint other) {
    if(ints.length == 0 || other.ints.length == 0)
      return ZERO;
    return new uint(Arrays.multiply(ints, other.ints, MAX_WIDTH));
  }

  public uint mulmod(final uint mul, final uint mod) {
    if(mod.isZero())
      throw new ArithmeticException("div/mod by zero");
    return new uint(Arrays.mulmod(ints, mul.ints, mod.ints));
  }

  public uint pow(final int exp) {
    if(exp < 0)
      throw new ArithmeticException("Negative exponent");
    if(exp == 0)
      return ONE;
    if(isZero())
      return this;
    return (exp == 1 ? this :
            new uint(Arrays.pow(ints, getLowestSetBit(), exp, MAX_WIDTH)));
  }

  public uint divide(final uint other) {
    if(other.isZero())
      throw new ArithmeticException("div/mod by zero");
    if(isZero())
      return ZERO;
    final int cmp = compareTo(other);
    return (cmp  <  0 ? ZERO :
            (cmp == 0 ? ONE  :
             new uint(Arrays.divide(ints, other.ints))));
  }

  public uint mod(final uint other) {
    if(other.isZero())
      throw new ArithmeticException("div/mod by zero");
    if(isZero())
      return ZERO;
    final int cmp = compareTo(other);
    return (cmp  <  0 ? this :
            (cmp == 0 ? ZERO :
             new uint(Arrays.mod(ints, other.ints))));
  }

  public uint[] divmod(final uint other) {
    if(other.isZero())
      throw new ArithmeticException("div/mod by zero");
    if(isZero())
      return new uint[]{ZERO, ZERO};
    final int cmp = compareTo(other);
    if(cmp < 0)
      return new uint[]{ZERO, this};
    if(cmp == 0)
      return new uint[]{ONE, ZERO};

    final int[][] qr = Arrays.divmod(ints, other.ints);
    return new uint[]{new uint(qr[0]), new uint(qr[1])};
  }

  public boolean equals(final Object other) {
    if(other instanceof BigInteger)
      return Arrays.compare(ints, (BigInteger)other, MAX_WIDTH) == 0;
    return super.equals(other);
  }

  @Override
  public uintType<uint> getMaxValue() {
    return MAX_VALUE;
  }
}
