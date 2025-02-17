package java.lang;

import java.math.BigInteger;
import java.lang.types.BytesArray;

/**
 * Represents an unsigned integer less than 2^120.
 *
 */
public final class uint120 extends uintType<uint120> {

    /**
     * Number of bits for this type: 120.
     */
    private static final int BITS = 120;

    /**
     * Number of 32-bit words needed: 4.
     */
    private static final int MAX_WIDTH = 4;

    public static int bitSize() {
        return BITS;
    }

    /**
     * The maximum representable value of uint120 (2^120 - 1).
     */
    public static final uint120 MAX_VALUE =
        new uint120(BytesArray.maxValue(MAX_WIDTH));

    public static final uint120 ZERO = new uint120(BytesArray.ZERO);
    public static final uint120 ONE  = new uint120(BytesArray.ONE);
    public static final uint120 TWO  = new uint120(BytesArray.TWO);

    // Constructors...
    public uint120(int[] ints)              { super(ints);           }
    public uint120(byte[] bytes)            { super(bytes, MAX_VALUE);}
    public uint120(String s)                { super(s, 10);          }
    public uint120(String s, int radix)     { super(s, radix);       }
    public uint120(long v)                  { super(v);              }
    public uint120(BigInteger b)            { super(b);              }
    public uint120(uintType<?> other)       { super(other);          }

    public static uint120 valueOf(byte[] value) {
        return new uint120(value);
    }

    @Override
    public uint120 getMaxValue() {
        return MAX_VALUE;
    }

    @Override
    protected int getMaxWidth() {
        return MAX_WIDTH;
    }

    @Override
    protected uint120 newInstance(int[] ints) {
        return new uint120(ints);
    }
}