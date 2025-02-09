package java.lang;

import java.lang.bytes.Hex;
import java.lang.bytes.Type;
import java.math.BigInteger;
import java.io.Storable;

/**
 * Address type, which by default is equivalent to uint160 
 * which follows the Ethereum specification.
 */
public class Address implements Type<String>, Storable {

    public static final String TYPE_NAME = "address";
    public static final int DEFAULT_LENGTH = 160;
    public static final Address DEFAULT = new Address(BigInteger.ZERO);
    public static final Address ZERO_ADDRESS = new Address(BigInteger.ZERO);

    private final UInt160 value;

    public Address(UInt160 value) {
        this.value = value;
    }

    public Address(BigInteger value) {
        this(DEFAULT_LENGTH, value);
    }

    public Address(int bitSize, BigInteger value) {
        this(new UInt160(value));
    }

    public Address(String hexValue) {
        this(DEFAULT_LENGTH, hexValue);
    }

    public Address(int bitSize, String hexValue) {
        this(bitSize, Hex.toBigInt(hexValue));
    }

    public UInt160 toUint() {
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

        Address address = (Address) o;

        return value != null ? value.equals(address.value) : address.value == null;
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
