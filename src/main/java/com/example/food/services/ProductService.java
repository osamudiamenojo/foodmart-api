package com.example.food.services;

import com.example.food.dto.ProductDto;
import com.example.food.dto.ProductSearchDto;
import com.example.food.pojos.CreateProductResponse;
import com.example.food.pojos.PaginatedProductResponse;
import com.example.food.pojos.ProductResponse;
import com.example.food.util.ResponseCodeUtil;
import com.example.food.pojos.ProductResponseDto;
import com.example.food.restartifacts.BaseResponse;

public interface ProductService {
    PaginatedProductResponse searchProduct(ProductSearchDto productSearchDto);
    
    CreateProductResponse addNewProduct(ProductDto productDto);

    ProductResponse fetchAllProducts();

    ProductResponseDto fetchSingleProduct(Long productId);

}
