package java.lang;

import java.math.BigInteger;

public final class int168 extends intType<int168> {
    public static final int MAX_WIDTH = 6; // 168 bits = 6 * 32
    /**
     * Number of bits for this type: 168.
     */
    public static final int BITS = 168;
    
    /**
     * The maximum value for int168 (2^(168-1) - 1)
     */
    public static final int168 MAX_VALUE = 
        new int168(BigInteger.valueOf(2).pow(168 - 1).subtract(BigInteger.ONE));
    
    /**
     * The minimum value for int168 (-2^(168-1))
     */
    public static final int168 MIN_VALUE = 
        new int168(BigInteger.valueOf(2).pow(168 - 1).negate());
    
    /**
     * Common constants
     */
    public static final int168 ZERO = new int168(0);
    public static final int168 ONE = new int168(1);
    public static final int168 MINUS_ONE = new int168(-1);

    public static int168 valueOf(byte[] value) {
        return new int168(value);
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
    public int168 getMaxValue() {
        return MAX_VALUE;
    }
    
    @Override
    public int168 getMinValue() {
        return MIN_VALUE;
    }
    
    @Override
    protected int168 newInstance(BigInteger value) {
        return new int168(value);
    }
    
    // Constructors
    public int168(long l) { super(l); }
    public int168(int[] ints) { super(ints); }
    public int168(BigInteger b) { super(b); }
    public int168(String s) { super(s, DEFAULT_RADIX); }
    public int168(String s, int radix) { super(s, radix); }
    public int168(byte[] bytes) { super(bytes, MAX_VALUE, MIN_VALUE); }
}