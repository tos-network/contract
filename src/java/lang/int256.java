package java.lang;

import java.math.BigInteger;

public final class int256 extends intType<int256> {
    public static final int MAX_WIDTH = 8; // 256 bits = 8 * 32
    /**
     * Number of bits for this type: 256.
     */
    public static final int BITS = 256;
    
    /**
     * The maximum value for int256 (2^(256-1) - 1)
     */
    public static final int256 MAX_VALUE = 
        new int256(BigInteger.valueOf(2).pow(256 - 1).subtract(BigInteger.ONE));
    
    /**
     * The minimum value for int256 (-2^(256-1))
     */
    public static final int256 MIN_VALUE = 
        new int256(BigInteger.valueOf(2).pow(256 - 1).negate());
    
    /**
     * Common constants
     */
    public static final int256 ZERO = new int256(0);
    public static final int256 ONE = new int256(1);
    public static final int256 MINUS_ONE = new int256(-1);

    public static int256 valueOf(byte[] value) {
        return new int256(value);
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
    public int256 getMaxValue() {
        return MAX_VALUE;
    }
    
    @Override
    public int256 getMinValue() {
        return MIN_VALUE;
    }
    
    @Override
    protected int256 newInstance(BigInteger value) {
        return new int256(value);
    }
    
    // Constructors
    public int256(long l) { super(l); }
    public int256(int[] ints) { super(ints); }
    public int256(BigInteger b) { super(b); }
    public int256(String s) { super(s, DEFAULT_RADIX); }
    public int256(String s, int radix) { super(s, radix); }
    public int256(byte[] bytes) { super(bytes, MAX_VALUE, MIN_VALUE); }
}