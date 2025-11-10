package com.tb.javaecommerce.service.impl;

import com.tb.javaecommerce.common.ProductStatus;
import com.tb.javaecommerce.domain.Category;
import com.tb.javaecommerce.domain.Product;
import com.tb.javaecommerce.dto.product.ProductRequestDto;
import com.tb.javaecommerce.service.CategoryService;
import com.tb.javaecommerce.service.ProductService;
import com.tb.javaecommerce.service.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private final CategoryService categoryService;
    private final List<Product> productList = new ArrayList<>();

    public ProductServiceImpl(CategoryService categoryService) {
        this.categoryService = categoryService;

        productList.add(Product.builder()
                .id(UUID.fromString("92cbf62b-abab-451b-9e8f-b092ee27cb62"))
                .title("Yummy moon apple")
                .description("An eatable apple found on the Earths satellite")
                .price(20.50)
                .status(ProductStatus.IN_STOCK)
                .category(categoryService.findCategoryById(1))
                .build());

        productList.add(Product.builder()
                .id(UUID.fromString("92cbf62b-abab-451b-9e8f-b092ee27cb63"))
                .title("Space Borsch")
                .description("Soup with outstanding taste")
                .price(75.99)
                .status(ProductStatus.IN_STOCK)
                .category(categoryService.findCategoryById(1))
                .build());

        productList.add(Product.builder()
                .id(UUID.randomUUID())
                .title("An unknown rock")
                .description("Mysterious rock people found at the edge of the galaxy")
                .price(700.50)
                .status(ProductStatus.IN_STOCK)
                .category(categoryService.findCategoryById(2))
                .build());
    }

    @Override
    public List<Product> getAllProducts() {
        return productList;
    }

    @Override
    public Product getProductById(String productId) {
        return productList.stream()
                .filter(item -> item.getId().toString().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

    @Override
    public Product createProduct(ProductRequestDto productRequestDto) {
        Category category = categoryService.findCategoryById(productRequestDto.getCategoryId());

        Product product = Product.builder()
                .id(UUID.randomUUID())
                .title(productRequestDto.getTitle())
                .description(productRequestDto.getDescription())
                .price(productRequestDto.getPrice())
                .status(productRequestDto.getStatus())
                .category(category)
                .build();

        productList.add(product);
        return product;
    }

    @Override
    public Product updateProduct(ProductRequestDto productRequestDto, String id) {
        Product product = getProductById(id);
        Category category = categoryService.findCategoryById(productRequestDto.getCategoryId());

        product.setTitle(productRequestDto.getTitle());
        product.setDescription(productRequestDto.getDescription());
        product.setPrice(productRequestDto.getPrice());
        product.setStatus(productRequestDto.getStatus());
        product.setCategory(category);

        return product;
    }

    @Override
    public void deleteProduct(String id) {
        productList.removeIf(product -> product.getId().toString().equals(id));
    }
}
