package java.lang;

public class bytes2 extends bytes {
    public static final bytes2 DEFAULT = new bytes2(new byte[2]);

    public static bytes2 valueOf(byte[] value) {
        return new bytes2(value);
    }

    public bytes2(byte[] value) {
        super(2, value);
    }
}
