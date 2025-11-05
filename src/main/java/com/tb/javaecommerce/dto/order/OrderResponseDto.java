package com.tb.javaecommerce.dto.order;

import com.tb.javaecommerce.dto.cart.CartDto;
import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
@Builder(toBuilder = true)
public class OrderResponseDto {
    UUID id;
    String consumerName;
    String address;
    String email;
    String orderStatus;
    List<CartDto> cartItems;
    double totalPrice;
}
