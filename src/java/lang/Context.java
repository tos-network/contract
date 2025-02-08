package java.lang;

// SPDX-License-Identifier: MIT

/**
 * Provides information about the current execution context, including the
 * sender of the transaction and its data.
 *
 * This contract is only required for intermediate, library-like contracts.
 */
public abstract class Context {
    // Message is a class that contains the sender of the transaction and its data.
    protected final Message msg = new Message();

    // Returns the sender of the transaction.
    protected Address _msgSender() {
        return msg.sender;
    }

    // Returns the data of the transaction.
    protected byte[] _msgData() {
        return msg.data;
    }

    // Returns the length of the context suffix.
    protected UInt256 _contextSuffixLength() {
        return UInt256.ZERO;
    }

    // Reverts the transaction with the given exception.
    public static void revert(RuntimeException re) {
        throw new RevertException(re.getMessage()); 
    }

    // Emits an event.  
    public static void emit(EventLog el) {
        System.out.println(el.getMessage());
    }
}
