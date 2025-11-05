package com.tb.javaecommerce.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class Order {
    UUID id;
    String consumerName;
    String address;
    String email;
    String orderStatus;
    List<Cart> cartItems;
    double totalPrice;
}
