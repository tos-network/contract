package java.lang;

import java.math.BigInteger;
import java.lang.types.Arrays;

/**
 * Represents an unsigned integer less than 2^112.
 *
 */
public final class uint112 extends uintType<uint112> {

    /**
     * Number of bits for this type: 112.
     */
    private static final int BITS = 112;

    /**
     * Number of 32-bit words needed: 4.
     */
    private static final int MAX_WIDTH = 4;

    public static int bitSize() {
        return BITS;
    }

    /**
     * The maximum representable value of uint112 (2^112 - 1).
     */
    public static final uint112 MAX_VALUE =
        new uint112(Arrays.maxValue(MAX_WIDTH));

    public static final uint112 ZERO = new uint112(Arrays.ZERO);
    public static final uint112 ONE  = new uint112(Arrays.ONE);
    public static final uint112 TWO  = new uint112(Arrays.TWO);

    // Constructors...
    public uint112(int[] ints)              { super(ints);           }
    public uint112(byte[] bytes)            { super(bytes, MAX_VALUE);}
    public uint112(String s)                { super(s, 10);          }
    public uint112(String s, int radix)     { super(s, radix);       }
    public uint112(long v)                  { super(v);              }
    public uint112(BigInteger b)            { super(b);              }
    public uint112(uintType<?> other)       { super(other);          }

    @Override
    public uint112 getMaxValue() {
        return MAX_VALUE;
    }

    @Override
    protected int getMaxWidth() {
        return MAX_WIDTH;
    }

    @Override
    protected uint112 newInstance(int[] ints) {
        return new uint112(ints);
    }
}