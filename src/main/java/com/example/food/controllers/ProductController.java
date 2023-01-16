package com.example.food.controllers;

import com.example.food.Enum.Role;
import com.example.food.dto.ProductSearchDto;
import com.example.food.dto.UpdateProductDto;
import com.example.food.pojos.PaginatedProductResponse;
import com.example.food.pojos.UpdatedProductResponse;
import com.example.food.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/search")
    public ResponseEntity<PaginatedProductResponse> searchProduct(ProductSearchDto productSearchDto)
    {
        PaginatedProductResponse response = productService.searchProduct(productSearchDto);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
    @PutMapping("/updateProduct/{productId}")
//    @PreAuthorize("hasRole('Admin')")
    ResponseEntity<UpdatedProductResponse> updateProduct(@PathVariable Long productId, @RequestBody UpdateProductDto productDto){
        UpdatedProductResponse response = productService.updateProduct(productId,productDto);
        return  new ResponseEntity<>(response,HttpStatus.CREATED);
    }
}
