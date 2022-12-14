package com.example.food.service;

import com.example.food.dto.EmailSenderDto;

public interface EmailService {
    void sendMail(EmailSenderDto emailSenderDto);
}
