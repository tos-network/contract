package java.lang;

import java.math.BigInteger;
import java.lang.types.Arrays;

/**
 * Represents an unsigned integer less than 2^216.
 *
 */
public final class uint216 extends uintType<uint216> {

    /**
     * Number of bits for this type: 216.
     */
    private static final int BITS = 216;

    /**
     * Number of 32-bit words needed: 7.
     */
    private static final int MAX_WIDTH = 7;

    public static int bitSize() {
        return BITS;
    }

    /**
     * The maximum representable value of uint216 (2^216 - 1).
     */
    public static final uint216 MAX_VALUE =
        new uint216(Arrays.maxValue(MAX_WIDTH));

    public static final uint216 ZERO = new uint216(Arrays.ZERO);
    public static final uint216 ONE  = new uint216(Arrays.ONE);
    public static final uint216 TWO  = new uint216(Arrays.TWO);

    // Constructors...
    public uint216(int[] ints)              { super(ints);           }
    public uint216(byte[] bytes)            { super(bytes, MAX_VALUE);}
    public uint216(String s)                { super(s, 10);          }
    public uint216(String s, int radix)     { super(s, radix);       }
    public uint216(long v)                  { super(v);              }
    public uint216(BigInteger b)            { super(b);              }
    public uint216(uintType<?> other)       { super(other);          }

    @Override
    public uint216 getMaxValue() {
        return MAX_VALUE;
    }

    @Override
    protected int getMaxWidth() {
        return MAX_WIDTH;
    }

    @Override
    protected uint216 newInstance(int[] ints) {
        return new uint216(ints);
    }
}