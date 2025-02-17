package java.lang;

public class bytes32 extends bytes {
    public static final bytes32 DEFAULT = new bytes32(new byte[32]);

    public static bytes32 valueOf(byte[] value) {
        return new bytes32(value);
    }

    public bytes32(byte[] value) {
        super(32, value);
    }
}
