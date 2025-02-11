package java.lang;

import java.math.BigInteger;

public final class int200 extends intType<int200> {
    public static final int MAX_WIDTH = 7; // 200 bits = 7 * 32
    /**
     * Number of bits for this type: 200.
     */
    public static final int BITS = 200;
    
    /**
     * The maximum value for int200 (2^(200-1) - 1)
     */
    public static final int200 MAX_VALUE = 
        new int200(BigInteger.valueOf(2).pow(200 - 1).subtract(BigInteger.ONE));
    
    /**
     * The minimum value for int200 (-2^(200-1))
     */
    public static final int200 MIN_VALUE = 
        new int200(BigInteger.valueOf(2).pow(200 - 1).negate());
    
    /**
     * Common constants
     */
    public static final int200 ZERO = new int200(0);
    public static final int200 ONE = new int200(1);
    public static final int200 MINUS_ONE = new int200(-1);
    
    @Override
    public int bitSize() {
        return BITS;
    }
    
    @Override
    protected int getMaxWidth() {
        return MAX_WIDTH;
    }
    
    @Override
    public int200 getMaxValue() {
        return MAX_VALUE;
    }
    
    @Override
    public int200 getMinValue() {
        return MIN_VALUE;
    }
    
    @Override
    protected int200 newInstance(BigInteger value) {
        return new int200(value);
    }
    
    // Constructors
    public int200(long l) { super(l); }
    public int200(int[] ints) { super(ints); }
    public int200(BigInteger b) { super(b); }
    public int200(String s) { super(s, DEFAULT_RADIX); }
    public int200(String s, int radix) { super(s, radix); }
    public int200(byte[] bytes) { super(bytes, MAX_VALUE, MIN_VALUE); }
}