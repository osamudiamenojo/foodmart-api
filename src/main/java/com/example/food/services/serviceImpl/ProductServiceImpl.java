package com.example.food.services.serviceImpl;

import com.example.food.Enum.ResponseCodeEnum;
import com.example.food.dto.ProductSearchDto;
import com.example.food.model.Product;
import com.example.food.pojos.PaginatedProductResponse;
import com.example.food.repositories.ProductRepository;
import com.example.food.services.ProductService;
import com.example.food.util.ResponseCodeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ResponseCodeUtil responseCodeUtil;
    public PaginatedProductResponse searchProduct(ProductSearchDto productSearchDto){

        Sort sort = productSearchDto.getSortDirection()
                .equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(productSearchDto.getSortBy()).ascending() : Sort.by(productSearchDto.getSortBy()).descending();
        Pageable pageRequest = PageRequest.of(productSearchDto.getPageNumber(),productSearchDto.getPageSize(),sort);

        log.info("Sort: " + sort + " pageRequest: "+ pageRequest);

        Page<Product> products;
        if (productSearchDto.getFilter().isBlank()) {
            products = productRepository.findAll(pageRequest);
            log.info("Filter is null or Empty. All Products: {}",products);
        } else {
            products = productRepository.findByProductNameContainingIgnoreCase(productSearchDto.getFilter(), pageRequest);
        }
        PaginatedProductResponse paginatedResponse = PaginatedProductResponse.builder()
                .numberOfProducts(products.getTotalElements())
                .numberOfPages(products.getTotalPages())
                .productList(products.getContent())
                .build();
        log.info("Paginated Response generated. PaginatedResponse:{}",paginatedResponse);
        return responseCodeUtil.updateResponseData(paginatedResponse, ResponseCodeEnum.SUCCESS);
    }
}