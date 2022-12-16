package com.example.food.pojos.models;

import com.example.food.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    @NotEmpty
    private Category category;

    @NotBlank
    private String productName;

    @NotEmpty
    private double productPrice;

    @NotBlank
    private String imageUrl;

    @NotNull
    private Long quantity;

}
