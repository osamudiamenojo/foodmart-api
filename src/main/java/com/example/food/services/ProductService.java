package com.example.food.services;

import com.example.food.dto.ProductSearchDto;
import com.example.food.dto.UpdateProductDto;
import com.example.food.pojos.PaginatedProductResponse;
import com.example.food.pojos.UpdatedProductResponse;

public interface ProductService {
    PaginatedProductResponse searchProduct(ProductSearchDto productSearchDto);
    UpdatedProductResponse updateProduct(Long productId, UpdateProductDto productDto);
}
