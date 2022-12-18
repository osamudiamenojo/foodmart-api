package com.example.food.exception;

public class NotFoundException extends RuntimeException {

    private final String message;

    public NotFoundException() {
        super();
        this.message = "Not found";
    }

    ;

    public NotFoundException(String msg) {
        super(msg);
        this.message = msg;
    }
}

