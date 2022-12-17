package com.example.food.model;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
public class PasswordResetRequestModel {

    @NotBlank(message = "email not blank")
    private String email;

}
