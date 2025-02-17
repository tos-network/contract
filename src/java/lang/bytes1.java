package java.lang;

public class bytes1 extends bytes {
    public static final bytes1 DEFAULT = new bytes1(new byte[1]);

    public static bytes1 valueOf(byte[] value) {
        return new bytes1(value);
    }

    public bytes1(byte[] value) {
        super(1, value);
    }
}
