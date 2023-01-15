package com.example.food.controllers;

import com.example.food.dto.ProductSearchDto;
import com.example.food.pojos.PaginatedProductResponse;
import com.example.food.pojos.ProductResponse;
import com.example.food.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/search")
    public ResponseEntity<PaginatedProductResponse> searchProduct(ProductSearchDto productSearchDto)
    {
        PaginatedProductResponse response = productService.searchProduct(productSearchDto);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/get-all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProductResponse> fetchAllProducts() {
        return new ResponseEntity<>(productService.fetchAllProducts(),HttpStatus.ACCEPTED);
    }
}
