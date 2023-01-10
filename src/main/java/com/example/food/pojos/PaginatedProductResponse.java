package com.example.food.pojos;

import com.example.food.model.Product;
import com.example.food.restartifacts.BaseResponse;
import lombok.*;

import java.util.List;

@Builder
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedProductResponse extends BaseResponse {
    private List<Product> productList;
    private Long numberOfProducts;
    private int numberOfPages;
}