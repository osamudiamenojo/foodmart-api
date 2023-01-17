package com.example.food.exceptions;

public class ResourceNotFoundException  extends RuntimeException{

    private String message;

    public ResourceNotFoundException(String message){
        super(message);
        this.message = message;
    }

    public ResourceNotFoundException(){
        super("Not Found");
        this.message = "Not Found";
    }
}
