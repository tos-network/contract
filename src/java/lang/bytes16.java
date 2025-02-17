package java.lang;

public class bytes16 extends bytes {
    public static final bytes16 DEFAULT = new bytes16(new byte[16]);

    public static bytes16 valueOf(byte[] value) {
        return new bytes16(value);
    }

    public bytes16(byte[] value) {
        super(16, value);
    }
}
