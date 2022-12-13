package com.example.food.service;

import com.example.food.dto.EmailSenderDto;
import com.example.food.utils.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface EmailService {
    void sendMail(EmailSenderDto emailSenderDto);
}
