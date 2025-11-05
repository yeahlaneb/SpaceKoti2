package com.tb.javaecommerce.service.mappers;
import static org.junit.jupiter.api.Assertions.*;
import com.tb.javaecommerce.domain.Category;
import com.tb.javaecommerce.dto.category.CategoryDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CategoryMapperTest {
    private final CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);

    Category category;
    List<Category> categories;

    @BeforeEach
    void init() {
        category = Category.builder().id(1L).title("Test").description("Test").build();
        categories = List.of(category);
    }

    @Test
    void shouldMapCategoryToCategoryDto() {
        CategoryDto categoryDto = categoryMapper.categoryToCategoryDto(category);

        assertNotNull(categoryDto);
        assertEquals(category.getTitle(), categoryDto.getTitle());
        assertEquals(category.getDescription(), categoryDto.getDescription());
    }

    @Test
    void shouldMapCategoryListToCategoryDtoList() {
        List<CategoryDto> categoryDtoList = categoryMapper.categoryListToCategoryDtoList(categories);

        assertNotNull(categoryDtoList);
        assertEquals(categoryDtoList.size(), categories.size());
    }
}
