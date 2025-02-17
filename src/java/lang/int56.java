package java.lang;

import java.math.BigInteger;

public final class int56 extends intType<int56> {
    public static final int MAX_WIDTH = 2; // 56 bits = 2 * 32
    /**
     * Number of bits for this type: 56.
     */
    public static final int BITS = 56;
    
    /**
     * The maximum value for int56 (2^(56-1) - 1)
     */
    public static final int56 MAX_VALUE = 
        new int56(BigInteger.valueOf(2).pow(56 - 1).subtract(BigInteger.ONE));
    
    /**
     * The minimum value for int56 (-2^(56-1))
     */
    public static final int56 MIN_VALUE = 
        new int56(BigInteger.valueOf(2).pow(56 - 1).negate());
    
    /**
     * Common constants
     */
    public static final int56 ZERO = new int56(0);
    public static final int56 ONE = new int56(1);
    public static final int56 MINUS_ONE = new int56(-1);

    public static int56 valueOf(byte[] value) {
        return new int56(value);
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
    public int56 getMaxValue() {
        return MAX_VALUE;
    }
    
    @Override
    public int56 getMinValue() {
        return MIN_VALUE;
    }
    
    @Override
    protected int56 newInstance(BigInteger value) {
        return new int56(value);
    }
    
    // Constructors
    public int56(long l) { super(l); }
    public int56(int[] ints) { super(ints); }
    public int56(BigInteger b) { super(b); }
    public int56(String s) { super(s, DEFAULT_RADIX); }
    public int56(String s, int radix) { super(s, radix); }
    public int56(byte[] bytes) { super(bytes, MAX_VALUE, MIN_VALUE); }
}