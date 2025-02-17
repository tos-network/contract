package java.lang;

import java.lang.rlp.RlpString;
import java.lang.rlp.RlpEncoder;

/**
 * Maps Solidity types to contract data types, allowing to use Java primitive types for numbers.
 */
public final class ABI {

    //ABI constructor
    private ABI() {}

    /**
     * Returns the web3j data type for the given type.
     *
     * @param type A Solidity type.
     * @param primitives Use Java primitive types to wrap contract parameters.
     * @return The web3j Java class to represent this Solidity type.
     */
    public static Class<?> getType(String type) {
        switch (type) {
            //address
            case "address":
                return address.class;
            // Dynamic bytes
            case "bytes":
                return bytes.class;
            // Single byte
            case "byte":
                return bytes1.class;
            // Fixed-size bytes
            case "bytes1": return bytes1.class;
            case "bytes2": return bytes2.class;
            case "bytes3": return bytes3.class;
            case "bytes4": return bytes4.class;
            case "bytes5": return bytes5.class;
            case "bytes6": return bytes6.class;
            case "bytes7": return bytes7.class;
            case "bytes8": return bytes8.class;
            case "bytes9": return bytes9.class;
            case "bytes10": return bytes10.class;
            case "bytes11": return bytes11.class;
            case "bytes12": return bytes12.class;
            case "bytes13": return bytes13.class;
            case "bytes14": return bytes14.class;
            case "bytes15": return bytes15.class;
            case "bytes16": return bytes16.class;
            case "bytes17": return bytes17.class;
            case "bytes18": return bytes18.class;
            case "bytes19": return bytes19.class;
            case "bytes20": return bytes20.class;
            case "bytes21": return bytes21.class;
            case "bytes22": return bytes22.class;
            case "bytes23": return bytes23.class;
            case "bytes24": return bytes24.class;
            case "bytes25": return bytes25.class;
            case "bytes26": return bytes26.class;
            case "bytes27": return bytes27.class;
            case "bytes28": return bytes28.class;
            case "bytes29": return bytes29.class;
            case "bytes30": return bytes30.class;
            case "bytes31": return bytes31.class;
            case "bytes32": return bytes32.class;
            
            // Boolean
            case "bool":
            case "boolean":
                return bool.class;

            // Basic integer types
            case "int":
                return int256.class;
            case "uint":
                return uint256.class;

            // Unsigned integers
            case "uint8": return uint8.class;   
            case "uint16": return uint16.class;
            case "uint24": return uint24.class;
            case "uint32": return uint32.class;
            case "uint40": return uint40.class; 
            case "uint48": return uint48.class;
            case "uint56": return uint56.class;
            case "uint64": return uint64.class; 
            case "uint72": return uint72.class;
            case "uint80": return uint80.class;
            case "uint88": return uint88.class;
            case "uint96": return uint96.class;
            case "uint104": return uint104.class;
            case "uint112": return uint112.class;
            case "uint120": return uint120.class;
            case "uint128": return uint128.class;
            case "uint136": return uint136.class;
            case "uint144": return uint144.class;
            case "uint152": return uint152.class;
            case "uint160": return uint160.class;
            case "uint168": return uint168.class;
            case "uint176": return uint176.class;
            case "uint184": return uint184.class;
            case "uint192": return uint192.class;
            case "uint200": return uint200.class;
            case "uint208": return uint208.class;
            case "uint216": return uint216.class;
            case "uint224": return uint224.class;
            case "uint232": return uint232.class;
            case "uint240": return uint240.class;
            case "uint248": return uint248.class;
            case "uint256": return uint256.class;
            
            // Signed integers
            case "int8": return int8.class;
            case "int16": return int16.class;
            case "int24": return int24.class;
            case "int32": return int32.class;
            case "int40": return int40.class;
            case "int48": return int48.class;
            case "int56": return int56.class;
            case "int64": return int64.class;
            case "int72": return int72.class;
            case "int80": return int80.class;
            case "int88": return int88.class;
            case "int96": return int96.class;
            case "int104": return int104.class;
            case "int112": return int112.class; 
            case "int120": return int120.class;
            case "int128": return int128.class;
            case "int136": return int136.class;
            case "int144": return int144.class;
            case "int152": return int152.class;
            case "int160": return int160.class;
            case "int168": return int168.class;
            case "int176": return int176.class;
            case "int184": return int184.class;
            case "int192": return int192.class;
            case "int200": return int200.class;
            case "int208": return int208.class;
            case "int216": return int216.class;
            case "int224": return int224.class;
            case "int232": return int232.class; 
            case "int240": return int240.class;     
            case "int248": return int248.class; 
            case "int256": return int256.class;
            
            // String
            case "string":
                return string.class;
            
            default:
                throw new UnsupportedOperationException(
                        "Unsupported contract parameter type encountered: " + type);
        }
    }

