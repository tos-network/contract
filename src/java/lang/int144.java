package java.lang;

import java.math.BigInteger;

public final class int144 extends intType<int144> {
    public static final int MAX_WIDTH = 5; // 144 bits = 5 * 32
    /**
     * Number of bits for this type: 144.
     */
    public static final int BITS = 144;
    
    /**
     * The maximum value for int144 (2^(144-1) - 1)
     */
    public static final int144 MAX_VALUE = 
        new int144(BigInteger.valueOf(2).pow(144 - 1).subtract(BigInteger.ONE));
    
    /**
     * The minimum value for int144 (-2^(144-1))
     */
    public static final int144 MIN_VALUE = 
        new int144(BigInteger.valueOf(2).pow(144 - 1).negate());
    
    /**
     * Common constants
     */
    public static final int144 ZERO = new int144(0);
    public static final int144 ONE = new int144(1);
    public static final int144 MINUS_ONE = new int144(-1);

    public static int144 valueOf(byte[] value) {
        return new int144(value);
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
    public int144 getMaxValue() {
        return MAX_VALUE;
    }
    
    @Override
    public int144 getMinValue() {
        return MIN_VALUE;
    }
    
    @Override
    protected int144 newInstance(BigInteger value) {
        return new int144(value);
    }
    
    // Constructors
    public int144(long l) { super(l); }
    public int144(int[] ints) { super(ints); }
    public int144(BigInteger b) { super(b); }
    public int144(String s) { super(s, DEFAULT_RADIX); }
    public int144(String s, int radix) { super(s, radix); }
    public int144(byte[] bytes) { super(bytes, MAX_VALUE, MIN_VALUE); }
}