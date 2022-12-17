package com.example.food.services;

import com.example.food.model.PasswordResetTokenEntity;
import com.example.food.pojos.login.LoginRequestDto;
import org.springframework.http.ResponseEntity;


public interface UserService {

    ResponseEntity<String> login(LoginRequestDto request);

    PasswordResetTokenEntity requestPasswordReset(String email);

    String resetPassword(String token, String password);



}
