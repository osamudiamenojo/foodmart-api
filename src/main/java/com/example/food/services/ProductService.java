package com.example.food.services;

import com.example.food.dto.OrderDto;
import com.example.food.dto.ProductSearchDto;
import com.example.food.dto.UpdateProductDto;
import com.example.food.pojos.PaginatedProductResponse;
import com.example.food.pojos.UpdatedProductResponse;
import com.example.food.restartifacts.BaseResponse;

public interface ProductService {
    PaginatedProductResponse searchProduct(ProductSearchDto productSearchDto);
    UpdatedProductResponse updateProduct(Long productId, UpdateProductDto productDto);
//    BaseResponse view_Detail_Of_A_Particular_Order(Long OrderId);
}
