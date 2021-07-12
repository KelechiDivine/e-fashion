package com.houseofo.web.service;

import com.houseofo.data.model.Order;
import com.houseofo.data.model.Role;
import com.houseofo.data.repository.OrderRepository;
import com.houseofo.util.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Service
@Slf4j
@ExtendWith(MockitoExtension.class)
@DataMongoTest
class OrderServiceImplTest {
    @Mock
    OrderRepository orderRepository;

    @Mock
    ModelMapper modelMapper;

    @Mock
    UserMapper userMapper;

    @InjectMocks
    OrderServiceImpl orderService;

    Order order;

    @BeforeEach
    void setUp() {
        orderService = new OrderServiceImpl();
        MockitoAnnotations.openMocks(this);
        order = new Order();
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void findOrderByDateOrdered() {
        LocalDate dateOrdered = LocalDate.now();
        order.setDateOrdered(dateOrdered);

        orderService.findOrderByDateOrdered(dateOrdered);

        verify(orderRepository).findOrdersByDateOrdered(dateOrdered);


        log.info("the date ordered for ->{} is ->{}", order, dateOrdered);

        assertThat(order.getDateOrdered()).isEqualTo(dateOrdered);


    }

    @Test
    void findCompletedOrders() {
    }

}