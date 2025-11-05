package com.tb.javaecommerce.dto.order;

import com.tb.javaecommerce.dto.cart.CartDto;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {
    private String consumerName;
    private String address;
    private String email;
    private List<CartDto> cartItems;
}
