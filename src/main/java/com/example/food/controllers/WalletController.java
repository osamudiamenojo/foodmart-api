package com.example.food.controllers;

import com.example.food.dto.WithDrawalDto;
import com.example.food.pojos.WalletResponse;
import com.example.food.services.WalletService;
import com.example.food.services.paystack.payStackPojos.PaymentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wallet")
public class WalletController {

    private final WalletService walletService;

    @GetMapping("/balance")
    public ResponseEntity<WalletResponse> getBalance() {
        WalletResponse response = walletService.getWalletBalance();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/withdrawal")
    public ResponseEntity<String> walletWithdrawal(@RequestBody WithDrawalDto withDrawalDto){
        return walletService.walletWithdrawal(withDrawalDto);
    }
    @PostMapping("/fundWallet")
    public ResponseEntity<String> fundWallet(@RequestBody PaymentDto paymentDto){
        return walletService.fundWallet(paymentDto);
    }
    @GetMapping("/verifyPayment/{reference}")
    public ResponseEntity<String> verifyPayment(@PathVariable  String reference){
        return walletService.verifyPayment(reference);
    }
}
