package com.example.food.pojos;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link com.example.food.model.Users} entity
 */
@Data
@Builder
public class UserRequest implements Serializable {
    private  String firstName;
    private  String lastName;
    private  String email;
    private  String password;
    private  String confirmPassword;
}