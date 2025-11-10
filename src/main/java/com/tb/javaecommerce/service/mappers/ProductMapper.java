package com.tb.javaecommerce.service.mappers;

import com.tb.javaecommerce.common.ProductStatus;
import com.tb.javaecommerce.domain.Product;
import com.tb.javaecommerce.dto.product.ProductResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "status", source = "status", qualifiedByName = "toDisplayName")
    ProductResponseDto toProductResponseDto(Product product);

    List<ProductResponseDto> toProductResponseDtoList(List<Product> productList);

    @Named("toDisplayName")
    default String toDisplayStatus(ProductStatus status) {
        return status.getDisplayName();
    }
}
