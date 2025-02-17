package java.lang;

public class bytes21 extends bytes {
    public static final bytes21 DEFAULT = new bytes21(new byte[21]);

    public static bytes21 valueOf   (byte[] value) {
        return new bytes21(value);
    }

    public bytes21(byte[] value) {
        super(21, value);
    }
}
