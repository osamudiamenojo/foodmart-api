package com.example.food.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Data
public class PasswordResetModel {

    private String token;

    private String password;
}
