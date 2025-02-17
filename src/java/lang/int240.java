package java.lang;

import java.math.BigInteger;

public final class int240 extends intType<int240> {
    public static final int MAX_WIDTH = 8; // 240 bits = 8 * 32
    /**
     * Number of bits for this type: 240.
     */
    public static final int BITS = 240;
    
    /**
     * The maximum value for int240 (2^(240-1) - 1)
     */
    public static final int240 MAX_VALUE = 
        new int240(BigInteger.valueOf(2).pow(240 - 1).subtract(BigInteger.ONE));
    
    /**
     * The minimum value for int240 (-2^(240-1))
     */
    public static final int240 MIN_VALUE = 
        new int240(BigInteger.valueOf(2).pow(240 - 1).negate());
    
    /**
     * Common constants
     */
    public static final int240 ZERO = new int240(0);
    public static final int240 ONE = new int240(1);
    public static final int240 MINUS_ONE = new int240(-1);

    public static int240 valueOf(byte[] value) {
        return new int240(value);
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
    public int240 getMaxValue() {
        return MAX_VALUE;
    }
    
    @Override
    public int240 getMinValue() {
        return MIN_VALUE;
    }
    
    @Override
    protected int240 newInstance(BigInteger value) {
        return new int240(value);
    }
    
    // Constructors
    public int240(long l) { super(l); }
    public int240(int[] ints) { super(ints); }
    public int240(BigInteger b) { super(b); }
    public int240(String s) { super(s, DEFAULT_RADIX); }
    public int240(String s, int radix) { super(s, radix); }
    public int240(byte[] bytes) { super(bytes, MAX_VALUE, MIN_VALUE); }
}