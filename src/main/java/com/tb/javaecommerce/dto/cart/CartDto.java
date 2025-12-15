package com.tb.javaecommerce.dto.cart;

import lombok.Builder;
import lombok.Value;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
public class CartDto {
    Long productId;
    Integer quantity;
}
