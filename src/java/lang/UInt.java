package java.lang;

import java.math.BigInteger;
import java.lang.types.BytesArray;

/**
 * Represents an unsigned integer less than 2^256.
 *
 */
public final class uint extends uintType<uint> {

    /**
     * Number of bits for this type: 256.
     */
    private static final int BITS = 256;

    /**
     * Number of 32-bit words needed: 8.
     */
    private static final int MAX_WIDTH = 8;

    public static int bitSize() {
        return BITS;
    }

    /**
     * The maximum representable value of uint (2^256 - 1).
     */
    public static final uint MAX_VALUE =
        new uint(BytesArray.maxValue(MAX_WIDTH));

    public static final uint ZERO = new uint(BytesArray.ZERO);
    public static final uint ONE  = new uint(BytesArray.ONE);
    public static final uint TWO  = new uint(BytesArray.TWO);

    // Constructors...
    public uint(int[] ints)              { super(ints);           }
    public uint(byte[] bytes)            { super(bytes, MAX_VALUE);}
    public uint(String s)                { super(s, 10);          }
    public uint(String s, int radix)     { super(s, radix);       }
    public uint(long v)                  { super(v);              }
    public uint(BigInteger b)            { super(b);              }
    public uint(uintType<?> other)       { super(other);          }

    @Override
    public uint getMaxValue() {
        return MAX_VALUE;
    }

    @Override
    protected int getMaxWidth() {
        return MAX_WIDTH;
    }

    @Override
    protected uint newInstance(int[] ints) {
        return new uint(ints);
    }
}