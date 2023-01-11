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
    @Mock
    private ResponseCodeUtil responseCodeUtil;
    @Mock
    private ProductService productService;
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
    public void testFetchAllProducts_whenProductsExist() {

        List<Product> products = createProductList();
        when(productRepository.findAll()).thenReturn(products);

        ProductResponse response = productService.fetchAllProducts();

        assertNotNull(response, "Response object is null");

        assertTrue(response.isStatus());
        assertEquals("Products fetched successfully", response.getMessage());
        List<ProductDto> productDto = response.getProducts();
        assertEquals(2, productDto.size());
        assertEquals("product1-image.jpg", productDto.get(0).getImageUrl());
        assertEquals("Product 1", productDto.get(0).getProductName());

        assertEquals("product2-image.jpg", productDto.get(1).getImageUrl());
        assertEquals("Product 2", productDto.get(1).getProductName());
    }

    @Test
    public void testFetchAllProducts_whenNoProductsExist() {

        when(productRepository.findAll()).thenReturn(Collections.emptyList());

        ProductResponse response = productService.fetchAllProducts();

        assertFalse(response.isStatus());
        assertEquals("No products found", response.getMessage());
        assertTrue(response.getProducts().isEmpty());
    }

    private List<Product> createProductList() {

        Product product1 = new Product();
        product1.setImageUrl("product1-image.jpg");
        product1.setProductName("Product 1");

        Product product2 = new Product();
        product2.setImageUrl("product2-image.jpg");
        product2.setProductName("Product 2");

        return Arrays.asList(product1, product2);
    }

}