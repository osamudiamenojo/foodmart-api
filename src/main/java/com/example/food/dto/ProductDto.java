package com.example.food.dto;

import com.example.food.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    @NotNull (message = "Name must not be null")
    private Category category;
    @NotBlank(message = "Name must not be null")
    private String productName;
    @NotNull (message = "Price must not be null")
    private BigDecimal productPrice;
    @NotBlank (message = "Image must not be null")
    private String imageUrl;
    @NotNull (message = "Quantity must not be null")
    private Long quantity;

}
