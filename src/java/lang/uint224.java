package java.lang;

import java.math.BigInteger;
import java.lang.types.Arrays;

/**
 * Represents an unsigned integer less than 2^224.
 *
 */
public final class uint224 extends uintType<uint224> {

    /**
     * Number of bits for this type: 224.
     */
    private static final int BITS = 224;

    /**
     * Number of 32-bit words needed: 7.
     */
    private static final int MAX_WIDTH = 7;

    public static int bitSize() {
        return BITS;
    }

    /**
     * The maximum representable value of uint224 (2^224 - 1).
     */
    public static final uint224 MAX_VALUE =
        new uint224(Arrays.maxValue(MAX_WIDTH));

    public static final uint224 ZERO = new uint224(Arrays.ZERO);
    public static final uint224 ONE  = new uint224(Arrays.ONE);
    public static final uint224 TWO  = new uint224(Arrays.TWO);

    // Constructors...
    public uint224(int[] ints)              { super(ints);           }
    public uint224(byte[] bytes)            { super(bytes, MAX_VALUE);}
    public uint224(String s)                { super(s, 10);          }
    public uint224(String s, int radix)     { super(s, radix);       }
    public uint224(long v)                  { super(v);              }
    public uint224(BigInteger b)            { super(b);              }
    public uint224(uintType<?> other)       { super(other);          }

    @Override
    public uint224 getMaxValue() {
        return MAX_VALUE;
    }

    @Override
    protected int getMaxWidth() {
        return MAX_WIDTH;
    }

    @Override
    protected uint224 newInstance(int[] ints) {
        return new uint224(ints);
    }
}