package com.tb.javaecommerce.service.mappers;

import com.tb.javaecommerce.domain.Cart;
import com.tb.javaecommerce.entity.CartEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProductEntityMapper.class})
public interface CartEntityMapper {

    @Mapping(target = "order", ignore = true)
    CartEntity toEntity(Cart domain);

    Cart toDomain(CartEntity entity);
}

