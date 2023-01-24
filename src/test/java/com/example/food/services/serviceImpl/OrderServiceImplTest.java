package com.example.food.services.serviceImpl;

import com.example.food.model.Order;
import com.example.food.model.Users;
import com.example.food.pojos.ViewAllOrderResponse;
import com.example.food.repositories.OrderRepository;
import com.example.food.repositories.UserRepository;
import com.example.food.util.ResponseCodeUtil;
import com.example.food.util.UserUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @Mock
    UserRepository userRepository;
    @Mock
    OrderRepository orderRepository;
    @Mock
    private UserUtil userUtil;
    @Mock
    private ResponseCodeUtil responseCodeUtil;

    @InjectMocks
    private OrderServiceImpl orderServiceImpl;

    Users user1;
    Order order1;

    List<Order> listOfOrder;

    @BeforeEach
    void setUp() {

        listOfOrder = new ArrayList<>();
        UserDetails mockedUserDetails = Mockito.mock(UserDetails.class);
        when(userUtil.getAuthenticatedUserEmail()).thenReturn("faith@abiola.com");
        SecurityContextHolder.getContext().setAuthentication(new org.springframework.security.authentication
                .UsernamePasswordAuthenticationToken(mockedUserDetails, "password", new ArrayList<>()));
        Users loggedUser = new Users();
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(loggedUser));
    }

    @Test
    void viewAllOrdersWhenOrderIsEmpty() {
        when(orderRepository.findAllByUser_Email(anyString())).thenReturn(listOfOrder);
        ViewAllOrderResponse viewAllOrderResponse = orderServiceImpl.viewAllOrders(1L);
        assertEquals("No Order found",viewAllOrderResponse.getDescription());
        assertTrue(viewAllOrderResponse.getCode()==-9);
    }

    @Test
    void viewAllOrdersWhenOrderIsNotEmpty() {
        listOfOrder.add(new Order());
        when(orderRepository.findAllByUser_Email(anyString())).thenReturn(listOfOrder);
        ViewAllOrderResponse viewAllOrderResponse = orderServiceImpl.viewAllOrders(1L);
        assertEquals(0,viewAllOrderResponse.getCode());
    }
}