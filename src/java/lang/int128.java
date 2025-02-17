package java.lang;

import java.math.BigInteger;

public final class int128 extends intType<int128> {
    public static final int MAX_WIDTH = 4; // 128 bits = 4 * 32
    /**
     * Number of bits for this type: 128.
     */
    public static final int BITS = 128;
    
    /**
     * The maximum value for int128 (2^(128-1) - 1)
     */
    public static final int128 MAX_VALUE = 
        new int128(BigInteger.valueOf(2).pow(128 - 1).subtract(BigInteger.ONE));
    
    /**
     * The minimum value for int128 (-2^(128-1))
     */
    public static final int128 MIN_VALUE = 
        new int128(BigInteger.valueOf(2).pow(128 - 1).negate());
    
    /**
     * Common constants
     */
    public static final int128 ZERO = new int128(0);
    public static final int128 ONE = new int128(1);
    public static final int128 MINUS_ONE = new int128(-1);

    public static int128 valueOf(byte[] value) {
        return new int128(value);
    }
    
    @Override
    public int bitSize() {
        return BITS;
    }
    
    @Override
    protected int getMaxWidth() {
        return MAX_WIDTH;
    }
    
    @Override
    public int128 getMaxValue() {
        return MAX_VALUE;
    }
    
    @Override
    public int128 getMinValue() {
        return MIN_VALUE;
    }
    
    @Override
    protected int128 newInstance(BigInteger value) {
        return new int128(value);
    }
    
    // Constructors
    public int128(long l) { super(l); }
    public int128(int[] ints) { super(ints); }
    public int128(BigInteger b) { super(b); }
    public int128(String s) { super(s, DEFAULT_RADIX); }
    public int128(String s, int radix) { super(s, radix); }
    public int128(byte[] bytes) { super(bytes, MAX_VALUE, MIN_VALUE); }
}