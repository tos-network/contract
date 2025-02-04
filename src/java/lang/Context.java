package java.lang;

// SPDX-License-Identifier: MIT

/**
 * Provides information about the current execution context, including the
 * sender of the transaction and its data.
 *
 * This contract is only required for intermediate, library-like contracts.
 */
public abstract class Context {
    protected final Message msg = new Message();

    protected Address _msgSender() {
        return msg.sender;
    }

    protected byte[] _msgData() {
        return msg.data;
    }

    protected UInt256 _contextSuffixLength() {
        return UInt256.ZERO;
    }

    public static void revert(RuntimeException re) {
        throw new RevertException(re.getMessage()); 
    }

    public static void emit(EventLog el) {
        System.out.println(el.getMessage());
    }
}
