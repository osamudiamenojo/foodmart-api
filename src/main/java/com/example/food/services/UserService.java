package com.example.food.services;

import com.example.food.pojos.UserRequest;
import com.example.food.pojos.login.LoginRequestDto;
import com.example.food.util.ApiResponse;
import org.springframework.http.ResponseEntity;

import javax.mail.MessagingException;
import javax.xml.bind.ValidationException;


public interface UserService {

    ResponseEntity<ApiResponse<Object>> signUp(UserRequest userRequest);
    ResponseEntity<String> login(LoginRequestDto request);

}
