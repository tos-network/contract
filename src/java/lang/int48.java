package java.lang;

import java.math.BigInteger;

public final class int48 extends intType<int48> {
    public static final int MAX_WIDTH = 2; // 48 bits = 2 * 32
    /**
     * Number of bits for this type: 48.
     */
    public static final int BITS = 48;
    
    /**
     * The maximum value for int48 (2^(48-1) - 1)
     */
    public static final int48 MAX_VALUE = 
        new int48(BigInteger.valueOf(2).pow(48 - 1).subtract(BigInteger.ONE));
    
    /**
     * The minimum value for int48 (-2^(48-1))
     */
    public static final int48 MIN_VALUE = 
        new int48(BigInteger.valueOf(2).pow(48 - 1).negate());
    
    /**
     * Common constants
     */
    public static final int48 ZERO = new int48(0);
    public static final int48 ONE = new int48(1);
    public static final int48 MINUS_ONE = new int48(-1);
    
    @Override
    public int bitSize() {
        return BITS;
    }
    
    @Override
    protected int getMaxWidth() {
        return MAX_WIDTH;
    }
    
    @Override
    public int48 getMaxValue() {
        return MAX_VALUE;
    }
    
    @Override
    public int48 getMinValue() {
        return MIN_VALUE;
    }
    
    @Override
    protected int48 newInstance(BigInteger value) {
        return new int48(value);
    }
    
    // Constructors
    public int48(long l) { super(l); }
    public int48(int[] ints) { super(ints); }
    public int48(BigInteger b) { super(b); }
    public int48(String s) { super(s, DEFAULT_RADIX); }
    public int48(String s, int radix) { super(s, radix); }
    public int48(byte[] bytes) { super(bytes, MAX_VALUE, MIN_VALUE); }
}