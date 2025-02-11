package java.lang;

import java.math.BigInteger;

public final class int104 extends intType<int104> {
    public static final int MAX_WIDTH = 4; // 104 bits = 4 * 32
    /**
     * Number of bits for this type: 104.
     */
    public static final int BITS = 104;
    
    /**
     * The maximum value for int104 (2^(104-1) - 1)
     */
    public static final int104 MAX_VALUE = 
        new int104(BigInteger.valueOf(2).pow(104 - 1).subtract(BigInteger.ONE));
    
    /**
     * The minimum value for int104 (-2^(104-1))
     */
    public static final int104 MIN_VALUE = 
        new int104(BigInteger.valueOf(2).pow(104 - 1).negate());
    
    /**
     * Common constants
     */
    public static final int104 ZERO = new int104(0);
    public static final int104 ONE = new int104(1);
    public static final int104 MINUS_ONE = new int104(-1);
    
    @Override
    public int bitSize() {
        return BITS;
    }
    
    @Override
    protected int getMaxWidth() {
        return MAX_WIDTH;
    }
    
    @Override
    public int104 getMaxValue() {
        return MAX_VALUE;
    }
    
    @Override
    public int104 getMinValue() {
        return MIN_VALUE;
    }
    
    @Override
    protected int104 newInstance(BigInteger value) {
        return new int104(value);
    }
    
    // Constructors
    public int104(long l) { super(l); }
    public int104(int[] ints) { super(ints); }
    public int104(BigInteger b) { super(b); }
    public int104(String s) { super(s, DEFAULT_RADIX); }
    public int104(String s, int radix) { super(s, radix); }
    public int104(byte[] bytes) { super(bytes, MAX_VALUE, MIN_VALUE); }
}