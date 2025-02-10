package java.lang.contract;

import java.lang.address;
import java.lang.mapping;
import java.lang.Override;
import java.lang.string;
import java.lang.uint256;
import java.lang.uint8;
import java.lang.annotation.View;
import static java.lang.bool.TRUE;
import java.lang.annotation.Payable;
import java.lang.annotation.Pragma;

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
@Pragma("0.8.18")  
public abstract class ERC20 extends Contract implements IERC20Errors, IERC20Metadata {

    /**
     * Mapping of address to balance
     */
    private final mapping<address, uint256> balances =
         mapping.of(address.class, uint256.class);
 
    /**
     * Mapping of address to allowance
     */
    private final mapping<address, mapping<address, uint256>> allowances = 
        mapping.ofNested(address.class, uint256.class);  
 
    /**
     * Total supply of the token
     */ 
    private uint256 totalSupply = uint256.ZERO;

    /**
     * Name of the token
     */
    private final string name;

    /**
     * Symbol of the token
     */
    private final string symbol;

    /**
     * @dev Sets the values for {name} and {symbol}.
     *
     * All two of these values are immutable: they can only be set once during
     * construction.
     */
    public ERC20(string name_, string symbol_) {
        this.name = name_;
        this.symbol = symbol_;
    }

    /**
     * @dev Returns the name of the token.
     */
    @View
    @Override
    public string name() {
        return name;
    }

    /**
     * @dev Returns the symbol of the token.
     */
    @View
    @Override
    public string symbol() {
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
    public uint8 decimals() {
        return new uint8(18);
    }

    /**
     * @dev Returns the total supply of the token.
     */
    @View
    @Override
    public uint256 totalSupply() {
        return totalSupply;
    }

    /**
     * @dev Returns the balance of the token for a given account.
     */
    @View
    @Override
    public uint256 balanceOf(address account) {
        return balances.get(account);
    }

    /**
     * @dev Transfers a `value` amount of tokens from the caller to `to`.
     */
    @Payable
    @Override
    public bool transfer(address to, uint256 value) {
        address owner = _msgSender();
        _transfer(owner, to, value);
        return TRUE;
    }

    /**
     * @dev Returns the remaining number of tokens that `spender` will be
     * allowed to spend on behalf of `owner` through {transferFrom}. This is
     * zero by default.
     */
    @View
    @Override
    public uint256 allowance(address owner, address spender) {
        return allowances.get(owner).get(spender);
    }

    /**
     * @dev Sets `value` as the allowance of `spender` over the caller's tokens.
     */
    @Payable
    @Override
    public bool approve(address spender, uint256 value) {
        address owner = _msgSender();
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
    public bool transferFrom(address from, address to, uint256 value) {
        address spender = _msgSender();
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
    private void _transfer(address from, address to, uint256 value) {
        if (address.ZERO_ADDRESS.equals(from)) {
            revert(new ERC20InvalidSender(address.ZERO_ADDRESS));
        }
        if (address.ZERO_ADDRESS.equals(to)) {
            revert(new ERC20InvalidReceiver(address.ZERO_ADDRESS));
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
    protected void _update(address from, address to, uint256 value) {
        if (address.ZERO_ADDRESS.equals(from)) {
            // Minting tokens
            totalSupply = totalSupply.add(value);
        } else {
            uint256 fromBalance = balanceOf(from);
            if (fromBalance.compareTo(value) < 0) {
                revert(new ERC20InsufficientBalance(from, fromBalance, value));
            }
            balances.set(fromBalance.subtract(value), from);
        }

        if (address.ZERO_ADDRESS.equals(to)) {
            // Burning tokens
            totalSupply = totalSupply.subtract(value);
        } else {
            uint256 toBalance = balanceOf(to);
            balances.set(toBalance.add(value), to);
        }

        emit(new Transfer(from, to, value));
    }

    /**
     * @dev Creates a `value` amount of tokens and assigns them to `account`.
     *
     * Emits a {Transfer} event with `from` set to the zero address.
     */
    protected void _mint(address account, uint256 value) {
        if (address.ZERO_ADDRESS.equals(account)) {
            revert(new ERC20InvalidReceiver(address.ZERO_ADDRESS));
        }
        if (value.isZero()) {
            return; // No need to mint zero tokens
        }
        _update(address.ZERO_ADDRESS, account, value);
    }

    /**
     * @dev Destroys a `value` amount of tokens from `account`.
     *
     * Emits a {Transfer} event with `to` set to the zero address.
     */
    protected void _burn(address account, uint256 value) {
        if (address.ZERO_ADDRESS.equals(account)) {
            revert(new ERC20InvalidSender(address.ZERO_ADDRESS));
        }
        if (value.isZero()) {
            return; // No need to burn zero tokens
        }
        _update(account, address.ZERO_ADDRESS, value);
    }

    /**
     * @dev Sets `value` as the allowance of `spender` over the caller's tokens.
     */
    protected void _approve(address owner, address spender, uint256 value) {
        _approve(owner, spender, value, true);
    }

    /**
     * @dev Variant of {_approve} with an optional flag to enable or disable the {Approval} event.
     */
    protected void _approve(address owner, address spender, uint256 value, boolean emitEvent) {
        if (address.ZERO_ADDRESS.equals(owner)) {
            revert(new ERC20InvalidApprover(address.ZERO_ADDRESS));
        }
        if (address.ZERO_ADDRESS.equals(spender)) {
            revert(new ERC20InvalidSpender(address.ZERO_ADDRESS));
        }

        mapping<address, uint256> ownerAllowances = allowances.get(owner);
        ownerAllowances.set(value, spender);
        allowances.set(ownerAllowances, owner);

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
    protected void _spendAllowance(address owner, address spender, uint256 value) {
        uint256 currentAllowance = allowance(owner, spender);
        if (currentAllowance.compareTo(uint256.MAX_VALUE) < 0) {
            if (currentAllowance.compareTo(value) < 0) {
                revert(new ERC20InsufficientAllowance(spender, currentAllowance, value));
            }
            _approve(owner, spender, currentAllowance.subtract(value), false);
        }
    }
}
