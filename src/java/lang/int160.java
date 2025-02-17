package java.lang;

import java.math.BigInteger;

public final class int160 extends intType<int160> {
    public static final int MAX_WIDTH = 5; // 160 bits = 5 * 32
    /**
     * Number of bits for this type: 160.
     */
    public static final int BITS = 160;
    
    /**
     * The maximum value for int160 (2^(160-1) - 1)
     */
    public static final int160 MAX_VALUE = 
        new int160(BigInteger.valueOf(2).pow(160 - 1).subtract(BigInteger.ONE));
    
    /**
     * The minimum value for int160 (-2^(160-1))
     */
    public static final int160 MIN_VALUE = 
        new int160(BigInteger.valueOf(2).pow(160 - 1).negate());
    
    /**
     * Common constants
     */
    public static final int160 ZERO = new int160(0);
    public static final int160 ONE = new int160(1);
    public static final int160 MINUS_ONE = new int160(-1);

    public static int160 valueOf(byte[] value) {
        return new int160(value);
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
    public int160 getMaxValue() {
        return MAX_VALUE;
    }
    
    @Override
    public int160 getMinValue() {
        return MIN_VALUE;
    }
    
    @Override
    protected int160 newInstance(BigInteger value) {
        return new int160(value);
    }
    
    // Constructors
    public int160(long l) { super(l); }
    public int160(int[] ints) { super(ints); }
    public int160(BigInteger b) { super(b); }
    public int160(String s) { super(s, DEFAULT_RADIX); }
    public int160(String s, int radix) { super(s, radix); }
    public int160(byte[] bytes) { super(bytes, MAX_VALUE, MIN_VALUE); }
}