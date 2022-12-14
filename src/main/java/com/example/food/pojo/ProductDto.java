package com.example.food.pojo;

import com.example.food.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    @NotBlank
    private Category category;

    @NotBlank
    private String productName;

    @NotBlank
    private double productPrice;

    @NotBlank
    private String imageUrl;

    @NotBlank
    private Long quantity;

}
