package com.example.food.exception;

public class AuthenticationException extends RuntimeException {

    private final String message;

    public AuthenticationException() {
        super();
        this.message = "Authentication failed";
    }
    public AuthenticationException(String msg) {
        super(msg);
        this.message = msg;
    }
}

