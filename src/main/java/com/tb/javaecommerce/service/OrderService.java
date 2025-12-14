package com.tb.javaecommerce.service;

import com.tb.javaecommerce.dto.order.OrderRequestDto;
import com.tb.javaecommerce.dto.order.OrderResponseDto;

public interface OrderService {
    OrderResponseDto addOrder(OrderRequestDto orderRequestDto);
}
