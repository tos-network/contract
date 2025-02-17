package java.lang;

public class bytes12 extends bytes {
    public static final bytes12 DEFAULT = new bytes12(new byte[12]);


    public static bytes12 valueOf(byte[] value) {
        return new bytes12(value);
    }

    public bytes12(byte[] value) {
        super(12, value);
    }
}
