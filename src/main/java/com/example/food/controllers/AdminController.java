package com.example.food.controllers;

import com.example.food.dto.AdminPasswordResetDto;
import com.example.food.dto.AdminPasswordResetRequestDto;
import com.example.food.restartifacts.BaseResponse;
import com.example.food.services.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

    private AdminService adminService;


    @PostMapping("/password-reset-request")
    public BaseResponse adminRequestToken(@Valid @RequestBody AdminPasswordResetRequestDto adminPasswordResetRequestDto) {
        return adminService.adminRequestNewPassword(adminPasswordResetRequestDto);

    }

    @PostMapping("/password-reset")
    public BaseResponse adminResetPassword(@Valid @RequestBody AdminPasswordResetDto adminPasswordResetDto) {
        return adminService.adminResetPassword(adminPasswordResetDto);
    }
}
