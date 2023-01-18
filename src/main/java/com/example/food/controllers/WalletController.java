package com.example.food.controllers;

import com.example.food.pojos.WalletResponse;
import com.example.food.restartifacts.BaseResponse;
import com.example.food.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wallet")
public class WalletController {
    private final WalletService walletService;

    @GetMapping("/balance")
    public ResponseEntity<BaseResponse> viewWalletBalance() {
        BaseResponse walletBalance = walletService.getWalletBalance();
        return new ResponseEntity<>(walletBalance, HttpStatus.OK);
    }
}
