package com.tb.javaecommerce.service.impl;

import com.tb.javaecommerce.dto.cart.CartResponseDto;
import com.tb.javaecommerce.dto.order.OrderRequestDto;
import com.tb.javaecommerce.dto.order.OrderResponseDto;
import com.tb.javaecommerce.domain.Cart;
import com.tb.javaecommerce.domain.Order;
import com.tb.javaecommerce.domain.Product;
import com.tb.javaecommerce.entity.CartEntity;
import com.tb.javaecommerce.entity.OrderEntity;
import com.tb.javaecommerce.entity.ProductEntity;
import com.tb.javaecommerce.repository.OrderRepository;
import com.tb.javaecommerce.repository.ProductRepository;
import com.tb.javaecommerce.service.OrderService;
import com.tb.javaecommerce.service.mappers.CartEntityMapper;
import com.tb.javaecommerce.service.mappers.OrderEntityMapper;
import com.tb.javaecommerce.service.mappers.ProductEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    private final OrderEntityMapper orderEntityMapper;
    private final CartEntityMapper cartEntityMapper;
    private final ProductEntityMapper productEntityMapper;

    @Override
    @Transactional
    public OrderResponseDto addOrder(OrderRequestDto dto) {

        List<Cart> cartDomainItems = dto.getCartItems().stream().map(item -> {

            ProductEntity productEntity = productRepository.findById(item.getProductId())
                    .orElseThrow(() ->
                            new RuntimeException("Product not found: " + item.getProductId())
                    );

            Product product = productEntityMapper.toDomain(productEntity);

            Cart cart = new Cart();
            cart.setProduct(product);
            cart.setQuantity(item.getQuantity());
            return cart;

        }).toList();

        Order orderDomain = new Order();
        orderDomain.setConsumerName(dto.getConsumerName());
        orderDomain.setAddress(dto.getAddress());
        orderDomain.setEmail(dto.getEmail());
        orderDomain.setOrderStatus("CREATED");
        orderDomain.setCartItems(cartDomainItems);
        orderDomain.setTotalPrice(calculateTotal(cartDomainItems));
        orderDomain.setOrderNumber(generateOrderNumber());

        OrderEntity orderEntity = orderEntityMapper.toEntity(orderDomain);

        List<CartEntity> cartEntities = cartDomainItems.stream().map(cart -> {

            CartEntity cartEntity = cartEntityMapper.toEntity(cart);

            ProductEntity productEntity = productRepository.findById(
                    cart.getProduct().getId()
            ).orElseThrow(() ->
                    new RuntimeException("Product not found during Cart map")
            );

            cartEntity.setProduct(productEntity);
            cartEntity.setOrder(orderEntity);
            return cartEntity;

        }).toList();

        orderEntity.setCartItems(cartEntities);

        OrderEntity saved = orderRepository.save(orderEntity);
        Order savedDomain = orderEntityMapper.toDomain(saved);

        return toResponseDto(savedDomain);
    }


    private BigDecimal calculateTotal(List<Cart> items) {
        return items.stream()
                .map(item ->
                        item.getProduct().getPrice()
                                .multiply(BigDecimal.valueOf(item.getQuantity()))
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private String generateOrderNumber() {
        return "ORD-" + UUID.randomUUID();
    }

    private OrderResponseDto toResponseDto(Order order) {

        List<CartResponseDto> cartDtos = order.getCartItems().stream()
                .map(c -> CartResponseDto.builder()
                        .productId(c.getProduct().getId())
                        .productName(c.getProduct().getTitle())
                        .price(c.getProduct().getPrice())
                        .quantity(c.getQuantity())
                        .build()
                )
                .toList();

        return OrderResponseDto.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .consumerName(order.getConsumerName())
                .address(order.getAddress())
                .email(order.getEmail())
                .orderStatus(order.getOrderStatus())
                .totalPrice(order.getTotalPrice())
                .cartItems(cartDtos)
                .build();
    }
}
