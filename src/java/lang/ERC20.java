package java.lang;


public abstract class ERC20 extends Context implements IERC20Errors, IERC20Metadata {
    private final Mapping<Address, UInt256> balances = new Mapping<>(() -> UInt256.ZERO);
    private final Mapping<Address, Mapping<Address, UInt256>> allowances = new Mapping<>(() -> new Mapping<>(() -> UInt256.ZERO));
    private UInt256 totalSupply = UInt256.ZERO;
    private final String name;
    private final String symbol;

    public ERC20(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String symbol() {
        return symbol;
    }

    @Override
    public UInt8 decimals() {
        return new UInt8(18);
    }

    @Override
    public UInt256 totalSupply() {
        return totalSupply;
    }

    @Override
    public UInt256 balanceOf(Address account) {
        return balances.get(account);
    }

    @Override
    public boolean transfer(Address to, UInt256 value) {
        Address owner = _msgSender();
        _transfer(owner, to, value);
        return true;
    }

    @Override
    public UInt256 allowance(Address owner, Address spender) {
        return allowances.get(owner).get(spender);
    }

    @Override
    public boolean approve(Address spender, UInt256 value) {
        Address owner = _msgSender();
        _approve(owner, spender, value);
        return true;
    }

    @Override
    public boolean transferFrom(Address from, Address to, UInt256 value) {
        Address spender = _msgSender();
        _spendAllowance(from, spender, value);
        _transfer(from, to, value);
        return true;
    }

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
    private void _update(Address from, Address to, UInt256 value) { 
        if (Address.ZERO_ADDRESS.equals(from)) {
            // Minting tokens: from is the zero address
            totalSupply = totalSupply.add(value);
        } else {
            // Transferring or burning tokens: from is a valid address
            UInt256 fromBalance = balanceOf(from);
            if (fromBalance.compareTo(value) < 0) {
                revert(new ERC20InsufficientBalance(from, fromBalance, value));
            }
            // Subtract the value from the sender's balance
            balances.set(from, fromBalance.subtract(value));
        }
    
        if (Address.ZERO_ADDRESS.equals(to)) {
            // Burning tokens: to is the zero address
            totalSupply = totalSupply.subtract(value);
        } else {
            // Transferring or minting tokens: to is a valid address
            UInt256 toBalance = balanceOf(to);
            // Add the value to the recipient's balance
            balances.set(to, toBalance.add(value));
        }
    
        // Emit Transfer event
        emit(new Transfer(from, to, value));
    }


    protected void _mint(Address account, UInt256 value) {
        if (Address.ZERO_ADDRESS.equals(account)) {
            revert(new ERC20InvalidReceiver(Address.ZERO_ADDRESS));
        }
        if (value.isZero()) {
            return; // No need to mint zero tokens
        }
        _update(Address.ZERO_ADDRESS, account, value);

    }

    protected void _burn(Address account, UInt256 value) {
        if (Address.ZERO_ADDRESS.equals(account)) {
            revert(new ERC20InvalidSender(Address.ZERO_ADDRESS));
        }
        if (value.isZero()) {
            return; // No need to burn zero tokens
        }
        _update(account, Address.ZERO_ADDRESS, value);
    }

    private void _approve(Address owner, Address spender, UInt256 value) {
        _approve(owner, spender, value, true);
    }

    protected void _approve(Address owner, Address spender, UInt256 value, boolean emitEvent) {
        // Check for invalid owner address
        if (Address.ZERO_ADDRESS.equals(owner)) {
            revert(new ERC20InvalidApprover(owner));
        }
    
        // Check for invalid spender address
        if (Address.ZERO_ADDRESS.equals(spender)) {
            revert(new ERC20InvalidSpender(spender));
        }
    
        // Update the allowance
        Mapping<Address, UInt256> ownerAllowances = allowances.get(owner);
        ownerAllowances.set(spender, value);
        allowances.set(owner, ownerAllowances);
    
        // Emit Approval event if required
        if (emitEvent) {
            emit(new Approval(owner, spender, value));
        }
    }

    /**
     * @dev Updates `owner` s allowance for `spender` based on spent `value`.
     *
     * Does not update the allowance value in case of infinite allowance.
     * Revert if not enough allowance is available.
     *
     * Does not emit an {Approval} event.
     */
    private void _spendAllowance(Address owner, Address spender, UInt256 value) {
        UInt256 currentAllowance = allowance(owner, spender);
        if (currentAllowance.compareTo(UInt256.MAX_VALUE) < 0) {
          if (currentAllowance.compareTo(value) < 0) {
            revert(new ERC20InsufficientAllowance(spender, currentAllowance, value));
          }
        
          _approve(owner, spender, currentAllowance.subtract(value));
        }
    }
}
