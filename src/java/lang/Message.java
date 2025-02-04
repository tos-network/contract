package java.lang;

/**
 * Represents a message with a sender and data.
 */
public class Message {
    protected final Address sender;  // Sender of the message
    private final UInt256 value;
    protected final byte[] data;     // Data contained in the message
    protected final UInt64 asset;
    protected final byte[] sig;
    protected final UInt256 gas;
  
    /**
     * Native method to get the sender address of the message.
     *
     * @return The sender's address as a byte array.
     */
    private native byte[] nativeGetSender();

    /**
     * Native method to get the value of the message.
     *
     * @return The value as a byte array.
     */
    private native byte[] nativeGetValue();

    /**
     * Native method to get the data of the message.
     *
     * @return The data as a byte array.
     */
    private native byte[] nativeGetData();

    /**
     * Native method to get the asset of the message.
     *
     * @return The asset as a byte array.
     */
    private native byte[] nativeGetAsset();

    /**
     * Native method to get the signature of the message.
     *
     * @return The signature as a byte array.
     */
    private native byte[] nativeGetSig();

    /**
     * Native method to get the gas of the message.
     *
     * @return The gas as a byte array.
     */
    private native byte[] nativeGetGas();

    /**
     * Constructs a new Message with the specified sender and data.
     *
     */
    public Message() {
        this.sender = new Address(new UInt160(nativeGetSender()));
        this.value = new UInt256(nativeGetValue());
        this.data = nativeGetData();
        this.asset = new UInt64(nativeGetAsset());
        this.sig = nativeGetSig();
        this.gas = new UInt256(nativeGetGas());
    }

    /**
     * Returns the sender of the message.
     *
     * @return The sender of the message.
     */
    public final Address getSender() {
        return sender;
    }

    public final UInt256 getValue() {
        return value;
    }
    
    /**
     * Returns the data contained in the message.
     *
     * @return The data contained in the message.
     */
    public byte[] getData() {
        return data;
    }

    public final UInt64 getAsset() {
        return asset;
    }

    public final byte[] getSig() {
        return sig;
    }

    public final UInt256 getGas() {
        return gas;
    }

    /**
     * Returns a string representation of the message.
     *
     * @return A string representation of the message.
     */
    @Override
    public String toString() {
        return "Message{" +
                "sender=" + sender +
                ", value=" + value +
                ", data=" + java.util.Arrays.toString(data) +
                ", asset=" + asset +
                ", sig=" + java.util.Arrays.toString(sig) +
                ", gas=" + gas +
                '}';
    }

    /**
     * Load the native library when the class is loaded.
     */
    static {
        System.loadLibrary("message"); // Load libmessage.so/message.dll
    }

}