package com.example.food.service.impl;

import com.example.food.model.Product;
import com.example.food.pojos.models.ProductDto;
import com.example.food.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class ProductServiceImplementationTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductServiceImplementation productService;

    Product product;
    ProductDto productDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        product = new Product();
        productDto = new ProductDto();
    }

    @Test
    void addNewProduct() {
        when(productRepository.findByProductName(anyString()))
                .thenReturn(Optional.of(product));
        when(productRepository.save(product))
                .thenReturn(product);

        String status = productService.addNewProduct(productDto);
        assertEquals("New Product Saved!", status);

    }
}