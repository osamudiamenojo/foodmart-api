package com.example.food.services.serviceImpl;

import com.example.food.dto.CheckoutDto;
import com.example.food.model.Cart;
import com.example.food.model.Users;
import com.example.food.pojos.OrderResponse;
import com.example.food.repositories.CartRepository;
import com.example.food.repositories.OrderRepository;
import com.example.food.repositories.UserRepository;
import com.example.food.services.CheckoutService;
import com.example.food.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CheckoutServiceImpl implements CheckoutService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final UserUtil userUtil;
    @Override
    public OrderResponse checkout(CheckoutDto checkoutDto) {
        String loggedInUserEmail = userUtil.getAuthenticatedUserEmail();
        Users user = userRepository.findByEmail(loggedInUserEmail).orElseThrow(RuntimeException::new);
        Cart userCart = cartRepository.findByUsersEmail(loggedInUserEmail).orElseThrow(RuntimeException::new);
        if(userCart.getCartItemList().isEmpty());
        return null;
    }
}
