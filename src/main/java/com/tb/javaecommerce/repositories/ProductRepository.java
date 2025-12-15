package com.tb.javaecommerce.repository;

import com.tb.javaecommerce.entity.ProductEntity;
import com.tb.javaecommerce.repository.projection.ProductSalesView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query("""
        SELECT p.id AS productId,
               p.title AS productTitle,
               SUM(c.quantity) AS totalSold
        FROM CartEntity c
        JOIN c.product p
        GROUP BY p.id, p.title
        ORDER BY SUM(c.quantity) DESC
    """)
    List<ProductSalesView> findTopSellingProducts();
}
