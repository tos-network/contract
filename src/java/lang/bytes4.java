package java.lang;

public class bytes4 extends bytes {
    public static final bytes4 DEFAULT = new bytes4(new byte[4]);

    public static bytes4 valueOf(byte[] value) {
        return new bytes4(value);
    }

    public bytes4(byte[] value) {
        super(4, value);
    }
}
