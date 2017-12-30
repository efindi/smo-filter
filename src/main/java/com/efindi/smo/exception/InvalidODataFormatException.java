package com.efindi.smo.exception;

public class InvalidODataFormatException extends Exception {
    public InvalidODataFormatException() {
        super();
    }

    public InvalidODataFormatException(String message) {
        super(message);
    }

    public InvalidODataFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidODataFormatException(Throwable cause) {
        super(cause);
    }

    protected InvalidODataFormatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
