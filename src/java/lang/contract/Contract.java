package java.lang.contract;

// SPDX-License-Identifier: MIT

public abstract class Contract extends Context
{
    // constructor
    public Contract() {
        super();
    }


    /**
     * Requires the given condition to be true.
     * @param condition The condition to check.
     * @param message The message to throw if the condition is false.
     * @return no return.
    */
    public final static void require(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(new RuntimeException(message));
        }
    }
  
    /**
     * Reverts the transaction with the given exception.
     * @param re The exception to throw.
     * @return no return.
    */
    public final static void revert(RuntimeException re) {
        throw new Revert(re.getMessage()); 
    }

    /**
     * Emits an event.  
     * @param el The event to emit.
     * @return no return.
    */
    public final static void emit(Event el) {
        System.out.println(el.getMessage());
    }


}
