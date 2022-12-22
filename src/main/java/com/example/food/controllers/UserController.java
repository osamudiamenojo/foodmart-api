package com.example.food.controllers;

import com.example.food.pojos.CreateUserRequest;
import com.example.food.restartifacts.BaseResponse;
import com.example.food.services.serviceImpl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class UserController {
    @Autowired
    private UserServiceImpl userService;
    @PostMapping("/register")
    public BaseResponse createUser(@RequestBody CreateUserRequest createUserRequest){
        return userService.signUp(createUserRequest);
    }

}
