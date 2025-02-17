package java.lang;

public class bytes20 extends bytes {
    public static final bytes20 DEFAULT = new bytes20(new byte[20]);

    public static bytes20 valueOf(byte[] value) {
        return new bytes20(value);
    }

    public bytes20(byte[] value) {
        super(20, value);
    }
}
