package java.lang.contract;

// SPDX-License-Identifier: MIT

public final class Error {
    // The message of the error.
    private final String message;
    
    public Error(String message) {
        this.message = message;
    }
    
    public Error(Exception e) {
        this.message = e.getMessage();
    }
    
    public String getMessage() { 
        return message;
    }
}