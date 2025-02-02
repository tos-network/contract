package java.lang;

// SPDX-License-Identifier: MIT

public interface IERC20 {

    /**
     * @dev Emitted when `value` tokens are moved from one account (`from`) to
     * another (`to`).
     *
     * Note that `value` may be zero.
     */
    void emitTransfer(Address from, Address to, UInt256 value);

    /**
     * @dev Emitted when the allowance of a `spender` for an `owner` is set by
     * a call to {approve}. `value` is the new allowance.
     */
    void emitApproval(Address owner, Address spender, UInt256 value);

    /**
     * @dev Returns the value of tokens in existence.
     */
    UInt256 totalSupply();

    /**
     * @dev Returns the value of tokens owned by `account`.
     */
    UInt256 balanceOf(Address account);

    /**
     * @dev Moves a `value` amount of tokens from the caller's account to `to`.
     *
     * Returns a boolean value indicating whether the operation succeeded.
     *
     * Emits a {Transfer} event.
     */
    boolean transfer(Address to, UInt256 value);

    /**
     * @dev Returns the remaining number of tokens that `spender` will be
     * allowed to spend on behalf of `owner` through {transferFrom}. This is
     * zero by default.
     *
     * This value changes when {approve} or {transferFrom} are called.
     */
    UInt256 allowance(Address owner, Address spender);

    /**
     * @dev Sets a `value` amount of tokens as the allowance of `spender` over the
     * caller's tokens.
     *
     * Returns a boolean value indicating whether the operation succeeded.
     *
     * Emits an {Approval} event.
     */
    boolean approve(Address spender, UInt256 value);

    /**
     * @dev Moves a `value` amount of tokens from `from` to `to` using the
     * allowance mechanism. `value` is then deducted from the caller's
     * allowance.
     *
     * Returns a boolean value indicating whether the operation succeeded.
     *
     * Emits a {Transfer} event.
     */
    boolean transferFrom(Address from, Address to, UInt256 value);
}
