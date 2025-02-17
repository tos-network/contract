package java.lang;

import java.math.BigInteger;
import java.lang.types.BytesArray;

/**
 * Represents an unsigned integer less than 2^104.
 *
 */
public final class uint104 extends uintType<uint104> {

    /**
     * Number of bits for this type: 104.
     */
    private static final int BITS = 104;

    /**
     * Number of 32-bit words needed: 4.
     */
    private static final int MAX_WIDTH = 4;

    public static int bitSize() {
        return BITS;
    }

    /**
     * The maximum representable value of uint104 (2^104 - 1).
     */
    public static final uint104 MAX_VALUE =
        new uint104(BytesArray.maxValue(MAX_WIDTH));

    public static final uint104 ZERO = new uint104(BytesArray.ZERO);
    public static final uint104 ONE  = new uint104(BytesArray.ONE);
    public static final uint104 TWO  = new uint104(BytesArray.TWO);

    // Constructors...
    public uint104(int[] ints)              { super(ints);           }
    public uint104(byte[] bytes)            { super(bytes, MAX_VALUE);}
    public uint104(String s)                { super(s, 10);          }
    public uint104(String s, int radix)     { super(s, radix);       }
    public uint104(long v)                  { super(v);              }
    public uint104(BigInteger b)            { super(b);              }
    public uint104(uintType<?> other)       { super(other);          }

    public static uint104 valueOf(byte[] value) {
        return new uint104(value);
    }

    @Override
    public uint104 getMaxValue() {
        return MAX_VALUE;
    }

    @Override
    protected int getMaxWidth() {
        return MAX_WIDTH;
    }

    @Override
    protected uint104 newInstance(int[] ints) {
        return new uint104(ints);
    }
}