    /**
     * Converts an RLP string to the specified contract data type
     *
     * @param rlp The RLP string to convert
     * @param type The target contract data type
     * @return The converted value
     * @throws UnsupportedOperationException if the type is not supported
     */
    public static Object decode(RlpString rlp, Class<?> type) throws Exception {
        switch (type.getName()) {
            case "java.lang.address":
                return address.valueOf(rlp.asPositiveBigInteger());
                
            case "java.lang.bytes":
                return rlp.getBytes();
                
            case "java.lang.bool":
                return bool.valueOf(rlp.getBytes());

            case "boolean":
                return bool.valueOf(rlp.getBytes()[0] != 0);
                
            case "java.lang.string":
                return string.valueOf(rlp.asString());
                
            // Fixed-size bytes
            case "java.lang.bytes1": return bytes1.valueOf(rlp.getBytes());
            case "java.lang.bytes2": return bytes2.valueOf(rlp.getBytes());
            case "java.lang.bytes3": return bytes3.valueOf(rlp.getBytes());
            case "java.lang.bytes4": return bytes4.valueOf(rlp.getBytes());
            case "java.lang.bytes5": return bytes5.valueOf(rlp.getBytes());
            case "java.lang.bytes6": return bytes6.valueOf(rlp.getBytes());
            case "java.lang.bytes7": return bytes7.valueOf(rlp.getBytes());
            case "java.lang.bytes8": return bytes8.valueOf(rlp.getBytes());
            case "java.lang.bytes9": return bytes9.valueOf(rlp.getBytes());
            case "java.lang.bytes10": return bytes10.valueOf(rlp.getBytes());
            case "java.lang.bytes11": return bytes11.valueOf(rlp.getBytes());
            case "java.lang.bytes12": return bytes12.valueOf(rlp.getBytes());
            case "java.lang.bytes13": return bytes13.valueOf(rlp.getBytes());
            case "java.lang.bytes14": return bytes14.valueOf(rlp.getBytes());
            case "java.lang.bytes15": return bytes15.valueOf(rlp.getBytes());
            case "java.lang.bytes16": return bytes16.valueOf(rlp.getBytes());
            case "java.lang.bytes17": return bytes17.valueOf(rlp.getBytes());
            case "java.lang.bytes18": return bytes18.valueOf(rlp.getBytes());
            case "java.lang.bytes19": return bytes19.valueOf(rlp.getBytes());
            case "java.lang.bytes20": return bytes20.valueOf(rlp.getBytes());
            case "java.lang.bytes21": return bytes21.valueOf(rlp.getBytes());
            case "java.lang.bytes22": return bytes22.valueOf(rlp.getBytes());
            case "java.lang.bytes23": return bytes23.valueOf(rlp.getBytes());
            case "java.lang.bytes24": return bytes24.valueOf(rlp.getBytes());
            case "java.lang.bytes25": return bytes25.valueOf(rlp.getBytes());
            case "java.lang.bytes26": return bytes26.valueOf(rlp.getBytes());
            case "java.lang.bytes27": return bytes27.valueOf(rlp.getBytes());
            case "java.lang.bytes28": return bytes28.valueOf(rlp.getBytes());
            case "java.lang.bytes29": return bytes29.valueOf(rlp.getBytes());
            case "java.lang.bytes30": return bytes30.valueOf(rlp.getBytes());
            case "java.lang.bytes31": return bytes31.valueOf(rlp.getBytes());
            case "java.lang.bytes32": return bytes32.valueOf(rlp.getBytes());
            
            // Unsigned integers
            case "java.lang.uint8": return uint8.valueOf(rlp.getBytes());    
            case "java.lang.uint16": return uint16.valueOf(rlp.getBytes());
            case "java.lang.uint24": return uint24.valueOf(rlp.getBytes());
            case "java.lang.uint32": return uint32.valueOf(rlp.getBytes());
            case "java.lang.uint40": return uint40.valueOf(rlp.getBytes());
            case "java.lang.uint48": return uint48.valueOf(rlp.getBytes()); 
            case "java.lang.uint56": return uint56.valueOf(rlp.getBytes());
            case "java.lang.uint64": return uint64.valueOf(rlp.getBytes());
            case "java.lang.uint72": return uint72.valueOf(rlp.getBytes());
            case "java.lang.uint80": return uint80.valueOf(rlp.getBytes()); 
            case "java.lang.uint88": return uint88.valueOf(rlp.getBytes());
            case "java.lang.uint96": return uint96.valueOf(rlp.getBytes());
            case "java.lang.uint104": return uint104.valueOf(rlp.getBytes());
            case "java.lang.uint112": return uint112.valueOf(rlp.getBytes());
            case "java.lang.uint120": return uint120.valueOf(rlp.getBytes());
            case "java.lang.uint128": return uint128.valueOf(rlp.getBytes());
            case "java.lang.uint136": return uint136.valueOf(rlp.getBytes());
            case "java.lang.uint144": return uint144.valueOf(rlp.getBytes());
            case "java.lang.uint152": return uint152.valueOf(rlp.getBytes());
            case "java.lang.uint160": return uint160.valueOf(rlp.getBytes());
            case "java.lang.uint168": return uint168.valueOf(rlp.getBytes());
            case "java.lang.uint176": return uint176.valueOf(rlp.getBytes());
            case "java.lang.uint184": return uint184.valueOf(rlp.getBytes());
            case "java.lang.uint192": return uint192.valueOf(rlp.getBytes());
            case "java.lang.uint200": return uint200.valueOf(rlp.getBytes());   
            case "java.lang.uint208": return uint208.valueOf(rlp.getBytes());
            case "java.lang.uint216": return uint216.valueOf(rlp.getBytes());
            case "java.lang.uint224": return uint224.valueOf(rlp.getBytes());
            case "java.lang.uint232": return uint232.valueOf(rlp.getBytes());
            case "java.lang.uint240": return uint240.valueOf(rlp.getBytes());
            case "java.lang.uint248": return uint248.valueOf(rlp.getBytes());
            case "java.lang.uint256": return uint256.valueOf(rlp.getBytes());
            
            // Signed integers
            case "java.lang.int8": return int8.valueOf(rlp.getBytes());
            case "java.lang.int16": return int16.valueOf(rlp.getBytes());
            case "java.lang.int24": return int24.valueOf(rlp.getBytes());
            case "java.lang.int32": return int32.valueOf(rlp.getBytes());
            case "java.lang.int40": return int40.valueOf(rlp.getBytes());
            case "java.lang.int48": return int48.valueOf(rlp.getBytes());
            case "java.lang.int56": return int56.valueOf(rlp.getBytes());
            case "java.lang.int64": return int64.valueOf(rlp.getBytes());
            case "java.lang.int72": return int72.valueOf(rlp.getBytes());
            case "java.lang.int80": return int80.valueOf(rlp.getBytes());
            case "java.lang.int88": return int88.valueOf(rlp.getBytes());
            case "java.lang.int96": return int96.valueOf(rlp.getBytes());
            case "java.lang.int104": return int104.valueOf(rlp.getBytes());
            case "java.lang.int112": return int112.valueOf(rlp.getBytes());
            case "java.lang.int120": return int120.valueOf(rlp.getBytes());
            case "java.lang.int128": return int128.valueOf(rlp.getBytes());
            case "java.lang.int136": return int136.valueOf(rlp.getBytes());
            case "java.lang.int144": return int144.valueOf(rlp.getBytes());
            case "java.lang.int152": return int152.valueOf(rlp.getBytes());
            case "java.lang.int160": return int160.valueOf(rlp.getBytes());
            case "java.lang.int168": return int168.valueOf(rlp.getBytes());
            case "java.lang.int176": return int176.valueOf(rlp.getBytes());
            case "java.lang.int184": return int184.valueOf(rlp.getBytes());
            case "java.lang.int192": return int192.valueOf(rlp.getBytes());
            case "java.lang.int200": return int200.valueOf(rlp.getBytes());
            case "java.lang.int208": return int208.valueOf(rlp.getBytes());
            case "java.lang.int216": return int216.valueOf(rlp.getBytes());
            case "java.lang.int224": return int224.valueOf(rlp.getBytes());
            case "java.lang.int232": return int232.valueOf(rlp.getBytes());
            case "java.lang.int240": return int240.valueOf(rlp.getBytes());
            case "java.lang.int248": return int248.valueOf(rlp.getBytes());
            case "java.lang.int256": return int256.valueOf(rlp.getBytes());

            default:
                throw new UnsupportedOperationException(
                    "Unsupported type for RLP decoding: " + type.getName());
        }
    }

