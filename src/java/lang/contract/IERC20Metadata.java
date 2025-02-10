package java.lang.contract;
import java.lang.string;
import java.lang.uint8;

// SPDX-License-Identifier: MIT

public interface IERC20Metadata extends IERC20 {

    /**
     * @dev Returns the name of the token.
     */
    string name();

    /**
     * @dev Returns the symbol of the token.
     */
    string symbol();

    /**
     * @dev Returns the decimal places of the token.
     */
    uint8 decimals();
}
