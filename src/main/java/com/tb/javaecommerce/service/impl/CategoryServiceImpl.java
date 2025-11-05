package com.tb.javaecommerce.service.impl;

import com.tb.javaecommerce.domain.Category;
import com.tb.javaecommerce.service.CategoryService;
import com.tb.javaecommerce.service.exception.CategoryNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    List<Category> categoryList = new ArrayList<>(List.of(
            Category.builder().id(1L).title("Cosmic food").description("Special offer").build(),
            Category.builder().id(2L).title("Galaxy rare items").description("Only in our store").build()
    ));

    @Override
    public List<Category> findAllCategories() {
        return categoryList;
    }

    @Override
    public Category findCategoryById(int id) {
        return categoryList.stream().filter(category -> category.getId() == id).findFirst().orElseThrow(() -> new CategoryNotFoundException(id));
    }
}
