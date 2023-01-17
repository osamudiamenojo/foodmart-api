package com.example.food.exceptions;

public class UnauthorizedOperationException extends RuntimeException{

    private  String debugMessage;

    public UnauthorizedOperationException(String message, String debugMessage){
        super(message);
        this.debugMessage = debugMessage;
    }

    public UnauthorizedOperationException(String message){
        super(message);
    }
}
