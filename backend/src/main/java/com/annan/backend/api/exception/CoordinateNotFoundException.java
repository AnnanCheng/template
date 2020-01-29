package com.annan.backend.api.exception;

public class CoordinateNotFoundException extends Exception {

    public CoordinateNotFoundException(String message) {
        super(message);
    }

    public CoordinateNotFoundException(Throwable cause) {
        super(cause);
    }

    public CoordinateNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
