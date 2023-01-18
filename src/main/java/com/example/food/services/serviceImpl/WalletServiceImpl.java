package com.example.food.services.serviceImpl;

import com.example.food.Enum.ResponseCodeEnum;
import com.example.food.model.Users;
import com.example.food.model.Wallet;
import com.example.food.pojos.WalletResponse;
import com.example.food.repositories.UserRepository;
import com.example.food.repositories.WalletRepository;
import com.example.food.restartifacts.BaseResponse;
import com.example.food.services.WalletService;
import com.example.food.util.ResponseCodeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final ResponseCodeUtil responseCodeUtil;

    public Users getLoggedInUser() {
        String authentication = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(authentication).orElseThrow(() -> new RuntimeException("User not authorized"));
    }

    @Override
    public WalletResponse getWalletBalance() {
        WalletResponse walletResponse;
        try {
            Users walletOwner = getLoggedInUser();
            Wallet wallet = walletRepository.findWalletByUsers_Email(walletOwner.getEmail());
            walletResponse = WalletResponse.builder()
                    .balance(wallet.getWalletBalance())
                    .build();
            return responseCodeUtil.updateResponseData(walletResponse, ResponseCodeEnum.SUCCESS);
        } catch (Exception e) {
            log.error("Email not registered, Wallet balance cannot be displayed: {}", e.getMessage());
            walletResponse = WalletResponse.builder()
                    .balance(null)
                    .build();
            return responseCodeUtil.updateResponseData(walletResponse, ResponseCodeEnum.ERROR);
        }
    }
}