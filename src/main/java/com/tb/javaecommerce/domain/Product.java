package com.tb.javaecommerce.domain;

import com.tb.javaecommerce.common.ProductStatus;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class Product {
    private UUID id;
    private String title;
    private String description;
    private Double price;
    private ProductStatus status;

    Category category;
}
