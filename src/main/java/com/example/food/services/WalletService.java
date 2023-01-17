package com.example.food.services;

import com.example.food.restartifacts.BaseResponse;

public interface WalletService {
    BaseResponse getWalletBalance(Long walletId);
}
