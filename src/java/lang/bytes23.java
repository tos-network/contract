package java.lang;

public class bytes23 extends bytes {
    public static final bytes23 DEFAULT = new bytes23(new byte[23]);

    public static bytes23 valueOf(byte[] value) {
        return new bytes23(value);
    }

    public bytes23(byte[] value) {
        super(23, value);
    }
}
