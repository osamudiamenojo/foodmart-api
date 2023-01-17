package com.example.food.exceptions;

public class ProductNotFoundException extends RuntimeException{

    private String debugMessage;

    public ProductNotFoundException(String message, String debugMessage){
        super(message);
        this.debugMessage = debugMessage;
    }

    public ProductNotFoundException(String message){
        super(message);
    }
}
