package com.example.food.utils;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ResponseProvider {
    public ResponseEntity<ApiResponse<Object>> success(HttpStatus httpStatus,String message, Object payload) {
        return ResponseEntity
                .status(httpStatus)
                .body(new ApiResponse<>(true, message, payload));
    }
    public ResponseEntity<ApiResponse<Object>> failed(HttpStatus httpStatus,String message, Object payload) {
        return ResponseEntity
                .status(httpStatus)
                .body(new ApiResponse<>(false, message, payload));
    }
}
