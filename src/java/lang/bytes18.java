package java.lang;

public class bytes18 extends bytes {
    public static final bytes18 DEFAULT = new bytes18(new byte[18]);

    public static bytes18 valueOf(byte[] value) {
        return new bytes18(value);
    }

    public bytes18(byte[] value) {
        super(18, value);
    }
}
