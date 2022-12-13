package com.example.food.controllers;

import com.example.food.dto.EmailSenderDto;
import com.example.food.service.EmailService;
import com.example.food.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/sendMail")
    public ResponseEntity<ApiResponse<Object>>  sendMail(@RequestBody EmailSenderDto emailSenderDto){
        return emailService.sendMail(emailSenderDto);
    }
}
