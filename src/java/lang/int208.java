package java.lang;

import java.math.BigInteger;

public final class int208 extends intType<int208> {
    public static final int MAX_WIDTH = 7; // 208 bits = 7 * 32
    /**
     * Number of bits for this type: 208.
     */
    public static final int BITS = 208;
    
    /**
     * The maximum value for int208 (2^(208-1) - 1)
     */
    public static final int208 MAX_VALUE = 
        new int208(BigInteger.valueOf(2).pow(208 - 1).subtract(BigInteger.ONE));
    
    /**
     * The minimum value for int208 (-2^(208-1))
     */
    public static final int208 MIN_VALUE = 
        new int208(BigInteger.valueOf(2).pow(208 - 1).negate());
    
    /**
     * Common constants
     */
    public static final int208 ZERO = new int208(0);
    public static final int208 ONE = new int208(1);
    public static final int208 MINUS_ONE = new int208(-1);

    public static int208 valueOf(byte[] value) {
        return new int208(value);
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
    public int208 getMaxValue() {
        return MAX_VALUE;
    }
    
    @Override
    public int208 getMinValue() {
        return MIN_VALUE;
    }
    
    @Override
    protected int208 newInstance(BigInteger value) {
        return new int208(value);
    }
    
    // Constructors
    public int208(long l) { super(l); }
    public int208(int[] ints) { super(ints); }
    public int208(BigInteger b) { super(b); }
    public int208(String s) { super(s, DEFAULT_RADIX); }
    public int208(String s, int radix) { super(s, radix); }
    public int208(byte[] bytes) { super(bytes, MAX_VALUE, MIN_VALUE); }
}