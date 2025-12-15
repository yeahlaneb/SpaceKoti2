package com.tb.javaecommerce.service.mappers;

import com.tb.javaecommerce.domain.Category;
import com.tb.javaecommerce.entity.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryEntityMapper {

    Category toDomain(CategoryEntity entity);

    CategoryEntity toEntity(Category domain);
}
