package com.example.food.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {

    private boolean status;

    private String message;

    private T payLoad;



}
