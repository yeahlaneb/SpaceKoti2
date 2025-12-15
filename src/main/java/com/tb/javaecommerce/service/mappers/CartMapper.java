package com.tb.javaecommerce.service.mappers;

import com.tb.javaecommerce.domain.Cart;
import com.tb.javaecommerce.domain.Product;
import com.tb.javaecommerce.dto.cart.CartRequestDto;
import com.tb.javaecommerce.dto.cart.CartResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartMapper {

    default Cart toCart(CartRequestDto dto) {
        if (dto == null) return null;
        Product product = new Product();
        product.setId(dto.getProductId());

        Cart cart = new Cart();
        cart.setProduct(product);
        cart.setQuantity(dto.getQuantity());
        return cart;
    }

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.title")
    @Mapping(target = "price", source = "product.price")
    CartResponseDto toResponse(Cart cart);

    List<CartResponseDto> toResponseList(List<Cart> carts);
}

