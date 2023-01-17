package com.example.food.services.serviceImpl;

import com.example.food.Enum.ResponseCodeEnum;
import com.example.food.model.Users;
import com.example.food.model.Wallet;
import com.example.food.repositories.UserRepository;
import com.example.food.repositories.WalletRepository;
import com.example.food.restartifacts.BaseResponse;
import com.example.food.services.WalletService;
import com.example.food.util.ResponseCodeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final ResponseCodeUtil responseCodeUtil;

    private Users getLoggedInUser() {
        String authentication = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(authentication).orElseThrow(() -> new RuntimeException("User not authorized"));
    }

    @Override
    public BaseResponse getWalletBalance() {
        Users walletOwner = getLoggedInUser();
        Wallet wallet = walletRepository.findWalletByUsers_Email(walletOwner.getEmail());
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(0);
        baseResponse.setDescription(wallet.getWalletBalance().toString());
        return responseCodeUtil.updateResponseData(baseResponse, ResponseCodeEnum.SUCCESS);
    }
}
