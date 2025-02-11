package java.lang;

import java.math.BigInteger;

public final class int24 extends intType<int24> {
    public static final int MAX_WIDTH = 1; // 24 bits = 1 * 32
    /**
     * Number of bits for this type: 24.
     */
    public static final int BITS = 24;
    
    /**
     * The maximum value for int24 (2^(24-1) - 1)
     */
    public static final int24 MAX_VALUE = 
        new int24(BigInteger.valueOf(2).pow(24 - 1).subtract(BigInteger.ONE));
    
    /**
     * The minimum value for int24 (-2^(24-1))
     */
    public static final int24 MIN_VALUE = 
        new int24(BigInteger.valueOf(2).pow(24 - 1).negate());
    
    /**
     * Common constants
     */
    public static final int24 ZERO = new int24(0);
    public static final int24 ONE = new int24(1);
    public static final int24 MINUS_ONE = new int24(-1);
    
    @Override
    public int bitSize() {
        return BITS;
    }
    
    @Override
    protected int getMaxWidth() {
        return MAX_WIDTH;
    }
    
    @Override
    public int24 getMaxValue() {
        return MAX_VALUE;
    }
    
    @Override
    public int24 getMinValue() {
        return MIN_VALUE;
    }
    
    @Override
    protected int24 newInstance(BigInteger value) {
        return new int24(value);
    }
    
    // Constructors
    public int24(long l) { super(l); }
    public int24(int[] ints) { super(ints); }
    public int24(BigInteger b) { super(b); }
    public int24(String s) { super(s, DEFAULT_RADIX); }
    public int24(String s, int radix) { super(s, radix); }
    public int24(byte[] bytes) { super(bytes, MAX_VALUE, MIN_VALUE); }
}