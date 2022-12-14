package com.example.food.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class OperationStatusModel {

    private String operationResult;
    private String operationName;

}
