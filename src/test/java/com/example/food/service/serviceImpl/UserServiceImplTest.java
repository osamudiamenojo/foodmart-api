package com.example.food.service.serviceImpl;

import com.example.food.Enum.ResponseCodeEnum;
import com.example.food.configurations.security.CustomUserDetailsService;
import com.example.food.configurations.security.JwtUtil;
import com.example.food.dto.ConfirmRegistrationRequestDto;
import com.example.food.dto.EmailSenderDto;
import com.example.food.model.Cart;
import com.example.food.model.Users;
import com.example.food.model.Wallet;
import com.example.food.pojos.CreateUserRequest;
import com.example.food.dto.LoginRequestDto;
import com.example.food.repositories.CartRepository;
import com.example.food.repositories.UserRepository;
import com.example.food.repositories.WalletRepository;
import com.example.food.restartifacts.BaseResponse;
import com.example.food.services.EmailService;
import com.example.food.services.serviceImpl.UserServiceImpl;
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

import java.math.BigDecimal;
import java.util.Optional;

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
    WalletRepository walletRepository;
    @Mock
    CartRepository cartRepository;
    @Mock
    private EmailService emailService;
    @Mock
    EmailSenderDto emailSenderDto;

    @InjectMocks
    private UserServiceImpl userServiceImpl;
    private ResponseCodeUtil responseCodeUtil;
    private CreateUserRequest createUserRequest;
    private Users users;
    private Cart cart;
    LoginRequestDto loginRequestDto;
    ConfirmRegistrationRequestDto confirmRegistrationRequestDto;
    Wallet wallet;



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
        users.setConfirmationToken("adsfshdgfgkgkgjgkggk");

        cart = new Cart();
        cart.setUsers(users);

        responseCodeUtil = new ResponseCodeUtil();
        confirmRegistrationRequestDto = new ConfirmRegistrationRequestDto();
        confirmRegistrationRequestDto.setToken(users.getConfirmationToken());
        wallet = Wallet.builder()
                .user(users)
                .walletBalance(BigDecimal.valueOf(0)).build();
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
        when(cartRepository.save(cart)).thenReturn(cart);
        when(walletRepository.save(wallet)).thenReturn(wallet);
        doNothing().when(emailService).sendMail(isA(EmailSenderDto.class));
        emailService.sendMail(emailSenderDto);
        verify(emailService, times(1)).sendMail(emailSenderDto);
        BaseResponse baseResponse = userServiceImpl.signUp(createUserRequest);
        Assertions.assertThat(baseResponse.getDescription())
                .isEqualTo("You have successful registered. Check your email for verification link to validate your account");
    }
    @Test
    public void confirmRegistration(){
        when(userRepository.findByConfirmationToken(confirmRegistrationRequestDto.getToken())).thenReturn(Optional.of(users));
        when(userRepository.save(users)).thenReturn(users);
        when(walletRepository.save(wallet)).thenReturn(wallet);
        BaseResponse baseResponse = userServiceImpl.confirmRegistration(confirmRegistrationRequestDto);
        Assertions.assertThat(baseResponse.getDescription()).isEqualTo("Account verification successful");
    }
}

