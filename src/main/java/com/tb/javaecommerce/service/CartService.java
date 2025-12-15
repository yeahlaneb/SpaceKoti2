package com.tb.javaecommerce.service;

import com.tb.javaecommerce.dto.cart.CartDto;
import com.tb.javaecommerce.dto.cart.CartRequestDto;
import com.tb.javaecommerce.dto.cart.CartResponseDto;

public interface CartService {
    CartResponseDto addToCart(CartRequestDto request);
    CartResponseDto getCart(Long cartId);
    void clearCart(Long cartId);
}
