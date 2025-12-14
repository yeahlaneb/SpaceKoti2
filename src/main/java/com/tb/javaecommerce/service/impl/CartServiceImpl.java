package com.tb.javaecommerce.service.impl;

import com.tb.javaecommerce.domain.Cart;
import com.tb.javaecommerce.domain.Product;
import com.tb.javaecommerce.dto.cart.CartRequestDto;
import com.tb.javaecommerce.dto.cart.CartResponseDto;
import com.tb.javaecommerce.service.CartService;
import com.tb.javaecommerce.service.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final ProductService productService;
    private final List<Cart> inMemoryCart = new ArrayList<>();

    public CartServiceImpl(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public CartResponseDto addToCart(CartRequestDto request) {

        Product product = productService.getProductById(
                String.valueOf(request.getProductId())
        );

        Cart cartItem = new Cart();
        cartItem.setProduct(product);
        cartItem.setQuantity(request.getQuantity());

        inMemoryCart.add(cartItem);

        return CartResponseDto.builder()
                .productId(product.getId())
                .productName(product.getTitle())
                .price(
                        product.getPrice() != null
                                ? product.getPrice()
                                : BigDecimal.ZERO
                )
                .quantity(request.getQuantity())
                .build();
    }

    @Override
    public CartResponseDto getCart(Long cartId) {

        BigDecimal totalPrice = inMemoryCart.stream()
                .map(c ->
                        c.getProduct() != null && c.getProduct().getPrice() != null
                                ? c.getProduct().getPrice()
                                        .multiply(BigDecimal.valueOf(c.getQuantity()))
                                : BigDecimal.ZERO
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int totalQuantity = inMemoryCart.stream()
                .mapToInt(Cart::getQuantity)
                .sum();

        return CartResponseDto.builder()
                .productId(null)
                .productName("TOTAL")
                .price(totalPrice)
                .quantity(totalQuantity)
                .build();
    }

    @Override
    public void clearCart(Long cartId) {
        inMemoryCart.clear();
    }
}
