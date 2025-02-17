package java.lang.contract;
import java.lang.reflect.Field;
import java.io.Storable;
import java.lang.rlp.RlpDecoder;
import java.lang.rlp.RlpList;
import java.lang.rlp.RlpString;
import java.lang.ABI;
import java.crypto.Keccak256;

// Entry point of the contract
public final class MainCaller {

    // constructor
    public MainCaller() {
    }

    /**
     * Calls the contract.
     * @param args The name of the contract to call.
     * @return The result of the call to msg.output.
    */
    public static void main(String[] args) {
        Message msg = Message.getMessage();
        msg.setSuccess(false);
        msg.setOutput(new byte[0]);

        byte[] input = msg.getData();
        String contractName = args[0];
        try {
            Class<?> contractClass = Class.forName(contractName);
            Contract contract = (Contract) contractClass.getDeclaredConstructor().newInstance();

            // load contract
            if (!beforeCall(contract)) {
                return;
            }

            // execute call
            Result<byte[]> result = executeCall(contract, input);
            if (result.isSuccess()) {
                // after call
                afterCall();
                // set output
                msg.setSuccess(true);
                msg.setOutput(result.value);
            }
        } catch (Exception e) {
            msg.setSuccess(false);
            msg.setOutput(e.getMessage().getBytes());
        }
    }


