package com.tb.javaecommerce.service.mappers;

import com.tb.javaecommerce.domain.Order;
import com.tb.javaecommerce.dto.order.OrderResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = CartResponseMapper.class)
public interface OrderResponseMapper {
    OrderResponseDto toResponse(Order order);
}
