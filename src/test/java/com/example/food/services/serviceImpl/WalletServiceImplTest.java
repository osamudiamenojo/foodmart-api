package com.example.food.services.serviceImpl;

import com.example.food.model.Users;
import com.example.food.model.Wallet;
import com.example.food.pojos.WalletResponse;
import com.example.food.repositories.UserRepository;
import com.example.food.repositories.WalletRepository;
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

import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WalletServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private WalletRepository walletRepository;
    @InjectMocks
    private WalletServiceImpl walletService;
    private Users walletOwner;
    private Wallet wallet;
    Authentication authentication;
    private WalletResponse walletResponse;

    @BeforeEach
    void setUp() {
        walletOwner = Users.builder()
                .usersId(1L)
                .email("mensa@gmail.com")
                .password("password")
                .build();
        authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn(walletOwner.getEmail());

    }

    @Test
    void testGetWalletBalance_success() {
        // Arrange
        walletService = new WalletServiceImpl(userRepository, walletRepository, new ResponseCodeUtil());

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.ofNullable(walletOwner));
        when(walletRepository.findWalletByUsers_Email(anyString())).thenReturn(wallet);

        Wallet wallet = new Wallet();
        wallet.setWalletBalance(1000.0);
        wallet.setUsers(walletOwner);
        when(walletRepository.findWalletByUsers_Email(anyString())).thenReturn(wallet);

        // Act
        WalletResponse response = walletService.getWalletBalance();

        // Assert
        assertEquals(Optional.ofNullable(response.getBalance()), Optional.of(1000D));
    }



    @Test
    void testGetWalletBalance_error() {
        // Arrange
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.ofNullable(walletOwner));
        when(walletRepository.findWalletByUsers_Email(anyString())).thenReturn(wallet);

        // Act
        WalletResponse response = walletService.getWalletBalance();

        // Assert
        assertNull(response);
    }
}