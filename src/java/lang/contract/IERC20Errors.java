package java.lang.contract;

import java.lang.address;   
import java.lang.RuntimeException;
import java.lang.String;
import java.lang.uint256;

// SPDX-License-Identifier: MIT

/**
 * Standard ERC20 Errors.
 * This interface defines custom exceptions for ERC20 token operations.
 */
public interface IERC20Errors {
    /**
     * Thrown when the sender's balance is insufficient for a transfer.
     */
    class ERC20InsufficientBalance extends RuntimeException {
        public ERC20InsufficientBalance(address sender, uint256 balance, uint256 needed) {
            super(String.format("ERC20: insufficient balance. Sender: %s, Balance: %s, Needed: %s", sender, balance, needed));
        }
    }

    /**
     * Thrown when the sender address is invalid (e.g., zero address).
     */
    class ERC20InvalidSender extends RuntimeException {
        public ERC20InvalidSender(address sender) {
            super(String.format("ERC20: invalid sender. Sender: %s", sender));
        }
    }

    /**
     * Thrown when the receiver address is invalid (e.g., zero address).
     */
    class ERC20InvalidReceiver extends RuntimeException {
        public ERC20InvalidReceiver(address receiver) {
            super(String.format("ERC20: invalid receiver. Receiver: %s", receiver));
        }
    }

    /**
     * Thrown when the spender's allowance is insufficient for a transfer.
     */
    class ERC20InsufficientAllowance extends RuntimeException {
        public ERC20InsufficientAllowance(address spender, uint256 allowance, uint256 needed) {
            super(String.format("ERC20: insufficient allowance. Spender: %s, Allowance: %s, Needed: %s", spender, allowance, needed));
        }
    }

    /**
     * Thrown when the approver address is invalid (e.g., zero address).
     */
    class ERC20InvalidApprover extends RuntimeException {
        public ERC20InvalidApprover(address approver) {
            super(String.format("ERC20: invalid approver. Approver: %s", approver));
        }
    }

    /**
     * Thrown when the spender address is invalid (e.g., zero address).
     */
    class ERC20InvalidSpender extends RuntimeException {
        public ERC20InvalidSpender(address spender) {
            super(String.format("ERC20: invalid spender. Spender: %s", spender));
        }
    }
}