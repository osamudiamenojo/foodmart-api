package com.example.food.service.serviceImpl;

import com.example.food.Enum.ResponseCodeEnum;
import com.example.food.Enum.Role;
import com.example.food.dto.OrderDto;
import com.example.food.dto.ProductDto;
import com.example.food.model.Order;
import com.example.food.model.Product;
import com.example.food.model.Users;
import com.example.food.pojos.OrderResponseDto;
import com.example.food.pojos.ProductResponseDto;
import com.example.food.repositories.OrderRepository;
import com.example.food.repositories.ProductRepository;
import com.example.food.repositories.UserRepository;
import com.example.food.services.OrderService;
import com.example.food.services.serviceImpl.OrderServiceImpl;
import com.example.food.services.serviceImpl.UserServiceImpl;
import com.example.food.util.ResponseCodeUtil;
import com.example.food.util.UserUtil;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
    public class ViewDetailsOfAParticularOrderTest {

        @Mock
        UserUtil userUtil;

        @Mock
        ResponseCodeUtil responseCodeUtil;

        @Mock
        UserRepository userRepository;

        @Mock
        OrderRepository orderRepository;

        @InjectMocks
        OrderServiceImpl service;

        @Test
        public void viewDetailsOfAParticularOrder_validOrder_returnsOrderDetails() {
            // Arrange
            Product product = new Product();
            product.setProductName("liberty");

            Order order = new Order();
            OrderDto orderDto = new OrderDto();
            orderDto.setOrder(order);

            Users user = new Users();
            user.setOrder(List.of(order));
            user.setEmail("test@email.com");
            user.setFirstName("Test_First_Name");
            user.setLastName("Test_Last_Name");
            user.setRole(Role.ROLE_ADMIN);

            OrderResponseDto expectedResponse = new OrderResponseDto();
            expectedResponse.setOrderDto(orderDto);

            when(userUtil.getAuthenticatedUserEmail()).thenReturn("test@email.com");
            when(userRepository.findByEmail("test@email.com")).thenReturn(Optional.of(user));
            when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

            OrderResponseDto actualResponse = service.viewDetailsOfAParticularOrder(1L);
            assertEquals(expectedResponse, actualResponse);
        }
    }

