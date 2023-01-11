package com.example.food.services;

import com.example.food.dto.EditUserDto;
import com.example.food.pojos.login.LoginRequestDto;
import com.example.food.restartifacts.BaseResponse;
import org.springframework.http.ResponseEntity;


public interface UserService {

    ResponseEntity<String> login(LoginRequestDto request);

    BaseResponse editUserDetails(EditUserDto editUserDto);
}
