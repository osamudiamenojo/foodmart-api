package com.example.food.dto;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDto {
    private long id;
    private long productId;
    private String productName;
    private String productImage;
    private String productSize;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal subTotal;
    private long cartId;

}
