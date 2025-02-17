package java.lang;

import java.math.BigInteger;

public final class int96 extends intType<int96> {
    public static final int MAX_WIDTH = 3; // 96 bits = 3 * 32
    /**
     * Number of bits for this type: 96.
     */
    public static final int BITS = 96;
    
    /**
     * The maximum value for int96 (2^(96-1) - 1)
     */
    public static final int96 MAX_VALUE = 
        new int96(BigInteger.valueOf(2).pow(96 - 1).subtract(BigInteger.ONE));
    
    /**
     * The minimum value for int96 (-2^(96-1))
     */
    public static final int96 MIN_VALUE = 
        new int96(BigInteger.valueOf(2).pow(96 - 1).negate());
    
    /**
     * Common constants
     */
    public static final int96 ZERO = new int96(0);
    public static final int96 ONE = new int96(1);
    public static final int96 MINUS_ONE = new int96(-1);

    public static int96 valueOf(byte[] value) {
        return new int96(value);
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
    public int96 getMaxValue() {
        return MAX_VALUE;
    }
    
    @Override
    public int96 getMinValue() {
        return MIN_VALUE;
    }
    
    @Override
    protected int96 newInstance(BigInteger value) {
        return new int96(value);
    }
    
    // Constructors
    public int96(long l) { super(l); }
    public int96(int[] ints) { super(ints); }
    public int96(BigInteger b) { super(b); }
    public int96(String s) { super(s, DEFAULT_RADIX); }
    public int96(String s, int radix) { super(s, radix); }
    public int96(byte[] bytes) { super(bytes, MAX_VALUE, MIN_VALUE); }
}