package com.example.food.services.serviceImpl;

import com.example.food.model.Cart;
import com.example.food.model.CartItem;
import com.example.food.model.Users;
import com.example.food.repositories.CartItemRepository;
import com.example.food.repositories.CartRepository;
import com.example.food.repositories.UserRepository;
import com.example.food.services.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@AllArgsConstructor
@Service
public class CartServiceImpl implements CartService {
    private CartRepository cartRepository;
    private UserRepository userRepository;
    private CartItemRepository cartItemRepository;

    private Users getLoggedInUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsernameOrEmail(authentication, authentication)
                .orElseThrow(() -> new RuntimeException("User not authorized"));
    }

    private void removeItem(long cartItemId, Cart cart, CartItem cartItem) {
        cartItemRepository.deleteById(cartItemId);
        cart.setCartTotal(cart.getCartTotal() - cartItem.getSubTotal());
        cart.setQuantity(cart.getQuantity() - 1);
        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public ResponseEntity<String> clearCart() {
        Users user = getLoggedInUser();
        Cart userCart = user.getCart();
        cartItemRepository.deleteAllByCart_CartId(userCart.getCartId());
        userCart.setCartTotal(0);
        userCart.setQuantity(0);
        cartRepository.save(userCart);
        return ResponseEntity.ok("Cart cleared successfully");

    }

    @Override
    public ResponseEntity<String> removeCartItem(long cartItemId) {
        Users user = getLoggedInUser();
        Cart cart = user.getCart();
        Optional<CartItem> cartItemCheck = cartItemRepository.findByCart_CartIdAndCartItemId(cart.getCartId(), cartItemId);
        if (cartItemCheck.isPresent()) {
            CartItem cartItem = cartItemCheck.get();
            removeItem(cartItemId, cart, cartItem);
            return ResponseEntity.ok("Item removed from user cart");
        } else {
            throw new RuntimeException("Item is not in user cart");
        }
    }
}
