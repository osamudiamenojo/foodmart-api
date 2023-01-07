package com.example.food.pojos;

import com.example.food.model.Product;
import com.example.food.restartifacts.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import java.util.List;

@Builder
@ToString
@Getter
@AllArgsConstructor
public class PaginatedProductResponse extends BaseResponse {
    private List<Product> productList;
    private Long numberOfProducts;
    private int numberOfPages;
}