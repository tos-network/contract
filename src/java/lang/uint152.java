package java.lang;

import java.math.BigInteger;
import java.lang.types.Arrays;

/**
 * Represents an unsigned integer less than 2^152.
 *
 */
public final class uint152 extends uintType<uint152> {

    /**
     * Number of bits for this type: 152.
     */
    private static final int BITS = 152;

    /**
     * Number of 32-bit words needed: 5.
     */
    private static final int MAX_WIDTH = 5;

    public static int bitSize() {
        return BITS;
    }

    /**
     * The maximum representable value of uint152 (2^152 - 1).
     */
    public static final uint152 MAX_VALUE =
        new uint152(Arrays.maxValue(MAX_WIDTH));

    public static final uint152 ZERO = new uint152(Arrays.ZERO);
    public static final uint152 ONE  = new uint152(Arrays.ONE);
    public static final uint152 TWO  = new uint152(Arrays.TWO);

    // Constructors...
    public uint152(int[] ints)              { super(ints);           }
    public uint152(byte[] bytes)            { super(bytes, MAX_VALUE);}
    public uint152(String s)                { super(s, 10);          }
    public uint152(String s, int radix)     { super(s, radix);       }
    public uint152(long v)                  { super(v);              }
    public uint152(BigInteger b)            { super(b);              }
    public uint152(uintType<?> other)       { super(other);          }

    @Override
    public uint152 getMaxValue() {
        return MAX_VALUE;
    }

    @Override
    protected int getMaxWidth() {
        return MAX_WIDTH;
    }

    @Override
    protected uint152 newInstance(int[] ints) {
        return new uint152(ints);
    }
}