package com.tb.javaecommerce.dto.product;

import com.tb.javaecommerce.common.ProductStatus;
import com.tb.javaecommerce.dto.validation.Extended;
import com.tb.javaecommerce.dto.validation.ValidSpaceTitle;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import java.math.BigDecimal;

@Value
@GroupSequence({ProductRequestDto.class, Extended.class})
@Builder(toBuilder = true)
public class ProductRequestDto {

    @NotBlank(message = "Title is mandatory")
    @Size(max = 50)
    @ValidSpaceTitle(groups = Extended.class)
    String title;

    @NotBlank(message = "Description is mandatory")
    @Size(max = 255)
    String description;

    @NotNull(message = "Price is mandatory")
    BigDecimal price;

    @NotNull(message = "Category is mandatory")
    Long categoryId;

    ProductStatus status;
}
