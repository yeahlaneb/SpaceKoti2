package com.tb.javaecommerce.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tb.javaecommerce.dto.cart.CartDto;
import com.tb.javaecommerce.dto.order.OrderRequestDto;
import com.tb.javaecommerce.dto.order.OrderResponseDto;
import com.tb.javaecommerce.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(OrderController.class)
@DisplayName("Order Controller Integration Test")
class OrderControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    private OrderRequestDto orderRequestDto;

    @BeforeEach
    void setUp() {
        List<CartDto> cartDtoList = List.of(
                new CartDto(UUID.randomUUID(), "Product1", 20.0, 2),
                new CartDto(UUID.randomUUID(), "Product2", 10.0, 1)
        );

        orderRequestDto = new OrderRequestDto(
                "John Doe",
                "123 Test St",
                "test@example.com",
                cartDtoList
        );

        Mockito.when(orderService.addOrder(any(OrderRequestDto.class)))
                .thenReturn(OrderResponseDto.builder()
                        .id(UUID.randomUUID())
                        .consumerName(orderRequestDto.getConsumerName())
                        .address(orderRequestDto.getAddress())
                        .email(orderRequestDto.getEmail())
                        .cartItems(orderRequestDto.getCartItems())
                        .totalPrice(50.0)
                        .orderStatus("CREATED")
                        .build());
    }

    @Test
    @DisplayName("POST /api/v1/orders should create an order successfully")
    void testAddOrderEndpoint() throws Exception {
        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequestDto)))
                .andExpect(status().isOk());
    }
}
