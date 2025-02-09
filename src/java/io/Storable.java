package java.io;

/**
 * Represents an object that can be stored and loaded.
 */
public interface Storable {

    /**
     * The slot of the object is not set.
     */
    public static final int NO_SLOT = -1;

    /**
     * Converts the object to a byte array.
     *
     * @return The byte array representation of the object.
     */
    void fromByteArray(byte[] data);

    /**
     * Converts the object to a byte array.
     *
     * @return The byte array representation of the object.
     */
    byte[] toByteArray();

    /**
     * Sets the slot of the object.
     *
     * @param slot The slot to set.
     */
    void setSlot(int slot);

    /**
     * Gets the slot of the object.
     *
     * @return The slot of the object.
     */
    int getSlot();

    /**
     * Saves the object to the storage.
     *
     * @return true if the object was saved successfully, false otherwise.
     */
    boolean save();
   
    /**
     * Loads the object from the storage.
     *
     * @return true if the object was loaded successfully, false otherwise.
     */
    boolean load();

    static <T extends Storable> T newInstanceFromByteArray(byte[] data) {
        try {
            T obj = (T) Class.forName(new String(data, 0, 32)).newInstance();
            obj.fromByteArray(data);
            return obj;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}