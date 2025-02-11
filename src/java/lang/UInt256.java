package java.lang;

import java.math.BigInteger;
import java.lang.types.Arrays;

/**
 * Represents an unsigned integer less than 2^256.
 *
 */
public final class uint256 extends uintType<uint256> {

    /**
     * Number of bits for this type: 256.
     */
    public static final int BITS = 256;

    /**
     * Number of 32-bit words needed: 8.
     */
    public static final int MAX_WIDTH = 8;

    public static int bitSize() {
        return BITS;
    }

    /**
     * The maximum representable value of uint256 (2^256 - 1).
     */
    public static final uint256 MAX_VALUE =
        new uint256(Arrays.maxValue(MAX_WIDTH));

    public static final uint256 ZERO = new uint256(Arrays.ZERO);
    public static final uint256 ONE  = new uint256(Arrays.ONE);
    public static final uint256 TWO  = new uint256(Arrays.TWO);

    // Constructors...
    public uint256(int[] ints)              { super(ints);           }
    public uint256(byte[] bytes)            { super(bytes, MAX_VALUE);}
    public uint256(String s)                { super(s, 10);          }
    public uint256(String s, int radix)     { super(s, radix);       }
    public uint256(long v)                  { super(v);              }
    public uint256(BigInteger b)            { super(b);              }
    public uint256(uintType<?> other)       { super(other);          }

    @Override
    public uint256 getMaxValue() {
        return MAX_VALUE;
    }

    @Override
    protected int getMaxWidth() {
        return MAX_WIDTH;
    }

    @Override
    protected uint256 newInstance(int[] ints) {
        return new uint256(ints);
    }
}