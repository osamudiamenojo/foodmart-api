package com.example.food.controllers;

import com.example.food.pojos.UserRequest;
import com.example.food.services.serviceImpl.UserServiceImpl;
import com.example.food.util.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@Data
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class UserController {
    private final UserServiceImpl userService;
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Object>> createUser(@RequestBody UserRequest userRequest){
        return userService.signUp(userRequest);
    }

}
