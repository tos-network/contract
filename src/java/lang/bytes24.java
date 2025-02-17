package java.lang;

public class bytes24 extends bytes {
    public static final bytes24 DEFAULT = new bytes24(new byte[24]);

    public static bytes24 valueOf(byte[] value) {
        return new bytes24(value);
    }

    public bytes24(byte[] value) {
        super(24, value);
    }
}