    /**
     * beforeCall is called before the call is made.
     * @param contract The contract to call.
     * @return true if the call is successful, false otherwise.
    */
    protected final static boolean beforeCall(Contract contract) {
        int slotCounter = 0;  // use for auto assign storage slot
        
        // loop through the fields of the current class
        for (Field field : contract.getClass().getDeclaredFields()) {
            // Skip static and transient fields
            int modifiers = field.getModifiers();
            if (java.lang.reflect.Modifier.isStatic(modifiers) || 
                java.lang.reflect.Modifier.isTransient(modifiers)) {
                continue;
            }
            field.setAccessible(true);
            try {
                Object fieldValue = field.get(contract);
                if (fieldValue instanceof Storable) {
                    Storable storable = (Storable) fieldValue;
                    storable.setSlot(slotCounter++);
                    storable.load();
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to load field: " + field.getName());
            }
        }
        
        // loop through the fields of the superclass
        Class<?> superClass = contract.getClass().getSuperclass();
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
                    Object fieldValue = field.get(contract);
                    if (fieldValue instanceof Storable) {
                        Storable storable = (Storable) fieldValue;
                        storable.setSlot(slotCounter++);
                        storable.load();
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Failed to load field: " + field.getName());
                }
            }
            superClass = superClass.getSuperclass();
        }
        return true;
    }

    /**
     * executeCall is called to execute the call.
     * @param contract The contract to call.
     * @param input The input to the contract.
     * @return The result of the call.
    */
    protected  final static Result<byte[]> executeCall(Contract contract, byte[] input) throws Exception {
        // Print input as hex string
        System.out.println("input: " + java.lang.types.Numeric.toHexString(input));
        // 2. Get method signature (first 4 bytes) and parameters
        byte[] methodId = new byte[4];
        System.arraycopy(input, 0, methodId, 0, 4);
        System.out.println("methodId: " + java.lang.types.Numeric.toHexString(methodId));

        // Get parameter list from remaining input
        byte[] paramData = new byte[input.length - 4];
        System.arraycopy(input, 4, paramData, 0, input.length - 4);
        RlpList params = RlpDecoder.decode(paramData);

        // 4. Find and invoke the corresponding method based on signature
        return dispatchMethod(contract,methodId, params);
    }


    /**
     * Dispatches the contract call to the appropriate method
     * @param contract The contract to call.
     * @param selector The method selector (4 bytes)
     * @param params The RLP encoded parameters
     * @return Result containing the encoded return value or error
     * @throws Exception if the method is not found
     */
    private final static Result<byte[]> dispatchMethod(Contract contract, byte[] selector, RlpList params) throws Exception {
        // Get all methods in the inheritance chain
        Class<?> currentClass = contract.getClass();
        while (currentClass != null && !currentClass.equals(Contract.class)) {
            System.out.println(currentClass.getName()+"："+currentClass.getDeclaredMethods().length);
            // Check methods in current class
            for (java.lang.reflect.Method method : currentClass.getDeclaredMethods()) {
                System.out.println(currentClass.getName()+"：" + method.getName());
                System.out.println(method.getParameterTypes());
                String methodSignature =buildMethodSignature(method.getParameterTypes(), method.getName());
                System.out.println("methodSignature: " + methodSignature);
                byte[] methodSelector = buildMethodId(methodSignature);
                System.out.println("methodSelector: " + java.lang.types.Numeric.toHexString(methodSelector));
                // Check if method has External annotation
                if (java.util.Arrays.equals(selector, methodSelector)) {
                    // Convert parameters to appropriate types
                    Object[] args = convertParams(method.getParameterTypes(), params);
                    
                    // Ensure method is accessible
                    method.setAccessible(true);
                    
                    // Invoke the method
                    Object result = method.invoke(contract, args);
                    
                    // Encode the return value
                    return Result.ok(encodeResult(result));
                }
            }
            // Move up to parent class
            currentClass = currentClass.getSuperclass();
        }
        return Result.fail("Method not found");            
    }

    /**
     * Converts RLP encoded parameters to Java objects
     * @param paramTypes Array of parameter types
     * @param params RLP encoded parameters
     * @return Array of converted parameters
     * @throws Exception if parameter conversion fails
     */
    private final static Object[] convertParams(Class<?>[] paramTypes, RlpList params) throws Exception {
        if (params == null) {
            return new Object[0];
        }

        Object[] args = new Object[paramTypes.length];
        for (int i = 0; i < paramTypes.length; i++) {
            RlpString param = (RlpString) params.getValues().get(i);
            args[i] = convertParam(param, paramTypes[i]);
        }
        return args;
    }

    /**
     * Builds a method signature from a method name and parameter types
     * @param paramTypes Array of parameter types
     * @param methodName The name of the method
     * @return The method signature as a string
    */
    private final static String buildMethodSignature(Class<?>[] paramTypes, String methodName) throws Exception {
        StringBuilder sb = new StringBuilder(methodName);
        sb.append("(");
        if (paramTypes != null) {
            for (int i = 0; i < paramTypes.length; i++) {
                if (i > 0) {
                    sb.append(",");
                }
                sb.append(getLastPart(paramTypes[i].getName()));
            }
        }
        sb.append(")");
        return sb.toString();
    }

    /**
     * Builds a method selector from a method signature
     * @param methodSignature The method signature
     * @return The method selector
    */
    private static byte[] buildMethodId(final String methodSignature) {
        return computeMethodSelector(methodSignature);
    }

    /**
     * Gets the last part of a dot-separated string
     * @param str The input string
     * @return The last part after the last dot, or the entire string if no dots
     */
    private static final String getLastPart(String str) {
        int lastDot = str.lastIndexOf('.');
        return lastDot >= 0 ? str.substring(lastDot + 1) : str;
    }

    /**
     * Converts a single RLP encoded parameter to its Java type
     * @param param RLP encoded parameter
     * @param type Target Java type
     * @return Converted parameter
     * @throws Exception if type is not supported
     */
    private final static Object convertParam(RlpString param, Class<?> type) throws Exception {
        return ABI.decode(param, type);
    }

    /**
     * Encodes the method return value into RLP format
     * @param result The method return value
     * @return RLP encoded result
     * @throws Exception if return type is not supported
     */
    private final static byte[] encodeResult(Object result) throws Exception {
        return ABI.encode(result);
    }

    /**
     * afterCall is called after the call is made.
     * @return no return.
     */
    protected final static void afterCall() {
    }

    /**
     * Compute the method selector (first 4 bytes of keccak256 hash of the method signature)
     * @param methodSignature The method signature string (e.g. "transfer(address,uint256)")
     * @return The 4-byte method selector
     */
    private final static byte[] computeMethodSelector(String methodSignature) {
        // Convert string to UTF-8 byte array
        byte[] input = methodSignature.getBytes();
        
        System.out.println("input: "+ methodSignature);

        // Convert to int8 array
        //for (int i = 0; i < input.length; i++) {
        //    input[i] = (byte)(input[i] & 0xFF);  // Ensure unsigned conversion
        //}

        // Calculate keccak256 hash
        Keccak256 keccak256 = Keccak256.getKeccak256();
        byte[] hash = keccak256.sha3(input);
        System.out.println("hash: " + java.lang.types.Numeric.toHexString(hash));
        // Take first 4 bytes as selector
        byte[] selector = new byte[4];
        System.arraycopy(hash, 0, selector, 0, 4);
        return selector;
    }
}
