package java.lang;

import java.math.BigInteger;

public final class int112 extends intType<int112> {
    public static final int MAX_WIDTH = 4; // 112 bits = 4 * 32
    /**
     * Number of bits for this type: 112.
     */
    public static final int BITS = 112;
    
    /**
     * The maximum value for int112 (2^(112-1) - 1)
     */
    public static final int112 MAX_VALUE = 
        new int112(BigInteger.valueOf(2).pow(112 - 1).subtract(BigInteger.ONE));
    
    /**
     * The minimum value for int112 (-2^(112-1))
     */
    public static final int112 MIN_VALUE = 
        new int112(BigInteger.valueOf(2).pow(112 - 1).negate());
    
    /**
     * Common constants
     */
    public static final int112 ZERO = new int112(0);
    public static final int112 ONE = new int112(1);
    public static final int112 MINUS_ONE = new int112(-1);
    
    @Override
    public int bitSize() {
        return BITS;
    }
    
    @Override
    protected int getMaxWidth() {
        return MAX_WIDTH;
    }
    
    @Override
    public int112 getMaxValue() {
        return MAX_VALUE;
    }
    
    @Override
    public int112 getMinValue() {
        return MIN_VALUE;
    }
    
    @Override
    protected int112 newInstance(BigInteger value) {
        return new int112(value);
    }
    
    // Constructors
    public int112(long l) { super(l); }
    public int112(int[] ints) { super(ints); }
    public int112(BigInteger b) { super(b); }
    public int112(String s) { super(s, DEFAULT_RADIX); }
    public int112(String s, int radix) { super(s, radix); }
    public int112(byte[] bytes) { super(bytes, MAX_VALUE, MIN_VALUE); }
}