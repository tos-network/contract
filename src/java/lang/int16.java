package java.lang;

import java.math.BigInteger;

public final class int16 extends intType<int16> {
    public static final int MAX_WIDTH = 1; // 16 bits = 1 * 32
    /**
     * Number of bits for this type: 16.
     */
    public static final int BITS = 16;
    
    /**
     * The maximum value for int16 (2^(16-1) - 1)
     */
    public static final int16 MAX_VALUE = 
        new int16(BigInteger.valueOf(2).pow(16 - 1).subtract(BigInteger.ONE));
    
    /**
     * The minimum value for int16 (-2^(16-1))
     */
    public static final int16 MIN_VALUE = 
        new int16(BigInteger.valueOf(2).pow(16 - 1).negate());
    
    /**
     * Common constants
     */
    public static final int16 ZERO = new int16(0);
    public static final int16 ONE = new int16(1);
    public static final int16 MINUS_ONE = new int16(-1);

    public static int16 valueOf(byte[] value) {
        return new int16(value);
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
    public int16 getMaxValue() {
        return MAX_VALUE;
    }
    
    @Override
    public int16 getMinValue() {
        return MIN_VALUE;
    }
    
    @Override
    protected int16 newInstance(BigInteger value) {
        return new int16(value);
    }
    
    // Constructors
    public int16(long l) { super(l); }
    public int16(int[] ints) { super(ints); }
    public int16(BigInteger b) { super(b); }
    public int16(String s) { super(s, DEFAULT_RADIX); }
    public int16(String s, int radix) { super(s, radix); }
    public int16(byte[] bytes) { super(bytes, MAX_VALUE, MIN_VALUE); }
}