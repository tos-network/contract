package java.lang;

public class bytes14 extends bytes {
    public static final bytes14 DEFAULT = new bytes14(new byte[14]);


    public static bytes14 valueOf(byte[] value) {
        return new bytes14(value);
    }

    public bytes14(byte[] value) {
        super(14, value);
    }
}
