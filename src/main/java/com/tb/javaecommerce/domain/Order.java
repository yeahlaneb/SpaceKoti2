package com.tb.javaecommerce.domain;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private Long id;
    private String consumerName;
    private String address;
    private String email;
    private String orderStatus;
    private List<Cart> cartItems;
    private BigDecimal totalPrice;
    private String orderNumber;
}
