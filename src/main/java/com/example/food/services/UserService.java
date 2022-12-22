package com.example.food.services;

import com.example.food.pojos.CreateUserRequest;
import com.example.food.pojos.login.LoginRequestDto;
import com.example.food.restartifacts.BaseResponse;
import org.springframework.http.ResponseEntity;


public interface UserService {

    BaseResponse signUp(CreateUserRequest createUserRequest);
    ResponseEntity<String> login(LoginRequestDto request);

}
