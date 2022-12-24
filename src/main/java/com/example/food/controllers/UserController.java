package com.example.food.controllers;

import com.example.food.dto.PasswordResetDto;
import com.example.food.dto.PasswordResetRequestDto;
import com.example.food.restartifacts.BaseResponse;
import com.example.food.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(path = "/password-reset-request")
    public BaseResponse requestReset(@RequestBody PasswordResetRequestDto passwordResetRequestModel) {
        return userService.requestPassword(passwordResetRequestModel);
    }

    @PostMapping(path = "/password-reset")
    public BaseResponse resetPassword(@RequestBody PasswordResetDto passwordResetDto) {
        return userService.resetPassword(passwordResetDto);
    }

}
