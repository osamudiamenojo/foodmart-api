package com.example.food.service.serviceImpl;

import com.example.food.Enum.Role;
import com.example.food.configurations.security.CustomUserDetailsService;
import com.example.food.configurations.security.JwtUtil;
import com.example.food.dto.AdminPasswordResetRequestDto;
import com.example.food.dto.EmailSenderDto;
import com.example.food.model.Users;
import com.example.food.repositories.AdminPasswordResetTokenRepository;
import com.example.food.repositories.UserRepository;
import com.example.food.restartifacts.BaseResponse;
import com.example.food.services.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {
    @Mock
    private AdminPasswordResetTokenRepository adminPasswordResetTokenRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    private CustomUserDetailsService customUserDetailsService;
    @Mock
    EmailService emailService;
    @InjectMocks
    private AdminServiceImpl adminService;
    Users user1;
    AdminPasswordResetRequestDto adminPasswordResetRequestDto;
    String token = "newtoken";

    @BeforeEach
    void setup() {
        JwtUtil jwt = new JwtUtil();
        UserDetails mockedUserDetails = Mockito.mock(UserDetails.class);
        user1 = Users.builder()
                .email("okoro@gmail.com")
                .role(Role.ROLE_ADMIN).build();

        adminPasswordResetRequestDto = AdminPasswordResetRequestDto.builder().email("okoro@gmail.com").build();

        EmailSenderDto emailSenderDto = new EmailSenderDto();
        emailSenderDto.setTo(adminPasswordResetRequestDto.getEmail());
        emailSenderDto.setSubject("Admin Password Reset Link");
        emailSenderDto.setContent("http://localhost:8080/admin/reset-password/" + token);

        when(jwt.generateToken(mockedUserDetails)).thenReturn(token);
//        when(userRepository.findByEmail((adminPasswordResetRequestDto.getEmail()))).thenReturn(Optional.ofNullable(user1));
        when(customUserDetailsService.loadUserByUsername(anyString())).thenReturn(mockedUserDetails);
    }
    @Test
    void adminRequestNewPassword() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.ofNullable(user1));
        doNothing().when(emailService).sendMail(any(EmailSenderDto.class));

        BaseResponse response = adminService.adminRequestNewPassword(adminPasswordResetRequestDto);
        assertEquals(response.getDescription(), "Please check your email for a password reset link.");
    }
}
