package java.lang.contract;

import java.lang.Address;
import java.lang.Mapping;
import java.lang.Override;
import java.lang.String;
import java.lang.UInt256;
import java.lang.UInt8;
import java.lang.annotation.View;
import static java.lang.Bool.TRUE;
import java.lang.annotation.Payable;

// SPDX-License-Identifier: MIT

/**
 * @dev Implementation of the {IERC20} interface.
 *
 * This implementation is agnostic to the way tokens are created. This means
 * that a supply mechanism has to be added in a derived contract using {_mint}.
 *
 * The default value of {decimals} is 18. To change this, you should override
 * this function so it returns a different value.
 *
 * We have followed general OpenZeppelin Contracts guidelines: functions revert
 * instead returning `false` on failure.
 */
public abstract class ERC20 extends Contract implements IERC20Errors, IERC20Metadata {
    private final Mapping<Address, UInt256> balances = new Mapping<Address, UInt256>();
    private final Mapping<Address, Mapping<Address, UInt256>> allowances = new Mapping<Address, Mapping<Address, UInt256>>();
    private UInt256 totalSupply = UInt256.ZERO;
    private final String name;
    private final String symbol;

    /**
     * @dev Sets the values for {name} and {symbol}.
     *
     * All two of these values are immutable: they can only be set once during
     * construction.
     */
    public ERC20(String name_, String symbol_) {
        this.name = name_;
        this.symbol = symbol_;
    }

    @View
    @Override
    public String name() {
        return name;
    }

    @View
    @Override
    public String symbol() {
        return symbol;
    }

    /**
     * @dev Returns the number of decimals used to get its user representation.
     * For example, if `decimals` equals `2`, a balance of `505` tokens should
     * be displayed to a user as `5.05` (`505 / 10 ** 2`).
     *
     * Tokens usually opt for a value of 18, imitating the relationship between
     * Ether and Wei. This is the default value returned by this function, unless
     * it's overridden.
     */
    @View
    @Override
    public UInt8 decimals() {
        return new UInt8(18);
    }

    @View
    @Override
    public UInt256 totalSupply() {
        return totalSupply;
    }

    @View
    @Override
    public UInt256 balanceOf(Address account) {
        return balances.get(account);
    }

    @Payable
    @Override
    public Bool transfer(Address to, UInt256 value) {
        Address owner = _msgSender();
        _transfer(owner, to, value);
        return TRUE;
    }

    @View
    @Override
    public UInt256 allowance(Address owner, Address spender) {
        return allowances.get(owner).get(spender);
    }

    @Payable
    @Override
    public Bool approve(Address spender, UInt256 value) {
        Address owner = _msgSender();
        _approve(owner, spender, value);
        return TRUE;
    }

    /**
     * @dev See {IERC20-transferFrom}.
     *
     * NOTE: Does not update the allowance if the current allowance
     * is the maximum `uint256`.
     */
    @Payable
    @Override
    public Bool transferFrom(Address from, Address to, UInt256 value) {
        Address spender = _msgSender();
        _spendAllowance(from, spender, value);
        _transfer(from, to, value);
        return TRUE;
    }

    /**
     * @dev Moves a `value` amount of tokens from `from` to `to`.
     *
     * This internal function is equivalent to {transfer}, and can be used to
     * e.g. implement automatic token fees, slashing mechanisms, etc.
     *
     * Emits a {Transfer} event.
     */
    private void _transfer(Address from, Address to, UInt256 value) {
        if (Address.ZERO_ADDRESS.equals(from)) {
            revert(new ERC20InvalidSender(Address.ZERO_ADDRESS));
        }
        if (Address.ZERO_ADDRESS.equals(to)) {
            revert(new ERC20InvalidReceiver(Address.ZERO_ADDRESS));
        }
        if (from.equals(to)) {
            return; // No need to transfer to self
        }
        if (value.isZero()) {
            return; // No need to process zero-value transfers
        }
        _update(from, to, value);
    }

    /**
     * @dev Transfers a `value` amount of tokens from `from` to `to`, or alternatively mints (or burns) if `from`
     * (or `to`) is the zero address. All customizations to transfers, mints, and burns should be done by overriding
     * this function.
     *
     * Emits a {Transfer} event.
     */
    protected void _update(Address from, Address to, UInt256 value) {
        if (Address.ZERO_ADDRESS.equals(from)) {
            // Minting tokens
            totalSupply = totalSupply.add(value);
        } else {
            UInt256 fromBalance = balanceOf(from);
            if (fromBalance.compareTo(value) < 0) {
                revert(new ERC20InsufficientBalance(from, fromBalance, value));
            }
            balances.set(from, fromBalance.subtract(value));
        }

        if (Address.ZERO_ADDRESS.equals(to)) {
            // Burning tokens
            totalSupply = totalSupply.subtract(value);
        } else {
            UInt256 toBalance = balanceOf(to);
            balances.set(to, toBalance.add(value));
        }

        emit(new Transfer(from, to, value));
    }

    /**
     * @dev Creates a `value` amount of tokens and assigns them to `account`.
     *
     * Emits a {Transfer} event with `from` set to the zero address.
     */
    protected void _mint(Address account, UInt256 value) {
        if (Address.ZERO_ADDRESS.equals(account)) {
            revert(new ERC20InvalidReceiver(Address.ZERO_ADDRESS));
        }
        if (value.isZero()) {
            return; // No need to mint zero tokens
        }
        _update(Address.ZERO_ADDRESS, account, value);
    }

    /**
     * @dev Destroys a `value` amount of tokens from `account`.
     *
     * Emits a {Transfer} event with `to` set to the zero address.
     */
    protected void _burn(Address account, UInt256 value) {
        if (Address.ZERO_ADDRESS.equals(account)) {
            revert(new ERC20InvalidSender(Address.ZERO_ADDRESS));
        }
        if (value.isZero()) {
            return; // No need to burn zero tokens
        }
        _update(account, Address.ZERO_ADDRESS, value);
    }

    protected void _approve(Address owner, Address spender, UInt256 value) {
        _approve(owner, spender, value, true);
    }

    /**
     * @dev Variant of {_approve} with an optional flag to enable or disable the {Approval} event.
     */
    protected void _approve(Address owner, Address spender, UInt256 value, boolean emitEvent) {
        if (Address.ZERO_ADDRESS.equals(owner)) {
            revert(new ERC20InvalidApprover(Address.ZERO_ADDRESS));
        }
        if (Address.ZERO_ADDRESS.equals(spender)) {
            revert(new ERC20InvalidSpender(Address.ZERO_ADDRESS));
        }

        Mapping<Address, UInt256> ownerAllowances = allowances.get(owner);
        ownerAllowances.set(spender, value);
        allowances.set(owner, ownerAllowances);

        if (emitEvent) {
            emit(new Approval(owner, spender, value));
        }
    }

    /**
     * @dev Updates `owner`'s allowance for `spender` based on spent `value`.
     *
     * Does not update the allowance value in case of infinite allowance.
     * Revert if not enough allowance is available.
     */
    protected void _spendAllowance(Address owner, Address spender, UInt256 value) {
        UInt256 currentAllowance = allowance(owner, spender);
        if (currentAllowance.compareTo(UInt256.MAX_VALUE) < 0) {
            if (currentAllowance.compareTo(value) < 0) {
                revert(new ERC20InsufficientAllowance(spender, currentAllowance, value));
            }
            _approve(owner, spender, currentAllowance.subtract(value), false);
        }
    }
}
