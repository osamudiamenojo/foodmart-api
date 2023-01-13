package com.example.food.pojos.models;

import com.example.food.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    @NotNull (message = "Name must not be null")
    private Category category;

    @NotBlank(message = "Name must not be null")
    private String productName;

    @NotNull (message = "Price must not be null")
    private double productPrice;

    @NotBlank (message = "Image must not be null")
    private String imageUrl;

    @NotNull (message = "Quantity must not be null")
    private Long quantity;

}
