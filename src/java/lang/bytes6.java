package java.lang;

public class bytes6 extends bytes {
    public static final bytes6 DEFAULT = new bytes6(new byte[6]);

    public static bytes6 valueOf(byte[] value) {
        return new bytes6(value);
    }

    public bytes6(byte[] value) {
        super(6, value);
    }
}
