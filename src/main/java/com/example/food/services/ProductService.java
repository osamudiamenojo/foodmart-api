package com.example.food.services;

import com.example.food.pojos.models.ProductDto;
import com.example.food.restartifacts.BaseResponse;
import org.springframework.stereotype.Service;

//@Service
public interface ProductService {
     BaseResponse addNewProduct(ProductDto productDto);
}
