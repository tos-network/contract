package java.lang;

import java.lang.types.BytesType;

/** Statically allocated sequence of bytes. */
public class bytes extends BytesType {

    public static final String TYPE_NAME = "bytes";

    protected bytes(int byteSize, byte[] value) {
        super(value, TYPE_NAME + value.length);
        if (!isValid(byteSize)) {
            throw new UnsupportedOperationException(
                    "Input byte array must be in range 0 < M <= 32 and length must match type");
        }
    }

    private boolean isValid(int byteSize) {
        int length = getValue().length;
        return length > 0 && length <= 32 && length == byteSize;
    }
}
