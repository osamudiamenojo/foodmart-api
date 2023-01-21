package com.example.food.controllers;

import com.example.food.pojos.CreateUserRequest;
import com.example.food.restartifacts.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.food.dto.EditUserDto;
import com.example.food.dto.PasswordResetDto;
import com.example.food.dto.PasswordResetRequestDto;
import com.example.food.services.UserService;
import org.springframework.http.HttpStatus;

import javax.validation.Valid;

@RequestMapping("/api/v1/auth")
@AllArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PutMapping("/edit-user")
    public ResponseEntity<BaseResponse> editUserDetails(@Valid @RequestBody EditUserDto editUserDto) {
        return new ResponseEntity<>(userService.editUserDetails(editUserDto), HttpStatus.ACCEPTED);
    }

    @PostMapping(path = "/password-reset-request")
    public BaseResponse requestReset(@RequestBody PasswordResetRequestDto passwordResetRequest) {
        return userService.requestPassword(passwordResetRequest);
    }

    @PostMapping(path = "/password-reset")
    public BaseResponse resetPassword(@RequestBody PasswordResetDto passwordReset) {
        return userService.resetPassword(passwordReset);
    }

    @PostMapping("/register")
    public BaseResponse createUser(@RequestBody CreateUserRequest createUserRequest){
        return userService.signUp(createUserRequest);
    }
}
