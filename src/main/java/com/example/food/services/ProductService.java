package com.example.food.services;

import com.example.food.pojos.models.ProductDto;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
     String addNewProduct(ProductDto productDto);
}
