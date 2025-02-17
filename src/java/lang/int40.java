package java.lang;

import java.math.BigInteger;

public final class int40 extends intType<int40> {
    public static final int MAX_WIDTH = 2; // 40 bits = 2 * 32
    /**
     * Number of bits for this type: 40.
     */
    public static final int BITS = 40;
    
    /**
     * The maximum value for int40 (2^(40-1) - 1)
     */
    public static final int40 MAX_VALUE = 
        new int40(BigInteger.valueOf(2).pow(40 - 1).subtract(BigInteger.ONE));
    
    /**
     * The minimum value for int40 (-2^(40-1))
     */
    public static final int40 MIN_VALUE = 
        new int40(BigInteger.valueOf(2).pow(40 - 1).negate());
    
    /**
     * Common constants
     */
    public static final int40 ZERO = new int40(0);
    public static final int40 ONE = new int40(1);
    public static final int40 MINUS_ONE = new int40(-1);

    public static int40 valueOf(byte[] value) {
        return new int40(value);
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
    public int40 getMaxValue() {
        return MAX_VALUE;
    }
    
    @Override
    public int40 getMinValue() {
        return MIN_VALUE;
    }
    
    @Override
    protected int40 newInstance(BigInteger value) {
        return new int40(value);
    }
    
    // Constructors
    public int40(long l) { super(l); }
    public int40(int[] ints) { super(ints); }
    public int40(BigInteger b) { super(b); }
    public int40(String s) { super(s, DEFAULT_RADIX); }
    public int40(String s, int radix) { super(s, radix); }
    public int40(byte[] bytes) { super(bytes, MAX_VALUE, MIN_VALUE); }
}