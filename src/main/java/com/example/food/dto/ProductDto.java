package com.example.food.dto;

import com.example.food.model.Category;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ProductDto {

    @NotNull (message = "Name must not be null")
    private String categoryName;
    @NotBlank(message = "Name must not be null")
    private String productName;
    @NotNull (message = "Price must not be null")
    private BigDecimal productPrice;
    @NotBlank (message = "Image must not be null")
    private String imageUrl;
    @NotNull (message = "Quantity must not be null")
    private Integer quantity;

}
