package java.lang;

import java.math.BigInteger;
import java.lang.types.BytesArray;

/**
 * Represents an unsigned integer less than 2^144.
 *
 */
public final class uint144 extends uintType<uint144> {

    /**
     * Number of bits for this type: 144.
     */
    private static final int BITS = 144;

    /**
     * Number of 32-bit words needed: 5.
     */
    private static final int MAX_WIDTH = 5;

    public static int bitSize() {
        return BITS;
    }

    /**
     * The maximum representable value of uint144 (2^144 - 1).
     */
    public static final uint144 MAX_VALUE =
        new uint144(BytesArray.maxValue(MAX_WIDTH));

    public static final uint144 ZERO = new uint144(BytesArray.ZERO);
    public static final uint144 ONE  = new uint144(BytesArray.ONE);
    public static final uint144 TWO  = new uint144(BytesArray.TWO);

    // Constructors...
    public uint144(int[] ints)              { super(ints);           }
    public uint144(byte[] bytes)            { super(bytes, MAX_VALUE);}
    public uint144(String s)                { super(s, 10);          }
    public uint144(String s, int radix)     { super(s, radix);       }
    public uint144(long v)                  { super(v);              }
    public uint144(BigInteger b)            { super(b);              }
    public uint144(uintType<?> other)       { super(other);          }

    public static uint144 valueOf(byte[] value) {
        return new uint144(value);
    }

    @Override
    public uint144 getMaxValue() {
        return MAX_VALUE;
    }

    @Override
    protected int getMaxWidth() {
        return MAX_WIDTH;
    }

    @Override
    protected uint144 newInstance(int[] ints) {
        return new uint144(ints);
    }
}