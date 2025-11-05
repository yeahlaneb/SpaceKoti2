package com.tb.javaecommerce.domain;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Cart {
    private UUID id;
    private UUID productId;
    private String productName;
    private double price;
    private int quantity;
}