    /**
     * Encodes a Java object to RLP format byte array
     *
     * @param value The value to encode
     * @return The RLP encoded byte array
     * @throws UnsupportedOperationException if the type is not supported
     */
    public static byte[] encode(Object value) {
        if (value == null) {
            return new byte[0];
        }

        switch (value.getClass().getName()) {
            case "java.lang.address":
                return RlpEncoder.encode(((address) value).toByteArray(), 0);
                
            case "java.lang.bytes":
            case "[B":  // byte[] array
                return RlpEncoder.encode(RlpString.create((byte[]) value));
                
            case "java.lang.bool":
            case "boolean":
                return RlpEncoder.encode(RlpString.create(((Boolean) value) ? 1 : 0));
            case "java.lang.String":
            case "java.lang.string":
                return RlpEncoder.encode(RlpString.create((String) value));
            // Fixed-size bytes
            case "java.lang.bytes1": return RlpEncoder.encode(((bytes1) value).getValue(),0);   
            case "java.lang.bytes2": return RlpEncoder.encode(((bytes2) value).getValue(),0);
            case "java.lang.bytes3": return RlpEncoder.encode(((bytes3) value).getValue(),0);
            case "java.lang.bytes4": return RlpEncoder.encode(((bytes4) value).getValue(),0);
            case "java.lang.bytes5": return RlpEncoder.encode(((bytes5) value).getValue(),0);
            case "java.lang.bytes6": return RlpEncoder.encode(((bytes6) value).getValue(),0);
            case "java.lang.bytes7": return RlpEncoder.encode(((bytes7) value).getValue(),0);
            case "java.lang.bytes8": return RlpEncoder.encode(((bytes8) value).getValue(),0);   
            case "java.lang.bytes9": return RlpEncoder.encode(((bytes9) value).getValue(),0);
            case "java.lang.bytes10": return RlpEncoder.encode(((bytes10) value).getValue(),0);     
            case "java.lang.bytes11": return RlpEncoder.encode(((bytes11) value).getValue(),0);
            case "java.lang.bytes12": return RlpEncoder.encode(((bytes12) value).getValue(),0);     
            case "java.lang.bytes13": return RlpEncoder.encode(((bytes13) value).getValue(),0); 
            case "java.lang.bytes14": return RlpEncoder.encode(((bytes14) value).getValue(),0); 
            case "java.lang.bytes15": return RlpEncoder.encode(((bytes15) value).getValue(),0);     
            case "java.lang.bytes16": return RlpEncoder.encode(((bytes16) value).getValue(),0); 
            case "java.lang.bytes17": return RlpEncoder.encode(((bytes17) value).getValue(),0); 
            case "java.lang.bytes18": return RlpEncoder.encode(((bytes18) value).getValue(),0);
            case "java.lang.bytes19": return RlpEncoder.encode(((bytes19) value).getValue(),0);
            case "java.lang.bytes20": return RlpEncoder.encode(((bytes20) value).getValue(),0);
            case "java.lang.bytes21": return RlpEncoder.encode(((bytes21) value).getValue(),0);
            case "java.lang.bytes22": return RlpEncoder.encode(((bytes22) value).getValue(),0);
            case "java.lang.bytes23": return RlpEncoder.encode(((bytes23) value).getValue(),0);
            case "java.lang.bytes24": return RlpEncoder.encode(((bytes24) value).getValue(),0);
            case "java.lang.bytes25": return RlpEncoder.encode(((bytes25) value).getValue(),0);
            case "java.lang.bytes26": return RlpEncoder.encode(((bytes26) value).getValue(),0);
            case "java.lang.bytes27": return RlpEncoder.encode(((bytes27) value).getValue(),0);
            case "java.lang.bytes28": return RlpEncoder.encode(((bytes28) value).getValue(),0);
            case "java.lang.bytes29": return RlpEncoder.encode(((bytes29) value).getValue(),0);
            case "java.lang.bytes30": return RlpEncoder.encode(((bytes30) value).getValue(),0);
            case "java.lang.bytes31": return RlpEncoder.encode(((bytes31) value).getValue(),0);
            case "java.lang.bytes32": return RlpEncoder.encode(((bytes32) value).getValue(),0);
            // Unsigned integers
            case "java.lang.uint8": return RlpEncoder.encode(((uint8) value).toByteArray(),0);
            case "java.lang.uint16": return RlpEncoder.encode(((uint16) value).toByteArray(),0);
            case "java.lang.uint24": return RlpEncoder.encode(((uint24) value).toByteArray(),0);
            case "java.lang.uint32": return RlpEncoder.encode(((uint32) value).toByteArray(),0);
            case "java.lang.uint40": return RlpEncoder.encode(((uint40) value).toByteArray(),0);
            case "java.lang.uint48": return RlpEncoder.encode(((uint48) value).toByteArray(),0);
            case "java.lang.uint56": return RlpEncoder.encode(((uint56) value).toByteArray(),0);
            case "java.lang.uint64": return RlpEncoder.encode(((uint64) value).toByteArray(),0);
            case "java.lang.uint72": return RlpEncoder.encode(((uint72) value).toByteArray(),0);
            case "java.lang.uint80": return RlpEncoder.encode(((uint80) value).toByteArray(),0);
            case "java.lang.uint88": return RlpEncoder.encode(((uint88) value).toByteArray(),0);
            case "java.lang.uint96": return RlpEncoder.encode(((uint96) value).toByteArray(),0);
            case "java.lang.uint104": return RlpEncoder.encode(((uint104) value).toByteArray(),0);
            case "java.lang.uint112": return RlpEncoder.encode(((uint112) value).toByteArray(),0);
            case "java.lang.uint120": return RlpEncoder.encode(((uint120) value).toByteArray(),0);
            case "java.lang.uint128": return RlpEncoder.encode(((uint128) value).toByteArray(),0);
            case "java.lang.uint136": return RlpEncoder.encode(((uint136) value).toByteArray(),0);
            case "java.lang.uint144": return RlpEncoder.encode(((uint144) value).toByteArray(),0);
            case "java.lang.uint152": return RlpEncoder.encode(((uint152) value).toByteArray(),0);
            case "java.lang.uint160": return RlpEncoder.encode(((uint160) value).toByteArray(),0); 
            case "java.lang.uint168": return RlpEncoder.encode(((uint168) value).toByteArray(),0);
            case "java.lang.uint176": return RlpEncoder.encode(((uint176) value).toByteArray(),0);
            case "java.lang.uint184": return RlpEncoder.encode(((uint184) value).toByteArray(),0);
            case "java.lang.uint192": return RlpEncoder.encode(((uint192) value).toByteArray(),0);
            case "java.lang.uint200": return RlpEncoder.encode(((uint200) value).toByteArray(),0);
            case "java.lang.uint208": return RlpEncoder.encode(((uint208) value).toByteArray(),0);
            case "java.lang.uint216": return RlpEncoder.encode(((uint216) value).toByteArray(),0);
            case "java.lang.uint224": return RlpEncoder.encode(((uint224) value).toByteArray(),0);
            case "java.lang.uint232": return RlpEncoder.encode(((uint232) value).toByteArray(),0);
            case "java.lang.uint240": return RlpEncoder.encode(((uint240) value).toByteArray(),0);
            case "java.lang.uint248": return RlpEncoder.encode(((uint248) value).toByteArray(),0);
            case "java.lang.uint256": return RlpEncoder.encode(((uint256) value).toByteArray(),0);
            
            // Signed integers
            case "java.lang.int8": return RlpEncoder.encode(((int8) value).toByteArray(),0);
            case "java.lang.int16": return RlpEncoder.encode(((int16) value).toByteArray(),0);
            case "java.lang.int24": return RlpEncoder.encode(((int24) value).toByteArray(),0);
            case "java.lang.int32": return RlpEncoder.encode(((int32) value).toByteArray(),0);
            case "java.lang.int40": return RlpEncoder.encode(((int40) value).toByteArray(),0);
            case "java.lang.int48": return RlpEncoder.encode(((int48) value).toByteArray(),0);
            case "java.lang.int56": return RlpEncoder.encode(((int56) value).toByteArray(),0);
            case "java.lang.int64": return RlpEncoder.encode(((int64) value).toByteArray(),0);
            case "java.lang.int72": return RlpEncoder.encode(((int72) value).toByteArray(),0);
            case "java.lang.int80": return RlpEncoder.encode(((int80) value).toByteArray(),0);
            case "java.lang.int88": return RlpEncoder.encode(((int88) value).toByteArray(),0);
            case "java.lang.int96": return RlpEncoder.encode(((int96) value).toByteArray(),0);
            case "java.lang.int104": return RlpEncoder.encode(((int104) value).toByteArray(),0);
            case "java.lang.int112": return RlpEncoder.encode(((int112) value).toByteArray(),0);
            case "java.lang.int120": return RlpEncoder.encode(((int120) value).toByteArray(),0);
            case "java.lang.int128": return RlpEncoder.encode(((int128) value).toByteArray(),0);
            case "java.lang.int136": return RlpEncoder.encode(((int136) value).toByteArray(),0);
            case "java.lang.int144": return RlpEncoder.encode(((int144) value).toByteArray(),0);
            case "java.lang.int152": return RlpEncoder.encode(((int152) value).toByteArray(),0);
            case "java.lang.int160": return RlpEncoder.encode(((int160) value).toByteArray(),0);
            case "java.lang.int168": return RlpEncoder.encode(((int168) value).toByteArray(),0);
            case "java.lang.int176": return RlpEncoder.encode(((int176) value).toByteArray(),0);
            case "java.lang.int184": return RlpEncoder.encode(((int184) value).toByteArray(),0);
            case "java.lang.int192": return RlpEncoder.encode(((int192) value).toByteArray(),0);
            case "java.lang.int200": return RlpEncoder.encode(((int200) value).toByteArray(),0);
            case "java.lang.int208": return RlpEncoder.encode(((int208) value).toByteArray(),0);
            case "java.lang.int216": return RlpEncoder.encode(((int216) value).toByteArray(),0);   
            case "java.lang.int224": return RlpEncoder.encode(((int224) value).toByteArray(),0);
            case "java.lang.int232": return RlpEncoder.encode(((int232) value).toByteArray(),0);   
            case "java.lang.int240": return RlpEncoder.encode(((int240) value).toByteArray(),0);
            case "java.lang.int248": return RlpEncoder.encode(((int248) value).toByteArray(),0);
            case "java.lang.int256": return RlpEncoder.encode(((int256) value).toByteArray(),0);

            default:
                throw new UnsupportedOperationException(
                    "Unsupported type for RLP encoding: " + value.getClass().getName());
        }
    }
}
