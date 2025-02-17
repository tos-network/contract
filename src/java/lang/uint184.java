package java.lang;

import java.math.BigInteger;
import java.lang.types.BytesArray;

/**
 * Represents an unsigned integer less than 2^184.
 *
 */
public final class uint184 extends uintType<uint184> {

    /**
     * Number of bits for this type: 184.
     */
    private static final int BITS = 184;

    /**
     * Number of 32-bit words needed: 6.
     */
    private static final int MAX_WIDTH = 6;

    public static int bitSize() {
        return BITS;
    }

    /**
     * The maximum representable value of uint184 (2^184 - 1).
     */
    public static final uint184 MAX_VALUE =
        new uint184(BytesArray.maxValue(MAX_WIDTH));

    public static final uint184 ZERO = new uint184(BytesArray.ZERO);
    public static final uint184 ONE  = new uint184(BytesArray.ONE);
    public static final uint184 TWO  = new uint184(BytesArray.TWO);

    // Constructors...
    public uint184(int[] ints)              { super(ints);           }
    public uint184(byte[] bytes)            { super(bytes, MAX_VALUE);}
    public uint184(String s)                { super(s, 10);          }
    public uint184(String s, int radix)     { super(s, radix);       }
    public uint184(long v)                  { super(v);              }
    public uint184(BigInteger b)            { super(b);              }
    public uint184(uintType<?> other)       { super(other);          }

    public static uint184 valueOf(byte[] value) {
        return new uint184(value);
    }

    @Override
    public uint184 getMaxValue() {
        return MAX_VALUE;
    }

    @Override
    protected int getMaxWidth() {
        return MAX_WIDTH;
    }

    @Override
    protected uint184 newInstance(int[] ints) {
        return new uint184(ints);
    }
}