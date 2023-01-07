package com.example.food.controllers;

import com.example.food.pojos.PaginatedProductResponse;
import com.example.food.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/search")
    public ResponseEntity<PaginatedProductResponse> searchProduct(
        @RequestParam(required = false, defaultValue = "DESC") String sortDirection,
        @RequestParam(required = false, defaultValue = "price") String sortBy,
        @RequestParam(required = false, defaultValue = "") String filter,
        @RequestParam(required = false, defaultValue = "0") int pageNumber,
        @RequestParam(required = false, defaultValue = "10") int pageSize)
    {
        PaginatedProductResponse response = productService.searchProduct(pageNumber, pageSize,sortDirection,sortBy,filter);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
}
