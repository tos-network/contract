package java.lang;

import java.math.BigInteger;
import java.lang.types.Arrays;

/**
 * Represents an unsigned integer less than 2^64.
 *
 */
public final class uint64 extends uintType<uint64> {

    /**
     * Number of bits for this type: 64.
     */
    private static final int BITS = 64;

    /**
     * Number of 32-bit words needed: 2.
     */
    private static final int MAX_WIDTH = 2;

    public static int bitSize() {
        return BITS;
    }

    /**
     * The maximum representable value of uint64 (2^64 - 1).
     */
    public static final uint64 MAX_VALUE =
        new uint64(Arrays.maxValue(MAX_WIDTH));

    public static final uint64 ZERO = new uint64(Arrays.ZERO);
    public static final uint64 ONE  = new uint64(Arrays.ONE);
    public static final uint64 TWO  = new uint64(Arrays.TWO);

    // Constructors...
    public uint64(int[] ints)              { super(ints);           }
    public uint64(byte[] bytes)            { super(bytes, MAX_VALUE);}
    public uint64(String s)                { super(s, 10);          }
    public uint64(String s, int radix)     { super(s, radix);       }
    public uint64(long v)                  { super(v);              }
    public uint64(BigInteger b)            { super(b);              }
    public uint64(uintType<?> other)       { super(other);          }

    @Override
    public uint64 getMaxValue() {
        return MAX_VALUE;
    }

    @Override
    protected int getMaxWidth() {
        return MAX_WIDTH;
    }

    @Override
    protected uint64 newInstance(int[] ints) {
        return new uint64(ints);
    }
}