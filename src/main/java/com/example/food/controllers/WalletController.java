package com.example.food.controllers;

import com.example.food.restartifacts.BaseResponse;
import com.example.food.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wallet")
public class WalletController {
    private final WalletService walletService;

    @GetMapping("/balance/{walletId}")
    public ResponseEntity<BaseResponse> viewWalletBalance(@PathVariable Long walletId) {
        BaseResponse response = walletService.getWalletBalance(walletId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
