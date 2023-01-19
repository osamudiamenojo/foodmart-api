package com.example.food.services.serviceImpl;

import com.example.food.Enum.ResponseCodeEnum;
import com.example.food.model.Cart;
import com.example.food.model.CartItem;
import com.example.food.model.Users;
import com.example.food.repositories.CartItemRepository;
import com.example.food.repositories.CartRepository;
import com.example.food.repositories.UserRepository;
import com.example.food.restartifacts.BaseResponse;
import com.example.food.services.CartService;
import com.example.food.util.ResponseCodeUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Transactional
@AllArgsConstructor
@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final ResponseCodeUtil responseCodeUtil = new ResponseCodeUtil();

    private Users getLoggedInUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(authentication)
                .orElseThrow(() -> new RuntimeException("User not authorized"));
    }

    @Override
    public BaseResponse removeCartItem(long cartItemId) {
            BaseResponse baseResponse = new BaseResponse();
        try {
            Users user = getLoggedInUser();
            Cart cart = user.getCart();
            Optional<CartItem> cartItemCheck = cartItemRepository.findByCartItemId(cartItemId);
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

    private void removeItem(long cartItemId, Cart cart, CartItem cartItem) {
        cartItemRepository.deleteById(cartItemId);
        cart.setCartTotal(cart.getCartTotal() - cartItem.getSubTotal());
        cart.setQuantity(cart.getQuantity() - 1);
        cartRepository.save(cart);
    }
}
