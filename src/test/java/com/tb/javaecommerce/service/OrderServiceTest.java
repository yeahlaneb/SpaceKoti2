package com.tb.javaecommerce.service;

import com.tb.javaecommerce.dto.cart.CartDto;
import com.tb.javaecommerce.dto.order.OrderRequestDto;
import com.tb.javaecommerce.dto.order.OrderResponseDto;
import com.tb.javaecommerce.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Order Service Test")
@SpringBootTest(classes = {OrderServiceImpl.class})
public class OrderServiceTest {

    @Autowired
    @Spy
    private OrderService orderService;

    private OrderRequestDto orderRequestDto;
    private List<CartDto> cartDtoList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        cartDtoList = new ArrayList<>();
        cartDtoList.add(new CartDto(UUID.randomUUID(), "Product1", 20.0, 2));
        cartDtoList.add(new CartDto(UUID.randomUUID(), "Product2", 10.0, 1));

        orderRequestDto = new OrderRequestDto(
                "John Doe",
                "123 Test St",
                "test@example.com",
                cartDtoList
        );
    }

    @Test
    public void testAddOrder() {
        OrderResponseDto order = orderService.addOrder(orderRequestDto);

        assertEquals(orderRequestDto.getConsumerName(), order.getConsumerName());
        assertEquals(orderRequestDto.getAddress(), order.getAddress());
        assertEquals(orderRequestDto.getEmail(), order.getEmail());
        assertEquals(cartDtoList.size(), order.getCartItems().size());
    }
}
