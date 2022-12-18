package com.example.food.controllers;

import com.example.food.dto.EditUserDto;
import com.example.food.restartifacts.BaseResponse;
import com.example.food.services.UserService;
import com.example.food.services.serviceImpl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @PutMapping("/edit-user")
    public BaseResponse editUserDetails(@Valid @RequestBody EditUserDto editUserDto) {
        return userService.editUserDetails(editUserDto);
    }
}
