package java.lang;

import java.math.BigInteger;

public final class int232 extends intType<int232> {
    public static final int MAX_WIDTH = 8; // 232 bits = 8 * 32
    /**
     * Number of bits for this type: 232.
     */
    public static final int BITS = 232;
    
    /**
     * The maximum value for int232 (2^(232-1) - 1)
     */
    public static final int232 MAX_VALUE = 
        new int232(BigInteger.valueOf(2).pow(232 - 1).subtract(BigInteger.ONE));
    
    /**
     * The minimum value for int232 (-2^(232-1))
     */
    public static final int232 MIN_VALUE = 
        new int232(BigInteger.valueOf(2).pow(232 - 1).negate());
    
    /**
     * Common constants
     */
    public static final int232 ZERO = new int232(0);
    public static final int232 ONE = new int232(1);
    public static final int232 MINUS_ONE = new int232(-1);

    public static int232 valueOf(byte[] value) {
        return new int232(value);
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
    public int232 getMaxValue() {
        return MAX_VALUE;
    }
    
    @Override
    public int232 getMinValue() {
        return MIN_VALUE;
    }
    
    @Override
    protected int232 newInstance(BigInteger value) {
        return new int232(value);
    }
    
    // Constructors
    public int232(long l) { super(l); }
    public int232(int[] ints) { super(ints); }
    public int232(BigInteger b) { super(b); }
    public int232(String s) { super(s, DEFAULT_RADIX); }
    public int232(String s, int radix) { super(s, radix); }
    public int232(byte[] bytes) { super(bytes, MAX_VALUE, MIN_VALUE); }
}