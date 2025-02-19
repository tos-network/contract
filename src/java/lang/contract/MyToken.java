package java.lang.contract;

import java.lang.annotation.Pure;
import java.lang.uint256;

// SPDX-License-Identifier: MIT

public class MyToken extends ERC20  {
    /**
     * The total supply of the token.
     */
    private transient uint256  _totalSupply;

    public MyToken() {
        super(string.valueOf("MyToken"), string.valueOf("MTK"));
    }

    @Pure
    public final uint256 add(uint256 a, uint256 b) {
        return a.add(b);
    }

}
