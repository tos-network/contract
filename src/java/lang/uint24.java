package java.lang;

import java.math.BigInteger;
import java.lang.types.Arrays;

/**
 * Represents an unsigned integer less than 2^24.
 *
 */
public final class uint24 extends uintType<uint24> {

    /**
     * Number of bits for this type: 24.
     */
    private static final int BITS = 24;

    /**
     * Number of 32-bit words needed: 1.
     */
    private static final int MAX_WIDTH = 1;

    public static int bitSize() {
        return BITS;
    }

    /**
     * The maximum representable value of uint24 (2^24 - 1).
     */
    public static final uint24 MAX_VALUE =
        new uint24(Arrays.maxValue(MAX_WIDTH));

    public static final uint24 ZERO = new uint24(Arrays.ZERO);
    public static final uint24 ONE  = new uint24(Arrays.ONE);
    public static final uint24 TWO  = new uint24(Arrays.TWO);

    // Constructors...
    public uint24(int[] ints)              { super(ints);           }
    public uint24(byte[] bytes)            { super(bytes, MAX_VALUE);}
    public uint24(String s)                { super(s, 10);          }
    public uint24(String s, int radix)     { super(s, radix);       }
    public uint24(long v)                  { super(v);              }
    public uint24(BigInteger b)            { super(b);              }
    public uint24(uintType<?> other)       { super(other);          }

    @Override
    public uint24 getMaxValue() {
        return MAX_VALUE;
    }

    @Override
    protected int getMaxWidth() {
        return MAX_WIDTH;
    }

    @Override
    protected uint24 newInstance(int[] ints) {
        return new uint24(ints);
    }
}