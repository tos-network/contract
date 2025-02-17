package java.lang;

import java.math.BigInteger;

public final class int152 extends intType<int152> {
    public static final int MAX_WIDTH = 5; // 152 bits = 5 * 32
    /**
     * Number of bits for this type: 152.
     */
    public static final int BITS = 152;
    
    /**
     * The maximum value for int152 (2^(152-1) - 1)
     */
    public static final int152 MAX_VALUE = 
        new int152(BigInteger.valueOf(2).pow(152 - 1).subtract(BigInteger.ONE));
    
    /**
     * The minimum value for int152 (-2^(152-1))
     */
    public static final int152 MIN_VALUE = 
        new int152(BigInteger.valueOf(2).pow(152 - 1).negate());
    
    /**
     * Common constants
     */
    public static final int152 ZERO = new int152(0);
    public static final int152 ONE = new int152(1);
    public static final int152 MINUS_ONE = new int152(-1);

    public static int152 valueOf(byte[] value) {
        return new int152(value);
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
    public int152 getMaxValue() {
        return MAX_VALUE;
    }
    
    @Override
    public int152 getMinValue() {
        return MIN_VALUE;
    }
    
    @Override
    protected int152 newInstance(BigInteger value) {
        return new int152(value);
    }
    
    // Constructors
    public int152(long l) { super(l); }
    public int152(int[] ints) { super(ints); }
    public int152(BigInteger b) { super(b); }
    public int152(String s) { super(s, DEFAULT_RADIX); }
    public int152(String s, int radix) { super(s, radix); }
    public int152(byte[] bytes) { super(bytes, MAX_VALUE, MIN_VALUE); }
}