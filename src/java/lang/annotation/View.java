package java.lang.annotation;

// SPDX-License-Identifier: MIT

/**
 * Annotation for marking view contract methods
 * Similar to Solidity's view modifier
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface View {
    // Methods marked with @View only read the blockchain state
    // Similar to Solidity's view
}