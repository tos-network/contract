package java.lang;

import java.math.BigInteger;

public final class int72 extends intType<int72> {
    public static final int MAX_WIDTH = 3; // 72 bits = 3 * 32
    /**
     * Number of bits for this type: 72.
     */
    public static final int BITS = 72;
    
    /**
     * The maximum value for int72 (2^(72-1) - 1)
     */
    public static final int72 MAX_VALUE = 
        new int72(BigInteger.valueOf(2).pow(72 - 1).subtract(BigInteger.ONE));
    
    /**
     * The minimum value for int72 (-2^(72-1))
     */
    public static final int72 MIN_VALUE = 
        new int72(BigInteger.valueOf(2).pow(72 - 1).negate());
    
    /**
     * Common constants
     */
    public static final int72 ZERO = new int72(0);
    public static final int72 ONE = new int72(1);
    public static final int72 MINUS_ONE = new int72(-1);

    public static int72 valueOf(byte[] value) {
        return new int72(value);
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
    public int72 getMaxValue() {
        return MAX_VALUE;
    }
    
    @Override
    public int72 getMinValue() {
        return MIN_VALUE;
    }
    
    @Override
    protected int72 newInstance(BigInteger value) {
        return new int72(value);
    }
    
    // Constructors
    public int72(long l) { super(l); }
    public int72(int[] ints) { super(ints); }
    public int72(BigInteger b) { super(b); }
    public int72(String s) { super(s, DEFAULT_RADIX); }
    public int72(String s, int radix) { super(s, radix); }
    public int72(byte[] bytes) { super(bytes, MAX_VALUE, MIN_VALUE); }
}