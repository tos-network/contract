package java.lang;

import java.math.BigInteger;

public final class int216 extends intType<int216> {
    public static final int MAX_WIDTH = 7; // 216 bits = 7 * 32
    /**
     * Number of bits for this type: 216.
     */
    public static final int BITS = 216;
    
    /**
     * The maximum value for int216 (2^(216-1) - 1)
     */
    public static final int216 MAX_VALUE = 
        new int216(BigInteger.valueOf(2).pow(216 - 1).subtract(BigInteger.ONE));
    
    /**
     * The minimum value for int216 (-2^(216-1))
     */
    public static final int216 MIN_VALUE = 
        new int216(BigInteger.valueOf(2).pow(216 - 1).negate());
    
    /**
     * Common constants
     */
    public static final int216 ZERO = new int216(0);
    public static final int216 ONE = new int216(1);
    public static final int216 MINUS_ONE = new int216(-1);
    
    @Override
    public int bitSize() {
        return BITS;
    }
    
    @Override
    protected int getMaxWidth() {
        return MAX_WIDTH;
    }
    
    @Override
    public int216 getMaxValue() {
        return MAX_VALUE;
    }
    
    @Override
    public int216 getMinValue() {
        return MIN_VALUE;
    }
    
    @Override
    protected int216 newInstance(BigInteger value) {
        return new int216(value);
    }
    
    // Constructors
    public int216(long l) { super(l); }
    public int216(int[] ints) { super(ints); }
    public int216(BigInteger b) { super(b); }
    public int216(String s) { super(s, DEFAULT_RADIX); }
    public int216(String s, int radix) { super(s, radix); }
    public int216(byte[] bytes) { super(bytes, MAX_VALUE, MIN_VALUE); }
}