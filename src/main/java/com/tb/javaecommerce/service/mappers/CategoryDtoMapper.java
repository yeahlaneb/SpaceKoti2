package com.tb.javaecommerce.service.mappers;

import com.tb.javaecommerce.domain.Category;
import com.tb.javaecommerce.dto.category.CategoryDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryDtoMapper {
    CategoryDto toDto(Category category);
}
