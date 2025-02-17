package java.lang;

public class bytes27 extends bytes {
    public static final bytes27 DEFAULT = new bytes27(new byte[27]);

    public static bytes27 valueOf(byte[] value) {
        return new bytes27(value);
    }

    public bytes27(byte[] value) {
        super(27, value);
    }
}
