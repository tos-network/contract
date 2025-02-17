package java.lang;

import java.math.BigInteger;

public final class int224 extends intType<int224> {
    public static final int MAX_WIDTH = 7; // 224 bits = 7 * 32
    /**
     * Number of bits for this type: 224.
     */
    public static final int BITS = 224;
    
    /**
     * The maximum value for int224 (2^(224-1) - 1)
     */
    public static final int224 MAX_VALUE = 
        new int224(BigInteger.valueOf(2).pow(224 - 1).subtract(BigInteger.ONE));
    
    /**
     * The minimum value for int224 (-2^(224-1))
     */
    public static final int224 MIN_VALUE = 
        new int224(BigInteger.valueOf(2).pow(224 - 1).negate());
    
    /**
     * Common constants
     */
    public static final int224 ZERO = new int224(0);
    public static final int224 ONE = new int224(1);
    public static final int224 MINUS_ONE = new int224(-1);

    public static int224 valueOf(byte[] value) {
        return new int224(value);
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
    public int224 getMaxValue() {
        return MAX_VALUE;
    }
    
    @Override
    public int224 getMinValue() {
        return MIN_VALUE;
    }
    
    @Override
    protected int224 newInstance(BigInteger value) {
        return new int224(value);
    }
    
    // Constructors
    public int224(long l) { super(l); }
    public int224(int[] ints) { super(ints); }
    public int224(BigInteger b) { super(b); }
    public int224(String s) { super(s, DEFAULT_RADIX); }
    public int224(String s, int radix) { super(s, radix); }
    public int224(byte[] bytes) { super(bytes, MAX_VALUE, MIN_VALUE); }
}