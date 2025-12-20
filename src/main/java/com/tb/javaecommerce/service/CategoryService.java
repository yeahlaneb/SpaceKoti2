package com.tb.javaecommerce.service;

import com.tb.javaecommerce.domain.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAllCategories();
    Category findCategoryById(Long categoryId);
}
