package com.example.food.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class PasswordResetModel {
    private String token;
    private String password;
}
