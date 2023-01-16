package com.example.food.services.serviceImpl;

import com.example.food.Enum.ResponseCodeEnum;
import com.example.food.dto.ProductDto;
import com.example.food.dto.ProductSearchDto;
import com.example.food.model.Product;
import com.example.food.pojos.PaginatedProductResponse;
import com.example.food.pojos.ProductResponse;
import com.example.food.repositories.ProductRepository;
import com.example.food.services.ProductService;
import com.example.food.util.ResponseCodeUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductServiceImpl productServiceImpl;

    @Test
    void testSearchProduct() {
        // Setting up test data
        List<Product> expectedProducts = Arrays.asList(
                createNewProduct(1L,"apple1",290D),
                createNewProduct(2L,"apple2",290D),
                createNewProduct(3L,"apple3",290D)
        );
        Page<Product> expectedPage = new PageImpl<>(expectedProducts);
        //MOCKING THE BEHAVIOUR
        when(productRepository.findByProductNameContainingIgnoreCase( anyString(), any(Pageable.class)))
                .thenReturn(expectedPage);

        ProductSearchDto productSearchDto = new ProductSearchDto();
        productSearchDto.setFilter("filter");
        productSearchDto.setPageSize(1);
        productSearchDto.setPageNumber(0);
        productSearchDto.setSortDirection("asc");

        PaginatedProductResponse response = productServiceImpl.searchProduct(productSearchDto);
        assertSame(response.getNumberOfProducts().intValue(), expectedProducts.size());
        verify(productRepository).findByProductNameContainingIgnoreCase((String) any(), (Pageable) any());
    }

    private Product createNewProduct(final Long productId, final String productName, final Double productPrice) {
        Product product = new Product();
        product.setProductName(productName);
        product.setProductId(productId);
        product.setPrice(productPrice);
        product.setCreatedAt(new Date());
        product.setModifiedAt(new Date());
        return product;
    }

    @Test
    void testSearchProductWhenFilterIsBlank() {
        // Setting up test data
        List<Product> expectedProducts = Arrays.asList(
                createNewProduct(1L,"apple1",290D),
                createNewProduct(2L,"apple2",290D),
                createNewProduct(3L,"apple3",290D)
        );
        Page<Product> expectedPage = new PageImpl<>(expectedProducts);
        //MOCKING THE BEHAVIOUR
        when(productRepository.findAll(any(Pageable.class))).thenReturn(expectedPage);
        //VERIFICATION OF THR MOCK BEHAVIOUR

        PaginatedProductResponse response = productServiceImpl.searchProduct(new ProductSearchDto());
        assertSame(response.getNumberOfProducts().intValue(), expectedProducts.size());
        verify(productRepository).findAll((Pageable) any());
    }


    @Test
    void testSearchProductIsNull() {
        // Setting up test data
        Page<Product> expectedPage = new PageImpl<>(new ArrayList<>());
        //MOCKING THE BEHAVIOUR
        when(productRepository.findAll(any(Pageable.class))).thenReturn(expectedPage);

        //VERIFICATION OF THR MOCK BEHAVIOUR
        PaginatedProductResponse response = productServiceImpl.searchProduct(new ProductSearchDto());
        assertTrue(response.getProductList().isEmpty());
        verify(productRepository).findAll((Pageable) any());
    }
}