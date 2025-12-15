package com.tb.javaecommerce.dto.order;

import com.tb.javaecommerce.dto.cart.CartResponseDto;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Value
@Builder(toBuilder = true)
public class OrderResponseDto {

    Long id;
    String consumerName;
    String address;
    String email;
    String orderStatus;
    List<CartResponseDto> cartItems;
    BigDecimal totalPrice;
    String orderNumber;
}
