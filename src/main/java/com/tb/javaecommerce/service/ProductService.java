package com.tb.javaecommerce.service;

import com.tb.javaecommerce.domain.Product;
import com.tb.javaecommerce.dto.product.ProductRequestDto;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(String productId);
    Product createProduct(ProductRequestDto productRequestDto);
    Product updateProduct(ProductRequestDto productRequestDto, String id);
    void deleteProduct(String id);
}
