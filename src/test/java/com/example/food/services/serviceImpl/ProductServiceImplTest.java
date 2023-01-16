package com.example.food.services.serviceImpl;

import com.example.food.Enum.ResponseCodeEnum;
import com.example.food.Enum.Role;
import com.example.food.dto.ProductSearchDto;
import com.example.food.dto.UpdateProductDto;
import com.example.food.model.Product;
import com.example.food.model.Users;
import com.example.food.pojos.PaginatedProductResponse;
import com.example.food.pojos.UpdatedProductResponse;
import com.example.food.repositories.ProductRepository;
import com.example.food.repositories.UserRepository;
import com.example.food.services.ProductService;
import com.example.food.util.ResponseCodeUtil;
import com.example.food.util.UserUtil;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    UserUtil userUtil;
    @Mock
    UserRepository userRepository;
    @Mock
    private ResponseCodeUtil responseCodeUtil;
    @BeforeEach
    public void setUp() {
        UpdateProductDto productDto = new UpdateProductDto();
        productDto.setProductName("Test Product");
        productDto.setPrice(234.0);

        Product product = new Product();
        product.setProductId(1L);
        product.setProductName("Old Product Name");
        product.setPrice(300.0);

        Users user = new Users();
        user.setRole(Role.ROLE_ADMIN);
    }
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
    public void updateProduct_withAdminRole_shouldReturnSuccessResponse() {
        // given
        Long productId = 1L;
        UpdateProductDto productDto = new UpdateProductDto();

        Users user = new Users();
        user.setEmail("test@email.com");
        user.setFirstName("Test_First_Name");
        user.setLastName("Test_Last_Name");
        user.setRole(Role.ROLE_ADMIN);
        Product product = new Product();
        UpdatedProductResponse expectedResponse = new UpdatedProductResponse();

        when(userUtil.getAuthenticatedUserEmail()).thenReturn("test@email.com");
        when(userRepository.findByEmail("test@email.com")).thenReturn(Optional.of(user));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(responseCodeUtil.updateResponseData(any(UpdatedProductResponse.class), eq(ResponseCodeEnum.SUCCESS), eq("Product updated successfully"))).thenReturn(expectedResponse);

        UpdatedProductResponse actualResponse = productServiceImpl.updateProduct(productId, productDto);

        verify(productRepository, times(1)).save(product);
        assertEquals(expectedResponse, actualResponse);
    }

        @Test
    public void updateProduct_withNonAdminRole_shouldReturnUnauthorizedResponse() {

        Long productId = 1L;
        UpdateProductDto productDto = new UpdateProductDto();
        Users user = new Users();
        user.setRole(Role.ROLE_USER);
        UpdatedProductResponse expectedResponse = new UpdatedProductResponse();

        when(userUtil.getAuthenticatedUserEmail()).thenReturn("test@email.com");
        when(userRepository.findByEmail("test@email.com")).thenReturn(Optional.of(user));
        when(responseCodeUtil.updateResponseData(any(UpdatedProductResponse.class), eq(ResponseCodeEnum.UNAUTHORISED_ACCESS),
                eq("You are not authorised to perform this operation"))).thenReturn(expectedResponse);

        UpdatedProductResponse actualResponse = productServiceImpl.updateProduct(productId, productDto);

        verify(productRepository, times(0)).save(any());
        assertEquals(expectedResponse, actualResponse);
    }

}