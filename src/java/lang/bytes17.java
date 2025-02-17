package java.lang;

public class bytes17 extends bytes {
    public static final bytes17 DEFAULT = new bytes17(new byte[17]);

    public static bytes17 valueOf(byte[] value) {
        return new bytes17(value);
    }

    public bytes17(byte[] value) {
        super(17, value);
    }
}
