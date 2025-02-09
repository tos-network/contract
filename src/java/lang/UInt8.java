package java.lang;
import java.math.BigInteger;
public final class UInt8 extends UIntType<UInt8> {
    static final int MAX_WIDTH = 1;  // 8 bits, stored in a single int

    public static final UInt8 MAX_VALUE = new UInt8(0xFF);
    public static final UInt8 ZERO = new UInt8(0);
    public static final UInt8 ONE = new UInt8(1);

    private final int value;  // Store the 8-bit value in a single int

    public UInt8(int value) {
        super(new int[] { value & 0xFF });
        if (value < 0 || value > 0xFF) {
            throw new IllegalArgumentException("Value out of range for UInt8");
        }
        this.value = value;
    }

    public UInt8(BigInteger value) {
        this(value.intValueExact());
    }

    @Override
    public UInt8 add(UInt8 other) {
        return new UInt8((this.value + other.value) & 0xFF);
    }

    @Override
    public UInt8 subtract(UInt8 other) {
        return new UInt8((this.value - other.value) & 0xFF);
    }

    @Override
    public UInt8 multiply(UInt8 other) {
        return new UInt8((this.value * other.value) & 0xFF);
    }

    @Override
    public UInt8 divide(UInt8 other) {
        if (other.value == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return new UInt8(this.value / other.value);
    }

    @Override
    public UInt8 mod(UInt8 other) {
        if (other.value == 0) {
            throw new ArithmeticException("Modulo by zero");
        }
        return new UInt8(this.value % other.value);
    }

    @Override
    public UInt8 and(UInt8 other) {
        return new UInt8(this.value & other.value);
    }

    @Override
    public UInt8 or(UInt8 other) {
        return new UInt8(this.value | other.value);
    }

    @Override
    public UInt8 xor(UInt8 other) {
        return new UInt8(this.value ^ other.value);
    }

    @Override
    public UInt8 not() {
        return new UInt8(~this.value & 0xFF);
    }

    @Override
    public UInt8 shiftRight(int places) {
        return new UInt8((value >>> places) & 0xFF);
    }

    @Override
    public UInt8 shiftLeft(int places) {
        return new UInt8((value << places) & 0xFF);
    }

    @Override
    public UInt8 setBit(int bit) {
        return new UInt8((value | (1 << bit)) & 0xFF);
    }

    @Override
    public UInt8 clearBit(int bit) {
        return new UInt8((value & ~(1 << bit)) & 0xFF);
    }

    @Override
    public UInt8 flipBit(int bit) {
        return new UInt8((value ^ (1 << bit)) & 0xFF);
    }

    @Override
    public UInt8 inc() {
        return new UInt8((value + 1) & 0xFF);
    }

    @Override
    public UInt8 dec() {
        return new UInt8((value - 1) & 0xFF);
    }

    @Override
    public UInt8 pow(int exp) {
        if (exp < 0) throw new ArithmeticException("Negative exponent");
        int result = 1;
        int base = value;
        while (exp > 0) {
            if ((exp & 1) == 1) result = (result * base) & 0xFF;
            base = (base * base) & 0xFF;
            exp >>= 1;
        }
        return new UInt8(result);
    }

    @Override
    public UInt8[] divmod(UInt8 other) {
        if (other.value == 0) throw new ArithmeticException("Division by zero");
        return new UInt8[] { divide(other), mod(other) };
    }

    @Override
    public UInt8 mulmod(UInt8 mul, UInt8 mod) {
        if (mod.value == 0) throw new ArithmeticException("Modulo by zero");
        return new UInt8((value * mul.value) % mod.value);
    }

    @Override
    public UInt8 addmod(UInt8 add, UInt8 mod) {
        if (mod.value == 0) throw new ArithmeticException("Modulo by zero");
        return new UInt8((value + add.value) % mod.value);
    }

    @Override
    public UIntType<UInt8> getMaxValue() {
        return MAX_VALUE;
    }
}