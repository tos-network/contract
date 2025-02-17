package java.lang;

import java.math.BigInteger;
import java.lang.types.BytesArray;

/**
 * Represents an unsigned integer less than 2^16.
 *
 */
public final class uint16 extends uintType<uint16> {

    /**
     * Number of bits for this type: 16.
     */
    private static final int BITS = 16;

    /**
     * Number of 32-bit words needed: 1.
     */
    private static final int MAX_WIDTH = 1;

    public static int bitSize() {
        return BITS;
    }

    /**
     * The maximum representable value of uint16 (2^16 - 1).
     */
    public static final uint16 MAX_VALUE =
        new uint16(BytesArray.maxValue(MAX_WIDTH));

    public static final uint16 ZERO = new uint16(BytesArray.ZERO);
    public static final uint16 ONE  = new uint16(BytesArray.ONE);
    public static final uint16 TWO  = new uint16(BytesArray.TWO);

    // Constructors...
    public uint16(int[] ints)              { super(ints);           }
    public uint16(byte[] bytes)            { super(bytes, MAX_VALUE);}
    public uint16(String s)                { super(s, 10);          }
    public uint16(String s, int radix)     { super(s, radix);       }
    public uint16(long v)                  { super(v);              }
    public uint16(BigInteger b)            { super(b);              }
    public uint16(uintType<?> other)       { super(other);          }

    public static uint16 valueOf(byte[] value) {
        return new uint16(value);
    }

    @Override
    public uint16 getMaxValue() {
        return MAX_VALUE;
    }

    @Override
    protected int getMaxWidth() {
        return MAX_WIDTH;
    }

    @Override
    protected uint16 newInstance(int[] ints) {
        return new uint16(ints);
    }
}