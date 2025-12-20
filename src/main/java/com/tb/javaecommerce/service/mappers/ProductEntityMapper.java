package com.tb.javaecommerce.service.mappers;

import com.tb.javaecommerce.domain.Product;
import com.tb.javaecommerce.common.ProductStatus;
import com.tb.javaecommerce.entity.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CategoryEntityMapper.class})
public interface ProductEntityMapper {

    Product toDomain(ProductEntity entity);

    ProductEntity toEntity(Product domain);
}
