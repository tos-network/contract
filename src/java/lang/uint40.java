package java.lang;

import java.math.BigInteger;
import java.lang.types.Arrays;

/**
 * Represents an unsigned integer less than 2^40.
 *
 */
public final class uint40 extends uintType<uint40> {

    /**
     * Number of bits for this type: 40.
     */
    private static final int BITS = 40;

    /**
     * Number of 32-bit words needed: 2.
     */
    private static final int MAX_WIDTH = 2;

    public static int bitSize() {
        return BITS;
    }

    /**
     * The maximum representable value of uint40 (2^40 - 1).
     */
    public static final uint40 MAX_VALUE =
        new uint40(Arrays.maxValue(MAX_WIDTH));

    public static final uint40 ZERO = new uint40(Arrays.ZERO);
    public static final uint40 ONE  = new uint40(Arrays.ONE);
    public static final uint40 TWO  = new uint40(Arrays.TWO);

    // Constructors...
    public uint40(int[] ints)              { super(ints);           }
    public uint40(byte[] bytes)            { super(bytes, MAX_VALUE);}
    public uint40(String s)                { super(s, 10);          }
    public uint40(String s, int radix)     { super(s, radix);       }
    public uint40(long v)                  { super(v);              }
    public uint40(BigInteger b)            { super(b);              }
    public uint40(uintType<?> other)       { super(other);          }

    @Override
    public uint40 getMaxValue() {
        return MAX_VALUE;
    }

    @Override
    protected int getMaxWidth() {
        return MAX_WIDTH;
    }

    @Override
    protected uint40 newInstance(int[] ints) {
        return new uint40(ints);
    }
}