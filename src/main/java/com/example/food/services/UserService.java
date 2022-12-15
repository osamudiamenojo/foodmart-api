package com.example.food.services;

import com.example.food.pojos.login.LoginRequestDto;
import org.springframework.http.ResponseEntity;


public interface UserService {

    ResponseEntity<String> login(LoginRequestDto request);

    boolean requestPasswordReset(String email);

    boolean resetPassword(String token, String password);


}
