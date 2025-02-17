package java.lang;

public class bytes15 extends bytes {
    public static final bytes15 DEFAULT = new bytes15(new byte[15]);

    public static bytes15 valueOf(byte[] value) {
        return new bytes15(value);
    }

    public bytes15(byte[] value) {
        super(15, value);
    }
}
