package java.lang;
import java.math.BigInteger;
public final class uint8 extends uintType<uint8> {
    static final int MAX_WIDTH = 1;  // 8 bits, stored in a single int

    public static final uint8 MAX_VALUE = new uint8(0xFF);
    public static final uint8 ZERO = new uint8(0);
    public static final uint8 ONE = new uint8(1);

    private final int value;  // Store the 8-bit value in a single int

    public uint8(int value) {
        super(new int[] { value & 0xFF });
        if (value < 0 || value > 0xFF) {
            throw new IllegalArgumentException("Value out of range for uint8");
        }
        this.value = value;
    }

    public uint8(BigInteger value) {
        this(value.intValueExact());
    }

    @Override
    public uint8 add(uint8 other) {
        return new uint8((this.value + other.value) & 0xFF);
    }

    @Override
    public uint8 subtract(uint8 other) {
        return new uint8((this.value - other.value) & 0xFF);
    }

    @Override
    public uint8 multiply(uint8 other) {
        return new uint8((this.value * other.value) & 0xFF);
    }

    @Override
    public uint8 divide(uint8 other) {
        if (other.value == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return new uint8(this.value / other.value);
    }

    @Override
    public uint8 mod(uint8 other) {
        if (other.value == 0) {
            throw new ArithmeticException("Modulo by zero");
        }
        return new uint8(this.value % other.value);
    }

    @Override
    public uint8 and(uint8 other) {
        return new uint8(this.value & other.value);
    }

    @Override
    public uint8 or(uint8 other) {
        return new uint8(this.value | other.value);
    }

    @Override
    public uint8 xor(uint8 other) {
        return new uint8(this.value ^ other.value);
    }

    @Override
    public uint8 not() {
        return new uint8(~this.value & 0xFF);
    }

    @Override
    public uint8 shiftRight(int places) {
        return new uint8((value >>> places) & 0xFF);
    }

    @Override
    public uint8 shiftLeft(int places) {
        return new uint8((value << places) & 0xFF);
    }

    @Override
    public uint8 setBit(int bit) {
        return new uint8((value | (1 << bit)) & 0xFF);
    }

    @Override
    public uint8 clearBit(int bit) {
        return new uint8((value & ~(1 << bit)) & 0xFF);
    }

    @Override
    public uint8 flipBit(int bit) {
        return new uint8((value ^ (1 << bit)) & 0xFF);
    }

    @Override
    public uint8 inc() {
        return new uint8((value + 1) & 0xFF);
    }

    @Override
    public uint8 dec() {
        return new uint8((value - 1) & 0xFF);
    }

    @Override
    public uint8 pow(int exp) {
        if (exp < 0) throw new ArithmeticException("Negative exponent");
        int result = 1;
        int base = value;
        while (exp > 0) {
            if ((exp & 1) == 1) result = (result * base) & 0xFF;
            base = (base * base) & 0xFF;
            exp >>= 1;
        }
        return new uint8(result);
    }

    @Override
    public uint8[] divmod(uint8 other) {
        if (other.value == 0) throw new ArithmeticException("Division by zero");
        return new uint8[] { divide(other), mod(other) };
    }

    @Override
    public uint8 mulmod(uint8 mul, uint8 mod) {
        if (mod.value == 0) throw new ArithmeticException("Modulo by zero");
        return new uint8((value * mul.value) % mod.value);
    }

    @Override
    public uint8 addmod(uint8 add, uint8 mod) {
        if (mod.value == 0) throw new ArithmeticException("Modulo by zero");
        return new uint8((value + add.value) % mod.value);
    }

    @Override
    public uintType<uint8> getMaxValue() {
        return MAX_VALUE;
    }
}