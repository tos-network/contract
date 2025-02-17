package java.lang;

public class bytes29 extends bytes {
    public static final bytes29 DEFAULT = new bytes29(new byte[29]);

    public static bytes29 valueOf(byte[] value) {
        return new bytes29(value);
    }

    public bytes29(byte[] value) {
        super(29, value);
    }
}
