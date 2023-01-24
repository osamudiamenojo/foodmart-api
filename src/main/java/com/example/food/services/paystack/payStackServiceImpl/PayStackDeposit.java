package com.example.food.services.paystack.payStackServiceImpl;

import com.example.food.Enum.TransactionType;
import com.example.food.model.Users;
import com.example.food.model.Wallet;
import com.example.food.model.WalletTransaction;
import com.example.food.repositories.UserRepository;
import com.example.food.repositories.WalletRepository;
import com.example.food.repositories.WalletTransactionRepository;
import com.example.food.services.paystack.PaystackDepositService;
import com.example.food.services.paystack.payStackPojos.PaymentDto;
import com.example.food.services.paystack.payStackPojos.PaymentResponse;
import com.example.food.util.PayStackUtil;
import com.example.food.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

//@AllArgsConstructor
@Service
@RequiredArgsConstructor
public class PayStackDeposit implements PaystackDepositService {
    private Wallet wallet;
    private BigDecimal fundingAmount;
    private String paymentReference;
    private String userEmail;
    private final UserUtil userUtil;
    private final WalletRepository walletRepository;
    private final WalletTransactionRepository walletTransactionRepository;
    private final UserRepository userRepository;

    public ResponseEntity<String> fundWallet(PaymentDto paymentDto) {
        userEmail = userUtil.getAuthenticatedUserEmail();
        Users user = userRepository.findByEmail(userEmail).get();
        wallet =user.getWallet();

        paymentDto.setReference(PayStackUtil.generateTransactionReference());
        paymentDto.setCallback_url(PayStackUtil.CALLBACK_URL+paymentDto.getReference());
        paymentDto.setEmail(userEmail);
        paymentReference = paymentDto.getReference();
        fundingAmount = paymentDto.getAmount();
        paymentDto.setAmount(fundingAmount.multiply(BigDecimal.valueOf(100)));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "+PayStackUtil.SECRET_KEY);

        HttpEntity<PaymentDto> entity = new HttpEntity<>(paymentDto, headers);
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<PaymentResponse> response = restTemplate.exchange(PayStackUtil.INITIALIZE_DEPOSIT, HttpMethod.POST, entity, PaymentResponse.class);

            System.out.println(response.getBody().getData().getAuthorization_url());

            return new ResponseEntity<>(response.getBody().getData().getAuthorization_url(),HttpStatus.ACCEPTED);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>("Failed to initiate transaction", e.getStatusCode());
        }
    }


    public ResponseEntity<String> verifyPayment(String reference) {
//        Users user = userUtil.currentUser();
        Users user = userRepository.findByEmail("engrveju@gmail.com").get();
        wallet =user.getWallet();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+PayStackUtil.SECRET_KEY);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(PayStackUtil.VERIFY_URL + reference, HttpMethod.GET, entity, String.class);
            if(response.getStatusCodeValue()==200){
                //UPDATE WALLET AND WALLET TRANSACTION IN DATABASE
                wallet.setWalletBalance(wallet.getWalletBalance().add(fundingAmount));
                walletRepository.save(wallet);

                WalletTransaction walletTransaction = WalletTransaction.builder()
                        .wallet(wallet)
                        .transactionType(TransactionType.FUNDWALLET)
                        .amount(fundingAmount)
                        .transactionReference(paymentReference)
                        .build();
                walletTransactionRepository.save(walletTransaction);
                return new ResponseEntity<>("Your Account has been successfully Funded",HttpStatus.OK);
            }
            return new ResponseEntity<>("Payment could not be verified",HttpStatus.BAD_REQUEST);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>("Payment Failed", HttpStatus.BAD_REQUEST);
        }
    }
}


