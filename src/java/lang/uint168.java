package java.lang;

import java.math.BigInteger;
import java.lang.types.Arrays;

/**
 * Represents an unsigned integer less than 2^168.
 *
 */
public final class uint168 extends uintType<uint168> {

    /**
     * Number of bits for this type: 168.
     */
    private static final int BITS = 168;

    /**
     * Number of 32-bit words needed: 6.
     */
    private static final int MAX_WIDTH = 6;

    public static int bitSize() {
        return BITS;
    }

    /**
     * The maximum representable value of uint168 (2^168 - 1).
     */
    public static final uint168 MAX_VALUE =
        new uint168(Arrays.maxValue(MAX_WIDTH));

    public static final uint168 ZERO = new uint168(Arrays.ZERO);
    public static final uint168 ONE  = new uint168(Arrays.ONE);
    public static final uint168 TWO  = new uint168(Arrays.TWO);

    // Constructors...
    public uint168(int[] ints)              { super(ints);           }
    public uint168(byte[] bytes)            { super(bytes, MAX_VALUE);}
    public uint168(String s)                { super(s, 10);          }
    public uint168(String s, int radix)     { super(s, radix);       }
    public uint168(long v)                  { super(v);              }
    public uint168(BigInteger b)            { super(b);              }
    public uint168(uintType<?> other)       { super(other);          }

    @Override
    public uint168 getMaxValue() {
        return MAX_VALUE;
    }

    @Override
    protected int getMaxWidth() {
        return MAX_WIDTH;
    }

    @Override
    protected uint168 newInstance(int[] ints) {
        return new uint168(ints);
    }
}