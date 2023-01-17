package com.example.food.controllers;

import com.example.food.Enum.ResponseCodeEnum;
import com.example.food.dto.ProductSearchDto;
import com.example.food.pojos.PaginatedProductResponse;
import com.example.food.restartifacts.BaseResponse;
import com.example.food.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @DeleteMapping("/deleteProduct/{productId}")
    public BaseResponse deleteAPost(@PathVariable("productId") Long productId){
       productService.deleteProduct(productId);
        return new BaseResponse(ResponseCodeEnum.SUCCESS);
    }
}
