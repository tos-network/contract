package java.lang;

/**
 * Standard ERC20 Errors.
 * This interface defines custom exceptions for ERC20 token operations.
 */
public interface IERC20Errors {
    /**
     * Thrown when the sender's balance is insufficient for a transfer.
     */
    class ERC20InsufficientBalanceException extends RuntimeException {
        public ERC20InsufficientBalanceException(Address sender, UInt256 balance, UInt256 needed) {
            super(String.format("ERC20: insufficient balance. Sender: %s, Balance: %s, Needed: %s", sender, balance, needed));
        }
    }

    /**
     * Thrown when the sender address is invalid (e.g., zero address).
     */
    class ERC20InvalidSenderException extends RuntimeException {
        public ERC20InvalidSenderException(Address sender) {
            super(String.format("ERC20: invalid sender. Sender: %s", sender));
        }
    }

    /**
     * Thrown when the receiver address is invalid (e.g., zero address).
     */
    class ERC20InvalidReceiverException extends RuntimeException {
        public ERC20InvalidReceiverException(Address receiver) {
            super(String.format("ERC20: invalid receiver. Receiver: %s", receiver));
        }
    }

    /**
     * Thrown when the spender's allowance is insufficient for a transfer.
     */
    class ERC20InsufficientAllowanceException extends RuntimeException {
        public ERC20InsufficientAllowanceException(Address spender, UInt256 allowance, UInt256 needed) {
            super(String.format("ERC20: insufficient allowance. Spender: %s, Allowance: %s, Needed: %s", spender, allowance, needed));
        }
    }

    /**
     * Thrown when the approver address is invalid (e.g., zero address).
     */
    class ERC20InvalidApproverException extends RuntimeException {
        public ERC20InvalidApproverException(Address approver) {
            super(String.format("ERC20: invalid approver. Approver: %s", approver));
        }
    }

    /**
     * Thrown when the spender address is invalid (e.g., zero address).
     */
    class ERC20InvalidSpenderException extends RuntimeException {
        public ERC20InvalidSpenderException(Address spender) {
            super(String.format("ERC20: invalid spender. Spender: %s", spender));
        }
    }
}