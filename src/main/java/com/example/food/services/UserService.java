package com.example.food.services;

import com.example.food.dto.PasswordResetDto;
import com.example.food.dto.PasswordResetRequestDto;
import com.example.food.pojos.login.LoginRequestDto;
import com.example.food.restartifacts.BaseResponse;
import org.springframework.http.ResponseEntity;


public interface UserService {

    ResponseEntity<String> login(LoginRequestDto request);

    BaseResponse requestPassword(PasswordResetRequestDto passwordResetRequestModel);

    BaseResponse resetPassword(PasswordResetDto passwordResetModel);
}
