package java.lang;

public class bytes25 extends bytes {
    public static final bytes25 DEFAULT = new bytes25(new byte[25]);

    public static bytes25 valueOf(byte[] value) {
        return new bytes25(value);
    }

    public bytes25(byte[] value) {
        super(25, value);
    }
}
