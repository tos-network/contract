package java.lang;

import java.math.BigInteger;
import java.lang.types.BytesArray;

/**
 * Represents an unsigned integer less than 2^128.
 *
 */
public final class uint128 extends uintType<uint128> {

    /**
     * Number of bits for this type: 128.
     */
    private static final int BITS = 128;

    /**
     * Number of 32-bit words needed: 4.
     */
    private static final int MAX_WIDTH = 4;

    public static int bitSize() {
        return BITS;
    }

    /**
     * The maximum representable value of uint128 (2^128 - 1).
     */
    public static final uint128 MAX_VALUE =
        new uint128(BytesArray.maxValue(MAX_WIDTH));

    public static final uint128 ZERO = new uint128(BytesArray.ZERO);
    public static final uint128 ONE  = new uint128(BytesArray.ONE);
    public static final uint128 TWO  = new uint128(BytesArray.TWO);

    // Constructors...
    public uint128(int[] ints)              { super(ints);           }
    public uint128(byte[] bytes)            { super(bytes, MAX_VALUE);}
    public uint128(String s)                { super(s, 10);          }
    public uint128(String s, int radix)     { super(s, radix);       }
    public uint128(long v)                  { super(v);              }
    public uint128(BigInteger b)            { super(b);              }
    public uint128(uintType<?> other)       { super(other);          }

    public static uint128 valueOf(byte[] value) {
        return new uint128(value);
    }

    @Override
    public uint128 getMaxValue() {
        return MAX_VALUE;
    }

    @Override
    protected int getMaxWidth() {
        return MAX_WIDTH;
    }

    @Override
    protected uint128 newInstance(int[] ints) {
        return new uint128(ints);
    }
}