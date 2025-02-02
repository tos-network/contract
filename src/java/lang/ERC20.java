package java.lang;

// SPDX-License-Identifier: MIT
/**
 * Java implementation of an ERC20 token contract using UInt256 and Mapping.
 */
public abstract class ERC20 extends Context implements IERC20Errors, IERC20Metadata {
    private final String name;
    private final String symbol;
    private final int decimals = 18; // Default value for ERC20 tokens
    private UInt256 totalSupply = UInt256.ZERO;

    // Balances of accounts
    private final Mapping<Address, UInt256> balances = new Mapping<>();

    // Allowances: owner -> (spender -> amount)
    private final Mapping<Address, Mapping<Address, UInt256>> allowances = new Mapping<>();

    /**
     * Constructor to initialize the token with a name and symbol.
     *
     * @param name_   The name of the token.
     * @param symbol_ The symbol of the token.
     */
    public ERC20(String name_, String symbol_) {
        this.name = name_;
        this.symbol = symbol_;
    }

    /**
     * Returns the name of the token.
     */
    public String name() {
        return name;
    }

    /**
     * Returns the symbol of the token.
     */
    public String symbol() {
        return symbol;
    }

    /**
     * Returns the number of decimals used for token display.
     */
    public UInt8 decimals() {
        return new UInt8(decimals);
    }

    /**
     * Returns the total supply of the token.
     */
    public UInt256 totalSupply() {
        return totalSupply;
    }

    /**
     * Returns the balance of the specified account.
     *
     * @param account The address of the account.
     */
    public UInt256 balanceOf(Address account) {
        return balances.getOrDefault(account, UInt256.ZERO);
    }

    /**
     * Transfers tokens from the caller to the specified recipient.
     *
     * @param to    The address of the recipient.
     * @param value The amount of tokens to transfer.
     * @return True if the transfer was successful.
     */
    public boolean transfer(Address to, UInt256 value) {
        Address owner = _msgSender();
        _transfer(owner, to, value);
        return true;
    }

    /**
     * Returns the remaining number of tokens that `spender` is allowed to spend on behalf of `owner`.
     *
     * @param owner  The address of the owner.
     * @param spender The address of the spender.
     */
    public UInt256 allowance(Address owner, Address spender) {
        return allowances.getOrDefault(owner, new Mapping<>()).getOrDefault(spender, UInt256.ZERO);
    }

    /**
     * Approves the specified spender to spend the specified amount of tokens on behalf of the caller.
     *
     * @param spender The address of the spender.
     * @param value   The amount of tokens to approve.
     * @return True if the approval was successful.
     */
    public boolean approve(Address spender, UInt256 value) {
        Address owner = _msgSender();
        _approve(owner, spender, value);
        return true;
    }

    /**
     * Transfers tokens from one account to another, using an allowance.
     *
     * @param from  The address of the sender.
     * @param to    The address of the recipient.
     * @param value The amount of tokens to transfer.
     * @return True if the transfer was successful.
     */
    public boolean transferFrom(Address from, Address to, UInt256 value) {
        Address spender = _msgSender();
        _spendAllowance(from, spender, value);
        _transfer(from, to, value);
        return true;
    }

    /**
     * Internal function to transfer tokens from one account to another.
     *
     * @param from  The address of the sender.
     * @param to    The address of the recipient.
     * @param value The amount of tokens to transfer.
     */
    protected void _transfer(Address from, Address to, UInt256 value) {
        if (from == null) {
            throw new ERC20InvalidSenderException(from);
        }
        if (to == null) {
            throw new ERC20InvalidReceiverException(to);
        }

        UInt256 fromBalance = balanceOf(from);
        if (fromBalance.compareTo(value) < 0) {
            throw new ERC20InsufficientBalanceException(from, fromBalance, value);
        }

        balances.put(from, fromBalance.subtract(value));
        balances.put(to, balanceOf(to).add(value));

        // Emit Transfer event
        emitTransfer(from, to, value);
    }

    /**
     * Internal function to mint new tokens.
     *
     * @param account The address to receive the minted tokens.
     * @param value   The amount of tokens to mint.
     */
    protected void _mint(Address account, UInt256 value) {
        if (account == null) {
            throw new ERC20InvalidReceiverException(account);
        }

        totalSupply = totalSupply.add(value);
        balances.put(account, balanceOf(account).add(value));

        // Emit Transfer event
        emitTransfer(null, account, value);
    }

    /**
     * Internal function to burn tokens.
     *
     * @param account The address to burn tokens from.
     * @param value   The amount of tokens to burn.
     */
    protected void _burn(Address account, UInt256 value) {
        if (account == null) {
            throw new ERC20InvalidSenderException(account);
        }

        UInt256 accountBalance = balanceOf(account);
        if (accountBalance.compareTo(value) < 0) {
            throw new ERC20InsufficientBalanceException(account, accountBalance, value);
        }

        balances.put(account, accountBalance.subtract(value));
        totalSupply = totalSupply.subtract(value);

        // Emit Transfer event
        emitTransfer(account, null, value);
    }

    /**
     * Internal function to approve an allowance.
     *
     * @param owner   The address of the owner.
     * @param spender The address of the spender.
     * @param value   The amount of tokens to approve.
     */
    protected void _approve(Address owner, Address spender, UInt256 value) {
        if (owner == null) {
            throw new ERC20InvalidApproverException(owner);
        }
        if (spender == null) {
            throw new ERC20InvalidSpenderException(spender);
        }

        Mapping<Address, UInt256> ownerAllowances = allowances.getOrDefault(owner, new Mapping<>());
        ownerAllowances.put(spender, value);
        allowances.put(owner, ownerAllowances);

        // Emit Approval event
        emitApproval(owner, spender, value);
    }

    /**
     * Internal function to spend an allowance.
     *
     * @param owner   The address of the owner.
     * @param spender The address of the spender.
     * @param value   The amount of tokens to spend.
     */
    protected void _spendAllowance(Address owner, Address spender, UInt256 value) {
        Mapping<Address, UInt256> ownerAllowances = allowances.getOrDefault(owner, new Mapping<>());
        UInt256 currentAllowance = ownerAllowances.getOrDefault(spender, UInt256.ZERO);

        if (currentAllowance.compareTo(UInt256.MAX_VALUE) < 0) {
            if (currentAllowance.compareTo(value) < 0) {
                throw new ERC20InsufficientAllowanceException(spender, currentAllowance, value);
            }
            ownerAllowances.put(spender, currentAllowance.subtract(value));
            allowances.put(owner, ownerAllowances);
        }
    }

    /**
     * Emits a Transfer event.
     *
     * @param from  The address of the sender.
     * @param to    The address of the recipient.
     * @param value The amount of tokens transferred.
     */
    public void emitTransfer(Address from, Address to, UInt256 value) {
        // In Java, this could be implemented using a logging framework or custom event system.
        System.out.println("Transfer: from=" + from + ", to=" + to + ", value=" + value);
    }

    /**
     * Emits an Approval event.
     *
     * @param owner   The address of the owner.
     * @param spender The address of the spender.
     * @param value   The amount of tokens approved.
     */
    public void emitApproval(Address owner, Address spender, UInt256 value) {
        // In Java, this could be implemented using a logging framework or custom event system.
        System.out.println("Approval: owner=" + owner + ", spender=" + spender + ", value=" + value);
    }
}
