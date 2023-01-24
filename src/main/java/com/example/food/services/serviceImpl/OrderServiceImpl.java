package com.example.food.services.serviceImpl;

import com.example.food.Enum.ResponseCodeEnum;
import com.example.food.dto.OrderDto;
import com.example.food.model.Order;
import com.example.food.model.Users;
import com.example.food.pojos.OrderResponseDto;
import com.example.food.pojos.ViewAllOrderResponse;
import com.example.food.repositories.OrderRepository;
import com.example.food.repositories.UserRepository;
import com.example.food.services.OrderService;
import com.example.food.util.ResponseCodeUtil;
import com.example.food.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final UserUtil userUtil;
    private final ResponseCodeUtil responseCodeUtil = new ResponseCodeUtil();

    @Override
    public OrderResponseDto viewDetailsOfAParticularOrder(Long orderId) {
        OrderResponseDto response = new OrderResponseDto();
        String email = userUtil.getAuthenticatedUserEmail();
        Optional<Users> user = userRepository.findByEmail(email);

        if (user.isEmpty()){
            return responseCodeUtil.updateResponseData(response, ResponseCodeEnum.ERROR,"Unauthorised access");
        }

        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isEmpty()){
            return responseCodeUtil.updateResponseData(response,ResponseCodeEnum.ERROR,"Order not found");
        }
        OrderDto orderDto = new OrderDto();
        orderDto.setOrder(order.get());
//        BeanUtils.copyProperties(order.get(),orderDto);
        response.setOrderDto(orderDto);
        return responseCodeUtil.updateResponseData(response,ResponseCodeEnum.SUCCESS,"These are the order details");
    }

    @Override
    public ViewAllOrderResponse viewAllOrders(Long userId) {
        ViewAllOrderResponse allOrderResponse = new ViewAllOrderResponse();
        String email = userUtil.getAuthenticatedUserEmail();
        Optional<Users> users = userRepository.findByEmail(email);

        if (users.isEmpty()) {
            return responseCodeUtil.updateResponseData(allOrderResponse, ResponseCodeEnum.USER_NOT_FOUND);
        }
        List<Order> myOrder = orderRepository.findAllByUser_Email(email);

        if (myOrder.isEmpty()) {
            allOrderResponse.setCode(-1);
            allOrderResponse.setListOfOrders(new ArrayList<>());
//            allOrderResponse.setDescription("You have not made an order");
            return responseCodeUtil.updateResponseData(allOrderResponse, ResponseCodeEnum.ORDERS_NOT_FOUND);

        }else {
            allOrderResponse.setCode(0);
            allOrderResponse.setListOfOrders(myOrder);
            allOrderResponse.setDescription("You have  made an order");
            return responseCodeUtil.updateResponseData(allOrderResponse, ResponseCodeEnum.SUCCESS);
        }
    }
}
