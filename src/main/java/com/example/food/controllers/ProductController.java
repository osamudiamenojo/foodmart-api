package com.example.food.controllers;
import com.example.food.dto.ProductSearchDto;
import com.example.food.pojos.CreateProductResponse;
import com.example.food.pojos.PaginatedProductResponse;
import com.example.food.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ProductController {


    private final ProductService productService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add-new-product")
    public ResponseEntity<CreateProductResponse> addNewProduct (@Valid @RequestBody ProductDto productDto)  {
        CreateProductResponse productResponse = productService.addNewProduct(productDto);
        return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
    }

    @GetMapping("/search")
    public ResponseEntity<PaginatedProductResponse> searchProduct(ProductSearchDto productSearchDto)
    {
        PaginatedProductResponse response = productService.searchProduct(productSearchDto);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
    }
}
