package java.lang;

import java.math.BigInteger;
import java.lang.types.Arrays;

/**
 * Represents an unsigned integer less than 2^32.
 *
 */
public final class uint32 extends uintType<uint32> {

    /**
     * Number of bits for this type: 32.
     */
    private static final int BITS = 32;

    /**
     * Number of 32-bit words needed: 1.
     */
    private static final int MAX_WIDTH = 1;

    public static int bitSize() {
        return BITS;
    }

    /**
     * The maximum representable value of uint32 (2^32 - 1).
     */
    public static final uint32 MAX_VALUE =
        new uint32(Arrays.maxValue(MAX_WIDTH));

    public static final uint32 ZERO = new uint32(Arrays.ZERO);
    public static final uint32 ONE  = new uint32(Arrays.ONE);
    public static final uint32 TWO  = new uint32(Arrays.TWO);

    // Constructors...
    public uint32(int[] ints)              { super(ints);           }
    public uint32(byte[] bytes)            { super(bytes, MAX_VALUE);}
    public uint32(String s)                { super(s, 10);          }
    public uint32(String s, int radix)     { super(s, radix);       }
    public uint32(long v)                  { super(v);              }
    public uint32(BigInteger b)            { super(b);              }
    public uint32(uintType<?> other)       { super(other);          }

    @Override
    public uint32 getMaxValue() {
        return MAX_VALUE;
    }

    @Override
    protected int getMaxWidth() {
        return MAX_WIDTH;
    }

    @Override
    protected uint32 newInstance(int[] ints) {
        return new uint32(ints);
    }
}