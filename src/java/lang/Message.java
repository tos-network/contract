package java.lang;

/**
 * Represents a message with a sender and data.
 */
public class Message {
    public Address sender;  // Sender of the message
    public byte[] data;     // Data contained in the message

    /**
     * Constructs a new Message with the specified sender and data.
     *
     * @param sender The sender of the message.
     * @param data   The data contained in the message.
     */
    public Message(Address sender, byte[] data) {
        this.sender = sender;
        this.data = data;
    }

    /**
     * Returns the sender of the message.
     *
     * @return The sender of the message.
     */
    public Address getSender() {
        return sender;
    }

    /**
     * Sets the sender of the message.
     *
     * @param sender The new sender of the message.
     */
    public void setSender(Address sender) {
        this.sender = sender;
    }

    /**
     * Returns the data contained in the message.
     *
     * @return The data contained in the message.
     */
    public byte[] getData() {
        return data;
    }

    /**
     * Sets the data contained in the message.
     *
     * @param data The new data to be contained in the message.
     */
    public void setData(byte[] data) {
        this.data = data;
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
                ", data=" + java.util.Arrays.toString(data) +
                '}';
    }
}