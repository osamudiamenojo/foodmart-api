package com.example.food.services.serviceImpl;

import com.example.food.Enum.ResponseCodeEnum;
import com.example.food.model.Users;
import com.example.food.model.Wallet;
import com.example.food.repositories.UserRepository;
import com.example.food.repositories.WalletRepository;
import com.example.food.restartifacts.BaseResponse;
import com.example.food.util.ResponseCodeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WalletServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private WalletRepository walletRepository;
    @Mock
    private ResponseCodeUtil responseCodeUtil;
    @InjectMocks
    private WalletServiceImpl walletService;
    private Users user;
    private Wallet wallet;
    Authentication authentication;
    private BaseResponse baseResponse1, baseResponse2;

    @BeforeEach
    void setUp() {
        authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        user = Users.builder()
                .usersId(1L)
                .email("mensa@gmail.com")
                .password("password")
                .build();
        wallet = Wallet.builder()
                .walletId(1L)
                .walletBalance(3_000)
                .users(user)
                .build();
        baseResponse1 = new BaseResponse();
        baseResponse1.setCode(0);
        baseResponse1.setDescription(wallet.getWalletBalance().toString());
        baseResponse2 = new BaseResponse();
        baseResponse2.setCode(-1);
        baseResponse2.setDescription("An Error Occurred");
    }

    @Test
    void getWalletBalance() {
        when(authentication.getName()).thenReturn(user.getEmail());
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.ofNullable(user));
        when(walletRepository.findWalletByUsers_Email(anyString())).thenReturn(wallet);
        when(responseCodeUtil.updateResponseData(eq(baseResponse1), (ResponseCodeEnum) any())).thenReturn(baseResponse1);
        BaseResponse walletBalance = walletService.getWalletBalance();
        assertThat(walletBalance.getDescription()).isEqualTo("3000");
    }
}