package com.example.food.dto;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class UpdateProductDto {
    @NotNull(message = "product id should not be null")
    private String productName;
    private BigDecimal price;
}
