package com.tb.javaecommerce.domain;

import com.tb.javaecommerce.common.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private ProductStatus status;
    private Category category;
}
