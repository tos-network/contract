package java.lang;

public class bytes9 extends bytes {
    public static final bytes9 DEFAULT = new bytes9(new byte[9]);


    public static bytes9 valueOf(byte[] value) {
        return new bytes9(value);
    }

    public bytes9(byte[] value) {
        super(9, value);
    }
}
