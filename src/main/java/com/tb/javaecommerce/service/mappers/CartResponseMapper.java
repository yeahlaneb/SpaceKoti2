package com.tb.javaecommerce.service.mappers;

import com.tb.javaecommerce.domain.Cart;
import com.tb.javaecommerce.dto.cart.CartResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartResponseMapper {

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.title")
    @Mapping(target = "price", source = "product.price")
    @Mapping(target = "quantity", source = "quantity")
    CartResponseDto toResponse(Cart cart);

    List<CartResponseDto> toResponseList(List<Cart> carts);
}
