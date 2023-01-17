package com.example.food.exceptions;

import com.example.food.dto.ApiResponse;
import com.example.food.dto.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiResponse<String> resourceNotFound(ResourceNotFoundException ex){
        return new ApiResponse<>(ex.getMessage(), HttpStatus.NOT_FOUND, null);
    }

    @ExceptionHandler(UnauthorizedOperationException.class)
    public ResponseEntity<ErrorResponse> notPermittedToPerform(UnauthorizedOperationException ne){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setHttpStatus(HttpStatus.UNAUTHORIZED);
        errorResponse.setMessage(ne.getMessage());
        errorResponse.setDebugMessage("Only an Admin is allowed to perform this Operation");
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> productNotFound(ProductNotFoundException ne){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setHttpStatus(HttpStatus.NOT_FOUND);
        errorResponse.setMessage(ne.getMessage());
        errorResponse.setDebugMessage("User with provided Id already exist");
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
}
