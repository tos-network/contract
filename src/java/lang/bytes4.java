package java.lang;

public class bytes4 extends bytes {
    public static final bytes4 DEFAULT = new bytes4(new byte[4]);

    public bytes4(byte[] value) {
        super(4, value);
    }
}
