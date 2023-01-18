package com.example.food.services.serviceImpl;
import com.example.food.Enum.ResponseCodeEnum;
import com.example.food.Enum.Role;
import com.example.food.dto.ProductDto;
import com.example.food.dto.ProductSearchDto;
import com.example.food.dto.UpdateProductDto;
import com.example.food.model.Product;
import com.example.food.model.Users;
import com.example.food.pojos.UpdatedProductResponse;
import com.example.food.repositories.UserRepository;
import com.example.food.pojos.CreateProductResponse;
import com.example.food.pojos.PaginatedProductResponse;
import com.example.food.pojos.ProductResponse;
import com.example.food.pojos.ProductResponseDto;
import com.example.food.repositories.ProductRepository;
import com.example.food.restartifacts.BaseResponse;
import com.example.food.services.ProductService;
import com.example.food.util.ResponseCodeUtil;
import com.example.food.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    

    private final UserUtil userUtil;

    public PaginatedProductResponse searchProduct(ProductSearchDto productSearchDto) {
    private final ResponseCodeUtil responseCodeUtil = new ResponseCodeUtil();

    public PaginatedProductResponse searchProduct(ProductSearchDto productSearchDto) {

        Sort sort = productSearchDto.getSortDirection()
                .equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(productSearchDto.getSortBy()).ascending() : Sort.by(productSearchDto.getSortBy()).descending();
        Pageable pageRequest = PageRequest.of(productSearchDto.getPageNumber(), productSearchDto.getPageSize(), sort);

        log.info("Sort: " + sort + " pageRequest: " + pageRequest);

        Page<Product> products;
        if (productSearchDto.getFilter().isBlank()) {
            products = productRepository.findAll(pageRequest);
            log.info("Filter is null or Empty. All Products: {}", products);
        } else {
            products = productRepository.findByProductNameContainingIgnoreCase(productSearchDto.getFilter(), pageRequest);
        }
        PaginatedProductResponse paginatedResponse = PaginatedProductResponse.builder()
                .numberOfProducts(products.getTotalElements())
                .numberOfPages(products.getTotalPages())
                .productList(products.getContent())
                .build();
        log.info("Paginated Response generated. PaginatedResponse:{}", paginatedResponse);
        return responseCodeUtil.updateResponseData(paginatedResponse, ResponseCodeEnum.SUCCESS);
    }

    @Override
    public UpdatedProductResponse updateProduct(Long productId, UpdateProductDto productDto) {
        UpdatedProductResponse response = new UpdatedProductResponse();
        String email = userUtil.getAuthenticatedUserEmail();
        Users user = userRepository.findByEmail(email).get();
        System.out.println(user);
        if (!user.getRole().equals(Role.ROLE_ADMIN)) {
            return responseCodeUtil.updateResponseData(response, ResponseCodeEnum.UNAUTHORISED_ACCESS, "You are not authorised to perform this operation");
        }
        Product product = productRepository.findById(productId).orElse(null);
        if (product.equals(null)){
            return responseCodeUtil.updateResponseData(response,ResponseCodeEnum.ERROR,"Product does not exist");
        }

        BeanUtils.copyProperties(productDto, product);
        productRepository.save(product);

        return responseCodeUtil.updateResponseData(response, ResponseCodeEnum.SUCCESS, "Product updated successfully");
    }


    public CreateProductResponse addNewProduct(ProductDto productDto) {
        Optional<Product> newProduct = productRepository.findByProductName(productDto.getProductName());

        CreateProductResponse createProductResponse = CreateProductResponse.builder()
                .productName(productDto.getProductName())
                .build();
        if (newProduct.isPresent()) {
            return responseCodeUtil.updateResponseData(createProductResponse, ResponseCodeEnum.ERROR, "Product Already Exists!");
        }
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product);
        productRepository.save(product);
        return responseCodeUtil.updateResponseData(createProductResponse, ResponseCodeEnum.SUCCESS, "New Product Has Been Added");
    }
    

    public ProductResponse fetchAllProducts() {

        ProductResponse response = new ProductResponse();

        List<Product> products = productRepository.findAll();

        if (products.isEmpty()) {
            return responseCodeUtil.updateResponseData(response, ResponseCodeEnum.PRODUCT_NOT_FOUND);
        }
        List<ProductDto> productDto = products.stream()
                .map(product -> new ProductDto(product.getCategory(),
                        product.getProductName(),
                        product.getProductPrice(),
                        product.getImageUrl(),
                        product.getQuantity()))
                 //       product.getCreatedAt(),
                 //       product.getModifiedAt()))
                .collect(Collectors.toList());

        response.setProductDto(productDto);

        return responseCodeUtil.updateResponseData(response, ResponseCodeEnum.SUCCESS);
    }

    public ProductResponseDto fetchSingleProduct(Long productId) {

        ProductResponseDto responseDto = new ProductResponseDto();
        Optional<Product> fetchedProduct = productRepository.findByProductId(productId);

        if (fetchedProduct.isEmpty()) {
            return responseCodeUtil.updateResponseData(responseDto,
                    ResponseCodeEnum.PRODUCT_NOT_FOUND, "Product not found for ID:" + productId);
        }
        Product product = fetchedProduct.get();
        log.info("response object {}",product);
        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(product, productDto);
        responseDto.setProductDto(productDto);

        return responseCodeUtil.updateResponseData(responseDto, ResponseCodeEnum.SUCCESS);
    }

}
