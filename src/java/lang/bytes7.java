package java.lang;

public class bytes7 extends bytes {
    public static final bytes7 DEFAULT = new bytes7(new byte[7]);


    public static bytes7 valueOf(byte[] value) {
        return new bytes7(value);
    }

    public bytes7(byte[] value) {
        super(7, value);
    }
}
