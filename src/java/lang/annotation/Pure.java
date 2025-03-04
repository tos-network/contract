package java.lang.annotation;

// SPDX-License-Identifier: MIT

/**
 * Annotation for marking pure contract methods
 * Similar to Solidity's pure modifier
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Pure {
    // Methods marked with @Pure don't read or modify blockchain state
    // Similar to Solidity's pure
}