package java.lang;

import java.math.BigInteger;

public final class int136 extends intType<int136> {
    public static final int MAX_WIDTH = 5; // 136 bits = 5 * 32
    /**
     * Number of bits for this type: 136.
     */
    public static final int BITS = 136;
    
    /**
     * The maximum value for int136 (2^(136-1) - 1)
     */
    public static final int136 MAX_VALUE = 
        new int136(BigInteger.valueOf(2).pow(136 - 1).subtract(BigInteger.ONE));
    
    /**
     * The minimum value for int136 (-2^(136-1))
     */
    public static final int136 MIN_VALUE = 
        new int136(BigInteger.valueOf(2).pow(136 - 1).negate());
    
    /**
     * Common constants
     */
    public static final int136 ZERO = new int136(0);
    public static final int136 ONE = new int136(1);
    public static final int136 MINUS_ONE = new int136(-1);
    
    @Override
    public int bitSize() {
        return BITS;
    }
    
    @Override
    protected int getMaxWidth() {
        return MAX_WIDTH;
    }
    
    @Override
    public int136 getMaxValue() {
        return MAX_VALUE;
    }
    
    @Override
    public int136 getMinValue() {
        return MIN_VALUE;
    }
    
    @Override
    protected int136 newInstance(BigInteger value) {
        return new int136(value);
    }
    
    // Constructors
    public int136(long l) { super(l); }
    public int136(int[] ints) { super(ints); }
    public int136(BigInteger b) { super(b); }
    public int136(String s) { super(s, DEFAULT_RADIX); }
    public int136(String s, int radix) { super(s, radix); }
    public int136(byte[] bytes) { super(bytes, MAX_VALUE, MIN_VALUE); }
}