package java.lang;

import java.lang.types.Type;

/** Boolean type. */
public class bool implements Type<Boolean> {

    public static final String TYPE_NAME = "bool";
    public static final bool DEFAULT = new bool(false);
    public static final bool TRUE = new bool(true);
    public static final bool FALSE = new bool(false);

    private boolean value;

    public static bool valueOf(boolean value) {
        return new bool(value);
    }

    public static bool valueOf(byte[] value) {
        if (value.length != 1) {
           return new bool(false);
        }
        return new bool(value[0] != 0);
    }

    public bool(boolean value) {
        this.value = value;
    }

    public bool(Boolean value) {
        this.value = value;
    }

    @Override
    public String getTypeAsString() {
        return TYPE_NAME;
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        bool b = (bool) o;

        return value == b.value;
    }

    @Override
    public int hashCode() {
        return (value ? 1 : 0);
    }

    public boolean booleanValue() {
        return value;
    }

    @Override
    public String toString() {
        return Boolean.toString(value);
    }
}
