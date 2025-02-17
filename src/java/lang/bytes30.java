package java.lang;

public class bytes30 extends bytes {
    public static final bytes30 DEFAULT = new bytes30(new byte[30]);

    public static bytes30 valueOf(byte[] value) {
        return new bytes30(value);
    }

    public bytes30(byte[] value) {
        super(30, value);
    }
}
