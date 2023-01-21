package com.example.food.services.serviceImpl;

import com.example.food.Enum.ResponseCodeEnum;
import com.example.food.dto.CartDto;
import com.example.food.dto.CartItemDto;
import com.example.food.dto.CartItemDto;
import com.example.food.model.Cart;
import com.example.food.model.CartItem;
import com.example.food.model.Product;
import com.example.food.model.Users;
import com.example.food.pojos.CartResponse;
import com.example.food.repositories.CartItemRepository;
import com.example.food.repositories.CartRepository;
import com.example.food.repositories.ProductRepository;
import com.example.food.repositories.UserRepository;
import com.example.food.restartifacts.BaseResponse;
import com.example.food.services.CartService;
import com.example.food.util.ResponseCodeUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
@AllArgsConstructor
@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final ResponseCodeUtil responseCodeUtil = new ResponseCodeUtil();

    private Users getLoggedInUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(authentication)
                .orElseThrow(() -> new RuntimeException("User not authorized"));
    }

    @Override
    public BaseResponse removeCartItem(long cartItemId) {
        CartResponse baseResponse = new CartResponse();
        try {
            Users user = getLoggedInUser();
            Cart cart = user.getCart();
            Optional<CartItem> cartItemCheck = cartItemRepository.findById(cartItemId);
            if (cartItemCheck.isPresent()) {
                CartItem cartItem = cartItemCheck.get();
                removeItem(cartItemId, cart, cartItem);
                responseCodeUtil.updateResponseData(baseResponse, ResponseCodeEnum.SUCCESS, "Item removed from user cart");
            } else {
                responseCodeUtil.updateResponseData(baseResponse, ResponseCodeEnum.SUCCESS, "Item is not in user cart");
            }
            return baseResponse;
        } catch (Exception e) {
            log.error("Email not registered, Product cannot be removed: {}", e.getMessage());
        }
        return responseCodeUtil.updateResponseData(baseResponse, ResponseCodeEnum.ERROR);
    }

    @Override
    public CartResponse addCartItem(Long productId) {
        try {
            Users user = getLoggedInUser();
            Product product = productRepository.findById(productId).orElseThrow(RuntimeException::new);
            Cart userCart = cartRepository.findByUsersEmail(user.getEmail()).orElseThrow(RuntimeException::new);
            Optional<CartItem> DbCartItem = cartItemRepository.findCartItemByCartIdAndProductId(userCart.getId(), productId);
            CartItem cartItem;
            if (DbCartItem.isEmpty() && userCart.getCartItemList().isEmpty()) {
                cartItem = new CartItem();
                cartItem.setCart(userCart);
                cartItem.setProduct(product);
                cartItem.setQuantity(1);
                cartItem.setSubTotal(product.getProductPrice());
                CartItem savedCartItem = cartItemRepository.save(cartItem);

                userCart.setCartTotal(cartItem.getSubTotal());
                userCart.getCartItemList().add(savedCartItem);
                userCart.setQuantity(userCart.getCartItemList().size());
                cartRepository.save(userCart);

            } else if (DbCartItem.isEmpty()) {
                cartItem = new CartItem();
                cartItem.setCart(userCart);
                cartItem.setProduct(product);
                cartItem.setQuantity(1);
                cartItem.setSubTotal(product.getProductPrice());
                CartItem savedCartItem = cartItemRepository.save(cartItem);

                userCart.setCartTotal(userCart.getCartTotal().add(cartItem.getSubTotal()));
                userCart.getCartItemList().add(savedCartItem);
                userCart.setQuantity(userCart.getCartItemList().size());
                cartRepository.save(userCart);
            } else {
                cartItem = DbCartItem.get();
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                cartItem.setSubTotal(cartItem.getSubTotal().add(product.getProductPrice()));
                cartItem.setCart(userCart);
                CartItem savedCartItem = cartItemRepository.save(cartItem);

                userCart.setCartTotal(userCart.getCartTotal().add(product.getProductPrice()));
                userCart.setQuantity(userCart.getCartItemList().size());
                userCart.getCartItemList().add(savedCartItem);
                userCart = cartRepository.save(userCart);
            }
            List<CartItemDto> cartItemDtoList = new ArrayList<>();
            for (CartItem item : userCart.getCartItemList()) {
                ObjectMapper objectMapper = new ObjectMapper();
                CartItemDto cartItemDto = objectMapper.convertValue(item, CartItemDto.class);
                cartItemDtoList.add(cartItemDto);
            }
            CartResponse cartResponse = CartResponse.builder()
                    .cartItemList(cartItemDtoList)
                    .cartTotal(userCart.getCartTotal())
                    .quantity(userCart.getQuantity())
                    .build();
            return responseCodeUtil.updateResponseData(cartResponse, ResponseCodeEnum.SUCCESS, product.getProductName());
        } catch (Exception e) {
            log.error("An error occurred, Product cannot be added: {}", e.getMessage());
            return responseCodeUtil.updateResponseData(new CartResponse(), ResponseCodeEnum.ERROR);
        }
    }


    private void removeItem(long cartItemId, Cart cart, CartItem cartItem) {
        cartItemRepository.deleteById(cartItemId);
        cart.setCartTotal(cart.getCartTotal().subtract(cartItem.getSubTotal()));
        cart.setQuantity(cart.getQuantity() - 1);
        cartRepository.save(cart);
    }

}