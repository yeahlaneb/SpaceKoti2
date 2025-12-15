package com.tb.javaecommerce.entity;

import com.tb.javaecommerce.common.ProductStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(
        name = "products",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_product_title_category",
                        columnNames = {"title", "category_id"}
                )
        }
)
@Data
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_products")
    @SequenceGenerator(
            name = "seq_products",
            sequenceName = "seq_products",
            allocationSize = 1
    )
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;
}
