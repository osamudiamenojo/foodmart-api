package com.example.food.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class UpdateProductDto {
    @NotNull(message = "product id should not be null")
    private String productName;
    private Integer quantity;
    private BigDecimal price;
}
