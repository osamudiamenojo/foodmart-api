package com.example.food.services.serviceImpl;

import com.example.food.Enum.ResponseCodeEnum;
import com.example.food.configurations.security.CustomUserDetailsService;
import com.example.food.configurations.security.JwtUtil;
import com.example.food.dto.EmailSenderDto;
import com.example.food.model.Users;
import com.example.food.pojos.CreateUserRequest;
import com.example.food.pojos.login.LoginRequestDto;
import com.example.food.repositories.UserRepository;
import com.example.food.restartifacts.BaseResponse;
import com.example.food.services.EmailService;
import com.example.food.util.AppUtil;
import com.example.food.util.ResponseCodeUtil;
import org.assertj.core.api.Assertions;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;
    @Mock
    private AppUtil appUtil;//me

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private UserDetails userDetails;


    @Mock
    private Authentication authentication;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    UserRepository userRepository;
    @Mock
    private EmailService emailService;
    @Mock
    EmailSenderDto emailSenderDto;

    @InjectMocks
    private UserServiceImpl userServiceImpl;
    private ResponseCodeUtil responseCodeUtil;
    private CreateUserRequest createUserRequest;
    private Users users;
    LoginRequestDto loginRequestDto;



    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        loginRequestDto = new LoginRequestDto();
        loginRequestDto.setEmail("opeyemialbert20@gmail.com");
        loginRequestDto.setPassword("12345");

        createUserRequest = CreateUserRequest.builder()
                .firstName("Akeem")
                .lastName("Jaiyeade")
                .email("haykay364@gmail.com")
                .password("1234")
                .confirmPassword("1234")
                .build();
        users = new Users();
        users.setFirstName(createUserRequest.getFirstName());
        users.setLastName(createUserRequest.getLastName());
        users.setEmail(createUserRequest.getEmail());
        users.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));

        responseCodeUtil = new ResponseCodeUtil();
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
    
    @Test
    public void shouldReturnInvalidEmailAddressWhenUserTriesToRegisterWithInvalidEmail(){
        when(appUtil.validEmail(createUserRequest.getEmail())).thenReturn(false);
        BaseResponse baseResponse = userServiceImpl.signUp(createUserRequest);
        Assertions.assertThat(responseCodeUtil.updateResponseData(baseResponse, ResponseCodeEnum.ERROR_EMAIL_INVALID)
                .getDescription()).isEqualTo("Invalid email address.");
    }

    @Test
    public void shouldReturnUserAlreadyExistWhenUserTriesToSignUpWithAlreadyRegisteredEmail(){
        when(userRepository.existsByEmail(createUserRequest.getEmail())).thenReturn(true);
        BaseResponse baseResponse = userServiceImpl.signUp(createUserRequest);
        Assertions.assertThat(responseCodeUtil.updateResponseData(baseResponse, ResponseCodeEnum.ERROR_DUPLICATE_USER)
                .getDescription()).isEqualTo("User already exist.");
    }
    @Test
    public void signUp(){
        when(appUtil.validEmail(createUserRequest.getEmail())).thenReturn(true);
        when(userRepository.existsByEmail(createUserRequest.getEmail())).thenReturn(false);
        when(userRepository.save(users)).thenReturn(users);
        doNothing().when(emailService).sendMail(isA(EmailSenderDto.class));
        emailService.sendMail(emailSenderDto);
        verify(emailService, times(1)).sendMail(emailSenderDto);
        BaseResponse baseResponse = userServiceImpl.signUp(createUserRequest);
        Assertions.assertThat(baseResponse.getDescription())
                .isEqualTo("You have successful registered. Check your email for verification link to validate your account");
    }
}

