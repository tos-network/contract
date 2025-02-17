package java.lang;

public class bytes28 extends bytes {
    public static final bytes28 DEFAULT = new bytes28(new byte[28]);

    public static bytes28 valueOf(byte[] value) {
        return new bytes28(value);
    }

    public bytes28(byte[] value) {
        super(28, value);
    }
}
