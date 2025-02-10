package java.lang;

import java.math.BigInteger;
import java.io.Storable;
import java.lang.types.Hex;
import java.lang.types.Type;

/**
 * Address type, which by default is equivalent to uint160 
 * which follows the Ethereum specification.
 */
public class address implements Type<String>, Storable {

    public static final String TYPE_NAME = "address";
    public static final int DEFAULT_LENGTH = 160;
    public static final address DEFAULT = new address(BigInteger.ZERO);
    public static final address ZERO_ADDRESS = new address(BigInteger.ZERO);

    private final uint160 value;

    public address(uint160 value) {
        this.value = value;
    }

    public address(BigInteger value) {
        this(DEFAULT_LENGTH, value);
    }

    public address(int bitSize, BigInteger value) {
        this(new uint160(value));
    }

    public address(String hexValue) {
        this(DEFAULT_LENGTH, hexValue);
    }

    public address(int bitSize, String hexValue) {
        this(bitSize, Hex.toBigInt(hexValue));
    }

    public uint160 toUint() {
        return value;
    }

     /**
   * {@code this == 0}
   */
    public final boolean isZeroAddress() {
        return value.isZero();
    }

    @Override
    public String getTypeAsString() {
        return TYPE_NAME;
    }

    @Override
    public String toString() {
        return Hex.toHexStringWithPrefixZeroPadded(value.toBigInteger(), DEFAULT_LENGTH >> 2);
    }

    @Override
    public String getValue() {
        return toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null ) {
            if (value == null || value.isZero()) {
                return true;
            }
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        address addr = (address) o;

        return value != null ? value.equals(addr.value) : addr.value == null;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    @Override
    public void fromByteArray(byte[] data) {
        value.fromByteArray(data);
    }

    @Override
    public byte[] toByteArray() {
        return value.toByteArray();
    }

    @Override
    public void setSlot(int slot) {
        value.setSlot(slot);    
    }

    @Override
    public int getSlot() {
        return value.getSlot();
    }
    
    @Override
    public boolean save() {
        return value.save();
    }

    @Override
    public boolean load() {
        return value.load();
    }
}
