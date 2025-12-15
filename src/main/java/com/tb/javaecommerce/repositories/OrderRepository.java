package com.tb.javaecommerce.repository;

import com.tb.javaecommerce.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    Optional<OrderEntity> findByOrderNumber(String orderNumber);

    Optional<OrderEntity> findByEmail(String email);
}
