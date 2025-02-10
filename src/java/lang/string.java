package java.lang;
import java.io.Storable;
import java.nio.charset.StandardCharsets;
import java.lang.contract.Storage;

public class string implements Storable {
    private int slot;
    private String value;

    /**
     * Returns a string representation of the specified value.
     *
     * @param str The value to convert to a string.
     * @return A string representation of the specified value.
     */
    public static string valueOf(String str) {
        return new string(str);
    }

    /**
     * Constructs a new string with the specified initial value.
     *
     * @param initialValue The initial value of the string.
     */
    public string(String initialValue) {
        this.slot = Storable.NO_SLOT;
        this.value = initialValue;
    }

    @Override
    public void setSlot(int slot) {
        this.slot = slot;
    }

    @Override
    public int getSlot() {
        return slot;
    }

    @Override
    public boolean save() {
        // if the slot is not set, return true
        if (slot == Storable.NO_SLOT) {
            return true;
        }
        // save the data to the storage
        byte[] data = value == null ? new byte[0] : value.getBytes(StandardCharsets.UTF_8);
        Storage.getStorage().SetStorageFixedValue(slot, data);
        return true;
    }

    @Override
    public boolean load() {
        // if the slot is not set, return true
        if (slot == Storable.NO_SLOT) {
            return true;
        }
        // get the data from the storage
        System.out.println("Loading string from slot: " + slot);
        byte[] data = Storage.getStorage().GetStorageFixedValue(slot);
        if (data == null) {
            data = new byte[0];
        }
        this.value = new String(data, StandardCharsets.UTF_8);
        return true;
    }

    public String get() {
        return value;
    }

    @Override
    public byte[] toByteArray() {
        return value.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public void fromByteArray(byte[] data) {
        value = new String(data, StandardCharsets.UTF_8);
    }
}
