package java.lang;

import java.math.BigInteger;

public final class int176 extends intType<int176> {
    public static final int MAX_WIDTH = 6; // 176 bits = 6 * 32
    /**
     * Number of bits for this type: 176.
     */
    public static final int BITS = 176;
    
    /**
     * The maximum value for int176 (2^(176-1) - 1)
     */
    public static final int176 MAX_VALUE = 
        new int176(BigInteger.valueOf(2).pow(176 - 1).subtract(BigInteger.ONE));
    
    /**
     * The minimum value for int176 (-2^(176-1))
     */
    public static final int176 MIN_VALUE = 
        new int176(BigInteger.valueOf(2).pow(176 - 1).negate());
    
    /**
     * Common constants
     */
    public static final int176 ZERO = new int176(0);
    public static final int176 ONE = new int176(1);
    public static final int176 MINUS_ONE = new int176(-1);

    public static int176 valueOf(byte[] value) {
        return new int176(value);
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
    public int176 getMaxValue() {
        return MAX_VALUE;
    }
    
    @Override
    public int176 getMinValue() {
        return MIN_VALUE;
    }
    
    @Override
    protected int176 newInstance(BigInteger value) {
        return new int176(value);
    }
    
    // Constructors
    public int176(long l) { super(l); }
    public int176(int[] ints) { super(ints); }
    public int176(BigInteger b) { super(b); }
    public int176(String s) { super(s, DEFAULT_RADIX); }
    public int176(String s, int radix) { super(s, radix); }
    public int176(byte[] bytes) { super(bytes, MAX_VALUE, MIN_VALUE); }
}