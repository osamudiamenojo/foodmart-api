package com.example.food.controllers;

import com.example.food.dto.EditUserDto;
import com.example.food.dto.PasswordResetDto;
import com.example.food.dto.PasswordResetRequestDto;
import com.example.food.restartifacts.BaseResponse;
import com.example.food.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/edit-user")
    public BaseResponse editUserDetails(@Valid @RequestBody EditUserDto editUserDto) {
        return userService.editUserDetails(editUserDto);
    }

    @PostMapping(path = "/password-reset-request")
    public BaseResponse requestReset(@RequestBody PasswordResetRequestDto passwordResetRequest) {
        return userService.requestPassword(passwordResetRequest);
    }

    @PostMapping(path = "/password-reset")
    public BaseResponse resetPassword(@RequestBody PasswordResetDto passwordReset) {
        return userService.resetPassword(passwordReset);
    }
}
