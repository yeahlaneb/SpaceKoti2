package com.tb.javaecommerce.service.impl;

import com.tb.javaecommerce.domain.Category;
import com.tb.javaecommerce.repository.CategoryRepository;
import com.tb.javaecommerce.service.CategoryService;
import com.tb.javaecommerce.service.exception.CategoryNotFoundException;
import com.tb.javaecommerce.service.mappers.CategoryEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryEntityMapper categoryMapper;

    @Override
    public List<Category> findAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDomain)
                .toList();
    }

    @Override
    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toDomain)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }
}
