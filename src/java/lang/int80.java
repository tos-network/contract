package java.lang;

import java.math.BigInteger;

public final class int80 extends intType<int80> {
    public static final int MAX_WIDTH = 3; // 80 bits = 3 * 32
    /**
     * Number of bits for this type: 80.
     */
    public static final int BITS = 80;
    
    /**
     * The maximum value for int80 (2^(80-1) - 1)
     */
    public static final int80 MAX_VALUE = 
        new int80(BigInteger.valueOf(2).pow(80 - 1).subtract(BigInteger.ONE));
    
    /**
     * The minimum value for int80 (-2^(80-1))
     */
    public static final int80 MIN_VALUE = 
        new int80(BigInteger.valueOf(2).pow(80 - 1).negate());
    
    /**
     * Common constants
     */
    public static final int80 ZERO = new int80(0);
    public static final int80 ONE = new int80(1);
    public static final int80 MINUS_ONE = new int80(-1);

    public static int80 valueOf(byte[] value) {
        return new int80(value);
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
    public int80 getMaxValue() {
        return MAX_VALUE;
    }
    
    @Override
    public int80 getMinValue() {
        return MIN_VALUE;
    }
    
    @Override
    protected int80 newInstance(BigInteger value) {
        return new int80(value);
    }
    
    // Constructors
    public int80(long l) { super(l); }
    public int80(int[] ints) { super(ints); }
    public int80(BigInteger b) { super(b); }
    public int80(String s) { super(s, DEFAULT_RADIX); }
    public int80(String s, int radix) { super(s, radix); }
    public int80(byte[] bytes) { super(bytes, MAX_VALUE, MIN_VALUE); }
}