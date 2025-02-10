package java.lang.contract;

import java.lang.address;
import java.lang.Override;
import java.lang.String;
import java.lang.System;
import java.lang.uint160;
import java.lang.uint256;
import java.lang.uint64;

/**
 * Represents a message with a sender and data.
 */
public class Message {

    // Sender of the message
    protected final address sender; 
    
    // Value of the message
    private final uint256 value;
    
    // Data contained in the message
    protected final byte[] data;    

    // Asset of the message
    protected final uint64 asset;
    
    // Signature of the method
    protected final byte[] sig;
    
    // Gas of the message
    protected final uint256 gas;

    // Singleton pattern
    private static Message message;
  
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
        this.sender = new address(new uint160(nativeGetSender()));
        this.value = new uint256(nativeGetValue());
        this.data = nativeGetData();
        this.asset = new uint64(nativeGetAsset());
        this.sig = nativeGetSig();
        this.gas = new uint256(nativeGetGas());
    }


    /**
     * Returns a new Message object.
     *
     * @return A new Message object.
     */
    public static Message getMessage() {
        if (message == null) {
            message = new Message();
        }
        return message;
    }

    /**
     * Returns the sender of the message.
     *
     * @return The sender of the message.
     */
    public final address getSender() {
        return sender;
    }

    /**
     * Returns the value of the message.
     *
     * @return The value of the message.
     */
    public final uint256 getValue() {
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

    /**
     * Returns the asset of the message.
     *
     * @return The asset of the message.
     */
    public final uint64 getAsset() {
        return asset;
    }

    /**
     * Returns the signature of the message.
     *
     * @return The signature of the message.
     */
    public final byte[] getSig() {
        return sig;
    }

    /**
     * Returns the gas of the message.
     *
     * @return The gas of the message.
     */
    public final uint256 getGas() {
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
        System.loadLibrary("java.lang.contract.Message"); // Load libmessage.so/message.dll
    }

}