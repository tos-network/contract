package java.lang;

import java.math.BigInteger;

public final class int8 extends intType<int8> {
    public static final int MAX_WIDTH = 1; // 8 bits = 1 * 32
    /**
     * Number of bits for this type: 8.
     */
    public static final int BITS = 8;
    
    /**
     * The maximum value for int8 (2^(8-1) - 1)
     */
    public static final int8 MAX_VALUE = 
        new int8(BigInteger.valueOf(2).pow(8 - 1).subtract(BigInteger.ONE));
    
    /**
     * The minimum value for int8 (-2^(8-1))
     */
    public static final int8 MIN_VALUE = 
        new int8(BigInteger.valueOf(2).pow(8 - 1).negate());
    
    /**
     * Common constants
     */
    public static final int8 ZERO = new int8(0);
    public static final int8 ONE = new int8(1);
    public static final int8 MINUS_ONE = new int8(-1);

    public static int8 valueOf(byte[] value) {
        return new int8(value);
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
    public int8 getMaxValue() {
        return MAX_VALUE;
    }
    
    @Override
    public int8 getMinValue() {
        return MIN_VALUE;
    }
    
    @Override
    protected int8 newInstance(BigInteger value) {
        return new int8(value);
    }
    
    // Constructors
    public int8(long l) { super(l); }
    public int8(int[] ints) { super(ints); }
    public int8(BigInteger b) { super(b); }
    public int8(String s) { super(s, DEFAULT_RADIX); }
    public int8(String s, int radix) { super(s, radix); }
    public int8(byte[] bytes) { super(bytes, MAX_VALUE, MIN_VALUE); }
}