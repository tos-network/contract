package java.lang;

import java.math.BigInteger;
import java.lang.types.Arrays;

/**
 * Represents an unsigned integer less than 2^200.
 *
 */
public final class uint200 extends uintType<uint200> {

    /**
     * Number of bits for this type: 200.
     */
    private static final int BITS = 200;

    /**
     * Number of 32-bit words needed: 7.
     */
    private static final int MAX_WIDTH = 7;

    public static int bitSize() {
        return BITS;
    }

    /**
     * The maximum representable value of uint200 (2^200 - 1).
     */
    public static final uint200 MAX_VALUE =
        new uint200(Arrays.maxValue(MAX_WIDTH));

    public static final uint200 ZERO = new uint200(Arrays.ZERO);
    public static final uint200 ONE  = new uint200(Arrays.ONE);
    public static final uint200 TWO  = new uint200(Arrays.TWO);

    // Constructors...
    public uint200(int[] ints)              { super(ints);           }
    public uint200(byte[] bytes)            { super(bytes, MAX_VALUE);}
    public uint200(String s)                { super(s, 10);          }
    public uint200(String s, int radix)     { super(s, radix);       }
    public uint200(long v)                  { super(v);              }
    public uint200(BigInteger b)            { super(b);              }
    public uint200(uintType<?> other)       { super(other);          }

    @Override
    public uint200 getMaxValue() {
        return MAX_VALUE;
    }

    @Override
    protected int getMaxWidth() {
        return MAX_WIDTH;
    }

    @Override
    protected uint200 newInstance(int[] ints) {
        return new uint200(ints);
    }
}