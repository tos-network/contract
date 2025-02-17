package java.lang;

import java.math.BigInteger;

public final class int192 extends intType<int192> {
    public static final int MAX_WIDTH = 6; // 192 bits = 6 * 32
    /**
     * Number of bits for this type: 192.
     */
    public static final int BITS = 192;
    
    /**
     * The maximum value for int192 (2^(192-1) - 1)
     */
    public static final int192 MAX_VALUE = 
        new int192(BigInteger.valueOf(2).pow(192 - 1).subtract(BigInteger.ONE));
    
    /**
     * The minimum value for int192 (-2^(192-1))
     */
    public static final int192 MIN_VALUE = 
        new int192(BigInteger.valueOf(2).pow(192 - 1).negate());
    
    /**
     * Common constants
     */
    public static final int192 ZERO = new int192(0);
    public static final int192 ONE = new int192(1);
    public static final int192 MINUS_ONE = new int192(-1);

    public static int192 valueOf(byte[] value) {
        return new int192(value);
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
    public int192 getMaxValue() {
        return MAX_VALUE;
    }
    
    @Override
    public int192 getMinValue() {
        return MIN_VALUE;
    }
    
    @Override
    protected int192 newInstance(BigInteger value) {
        return new int192(value);
    }
    
    // Constructors
    public int192(long l) { super(l); }
    public int192(int[] ints) { super(ints); }
    public int192(BigInteger b) { super(b); }
    public int192(String s) { super(s, DEFAULT_RADIX); }
    public int192(String s, int radix) { super(s, radix); }
    public int192(byte[] bytes) { super(bytes, MAX_VALUE, MIN_VALUE); }
}