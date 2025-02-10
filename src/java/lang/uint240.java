package java.lang;

import java.math.BigInteger;
import java.lang.types.Arrays;

/**
 * Represents an unsigned integer less than 2^240.
 *
 */
public final class uint240 extends uintType<uint240> {

    /**
     * Number of bits for this type: 240.
     */
    private static final int BITS = 240;

    /**
     * Number of 32-bit words needed: 8.
     */
    private static final int MAX_WIDTH = 8;

    public static int bitSize() {
        return BITS;
    }

    /**
     * The maximum representable value of uint240 (2^240 - 1).
     */
    public static final uint240 MAX_VALUE =
        new uint240(Arrays.maxValue(MAX_WIDTH));

    public static final uint240 ZERO = new uint240(Arrays.ZERO);
    public static final uint240 ONE  = new uint240(Arrays.ONE);
    public static final uint240 TWO  = new uint240(Arrays.TWO);

    // Constructors...
    public uint240(int[] ints)              { super(ints);           }
    public uint240(byte[] bytes)            { super(bytes, MAX_VALUE);}
    public uint240(String s)                { super(s, 10);          }
    public uint240(String s, int radix)     { super(s, radix);       }
    public uint240(long v)                  { super(v);              }
    public uint240(BigInteger b)            { super(b);              }
    public uint240(uintType<?> other)       { super(other);          }

    @Override
    public uint240 getMaxValue() {
        return MAX_VALUE;
    }

    @Override
    protected int getMaxWidth() {
        return MAX_WIDTH;
    }

    @Override
    protected uint240 newInstance(int[] ints) {
        return new uint240(ints);
    }
}