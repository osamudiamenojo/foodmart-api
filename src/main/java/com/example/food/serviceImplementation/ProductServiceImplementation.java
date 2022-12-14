package com.example.food.serviceImplementation;

import com.example.food.model.Product;
import com.example.food.pojo.ProductDto;
import com.example.food.repositories.ProductRepository;
import com.example.food.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductServiceImplementation implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductDto addNewProduct(ProductDto productDto) {

            Product product = new Product();
            BeanUtils.copyProperties(productDto, product);
            productRepository.save(product);

        return productDto;
    }
}
