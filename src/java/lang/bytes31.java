package java.lang;

public class bytes31 extends bytes {
    public static final bytes31 DEFAULT = new bytes31(new byte[31]);

    public static bytes31 valueOf(byte[] value) {
        return new bytes31(value);
    }

    public bytes31(byte[] value) {
        super(31, value);
    }
}
