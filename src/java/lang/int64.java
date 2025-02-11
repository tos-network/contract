package java.lang;

import java.math.BigInteger;

public final class int64 extends intType<int64> {
    public static final int MAX_WIDTH = 2; // 64 bits = 2 * 32
    /**
     * Number of bits for this type: 64.
     */
    public static final int BITS = 64;
    
    /**
     * The maximum value for int64 (2^(64-1) - 1)
     */
    public static final int64 MAX_VALUE = 
        new int64(BigInteger.valueOf(2).pow(64 - 1).subtract(BigInteger.ONE));
    
    /**
     * The minimum value for int64 (-2^(64-1))
     */
    public static final int64 MIN_VALUE = 
        new int64(BigInteger.valueOf(2).pow(64 - 1).negate());
    
    /**
     * Common constants
     */
    public static final int64 ZERO = new int64(0);
    public static final int64 ONE = new int64(1);
    public static final int64 MINUS_ONE = new int64(-1);
    
    @Override
    public int bitSize() {
        return BITS;
    }
    
    @Override
    protected int getMaxWidth() {
        return MAX_WIDTH;
    }
    
    @Override
    public int64 getMaxValue() {
        return MAX_VALUE;
    }
    
    @Override
    public int64 getMinValue() {
        return MIN_VALUE;
    }
    
    @Override
    protected int64 newInstance(BigInteger value) {
        return new int64(value);
    }
    
    // Constructors
    public int64(long l) { super(l); }
    public int64(int[] ints) { super(ints); }
    public int64(BigInteger b) { super(b); }
    public int64(String s) { super(s, DEFAULT_RADIX); }
    public int64(String s, int radix) { super(s, radix); }
    public int64(byte[] bytes) { super(bytes, MAX_VALUE, MIN_VALUE); }
}