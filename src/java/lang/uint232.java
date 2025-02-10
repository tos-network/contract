package java.lang;

import java.math.BigInteger;
import java.lang.types.Arrays;

/**
 * Represents an unsigned integer less than 2^232.
 *
 */
public final class uint232 extends uintType<uint232> {

    /**
     * Number of bits for this type: 232.
     */
    private static final int BITS = 232;

    /**
     * Number of 32-bit words needed: 8.
     */
    private static final int MAX_WIDTH = 8;

    public static int bitSize() {
        return BITS;
    }

    /**
     * The maximum representable value of uint232 (2^232 - 1).
     */
    public static final uint232 MAX_VALUE =
        new uint232(Arrays.maxValue(MAX_WIDTH));

    public static final uint232 ZERO = new uint232(Arrays.ZERO);
    public static final uint232 ONE  = new uint232(Arrays.ONE);
    public static final uint232 TWO  = new uint232(Arrays.TWO);

    // Constructors...
    public uint232(int[] ints)              { super(ints);           }
    public uint232(byte[] bytes)            { super(bytes, MAX_VALUE);}
    public uint232(String s)                { super(s, 10);          }
    public uint232(String s, int radix)     { super(s, radix);       }
    public uint232(long v)                  { super(v);              }
    public uint232(BigInteger b)            { super(b);              }
    public uint232(uintType<?> other)       { super(other);          }

    @Override
    public uint232 getMaxValue() {
        return MAX_VALUE;
    }

    @Override
    protected int getMaxWidth() {
        return MAX_WIDTH;
    }

    @Override
    protected uint232 newInstance(int[] ints) {
        return new uint232(ints);
    }
}