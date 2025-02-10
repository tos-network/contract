package java.lang;

public class bytes16 extends bytes {
    public static final bytes16 DEFAULT = new bytes16(new byte[16]);

    public bytes16(byte[] value) {
        super(16, value);
    }
}
