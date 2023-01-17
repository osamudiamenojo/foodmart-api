package com.example.food.services.serviceImpl;
import com.example.food.Enum.ResponseCodeEnum;
import com.example.food.model.Cart;
import com.example.food.model.CartItem;
import com.example.food.model.Product;
import com.example.food.model.Users;
import com.example.food.repositories.CartItemRepository;
import com.example.food.repositories.CartRepository;
import com.example.food.repositories.UserRepository;
import com.example.food.restartifacts.BaseResponse;
import com.example.food.util.ResponseCodeUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.ArrayList;
import java.util.Optional;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class CartServiceImplTest {
    @Mock
    private CartRepository cartRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private ResponseCodeUtil responseCodeUtil;
    @InjectMocks
    private CartServiceImpl cartServiceImpl;
    Users user;
    Cart cart;
    CartItem cartItem1,cartItem2, cartItem3;
    Product product1, product2;
    BaseResponse baseResponse1, baseResponse2;
    @BeforeEach
    void setUp() {
        product1 = Product.builder().productId(1L)
                .productName("Burger")
                .price(2000D).build();
        product2 = Product.builder().productId(2L)
                .productName("Apple")
                .price(2000D).build();
        user = Users.builder().usersId(1L).email("mensa@gmail.com").password("password").build();
        cart = Cart.builder().cartId(1L).users(user).cartItemList(new ArrayList<>()).cartTotal(0).quantity(0).build();
        user.setCart(cart);
        cartItem1 = CartItem.builder().cartItemId(1L).cart(cart).product(product1).quantity(0).subTotal(0).build();
        cartItem2 = CartItem.builder().cartItemId(2L).cart(cart).product(product2).quantity(0).subTotal(0).build();
        cartItem3 = CartItem.builder().cartItemId(3L).cart(cart).product(product2).quantity(0).subTotal(0).build();
        baseResponse1 = new BaseResponse();
        baseResponse1.setCode(0);
        baseResponse1.setDescription("Item removed from user cart");
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
        when(responseCodeUtil.updateResponseData(eq(baseResponse1), (ResponseCodeEnum) any()))
                .thenReturn(baseResponse1);
        BaseResponse baseResponse = cartServiceImpl.removeCartItem(1l);
        Assertions.assertThat(baseResponse.getDescription()).isEqualTo("Item removed from user cart");
    }
}