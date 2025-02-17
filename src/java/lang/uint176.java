package java.lang;

import java.math.BigInteger;
import java.lang.types.BytesArray;

/**
 * Represents an unsigned integer less than 2^176.
 *
 */
public final class uint176 extends uintType<uint176> {

    /**
     * Number of bits for this type: 176.
     */
    private static final int BITS = 176;

    /**
     * Number of 32-bit words needed: 6.
     */
    private static final int MAX_WIDTH = 6;

    public static int bitSize() {
        return BITS;
    }

    /**
     * The maximum representable value of uint176 (2^176 - 1).
     */
    public static final uint176 MAX_VALUE =
        new uint176(BytesArray.maxValue(MAX_WIDTH));

    public static final uint176 ZERO = new uint176(BytesArray.ZERO);
    public static final uint176 ONE  = new uint176(BytesArray.ONE);
    public static final uint176 TWO  = new uint176(BytesArray.TWO);

    // Constructors...
    public uint176(int[] ints)              { super(ints);           }
    public uint176(byte[] bytes)            { super(bytes, MAX_VALUE);}
    public uint176(String s)                { super(s, 10);          }
    public uint176(String s, int radix)     { super(s, radix);       }
    public uint176(long v)                  { super(v);              }
    public uint176(BigInteger b)            { super(b);              }
    public uint176(uintType<?> other)       { super(other);          }

    public static uint176 valueOf(byte[] value) {
        return new uint176(value);
    }

    @Override
    public uint176 getMaxValue() {
        return MAX_VALUE;
    }

    @Override
    protected int getMaxWidth() {
        return MAX_WIDTH;
    }

    @Override
    protected uint176 newInstance(int[] ints) {
        return new uint176(ints);
    }
}