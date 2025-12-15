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
import com.tb.javaecommerce.service.mappers.ProductEntityMapper;
import com.tb.javaecommerce.service.mappers.CategoryEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
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
    public List<ProductSalesView> getTopSellingProducts() {
        return productRepository.findTopSellingProducts();
    }


    @Override
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public Product createProduct(ProductRequestDto dto) {
        Category category = categoryService.findCategoryById(dto.getCategoryId());

        ProductEntity entity = new ProductEntity();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setStatus(dto.getStatus());
        entity.setCategory(categoryMapper.toEntity(category));

        return productMapper.toDomain(productRepository.save(entity));
    }

    @Override
    @PreAuthorize("hasAuthority('SCOPE_admin')")
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
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public void deleteProduct(String id) {
        productRepository.deleteById(Long.parseLong(id));
    }
}
