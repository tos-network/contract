package java.lang;

import java.math.BigInteger;
import java.lang.types.BytesArray;

/**
 * Represents an unsigned integer less than 2^8.
 *
 */
public final class uint8 extends uintType<uint8> {

    /**
     * Number of bits for this type: 8.
     */
    private static final int BITS = 8;

    /**
     * Number of 32-bit words needed: 1.
     */
    private static final int MAX_WIDTH = 1;

    public static int bitSize() {
        return BITS;
    }

    /**
     * The maximum representable value of uint8 (2^8 - 1).
     */
    public static final uint8 MAX_VALUE =
        new uint8(BytesArray.maxValue(MAX_WIDTH));

    public static final uint8 ZERO = new uint8(BytesArray.ZERO);
    public static final uint8 ONE  = new uint8(BytesArray.ONE);
    public static final uint8 TWO  = new uint8(BytesArray.TWO);

    // Constructors...
    public uint8(int[] ints)              { super(ints);           }
    public uint8(byte[] bytes)            { super(bytes, MAX_VALUE);}
    public uint8(String s)                { super(s, 10);          }
    public uint8(String s, int radix)     { super(s, radix);       }
    public uint8(long v)                  { super(v);              }
    public uint8(BigInteger b)            { super(b);              }
    public uint8(uintType<?> other)       { super(other);          }

    public static uint8 valueOf(byte[] value) {
        return new uint8(value);
    }

    @Override
    public uint8 getMaxValue() {
        return MAX_VALUE;
    }

    @Override
    protected int getMaxWidth() {
        return MAX_WIDTH;
    }

    @Override
    protected uint8 newInstance(int[] ints) {
        return new uint8(ints);
    }
}