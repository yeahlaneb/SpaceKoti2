package com.tb.javaecommerce.service;

import com.tb.javaecommerce.domain.Cart;
import com.tb.javaecommerce.domain.Product;
import com.tb.javaecommerce.common.ProductStatus;
import com.tb.javaecommerce.dto.cart.CartRequestDto;
import com.tb.javaecommerce.dto.cart.CartResponseDto;
import com.tb.javaecommerce.dto.category.CategoryDto;
import com.tb.javaecommerce.dto.product.ProductResponseDto;
import com.tb.javaecommerce.service.impl.CartServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class CartServiceTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private CartServiceImpl cartService;

    private UUID productId;
    private CartRequestDto request;
    private Product mockProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        productId = UUID.randomUUID();
        request = CartRequestDto.builder()
                .productId(productId)
                .quantity(2)
                .build();

        mockProduct = Product.builder()
                .id(productId)
                .title("Test Product")
                .description("Awesome product")
                .price(100.0)
                .status(ProductStatus.IN_STOCK)
                .category(com.tb.javaecommerce.domain.Category.builder()
                        .title("Space Goods")
                        .description("Galactic stuff")
                        .build())
                .build();
    }

    @Test
    void addToCart_ShouldAddProductSuccessfully() {
        when(productService.getProductById(productId.toString())).thenReturn(mockProduct);

        CartResponseDto response = cartService.addToCart(request);

        assertNotNull(response);
        assertEquals(1, response.getProducts().size());
        assertEquals("Test Product", response.getProducts().get(0).getTitle());
        assertEquals(200.0, response.getTotalPrice());
        verify(productService, times(1)).getProductById(productId.toString());
    }

    @Test
    void getCart_ShouldReturnCartWithProducts() {

        when(productService.getProductById(productId.toString())).thenReturn(mockProduct);
        cartService.addToCart(request);


        CartResponseDto response = cartService.getCart(1L);

        assertNotNull(response);
        assertFalse(response.getProducts().isEmpty());
        assertEquals("Test Product", response.getProducts().get(0).getTitle());
        verify(productService, atLeastOnce()).getProductById(productId.toString());
    }

    @Test
    void clearCart_ShouldEmptyTheCart() {
        when(productService.getProductById(productId.toString())).thenReturn(mockProduct);
        cartService.addToCart(request);

        cartService.clearCart(1L);
        CartResponseDto response = cartService.getCart(1L);

        assertTrue(response.getProducts().isEmpty());
        assertEquals(0.0, response.getTotalPrice());
    }
}
