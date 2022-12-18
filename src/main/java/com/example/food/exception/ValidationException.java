package com.example.food.exception;

public class ValidationException extends RuntimeException {

    private final String message;

    public ValidationException() {
        super();
        this.message = "Validation failed";
    }
    public ValidationException(String msg) {
        super(msg);
        this.message = msg;
    }
}

