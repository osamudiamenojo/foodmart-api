package com.example.food.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Data
@Getter
@Setter
public class ErrorResponse {

    private HttpStatus httpStatus;
    private String message;
    private String debugMessage;

}
