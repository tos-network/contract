package java.lang;

import java.math.BigInteger;

public final class int184 extends intType<int184> {
    public static final int MAX_WIDTH = 6; // 184 bits = 6 * 32
    /**
     * Number of bits for this type: 184.
     */
    public static final int BITS = 184;
    
    /**
     * The maximum value for int184 (2^(184-1) - 1)
     */
    public static final int184 MAX_VALUE = 
        new int184(BigInteger.valueOf(2).pow(184 - 1).subtract(BigInteger.ONE));
    
    /**
     * The minimum value for int184 (-2^(184-1))
     */
    public static final int184 MIN_VALUE = 
        new int184(BigInteger.valueOf(2).pow(184 - 1).negate());
    
    /**
     * Common constants
     */
    public static final int184 ZERO = new int184(0);
    public static final int184 ONE = new int184(1);
    public static final int184 MINUS_ONE = new int184(-1);

    public static int184 valueOf(byte[] value) {
        return new int184(value);
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
    public int184 getMaxValue() {
        return MAX_VALUE;
    }
    
    @Override
    public int184 getMinValue() {
        return MIN_VALUE;
    }
    
    @Override
    protected int184 newInstance(BigInteger value) {
        return new int184(value);
    }
    
    // Constructors
    public int184(long l) { super(l); }
    public int184(int[] ints) { super(ints); }
    public int184(BigInteger b) { super(b); }
    public int184(String s) { super(s, DEFAULT_RADIX); }
    public int184(String s, int radix) { super(s, radix); }
    public int184(byte[] bytes) { super(bytes, MAX_VALUE, MIN_VALUE); }
}