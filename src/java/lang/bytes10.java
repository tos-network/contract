package java.lang;

public class bytes10 extends bytes {
    public static final bytes10 DEFAULT = new bytes10(new byte[10]);


    public static bytes10 valueOf(byte[] value) {
        return new bytes10(value);
    }

    public bytes10(byte[] value) {
        super(10, value);
    }
}
