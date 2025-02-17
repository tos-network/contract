package java.lang;

public class bytes26 extends bytes {
    public static final bytes26 DEFAULT = new bytes26(new byte[26]);

    public static bytes26 valueOf(byte[] value) {
        return new bytes26(value);
    }

    public bytes26(byte[] value) {
        super(26, value);
    }
}
