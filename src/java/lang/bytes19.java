package java.lang;

public class bytes19 extends bytes {
    public static final bytes19 DEFAULT = new bytes19(new byte[19]);

    public static bytes19 valueOf(byte[] value) {
        return new bytes19(value);
    }

    public bytes19(byte[] value) {
        super(19, value);
    }
}
