package java.lang;

import java.math.BigInteger;

public final class int32 extends intType<int32> {
    public static final int MAX_WIDTH = 1; // 32 bits = 1 * 32
    /**
     * Number of bits for this type: 32.
     */
    public static final int BITS = 32;
    
    /**
     * The maximum value for int32 (2^(32-1) - 1)
     */
    public static final int32 MAX_VALUE = 
        new int32(BigInteger.valueOf(2).pow(32 - 1).subtract(BigInteger.ONE));
    
    /**
     * The minimum value for int32 (-2^(32-1))
     */
    public static final int32 MIN_VALUE = 
        new int32(BigInteger.valueOf(2).pow(32 - 1).negate());
    
    /**
     * Common constants
     */
    public static final int32 ZERO = new int32(0);
    public static final int32 ONE = new int32(1);
    public static final int32 MINUS_ONE = new int32(-1);
    
    @Override
    public int bitSize() {
        return BITS;
    }
    
    @Override
    protected int getMaxWidth() {
        return MAX_WIDTH;
    }
    
    @Override
    public int32 getMaxValue() {
        return MAX_VALUE;
    }
    
    @Override
    public int32 getMinValue() {
        return MIN_VALUE;
    }
    
    @Override
    protected int32 newInstance(BigInteger value) {
        return new int32(value);
    }
    
    // Constructors
    public int32(long l) { super(l); }
    public int32(int[] ints) { super(ints); }
    public int32(BigInteger b) { super(b); }
    public int32(String s) { super(s, DEFAULT_RADIX); }
    public int32(String s, int radix) { super(s, radix); }
    public int32(byte[] bytes) { super(bytes, MAX_VALUE, MIN_VALUE); }
}