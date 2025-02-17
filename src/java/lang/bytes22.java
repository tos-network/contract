package java.lang;

public class bytes22 extends bytes {
    public static final bytes22 DEFAULT = new bytes22(new byte[22]);

    public static bytes22 valueOf(byte[] value) {
        return new bytes22(value);
    }

    public bytes22(byte[] value) {
        super(22, value);
    }
}
