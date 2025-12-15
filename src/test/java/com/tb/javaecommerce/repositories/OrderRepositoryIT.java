package com.tb.javaecommerce.repository;

import com.tb.javaecommerce.entity.OrderEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Testcontainers
@DataJpaTest
@ActiveProfiles("test")
class OrderRepositoryIT {

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:15-alpine")
                    .withDatabaseName("testdb")
                    .withUsername("test")
                    .withPassword("test");

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void shouldSaveAndFindOrderByOrderNumber() {
        OrderEntity order = new OrderEntity();
        order.setConsumerName("Cosmo Cat");
        order.setAddress("Mars Base 1");
        order.setEmail("cat@space.com");
        order.setOrderStatus("CREATED");
        order.setOrderNumber("ORD-TEST-123");
        order.setTotalPrice(new BigDecimal("199.99"));

        orderRepository.save(order);

        Optional<OrderEntity> found =
                orderRepository.findByOrderNumber("ORD-TEST-123");

        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo("cat@space.com");
    }

    @Test
    void shouldSaveAndFindOrderById() {
        OrderEntity order = new OrderEntity();
        order.setConsumerName("ID Cat");
        order.setAddress("Moon");
        order.setEmail("id@space.com");
        order.setOrderStatus("CREATED");
        order.setOrderNumber("ORD-ID-1");
        order.setTotalPrice(new BigDecimal("50.00"));

        OrderEntity saved = orderRepository.save(order);

        Optional<OrderEntity> found = orderRepository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getOrderNumber()).isEqualTo("ORD-ID-1");
    }

    @Test
    void shouldFindOrderByEmail() {
        OrderEntity order = new OrderEntity();
        order.setConsumerName("Natural Cat");
        order.setAddress("Venus");
        order.setEmail("natural@space.com");
        order.setOrderStatus("CREATED");
        order.setOrderNumber("ORD-NAT-1");
        order.setTotalPrice(new BigDecimal("75.00"));

        orderRepository.save(order);

        Optional<OrderEntity> found =
                orderRepository.findByEmail("natural@space.com");

        assertThat(found).isPresent();
        assertThat(found.get().getConsumerName()).isEqualTo("Natural Cat");
    }

    @Test
    void shouldUpdateOrderStatus() {
        OrderEntity order = new OrderEntity();
        order.setConsumerName("Update Cat");
        order.setAddress("Jupiter");
        order.setEmail("update@space.com");
        order.setOrderStatus("CREATED");
        order.setOrderNumber("ORD-UPD-1");
        order.setTotalPrice(new BigDecimal("120.00"));

        OrderEntity saved = orderRepository.save(order);

        saved.setOrderStatus("PAID");
        orderRepository.save(saved);

        OrderEntity updated =
                orderRepository.findById(saved.getId()).orElseThrow();

        assertThat(updated.getOrderStatus()).isEqualTo("PAID");
    }

    @Test
    void shouldDeleteOrder() {
        OrderEntity order = new OrderEntity();
        order.setConsumerName("Delete Cat");
        order.setAddress("Saturn");
        order.setEmail("delete@space.com");
        order.setOrderStatus("CREATED");
        order.setOrderNumber("ORD-DEL-1");
        order.setTotalPrice(new BigDecimal("30.00"));

        OrderEntity saved = orderRepository.save(order);

        orderRepository.deleteById(saved.getId());

        Optional<OrderEntity> found =
                orderRepository.findById(saved.getId());

        assertThat(found).isEmpty();
    }

    @Test
    void shouldFailWhenDuplicateEmail() {
        OrderEntity first = new OrderEntity();
        first.setConsumerName("Cat One");
        first.setAddress("Mars");
        first.setEmail("duplicate@space.com");
        first.setOrderStatus("CREATED");
        first.setOrderNumber("ORD-DUP-1");
        first.setTotalPrice(new BigDecimal("10.00"));

        OrderEntity second = new OrderEntity();
        second.setConsumerName("Cat Two");
        second.setAddress("Venus");
        second.setEmail("duplicate@space.com");
        second.setOrderStatus("CREATED");
        second.setOrderNumber("ORD-DUP-2");
        second.setTotalPrice(new BigDecimal("20.00"));

        orderRepository.save(first);

        assertThatThrownBy(() -> orderRepository.saveAndFlush(second))
                .isInstanceOf(Exception.class);
    }

    @Test
    void shouldFailWhenDuplicateOrderNumber() {
        OrderEntity first = new OrderEntity();
        first.setConsumerName("Cat One");
        first.setAddress("Pluto");
        first.setEmail("one@space.com");
        first.setOrderStatus("CREATED");
        first.setOrderNumber("ORD-UNIQ-1");
        first.setTotalPrice(new BigDecimal("15.00"));

        OrderEntity second = new OrderEntity();
        second.setConsumerName("Cat Two");
        second.setAddress("Neptune");
        second.setEmail("two@space.com");
        second.setOrderStatus("CREATED");
        second.setOrderNumber("ORD-UNIQ-1");
        second.setTotalPrice(new BigDecimal("25.00"));

        orderRepository.save(first);

        assertThatThrownBy(() -> orderRepository.saveAndFlush(second))
                .isInstanceOf(Exception.class);
    }
}
