package com.example.food.controllers;

import com.example.food.model.PasswordResetModel;
import com.example.food.model.PasswordResetRequestModel;
import com.example.food.model.PasswordResetTokenEntity;
import com.example.food.services.UserService;
import com.example.food.services.serviceImpl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping(path = "/password-reset-request")
    public PasswordResetTokenEntity requestReset(@RequestBody PasswordResetRequestModel passwordResetRequestModel) {
        return userService.requestPasswordReset(passwordResetRequestModel.getEmail());
    }

    @PostMapping(path = "/password-reset")
    public String resetPassword(@RequestBody PasswordResetModel passwordResetModel) {
        return userService.resetPassword(passwordResetModel.getToken(), passwordResetModel.getPassword());
    }

}
