package com.tb.javaecommerce.service.mappers;

import com.tb.javaecommerce.domain.Product;
import com.tb.javaecommerce.dto.product.ProductResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryEntityMapper.class})
public interface ProductMapper {

    @Mapping(target = "category", source = "category")
    @Mapping(target = "status", expression = "java(product.getStatus().name())")
    ProductResponseDto toProductResponseDto(Product product);

    List<ProductResponseDto> toProductResponseDtoList(List<Product> products);
}