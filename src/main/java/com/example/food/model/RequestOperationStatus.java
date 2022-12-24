package com.example.food.model;

public interface RequestOperationStatus {

    public default String Error(){
        return "Unsuccessful";
    }
}
