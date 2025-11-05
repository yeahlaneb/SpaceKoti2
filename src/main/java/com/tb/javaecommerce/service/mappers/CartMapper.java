package com.tb.javaecommerce.service.mappers;

import com.tb.javaecommerce.domain.Cart;
import com.tb.javaecommerce.dto.cart.CartRequestDto;
import com.tb.javaecommerce.dto.cart.CartResponseDto;
import com.tb.javaecommerce.dto.product.ProductResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(target = "productName", ignore = true)
    @Mapping(target = "price", ignore = true)
    Cart toCart(CartRequestDto cartRequestDto);

    @Mapping(target = "totalPrice", expression = "java(cart.getPrice() * cart.getQuantity())")
    @Mapping(target = "products", expression = "java(buildProductList(cart))")
    CartResponseDto toCartResponseDto(Cart cart);

    default List<ProductResponseDto> buildProductList(Cart cart) {
        if (cart == null) return List.of();

        ProductResponseDto product = ProductResponseDto.builder()
                .id(cart.getProductId() != null ? cart.getProductId().toString() : null)
                .title(cart.getProductName())
                .price(cart.getPrice())
                .build();

        return List.of(product);
    }
}
