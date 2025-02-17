package java.lang.annotation;

// SPDX-License-Identifier: MIT

/**
 * Annotation for marking payable contract methods
 * Similar to Solidity's payable modifier
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Payable {
    // Methods marked with @Payable can receive Ether
    // Similar to Solidity's payable
}