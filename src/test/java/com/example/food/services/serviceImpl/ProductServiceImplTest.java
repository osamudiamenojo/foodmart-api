package com.example.food.services.serviceImpl;

import com.example.food.Enum.ResponseCodeEnum;
import com.example.food.dto.ProductDto;
import com.example.food.dto.ProductSearchDto;
import com.example.food.model.Product;
import com.example.food.pojos.PaginatedProductResponse;
import com.example.food.repositories.ProductRepository;
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

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductServiceImpl productServiceImpl;
    @Mock
    private ResponseCodeUtil responseCodeUtil;
    @Test
    void testSearchProduct() {
        // Setting up test data
        List<Product> expectedProducts = Arrays.asList(
                new Product(1L,"apple1",290D, new Date(),new Date()),
                new Product(2L,"apple2",290D, new Date(),new Date()),
                new Product(3L,"apple3",290D, new Date(),new Date())
        );
        Page<Product> expectedPage = new PageImpl<>(expectedProducts);
        //MOCKING THE BEHAVIOUR
        when(productRepository.findByProductNameContainingIgnoreCase( anyString(), any(Pageable.class)))
                .thenReturn(expectedPage);
        PaginatedProductResponse paginatedProductResponse = PaginatedProductResponse.builder()
                .numberOfProducts(expectedPage.getTotalElements())
                .numberOfPages(expectedPage.getTotalPages())
                .productList(expectedPage.getContent())
                .build();
        when(responseCodeUtil.updateResponseData(eq(paginatedProductResponse), (ResponseCodeEnum) any()))
                .thenReturn(paginatedProductResponse);
        //VERIFICATION OF THR MOCK BEHAVIOUR

        ProductSearchDto productSearchDto = new ProductSearchDto();
        productSearchDto.setFilter("filter");
        productSearchDto.setPageSize(1);
        productSearchDto.setPageNumber(0);
        productSearchDto.setSortDirection("asc");
        assertSame(paginatedProductResponse,
                productServiceImpl.searchProduct(productSearchDto));
        verify(productRepository).findByProductNameContainingIgnoreCase((String) any(), (Pageable) any());
        verify(responseCodeUtil).updateResponseData((PaginatedProductResponse) any(), (ResponseCodeEnum) any());
    }


    @Test
    void testSearchProductWhenFilterIsBlank() {
        // Setting up test data
        List<Product> expectedProducts = Arrays.asList(
                new Product(1L,"apple1",290D, new Date(),new Date()),
                new Product(2L,"apple2",290D, new Date(),new Date()),
                new Product(3L,"apple3",290D, new Date(),new Date())
        );
        Page<Product> expectedPage = new PageImpl<>(expectedProducts);
        //MOCKING THE BEHAVIOUR
        when(productRepository.findAll(any(Pageable.class)))
                .thenReturn(expectedPage);
        PaginatedProductResponse paginatedProductResponse = PaginatedProductResponse.builder()
                .numberOfProducts(expectedPage.getTotalElements())
                .numberOfPages(expectedPage.getTotalPages())
                .productList(expectedPage.getContent())
                .build();
        when(responseCodeUtil.updateResponseData(eq(paginatedProductResponse), (ResponseCodeEnum) any()))
                .thenReturn(paginatedProductResponse);
        //VERIFICATION OF THR MOCK BEHAVIOUR
        ProductSearchDto productSearchDto = new ProductSearchDto();
        assertSame(paginatedProductResponse,
                productServiceImpl.searchProduct(productSearchDto));
        verify(productRepository).findAll((Pageable) any());
        verify(responseCodeUtil).updateResponseData((PaginatedProductResponse) any(), (ResponseCodeEnum) any());
    }


    @Test
    void testSearchProductIsNull() {
        // Setting up test data
        Page<Product> expectedPage = new PageImpl<>(new ArrayList<>());
        //MOCKING THE BEHAVIOUR
        when(productRepository.findAll(any(Pageable.class)))
                .thenReturn(expectedPage);
        PaginatedProductResponse paginatedProductResponse = PaginatedProductResponse.builder()
                .numberOfProducts(expectedPage.getTotalElements())
                .numberOfPages(expectedPage.getTotalPages())
                .productList(expectedPage.getContent())
                .build();
        when(responseCodeUtil.updateResponseData(eq(paginatedProductResponse), (ResponseCodeEnum) any()))
                .thenReturn(paginatedProductResponse);
        //VERIFICATION OF THR MOCK BEHAVIOUR
        ProductSearchDto productSearchDto = new ProductSearchDto();
        assertSame(paginatedProductResponse,
                productServiceImpl.searchProduct(productSearchDto));
        verify(productRepository).findAll((Pageable) any());
        verify(responseCodeUtil).updateResponseData((PaginatedProductResponse) any(), (ResponseCodeEnum) any());
    }

    @Test
    void testFetchSingleProduct(){

        Long productId = 1L;
        Product product = new Product();
        ProductDto productDto = new ProductDto();

        //when
        when(productRepository.findByProductId(productId))
                .thenReturn(Optional.of(product));

        when(responseCodeUtil.updateResponseData(productDto, ResponseCodeEnum.SUCCESS))
                .thenReturn(productDto);

        ProductDto actual = productServiceImpl.fetchSingleProduct(productId);

        assertSame(productDto, actual);
    }

}