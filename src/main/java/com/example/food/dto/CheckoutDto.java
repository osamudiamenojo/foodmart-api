package com.example.food.dto;

import com.example.food.Enum.DeliveryMethod;
import com.example.food.Enum.PaymentMethod;
import lombok.Data;

@Data
public class CheckoutDto {
    private DeliveryMethod deliveryMethod;
    private PaymentMethod paymentMethod;
}
