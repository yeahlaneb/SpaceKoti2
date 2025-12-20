package com.tb.javaecommerce.service.impl;

import com.tb.javaecommerce.domain.Category;
import com.tb.javaecommerce.domain.Product;
import com.tb.javaecommerce.dto.product.ProductRequestDto;
import com.tb.javaecommerce.entity.ProductEntity;
import com.tb.javaecommerce.repository.ProductRepository;
import com.tb.javaecommerce.repository.projection.ProductSalesView;
import com.tb.javaecommerce.service.CategoryService;
import com.tb.javaecommerce.service.ProductService;
import com.tb.javaecommerce.service.exception.ProductNotFoundException;
import com.tb.javaecommerce.service.exception.CategoryNotFoundException;
import com.tb.javaecommerce.service.mappers.ProductEntityMapper;
import com.tb.javaecommerce.service.mappers.CategoryEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final CategoryService categoryService;
    private final ProductRepository productRepository;
    private final ProductEntityMapper productMapper;
    private final CategoryEntityMapper categoryMapper;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDomain)
                .toList();
    }

    @Override
    public Product getProductById(String productId) {
        Long id = Long.parseLong(productId);

        ProductEntity entity = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        return productMapper.toDomain(entity);
    }

    @Override
    public Product createProduct(ProductRequestDto dto) {
        Category category;
        try {
            category = categoryService.findCategoryById(dto.getCategoryId());
        } catch (CategoryNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to create product due to database error", ex);
        }

        ProductEntity entity = new ProductEntity();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setStatus(dto.getStatus());
        entity.setCategory(categoryMapper.toEntity(category));

        return productMapper.toDomain(productRepository.save(entity));
    }

    @Override
    public Product updateProduct(ProductRequestDto dto, String id) {
        Long productId = Long.parseLong(id);

        ProductEntity entity = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(id));

        Category category = categoryService.findCategoryById(dto.getCategoryId());

        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setStatus(dto.getStatus());
        entity.setCategory(categoryMapper.toEntity(category));

        return productMapper.toDomain(productRepository.save(entity));
    }

    @Override
    public void deleteProduct(String id) {
        productRepository.deleteById(Long.parseLong(id));
    }

    @Override
    public List<ProductSalesView> getTopSellingProducts() {
        return productRepository.findTopSellingProducts();
    }
}
