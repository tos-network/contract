package java.lang;

public class bytes5 extends bytes {
    public static final bytes5 DEFAULT = new bytes5(new byte[5]);

    public static bytes5 valueOf(byte[] value) {
        return new bytes5(value);
    }

    public bytes5(byte[] value) {
        super(5, value);
    }
}
