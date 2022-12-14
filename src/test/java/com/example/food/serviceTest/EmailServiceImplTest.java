package com.example.food.serviceTest;

import com.example.food.dto.EmailSenderDto;
import com.example.food.service.EmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceImplTest {
    @Test
    public void whenSendMailCalledVerified(){

        EmailService email = mock(EmailService.class);
        EmailSenderDto emailDto = mock(EmailSenderDto.class);

        doNothing().when(email).sendMail(isA(EmailSenderDto.class));
        email.sendMail(emailDto);
        verify(email, times(1)).sendMail(emailDto);
    }

}