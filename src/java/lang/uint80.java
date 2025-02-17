package java.lang;

import java.math.BigInteger;
import java.lang.types.BytesArray;

/**
 * Represents an unsigned integer less than 2^80.
 *
 */
public final class uint80 extends uintType<uint80> {

    /**
     * Number of bits for this type: 80.
     */
    private static final int BITS = 80;

    /**
     * Number of 32-bit words needed: 3.
     */
    private static final int MAX_WIDTH = 3;

    public static int bitSize() {
        return BITS;
    }

    /**
     * The maximum representable value of uint80 (2^80 - 1).
     */
    public static final uint80 MAX_VALUE =
        new uint80(BytesArray.maxValue(MAX_WIDTH));

    public static final uint80 ZERO = new uint80(BytesArray.ZERO);
    public static final uint80 ONE  = new uint80(BytesArray.ONE);
    public static final uint80 TWO  = new uint80(BytesArray.TWO);

    // Constructors...
    public uint80(int[] ints)              { super(ints);           }
    public uint80(byte[] bytes)            { super(bytes, MAX_VALUE);}
    public uint80(String s)                { super(s, 10);          }
    public uint80(String s, int radix)     { super(s, radix);       }
    public uint80(long v)                  { super(v);              }
    public uint80(BigInteger b)            { super(b);              }
    public uint80(uintType<?> other)       { super(other);          }

    public static uint80 valueOf(byte[] value) {
        return new uint80(value);
    }

    @Override
    public uint80 getMaxValue() {
        return MAX_VALUE;
    }

    @Override
    protected int getMaxWidth() {
        return MAX_WIDTH;
    }

    @Override
    protected uint80 newInstance(int[] ints) {
        return new uint80(ints);
    }
}