package com.example.food.service.serviceImpl;
import com.example.food.model.Cart;
import com.example.food.model.CartItem;
import com.example.food.model.Product;
import com.example.food.model.Users;
import com.example.food.pojos.CartResponse;
import com.example.food.repositories.CartItemRepository;
import com.example.food.repositories.CartRepository;
import com.example.food.repositories.UserRepository;
import com.example.food.restartifacts.BaseResponse;
import com.example.food.services.serviceImpl.CartServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class CartServiceImplTest {
    @Mock
    private CartRepository cartRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CartItemRepository cartItemRepository;
    @InjectMocks
    private CartServiceImpl cartServiceImpl;
    Users user;
    Cart cart;
    CartItem cartItem1,cartItem2, cartItem3;
    Product product1, product2;
    BaseResponse baseResponse2;


    @BeforeEach
    void setUp() {
        product1 = Product.builder().id(1L)
                .productName("Burger")
                .productPrice(BigDecimal.valueOf(2000)).build();
        product2 = Product.builder().id(2L)
                .productName("Apple")
                .productPrice(BigDecimal.valueOf(2000)).build();
        user = Users.builder().id(1L).email("mensa@gmail.com").password("password").build();
        cart = Cart.builder().cartId(1L).users(user).cartItemList(new ArrayList<>()).cartTotal(BigDecimal.valueOf(0)).quantity(0).build();
        user.setCart(cart);
        cartItem1 = CartItem.builder().cartItemId(1L).cart(cart).product(product1).quantity(0).subTotal(BigDecimal.valueOf(0)).build();
        cartItem2 = CartItem.builder().cartItemId(2L).cart(cart).product(product2).quantity(0).subTotal(BigDecimal.valueOf(0)).build();
        cartItem3 = CartItem.builder().cartItemId(3L).cart(cart).product(product2).quantity(0).subTotal(BigDecimal.valueOf(0)).build();
        baseResponse2 = new BaseResponse();
        baseResponse2.setCode(0);
        baseResponse2.setDescription("Item is not in user cart");
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void testRemoveCartItemSuccess() {
        Mockito.when(cartItemRepository.findByCartItemId(anyLong())).thenReturn(Optional.of(cartItem1));
        Mockito.when(userRepository.findByEmail( any())).thenReturn(Optional.ofNullable(user));
        Mockito.doNothing().when(cartItemRepository).deleteById(anyLong());
        Mockito.when(cartRepository.save(any())).thenReturn(cart);

        BaseResponse baseResponse = cartServiceImpl.removeCartItem(1);
        Assertions.assertThat(baseResponse.getDescription()).isEqualTo("Item removed from user cart");
    }

    @Test
    public void testViewCartItems() {
        List<Cart> cartList = List.of(new Cart(1L, 20, new BigDecimal(2000), new ArrayList<>(), user));

        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        when(cartRepository.findAllByUsersOrderByCartId(user)).thenReturn(cartList);

        CartResponse cartResponse = cartServiceImpl.viewCartItems();

        Mockito.verify(cartRepository, times(1))
                .findAllByUsersOrderByCartId(any(Users.class));

        assertNotNull(cartResponse);
        assertEquals(1, cartResponse.getCartList().size());
    }
}