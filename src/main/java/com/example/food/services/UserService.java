package com.example.food.services;

import com.example.food.pojos.CreateUserRequest;
import com.example.food.dto.EditUserDto;
import com.example.food.dto.PasswordResetDto;
import com.example.food.dto.PasswordResetRequestDto;
import com.example.food.dto.LoginRequestDto;
import com.example.food.restartifacts.BaseResponse;
import org.springframework.http.ResponseEntity;


public interface UserService {

    BaseResponse signUp(CreateUserRequest createUserRequest);
    ResponseEntity<String> login(LoginRequestDto request);

    BaseResponse editUserDetails(EditUserDto editUserDto);

    BaseResponse requestPassword(PasswordResetRequestDto passwordResetRequest);

    BaseResponse resetPassword(PasswordResetDto passwordReset);
}
