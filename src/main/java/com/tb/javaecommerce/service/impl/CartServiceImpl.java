package com.tb.javaecommerce.service.impl;

import com.tb.javaecommerce.domain.Cart;
import com.tb.javaecommerce.domain.Product;
import com.tb.javaecommerce.dto.cart.CartRequestDto;
import com.tb.javaecommerce.dto.cart.CartResponseDto;
import com.tb.javaecommerce.dto.category.CategoryDto;
import com.tb.javaecommerce.dto.product.ProductResponseDto;
import com.tb.javaecommerce.service.CartService;
import com.tb.javaecommerce.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CartServiceImpl implements CartService {

    private final ProductService productService;
    private final List<Cart> inMemoryCart = new ArrayList<>();

    public CartServiceImpl(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public CartResponseDto addToCart(CartRequestDto request) {
        Product productEntity = productService.getProductById(request.getProductId().toString());

        ProductResponseDto product = ProductResponseDto.builder()
                .id(productEntity.getId().toString())
                .title(productEntity.getTitle())
                .description(productEntity.getDescription())
                .price(productEntity.getPrice())
                .status(productEntity.getStatus() != null ? productEntity.getStatus().name() : null)
                .category(productEntity.getCategory() != null
                        ? CategoryDto.builder()
                            .title(productEntity.getCategory().getTitle())
                            .description(productEntity.getCategory().getDescription())
                            .build()
                        : null)
                .build();

        Cart cartItem = Cart.builder()
                .id(UUID.randomUUID())
                .productId(request.getProductId())
                .productName(product.getTitle())
                .price(product.getPrice())
                .quantity(request.getQuantity())
                .build();

        inMemoryCart.add(cartItem);

        return new CartResponseDto(
                cartItem.getId(),
                cartItem.getQuantity(),
                calculateTotalPrice(),
                List.of(product)
        );
    }

    @Override
    public CartResponseDto getCart(Long cartId) {
        double totalPrice = calculateTotalPrice();
        int totalQuantity = inMemoryCart.stream().mapToInt(Cart::getQuantity).sum();

        List<ProductResponseDto> products = inMemoryCart.stream()
            .map(c -> {
                Product productEntity = productService.getProductById(c.getProductId().toString());
                return ProductResponseDto.builder()
                        .id(productEntity.getId().toString())
                        .title(productEntity.getTitle())
                        .description(productEntity.getDescription())
                        .price(productEntity.getPrice())
                        .status(productEntity.getStatus() != null ? productEntity.getStatus().name() : null)
                        .category(productEntity.getCategory() != null
                                ? CategoryDto.builder()
                                    .title(productEntity.getCategory().getTitle())
                                    .description(productEntity.getCategory().getDescription())
                                    .build()
                                : null)
                        .build();
            })
            .toList();

        return new CartResponseDto(
                UUID.randomUUID(),
                totalQuantity,
                totalPrice,
                products
        );
    }

    @Override
    public void clearCart(Long cartId) {
        inMemoryCart.clear();
    }

    private double calculateTotalPrice() {
        return inMemoryCart.stream()
                .mapToDouble(c -> c.getPrice() * c.getQuantity())
                .sum();
    }
}
