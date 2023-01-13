package com.example.food.services.serviceImpl;

import com.example.food.dto.EmailSenderDto;
import com.example.food.services.EmailService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Objects;

    @AllArgsConstructor
    @Service
    public class EmailServiceImpl implements EmailService {

        private JavaMailSender emailSender;
        private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
        @Override
        public void sendMail(EmailSenderDto emailSenderDto) {
            if (
                    (Objects.nonNull(emailSenderDto.getTo())) &&
                            (Objects.nonNull(emailSenderDto.getSubject())) &&
                            (Objects.nonNull(emailSenderDto.getContent()))
            ) {

                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(emailSenderDto.getTo());
                message.setSubject(emailSenderDto.getSubject());
                message.setText(emailSenderDto.getContent());
                emailSender.send(message);

                LOGGER.info("Mail has been sent");

            }
            LOGGER.error("Failed to send mail. Check the details you supplied " + String.valueOf(emailSenderDto));
        }

    }

