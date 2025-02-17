package java.lang;

public class bytes3 extends bytes {
    public static final bytes3 DEFAULT = new bytes3(new byte[3]);

    public static bytes3 valueOf(byte[] value) {
        return new bytes3(value);
    }

    public bytes3(byte[] value) {
        super(3, value);
    }
}
