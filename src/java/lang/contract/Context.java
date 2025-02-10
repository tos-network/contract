package java.lang.contract;

import java.lang.address;   
import java.lang.uint256;

// SPDX-License-Identifier: MIT

/**
 * Provides information about the current execution context, including the
 * sender of the transaction and its data.
 *
 * This contract is only required for intermediate, library-like contracts.
 */
public abstract class Context {

    // Message is a class that contains the sender of the transaction and its data.
    protected final Message msg = Message.getMessage();

    // Storage is a class that contains the storage of the contract.
    protected final Storage storage = Storage.getStorage();

    // Returns the sender of the transaction.
    protected final address _msgSender() {
        return msg.sender;
    }

    // Returns the data of the transaction.
    protected final byte[] _msgData() {
        return msg.data;
    }

    // Returns the length of the context suffix.
    protected final uint256 _contextSuffixLength() {
        return uint256.ZERO;
    }
}
