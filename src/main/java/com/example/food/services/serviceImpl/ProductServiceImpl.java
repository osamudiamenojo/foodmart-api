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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ResponseCodeUtil responseCodeUtil;

    private final UserUtil userUtil;

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

//    @Override
//    public BaseResponse view_Detail_Of_A_Particular_Order(Long orderId) {
//        BaseResponse response = new BaseResponse();
//        String email = userUtil.getAuthenticatedUserEmail();
//        Users users = userRepository.findByEmail(email).orElse(null);
//        if (users.equals(null)) {
//            return responseCodeUtil.updateResponseData(response, ResponseCodeEnum.ERROR, "Not a valid request");
//        }
//        return response;
//    }

//    }
}
