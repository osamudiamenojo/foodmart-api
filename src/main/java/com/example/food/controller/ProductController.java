package com.example.food.controller;

import com.example.food.pojo.ProductDto;
import com.example.food.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1")
public class ProductController {

    private final ProductService productService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add-new-product")
    public ResponseEntity<ProductDto> addNewProduct(@RequestBody ProductDto productDto) {

        return new ResponseEntity<>(productService.addNewProduct(productDto), HttpStatus.CREATED);

    }

}