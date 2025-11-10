package com.tb.javaecommerce.dto.cart;

import com.tb.javaecommerce.dto.product.ProductResponseDto;
import lombok.Builder;
import lombok.Value;
import lombok.AllArgsConstructor;
import lombok.AccessLevel;

import java.util.List;
import java.util.UUID;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class CartResponseDto {
    UUID id;
    Integer quantity;
    Double totalPrice;
    List<ProductResponseDto> products;
}
