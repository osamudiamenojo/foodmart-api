package com.example.food.services.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.example.food.Enum.DeliveryMethod;
import com.example.food.Enum.PaymentMethod;
import com.example.food.dto.CheckoutDto;
import com.example.food.pojos.OrderResponse;
import com.example.food.repositories.CartRepository;
import com.example.food.repositories.OrderRepository;
import com.example.food.repositories.OrderedItemRepository;
import com.example.food.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CheckoutServiceImpl.class})
@ExtendWith(SpringExtension.class)
class CheckoutServiceImplTest {
    @MockBean
    private CartRepository cartRepository;

    @MockBean
    private CartServiceImpl cartServiceImpl;

    @Autowired
    private CheckoutServiceImpl checkoutServiceImpl;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private OrderedItemRepository orderedItemRepository;

    @MockBean
    private UserRepository userRepository;

    @Test
    void testCheckout() {
        CheckoutDto checkoutDto = new CheckoutDto();
        checkoutDto.setDeliveryMethod(DeliveryMethod.FLAT_RATE);
        checkoutDto.setPaymentMethod(PaymentMethod.CARD);
        OrderResponse actualCheckoutResult = checkoutServiceImpl.checkout(checkoutDto);
        assertEquals(-1, actualCheckoutResult.getCode());
    }

    @Test
    void verifyTestCheckout() {
        CheckoutDto checkoutDto = mock(CheckoutDto.class);
        doNothing().when(checkoutDto).setDeliveryMethod((DeliveryMethod) any());
        doNothing().when(checkoutDto).setPaymentMethod((PaymentMethod) any());
        checkoutDto.setDeliveryMethod(DeliveryMethod.FLAT_RATE);
        checkoutDto.setPaymentMethod(PaymentMethod.CARD);
        OrderResponse actualCheckoutResult = checkoutServiceImpl.checkout(checkoutDto);

        verify(checkoutDto).setDeliveryMethod((DeliveryMethod) any());
        verify(checkoutDto).setPaymentMethod((PaymentMethod) any());
    }
}

