package java.lang;

import java.math.BigInteger;
import java.lang.types.BytesArray;

/**
 * Represents an unsigned integer less than 2^72.
 *
 */
public final class uint72 extends uintType<uint72> {

    /**
     * Number of bits for this type: 72.
     */
    private static final int BITS = 72;

    /**
     * Number of 32-bit words needed: 3.
     */
    private static final int MAX_WIDTH = 3;

    public static int bitSize() {
        return BITS;
    }

    /**
     * The maximum representable value of uint72 (2^72 - 1).
     */
    public static final uint72 MAX_VALUE =
        new uint72(BytesArray.maxValue(MAX_WIDTH));

    public static final uint72 ZERO = new uint72(BytesArray.ZERO);
    public static final uint72 ONE  = new uint72(BytesArray.ONE);
    public static final uint72 TWO  = new uint72(BytesArray.TWO);

    // Constructors...
    public uint72(int[] ints)              { super(ints);           }
    public uint72(byte[] bytes)            { super(bytes, MAX_VALUE);}
    public uint72(String s)                { super(s, 10);          }
    public uint72(String s, int radix)     { super(s, radix);       }
    public uint72(long v)                  { super(v);              }
    public uint72(BigInteger b)            { super(b);              }
    public uint72(uintType<?> other)       { super(other);          }

    public static uint72 valueOf(byte[] value) {
        return new uint72(value);
    }

    @Override
    public uint72 getMaxValue() {
        return MAX_VALUE;
    }

    @Override
    protected int getMaxWidth() {
        return MAX_WIDTH;
    }

    @Override
    protected uint72 newInstance(int[] ints) {
        return new uint72(ints);
    }
}