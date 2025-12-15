package com.tb.javaecommerce.dto.cart;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class CartResponseDto {
    Long productId;
    String productName;
    BigDecimal price;
    int quantity;
}
