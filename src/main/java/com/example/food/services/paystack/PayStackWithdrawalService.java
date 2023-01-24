package com.example.food.services.paystack;

import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

public interface PayStackWithdrawalService {
    ResponseEntity<String> getAllBanks(String currency);
    ResponseEntity<String> withDrawFromWallet(String account_number, String bank_code, BigDecimal amount);
}
