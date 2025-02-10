package java.lang;

import java.math.BigInteger;
import java.lang.types.Arrays;

/**
 * Represents an unsigned integer less than 2^48.
 *
 */
public final class uint48 extends uintType<uint48> {

    /**
     * Number of bits for this type: 48.
     */
    private static final int BITS = 48;

    /**
     * Number of 32-bit words needed: 2.
     */
    private static final int MAX_WIDTH = 2;

    public static int bitSize() {
        return BITS;
    }

    /**
     * The maximum representable value of uint48 (2^48 - 1).
     */
    public static final uint48 MAX_VALUE =
        new uint48(Arrays.maxValue(MAX_WIDTH));

    public static final uint48 ZERO = new uint48(Arrays.ZERO);
    public static final uint48 ONE  = new uint48(Arrays.ONE);
    public static final uint48 TWO  = new uint48(Arrays.TWO);

    // Constructors...
    public uint48(int[] ints)              { super(ints);           }
    public uint48(byte[] bytes)            { super(bytes, MAX_VALUE);}
    public uint48(String s)                { super(s, 10);          }
    public uint48(String s, int radix)     { super(s, radix);       }
    public uint48(long v)                  { super(v);              }
    public uint48(BigInteger b)            { super(b);              }
    public uint48(uintType<?> other)       { super(other);          }

    @Override
    public uint48 getMaxValue() {
        return MAX_VALUE;
    }

    @Override
    protected int getMaxWidth() {
        return MAX_WIDTH;
    }

    @Override
    protected uint48 newInstance(int[] ints) {
        return new uint48(ints);
    }
}