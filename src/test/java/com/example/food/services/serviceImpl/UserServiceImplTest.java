package com.example.food.services.serviceImpl;

import com.example.food.configurations.security.CustomUserDetailsService;
import com.example.food.configurations.security.JwtUtil;
import com.example.food.pojos.login.LoginRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private  AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private UserDetails userDetails;


    @Mock
    private Authentication authentication;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    LoginRequestDto loginRequestDto;


    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        loginRequestDto = new LoginRequestDto();
        loginRequestDto.setEmail("opeyemialbert20@gmail.com");
        loginRequestDto.setPassword("12345");
    }

    @Test
    public void login() {
        when(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword())))
                .thenReturn(authentication);
        when(customUserDetailsService.loadUserByUsername(anyString()))
                .thenReturn(userDetails);
        when(jwtUtil.generateToken(any(UserDetails.class))).thenReturn("ufhguoi355n5");

        ResponseEntity<String> response = userServiceImpl.login(loginRequestDto);
        assertNotNull(response);

        verify(customUserDetailsService, times(1)).loadUserByUsername(anyString());
    }
}