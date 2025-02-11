package java.lang;

import java.math.BigInteger;

public final class int120 extends intType<int120> {
    public static final int MAX_WIDTH = 4; // 120 bits = 4 * 32
    /**
     * Number of bits for this type: 120.
     */
    public static final int BITS = 120;
    
    /**
     * The maximum value for int120 (2^(120-1) - 1)
     */
    public static final int120 MAX_VALUE = 
        new int120(BigInteger.valueOf(2).pow(120 - 1).subtract(BigInteger.ONE));
    
    /**
     * The minimum value for int120 (-2^(120-1))
     */
    public static final int120 MIN_VALUE = 
        new int120(BigInteger.valueOf(2).pow(120 - 1).negate());
    
    /**
     * Common constants
     */
    public static final int120 ZERO = new int120(0);
    public static final int120 ONE = new int120(1);
    public static final int120 MINUS_ONE = new int120(-1);
    
    @Override
    public int bitSize() {
        return BITS;
    }
    
    @Override
    protected int getMaxWidth() {
        return MAX_WIDTH;
    }
    
    @Override
    public int120 getMaxValue() {
        return MAX_VALUE;
    }
    
    @Override
    public int120 getMinValue() {
        return MIN_VALUE;
    }
    
    @Override
    protected int120 newInstance(BigInteger value) {
        return new int120(value);
    }
    
    // Constructors
    public int120(long l) { super(l); }
    public int120(int[] ints) { super(ints); }
    public int120(BigInteger b) { super(b); }
    public int120(String s) { super(s, DEFAULT_RADIX); }
    public int120(String s, int radix) { super(s, radix); }
    public int120(byte[] bytes) { super(bytes, MAX_VALUE, MIN_VALUE); }
}