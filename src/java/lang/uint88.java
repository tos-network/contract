package java.lang;

import java.math.BigInteger;
import java.lang.types.Arrays;

/**
 * Represents an unsigned integer less than 2^88.
 *
 */
public final class uint88 extends uintType<uint88> {

    /**
     * Number of bits for this type: 88.
     */
    private static final int BITS = 88;

    /**
     * Number of 32-bit words needed: 3.
     */
    private static final int MAX_WIDTH = 3;

    public static int bitSize() {
        return BITS;
    }

    /**
     * The maximum representable value of uint88 (2^88 - 1).
     */
    public static final uint88 MAX_VALUE =
        new uint88(Arrays.maxValue(MAX_WIDTH));

    public static final uint88 ZERO = new uint88(Arrays.ZERO);
    public static final uint88 ONE  = new uint88(Arrays.ONE);
    public static final uint88 TWO  = new uint88(Arrays.TWO);

    // Constructors...
    public uint88(int[] ints)              { super(ints);           }
    public uint88(byte[] bytes)            { super(bytes, MAX_VALUE);}
    public uint88(String s)                { super(s, 10);          }
    public uint88(String s, int radix)     { super(s, radix);       }
    public uint88(long v)                  { super(v);              }
    public uint88(BigInteger b)            { super(b);              }
    public uint88(uintType<?> other)       { super(other);          }

    @Override
    public uint88 getMaxValue() {
        return MAX_VALUE;
    }

    @Override
    protected int getMaxWidth() {
        return MAX_WIDTH;
    }

    @Override
    protected uint88 newInstance(int[] ints) {
        return new uint88(ints);
    }
}