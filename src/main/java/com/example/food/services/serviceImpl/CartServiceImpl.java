package com.example.food.services.serviceImpl;

import com.example.food.Enum.ResponseCodeEnum;
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
import com.example.food.util.UserUtil;
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
    private CartRepository cartRepository;
    private UserRepository userRepository;
    private CartItemRepository cartItemRepository;
    private ResponseCodeUtil responseCodeUtil;
    private UserUtil userUtil;
    private ProductRepository productRepository;

    private Users getLoggedInUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(authentication)
                .orElseThrow(() -> new RuntimeException("User not authorized"));
    }

    @Override
    public BaseResponse removeCartItem(long cartItemId) {
        CartResponse cartResponse = new CartResponse();
        try {
            Users user = getLoggedInUser();
            Cart cart = user.getCart();
            Optional<CartItem> cartItemCheck = cartItemRepository.findByCartItemId(cartItemId);
            if (cartItemCheck.isPresent()) {
                CartItem cartItem = cartItemCheck.get();
                removeItem(cartItemId, cart, cartItem);
                cartResponse = CartResponse.builder()
                        .users(user)
                        .quantity(cart.getQuantity())
                        .cartItemList(cart.getCartItemList())
                        .cartTotal(cart.getCartTotal())
                        .build();
                cartResponse.setCode(0);
                cartResponse.setDescription("Item removed from user cart");
            } else {
                cartResponse.setCode(1);
                cartResponse.setDescription("Item is not in user cart");
            }
            return responseCodeUtil.updateResponseData(cartResponse, ResponseCodeEnum.SUCCESS);
        } catch (Exception e) {
            log.error("Email not registered, Product cannot be removed: {}", e.getMessage());
        }
        return responseCodeUtil.updateResponseData(cartResponse, ResponseCodeEnum.ERROR);
    }

    @Override
    public BaseResponse addCartItem(Long productId) {
        Users user = userUtil.currentUser();
        Cart userCart = user.getCart();
        CartResponse cartResponse = new CartResponse();
        //Adding Product to cartItem
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            CartItem cartItem = CartItem.builder()
                    .product(product.get())
                    .quantity(1)
                    .cart(userCart)
                    .subTotal(product.get().getProductPrice())
                    .build();
        }


        return null;
    }

    /**
     * @param cartItem
     * @return
     */
    @Override
    public BaseResponse reduceCartItemQuantity(Long cartItem) {
        return null;
    }

    private void removeItem(long cartItemId, Cart cart, CartItem cartItem) {
        cartItemRepository.deleteById(cartItemId);
        cart.setCartTotal(cart.getCartTotal().subtract(cartItem.getSubTotal()));
        cart.setQuantity(cart.getQuantity() - 1);
        cartRepository.save(cart);
    }

}
