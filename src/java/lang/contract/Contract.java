package java.lang.contract;
import java.lang.reflect.Field;
import java.io.Storable;
import java.util.concurrent.Callable;

// SPDX-License-Identifier: MIT

public abstract class Contract extends Context implements Callable<byte[]>
{
    // constructor
    public Contract() {
        super();
    }

    // Requires the given condition to be true.
    public final static void require(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(new RuntimeException(message));
        }
    }
  
    // Reverts the transaction with the given exception.
    public final static void revert(RuntimeException re) {
        throw new Revert(re.getMessage()); 
    }

    // Emits an event.  
    public final static void emit(Event el) {
        System.out.println(el.getMessage());
    }

    @Override
    public byte[] call() throws Exception {
        beforeCall();
        byte[] result = executeCall();
        afterCall();
        return result;
    }


    // beforeCall is called before the call is made.
    protected void beforeCall() {
        int slotCounter = 0;  // use for auto assign storage slot
        
        // loop through the fields of the current class
        for (Field field : this.getClass().getDeclaredFields()) {
            // Skip static and transient fields
            int modifiers = field.getModifiers();
            if (java.lang.reflect.Modifier.isStatic(modifiers) || 
                java.lang.reflect.Modifier.isTransient(modifiers)) {
                continue;
            }
            field.setAccessible(true);
            try {
                Object fieldValue = field.get(this);
                if (fieldValue instanceof Storable) {
                    Storable storable = (Storable) fieldValue;
                    storable.setSlot(slotCounter++);
                    storable.load();
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        
        // loop through the fields of the superclass
        Class<?> superClass = this.getClass().getSuperclass();
        while (superClass != null && !superClass.equals(Contract.class)) {
            for (Field field : superClass.getDeclaredFields()) {
                // Skip static and transient fields
                int modifiers = field.getModifiers();
                if (java.lang.reflect.Modifier.isStatic(modifiers) || 
                    java.lang.reflect.Modifier.isTransient(modifiers)) {
                    continue;
                }
                field.setAccessible(true);
                try {
                    Object fieldValue = field.get(this);
                    if (fieldValue instanceof Storable) {
                        Storable storable = (Storable) fieldValue;
                        storable.setSlot(slotCounter++);
                        storable.load();
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            superClass = superClass.getSuperclass();
        }
    }

    // executeCall is called to execute the call.
    protected  byte[] executeCall() throws Exception {
        return new byte[0];
    }

    // afterCall is called after the call is made.
    protected void afterCall() {
    }
}
