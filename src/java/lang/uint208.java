package java.lang;

import java.math.BigInteger;
import java.lang.types.Arrays;

/**
 * Represents an unsigned integer less than 2^208.
 *
 */
public final class uint208 extends uintType<uint208> {

    /**
     * Number of bits for this type: 208.
     */
    private static final int BITS = 208;

    /**
     * Number of 32-bit words needed: 7.
     */
    private static final int MAX_WIDTH = 7;

    public static int bitSize() {
        return BITS;
    }

    /**
     * The maximum representable value of uint208 (2^208 - 1).
     */
    public static final uint208 MAX_VALUE =
        new uint208(Arrays.maxValue(MAX_WIDTH));

    public static final uint208 ZERO = new uint208(Arrays.ZERO);
    public static final uint208 ONE  = new uint208(Arrays.ONE);
    public static final uint208 TWO  = new uint208(Arrays.TWO);

    // Constructors...
    public uint208(int[] ints)              { super(ints);           }
    public uint208(byte[] bytes)            { super(bytes, MAX_VALUE);}
    public uint208(String s)                { super(s, 10);          }
    public uint208(String s, int radix)     { super(s, radix);       }
    public uint208(long v)                  { super(v);              }
    public uint208(BigInteger b)            { super(b);              }
    public uint208(uintType<?> other)       { super(other);          }

    @Override
    public uint208 getMaxValue() {
        return MAX_VALUE;
    }

    @Override
    protected int getMaxWidth() {
        return MAX_WIDTH;
    }

    @Override
    protected uint208 newInstance(int[] ints) {
        return new uint208(ints);
    }
}