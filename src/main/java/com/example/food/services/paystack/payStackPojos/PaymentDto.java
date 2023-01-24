package com.example.food.services.paystack.payStackPojos;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {
    @NonNull
    private String email;
    @NonNull
    private BigDecimal amount;
    private String reference;
    private String callback_url;
    private String authorization_url;
}
