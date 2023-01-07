package com.example.food.services;

import com.example.food.pojos.PaginatedProductResponse;

public interface ProductService {
    PaginatedProductResponse searchProduct(int pageNumber, int pageSize, String sortDirection, String sortBy, String filter);
}
