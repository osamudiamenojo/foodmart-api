package com.example.food.services;

import com.example.food.dto.ProductDto;
import com.example.food.dto.ProductSearchDto;
import com.example.food.pojos.PaginatedProductResponse;
import com.example.food.pojos.ProductResponse;
import com.example.food.util.ResponseCodeUtil;

public interface ProductService {
    PaginatedProductResponse searchProduct(ProductSearchDto productSearchDto);

    ProductResponse fetchAllProducts();
}
