package com.tb.javaecommerce.dto.cart;

import lombok.Builder;
import lombok.Value;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

import java.util.UUID;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
public class CartDto {
    UUID productId;
    String productName;
    Double price;
    Integer quantity;
}
