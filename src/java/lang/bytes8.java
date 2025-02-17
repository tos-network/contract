package java.lang;

public class bytes8 extends bytes {
    public static final bytes8 DEFAULT = new bytes8(new byte[8]);

    public static bytes8 valueOf(byte[] value) {
        return new bytes8(value);
    }

    public bytes8(byte[] value) {
        super(8, value);
    }
}
