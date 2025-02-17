package java.lang;

import java.math.BigInteger;
import java.lang.types.BytesArray;

/**
 * Represents an unsigned integer less than 2^192.
 *
 */
public final class uint192 extends uintType<uint192> {

    /**
     * Number of bits for this type: 192.
     */
    private static final int BITS = 192;

    /**
     * Number of 32-bit words needed: 6.
     */
    private static final int MAX_WIDTH = 6;

    public static int bitSize() {
        return BITS;
    }

    /**
     * The maximum representable value of uint192 (2^192 - 1).
     */
    public static final uint192 MAX_VALUE =
        new uint192(BytesArray.maxValue(MAX_WIDTH));

    public static final uint192 ZERO = new uint192(BytesArray.ZERO);
    public static final uint192 ONE  = new uint192(BytesArray.ONE);
    public static final uint192 TWO  = new uint192(BytesArray.TWO);

    // Constructors...
    public uint192(int[] ints)              { super(ints);           }
    public uint192(byte[] bytes)            { super(bytes, MAX_VALUE);}
    public uint192(String s)                { super(s, 10);          }
    public uint192(String s, int radix)     { super(s, radix);       }
    public uint192(long v)                  { super(v);              }
    public uint192(BigInteger b)            { super(b);              }
    public uint192(uintType<?> other)       { super(other);          }

    public static uint192 valueOf(byte[] value) {
        return new uint192(value);
    }

    @Override
    public uint192 getMaxValue() {
        return MAX_VALUE;
    }

    @Override
    protected int getMaxWidth() {
        return MAX_WIDTH;
    }

    @Override
    protected uint192 newInstance(int[] ints) {
        return new uint192(ints);
    }
}