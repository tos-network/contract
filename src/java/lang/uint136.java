package java.lang;

import java.math.BigInteger;
import java.lang.types.BytesArray;

/**
 * Represents an unsigned integer less than 2^136.
 *
 */
public final class uint136 extends uintType<uint136> {

    /**
     * Number of bits for this type: 136.
     */
    private static final int BITS = 136;

    /**
     * Number of 32-bit words needed: 5.
     */
    private static final int MAX_WIDTH = 5;

    public static int bitSize() {
        return BITS;
    }

    /**
     * The maximum representable value of uint136 (2^136 - 1).
     */
    public static final uint136 MAX_VALUE =
        new uint136(BytesArray.maxValue(MAX_WIDTH));

    public static final uint136 ZERO = new uint136(BytesArray.ZERO);
    public static final uint136 ONE  = new uint136(BytesArray.ONE);
    public static final uint136 TWO  = new uint136(BytesArray.TWO);

    // Constructors...
    public uint136(int[] ints)              { super(ints);           }
    public uint136(byte[] bytes)            { super(bytes, MAX_VALUE);}
    public uint136(String s)                { super(s, 10);          }
    public uint136(String s, int radix)     { super(s, radix);       }
    public uint136(long v)                  { super(v);              }
    public uint136(BigInteger b)            { super(b);              }
    public uint136(uintType<?> other)       { super(other);          }

    public static uint136 valueOf(byte[] value) {
        return new uint136(value);
    }

    @Override
    public uint136 getMaxValue() {
        return MAX_VALUE;
    }

    @Override
    protected int getMaxWidth() {
        return MAX_WIDTH;
    }

    @Override
    protected uint136 newInstance(int[] ints) {
        return new uint136(ints);
    }
}