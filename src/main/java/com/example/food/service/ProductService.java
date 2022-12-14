package com.example.food.service;

import com.example.food.pojo.ProductDto;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
     ProductDto addNewProduct(ProductDto productDto);
}
