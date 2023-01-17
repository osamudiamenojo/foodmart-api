package com.example.food.services;

import com.example.food.dto.ProductSearchDto;
import com.example.food.pojos.PaginatedProductResponse;

public interface ProductService {
    PaginatedProductResponse searchProduct(ProductSearchDto productSearchDto);
    void deleteProduct(Long productId);
}
