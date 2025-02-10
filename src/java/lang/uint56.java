package java.lang;

import java.math.BigInteger;
import java.lang.types.Arrays;

/**
 * Represents an unsigned integer less than 2^56.
 *
 */
public final class uint56 extends uintType<uint56> {

    /**
     * Number of bits for this type: 56.
     */
    private static final int BITS = 56;

    /**
     * Number of 32-bit words needed: 2.
     */
    private static final int MAX_WIDTH = 2;

    public static int bitSize() {
        return BITS;
    }

    /**
     * The maximum representable value of uint56 (2^56 - 1).
     */
    public static final uint56 MAX_VALUE =
        new uint56(Arrays.maxValue(MAX_WIDTH));

    public static final uint56 ZERO = new uint56(Arrays.ZERO);
    public static final uint56 ONE  = new uint56(Arrays.ONE);
    public static final uint56 TWO  = new uint56(Arrays.TWO);

    // Constructors...
    public uint56(int[] ints)              { super(ints);           }
    public uint56(byte[] bytes)            { super(bytes, MAX_VALUE);}
    public uint56(String s)                { super(s, 10);          }
    public uint56(String s, int radix)     { super(s, radix);       }
    public uint56(long v)                  { super(v);              }
    public uint56(BigInteger b)            { super(b);              }
    public uint56(uintType<?> other)       { super(other);          }

    @Override
    public uint56 getMaxValue() {
        return MAX_VALUE;
    }

    @Override
    protected int getMaxWidth() {
        return MAX_WIDTH;
    }

    @Override
    protected uint56 newInstance(int[] ints) {
        return new uint56(ints);
    }
}