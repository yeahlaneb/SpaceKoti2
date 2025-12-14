package com.tb.javaecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.NaturalId;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_orders")
    @SequenceGenerator(name = "seq_orders", sequenceName = "seq_orders", allocationSize = 1)
    private Long id;

    private String consumerName;
    private String address;

    @NaturalId
    @Column(nullable = false, unique = true)
    private String orderNumber;

    @Column(nullable = false)
    private String email;

    private String orderStatus;

    @Column(precision = 19, scale = 2)
    private BigDecimal totalPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartEntity> cartItems;
}

