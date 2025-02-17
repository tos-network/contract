package java.lang;

import java.math.BigInteger;
import java.lang.types.BytesArray;

/**
 * Represents an unsigned integer less than 2^96.
 *
 */
public final class uint96 extends uintType<uint96> {

    /**
     * Number of bits for this type: 96.
     */
    private static final int BITS = 96;

    /**
     * Number of 32-bit words needed: 3.
     */
    private static final int MAX_WIDTH = 3;

    public static int bitSize() {
        return BITS;
    }

    /**
     * The maximum representable value of uint96 (2^96 - 1).
     */
    public static final uint96 MAX_VALUE =
        new uint96(BytesArray.maxValue(MAX_WIDTH));

    public static final uint96 ZERO = new uint96(BytesArray.ZERO);
    public static final uint96 ONE  = new uint96(BytesArray.ONE);
    public static final uint96 TWO  = new uint96(BytesArray.TWO);

    // Constructors...
    public uint96(int[] ints)              { super(ints);           }
    public uint96(byte[] bytes)            { super(bytes, MAX_VALUE);}
    public uint96(String s)                { super(s, 10);          }
    public uint96(String s, int radix)     { super(s, radix);       }
    public uint96(long v)                  { super(v);              }
    public uint96(BigInteger b)            { super(b);              }
    public uint96(uintType<?> other)       { super(other);          }

    public static uint96 valueOf(byte[] value) {
        return new uint96(value);
    }

    @Override
    public uint96 getMaxValue() {
        return MAX_VALUE;
    }

    @Override
    protected int getMaxWidth() {
        return MAX_WIDTH;
    }

    @Override
    protected uint96 newInstance(int[] ints) {
        return new uint96(ints);
    }
}