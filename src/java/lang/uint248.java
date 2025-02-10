package java.lang;

import java.math.BigInteger;
import java.lang.types.Arrays;

/**
 * Represents an unsigned integer less than 2^248.
 *
 */
public final class uint248 extends uintType<uint248> {

    /**
     * Number of bits for this type: 248.
     */
    private static final int BITS = 248;

    /**
     * Number of 32-bit words needed: 8.
     */
    private static final int MAX_WIDTH = 8;

    public static int bitSize() {
        return BITS;
    }

    /**
     * The maximum representable value of uint248 (2^248 - 1).
     */
    public static final uint248 MAX_VALUE =
        new uint248(Arrays.maxValue(MAX_WIDTH));

    public static final uint248 ZERO = new uint248(Arrays.ZERO);
    public static final uint248 ONE  = new uint248(Arrays.ONE);
    public static final uint248 TWO  = new uint248(Arrays.TWO);

    // Constructors...
    public uint248(int[] ints)              { super(ints);           }
    public uint248(byte[] bytes)            { super(bytes, MAX_VALUE);}
    public uint248(String s)                { super(s, 10);          }
    public uint248(String s, int radix)     { super(s, radix);       }
    public uint248(long v)                  { super(v);              }
    public uint248(BigInteger b)            { super(b);              }
    public uint248(uintType<?> other)       { super(other);          }

    @Override
    public uint248 getMaxValue() {
        return MAX_VALUE;
    }

    @Override
    protected int getMaxWidth() {
        return MAX_WIDTH;
    }

    @Override
    protected uint248 newInstance(int[] ints) {
        return new uint248(ints);
    }
}