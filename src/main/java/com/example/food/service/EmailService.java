package com.example.food.service;

import com.example.food.dto.EmailSenderDto;
import com.example.food.utils.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface EmailService {
    ResponseEntity<ApiResponse<Object>> sendMail(EmailSenderDto emailSenderDto);
}
