package com.tb.javaecommerce.service.impl;

import com.tb.javaecommerce.dto.cart.CartDto;
import com.tb.javaecommerce.dto.order.OrderRequestDto;
import com.tb.javaecommerce.dto.order.OrderResponseDto;
import com.tb.javaecommerce.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Override
    public OrderResponseDto addOrder(OrderRequestDto orderRequestDto) {
        double totalPrice = calculateTotalPrice(orderRequestDto.getCartItems());

        return createOrderMock(
                UUID.randomUUID(),
                orderRequestDto.getConsumerName(),
                orderRequestDto.getAddress(),
                orderRequestDto.getEmail(),
                "CREATED",
                orderRequestDto.getCartItems(),
                totalPrice
        );
    }

    private OrderResponseDto createOrderMock(
            UUID id,
            String consumerName,
            String address,
            String email,
            String orderStatus,
            List<CartDto> cartItems,
            double totalPrice) {

        return OrderResponseDto.builder()
                .id(id)
                .consumerName(consumerName)
                .address(address)
                .email(email)
                .orderStatus(orderStatus)
                .cartItems(cartItems)
                .totalPrice(totalPrice)
                .build();
    }

    private double calculateTotalPrice(List<CartDto> cartItems) {
        if (cartItems == null || cartItems.isEmpty()) {
            return 0.0;
        }

        return cartItems.stream()
                .mapToDouble(item -> (item.getPrice() != null ? item.getPrice() : 0.0)
                        * (item.getQuantity() != null ? item.getQuantity() : 0))
                .sum();
    }
}
