package com.houseofo.web.service;

import com.houseofo.data.dtos.OrderDto;
import com.houseofo.data.model.Order;
import com.houseofo.data.model.OrderStatus;
import com.houseofo.data.model.Type;
import com.houseofo.data.repository.DressRepository;
import com.houseofo.data.repository.OrderRepository;
import com.houseofo.exceptions.OrderException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    DressRepository dressRepository;

    @Autowired
    ModelMapper modelMapper;



//    @Autowired
//    UserMapper mapper;


    @Override
    public OrderDto findById(String id) throws OrderException {
        Order order = orderRepository.findById(id)
                .orElseThrow(()-> new OrderException("Id does not match any order"));
        OrderDto dto = modelMapper.map(order, OrderDto.class);
        return dto;
    }

    @Override
    public List<OrderDto> findOrderByDateOrdered(LocalDate dateOrdered) {
        List<Order> orders = orderRepository.findOrdersByDateOrdered(dateOrdered);
        List<OrderDto> orderDtos = orders.stream()
                .map(order -> modelMapper.map(order,OrderDto.class))
                .collect(Collectors.toList());
        return orderDtos;
    }


    @Override
    public List<OrderDto> findCompletedOrders() {
        List<Order> orderList = orderRepository.findOrdersByOrderStatus(OrderStatus.COMPLETED);
        List<OrderDto> orderDtoList = orderList
                .stream()
                .map(order -> modelMapper.map(order,OrderDto.class))
                .collect(Collectors.toList());
        return orderDtoList;
    }

    @Override
    public OrderDto createOrder(OrderDto orderDto) throws OrderException {
        if (orderRepository.findById(orderDto.getId()).isPresent()){
            throw new OrderException("Order already exists");
        }
        Order order = modelMapper.map(orderDto, Order.class);
        orderRepository.save(order);
        return orderDto;
    }

    @Override
    public void cancelOrder(String id) throws OrderException {
        Order order = orderRepository.findById(id)
                .orElseThrow(()-> new OrderException("Id does not match any order"));
        if (order.getOrderStatus().equals(OrderStatus.COMPLETED)){
            throw new OrderException("Order cannot be cancelled once completed");
        }
        order.setOrderStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }
}
