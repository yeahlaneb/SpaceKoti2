package com.tb.javaecommerce.dto.product;

import com.tb.javaecommerce.domain.Category;
import com.tb.javaecommerce.dto.category.CategoryDto;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class ProductResponseDto {
    Long id;
    String title;
    String description;
    Double price;
    String status;
    CategoryDto category;
}



