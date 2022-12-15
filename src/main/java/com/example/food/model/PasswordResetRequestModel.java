package com.example.food.model;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetRequestModel {

    private String email;

}
