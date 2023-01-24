package com.example.food.services;

import com.example.food.dto.WithDrawalDto;
import com.example.food.pojos.WalletResponse;
import com.example.food.services.paystack.payStackPojos.PaymentDto;
import org.springframework.http.ResponseEntity;

public interface WalletService {
    WalletResponse getWalletBalance();
    ResponseEntity<String> walletWithdrawal(WithDrawalDto withDrawalDto);
    ResponseEntity<String> fundWallet(PaymentDto paymentDto);
    ResponseEntity<String> verifyPayment(String reference);
}
