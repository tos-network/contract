package java.lang.contract;
import java.lang.reflect.Field;

// SPDX-License-Identifier: MIT

public abstract class Contract extends Context implements Callable<byte[]>
{
    // constructor
    public Contract() {
        super();
    }

    // beforeCall is called before the call is made.
    protected void beforeCall() {
        String className = this.getClass().getSimpleName();
        System.out.println("step10" + className);    
        
        // get all fields
        Field[] declaredFields = this.getClass().getDeclaredFields();
        System.out.println("Current class fields: " + declaredFields.length);
        // print current class fields
        for (Field field : declaredFields) {
            field.setAccessible(true); // allow access private fields
            System.out.println("Current class field: " + className + "." + field.getName() + "=" + field.getType());
        }
        
        // recursively get and print all parent class fields
        Class<?> superClass = this.getClass().getSuperclass();
        while (superClass != null && !superClass.equals(Object.class)) {
            Field[] parentFields = superClass.getDeclaredFields();
            System.out.println(superClass.getSimpleName() + " fields: " + parentFields.length);
            
            for (Field field : parentFields) {
                field.setAccessible(true); // allow access private fields
                System.out.println("Parent class field: " + superClass.getSimpleName() + "." + field.getName() + "=" + field.getType());
            }
            
            superClass = superClass.getSuperclass();
        }
    }

    @Override
    public byte[] call() throws Exception {
        System.out.println("step6");
        beforeCall();
        System.out.println("step7");
        byte[] result = executeCall();
        System.out.println("step8");
        afterCall();
        System.out.println("step9");
        return result;
    }


    // executeCall is called to execute the call.
    protected  byte[] executeCall() throws Exception {
        return new byte[0];
    }

    // afterCall is called after the call is made.
    protected void afterCall() {
    }
}
