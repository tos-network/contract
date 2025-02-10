package java.lang;

import java.math.BigInteger;
import java.lang.types.Arrays;

/**
 * Represents an unsigned integer less than 2^160.
 *
 */
public final class uint160 extends uintType<uint160> {

    /**
     * Number of bits for this type: 160.
     */
    private static final int BITS = 160;

    /**
     * Number of 32-bit words needed: 5.
     */
    private static final int MAX_WIDTH = 5;

    public static int bitSize() {
        return BITS;
    }

    /**
     * The maximum representable value of uint160 (2^160 - 1).
     */
    public static final uint160 MAX_VALUE =
        new uint160(Arrays.maxValue(MAX_WIDTH));

    public static final uint160 ZERO = new uint160(Arrays.ZERO);
    public static final uint160 ONE  = new uint160(Arrays.ONE);
    public static final uint160 TWO  = new uint160(Arrays.TWO);

    // Constructors...
    public uint160(int[] ints)              { super(ints);           }
    public uint160(byte[] bytes)            { super(bytes, MAX_VALUE);}
    public uint160(String s)                { super(s, 10);          }
    public uint160(String s, int radix)     { super(s, radix);       }
    public uint160(long v)                  { super(v);              }
    public uint160(BigInteger b)            { super(b);              }
    public uint160(uintType<?> other)       { super(other);          }

    @Override
    public uint160 getMaxValue() {
        return MAX_VALUE;
    }

    @Override
    protected int getMaxWidth() {
        return MAX_WIDTH;
    }

    @Override
    protected uint160 newInstance(int[] ints) {
        return new uint160(ints);
    }
}