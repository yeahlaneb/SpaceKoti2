package com.tb.javaecommerce.service.mappers;

import com.tb.javaecommerce.domain.Order;
import com.tb.javaecommerce.entity.OrderEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CartEntityMapper.class})
public interface OrderEntityMapper {

    Order toDomain(OrderEntity entity);

    OrderEntity toEntity(Order domain);
}
