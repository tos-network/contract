package java.lang;

public class bytes13 extends bytes {
    public static final bytes13 DEFAULT = new bytes13(new byte[13]);

    public static bytes13 valueOf(byte[] value) {
        return new bytes13(value);
    }

    public bytes13(byte[] value) {
        super(13, value);
    }
}
