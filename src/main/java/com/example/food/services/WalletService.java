package com.example.food.services;

import com.example.food.dto.WithDrawalDto;
import com.example.food.pojos.WalletResponse;
import org.springframework.http.ResponseEntity;
import java.math.BigDecimal;

public interface WalletService {
    WalletResponse getWalletBalance();
    ResponseEntity<String> walletWithdrawal(WithDrawalDto withDrawalDto);
    ResponseEntity<String> fundWallet(BigDecimal amount, String transactionType);
    ResponseEntity<String> verifyPayment(String reference,String transactionType);
}
