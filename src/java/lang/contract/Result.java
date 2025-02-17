package java.lang.contract;

// SPDX-License-Identifier: MIT

public final class Result<T> {
    // The value of the result.
    public final T value;
    // The error of the result.
    public final Error err;
    
    // The constructor of the Result.
    private Result(T value, Error err) {
        this.value = value;
        this.err = err;
    }

    // The static method to create a Result with a value.
    public static <T> Result<T> ok(T value) {
        return new Result<>(value, null);
    }

    // The static method to create a Result with an error.
    public static <T> Result<T> fail(Error err) {
        return new Result<>(null, err);
    }

    // The static method to create a Result with an error message.
    public static <T> Result<T> fail(String message) {
        return new Result<>(null, new Error(message));
    }

    public boolean isSuccess() {
        return err == null;
    }

    public boolean isFailure() {
        return err != null;
    }

    public String getErrorMessage() {
        return err != null ? err.getMessage() : null;
    }
}