package com.example.food.controllers;

import com.example.food.pojos.models.ProductDto;
import com.example.food.restartifacts.BaseResponse;
import com.example.food.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add-new-product")
    public ResponseEntity<BaseResponse> addNewProduct (@Valid @RequestBody ProductDto productDto)  {
        return new ResponseEntity<>(productService.addNewProduct(productDto), HttpStatus.CREATED);

    }

}