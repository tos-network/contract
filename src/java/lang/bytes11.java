package java.lang;

public class bytes11 extends bytes {
    public static final bytes11 DEFAULT = new bytes11(new byte[11]);


    public static bytes11 valueOf(byte[] value) {
        return new bytes11(value);
    }

    public bytes11(byte[] value) {
        super(11, value);
    }
}
