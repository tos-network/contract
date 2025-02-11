package java.lang;

import java.math.BigInteger;

public final class int248 extends intType<int248> {
    public static final int MAX_WIDTH = 8; // 248 bits = 8 * 32
    /**
     * Number of bits for this type: 248.
     */
    public static final int BITS = 248;
    
    /**
     * The maximum value for int248 (2^(248-1) - 1)
     */
    public static final int248 MAX_VALUE = 
        new int248(BigInteger.valueOf(2).pow(248 - 1).subtract(BigInteger.ONE));
    
    /**
     * The minimum value for int248 (-2^(248-1))
     */
    public static final int248 MIN_VALUE = 
        new int248(BigInteger.valueOf(2).pow(248 - 1).negate());
    
    /**
     * Common constants
     */
    public static final int248 ZERO = new int248(0);
    public static final int248 ONE = new int248(1);
    public static final int248 MINUS_ONE = new int248(-1);
    
    @Override
    public int bitSize() {
        return BITS;
    }
    
    @Override
    protected int getMaxWidth() {
        return MAX_WIDTH;
    }
    
    @Override
    public int248 getMaxValue() {
        return MAX_VALUE;
    }
    
    @Override
    public int248 getMinValue() {
        return MIN_VALUE;
    }
    
    @Override
    protected int248 newInstance(BigInteger value) {
        return new int248(value);
    }
    
    // Constructors
    public int248(long l) { super(l); }
    public int248(int[] ints) { super(ints); }
    public int248(BigInteger b) { super(b); }
    public int248(String s) { super(s, DEFAULT_RADIX); }
    public int248(String s, int radix) { super(s, radix); }
    public int248(byte[] bytes) { super(bytes, MAX_VALUE, MIN_VALUE); }
}