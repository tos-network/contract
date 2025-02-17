package java.lang;

import java.math.BigInteger;

public final class int88 extends intType<int88> {
    public static final int MAX_WIDTH = 3; // 88 bits = 3 * 32
    /**
     * Number of bits for this type: 88.
     */
    public static final int BITS = 88;
    
    /**
     * The maximum value for int88 (2^(88-1) - 1)
     */
    public static final int88 MAX_VALUE = 
        new int88(BigInteger.valueOf(2).pow(88 - 1).subtract(BigInteger.ONE));
    
    /**
     * The minimum value for int88 (-2^(88-1))
     */
    public static final int88 MIN_VALUE = 
        new int88(BigInteger.valueOf(2).pow(88 - 1).negate());
    
    /**
     * Common constants
     */
    public static final int88 ZERO = new int88(0);
    public static final int88 ONE = new int88(1);
    public static final int88 MINUS_ONE = new int88(-1);

    public static int88 valueOf(byte[] value) {
        return new int88(value);
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
    public int88 getMaxValue() {
        return MAX_VALUE;
    }
    
    @Override
    public int88 getMinValue() {
        return MIN_VALUE;
    }
    
    @Override
    protected int88 newInstance(BigInteger value) {
        return new int88(value);
    }
    
    // Constructors
    public int88(long l) { super(l); }
    public int88(int[] ints) { super(ints); }
    public int88(BigInteger b) { super(b); }
    public int88(String s) { super(s, DEFAULT_RADIX); }
    public int88(String s, int radix) { super(s, radix); }
    public int88(byte[] bytes) { super(bytes, MAX_VALUE, MIN_VALUE); }
}