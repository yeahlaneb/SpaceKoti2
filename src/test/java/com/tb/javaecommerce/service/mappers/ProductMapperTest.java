package com.tb.javaecommerce.service.mappers;

import static org.junit.jupiter.api.Assertions.*;
import com.tb.javaecommerce.common.ProductStatus;
import com.tb.javaecommerce.domain.Category;
import com.tb.javaecommerce.domain.Product;
import com.tb.javaecommerce.dto.product.ProductResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

@SpringBootTest
public class ProductMapperTest {
    private static final String PRODUCT_ID = "92cbf62b-abab-451b-9e8f-b092ee27cb62";

    private final ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    private Product product;
    private Category category;
    List<Product> products;

    @BeforeEach
    void init() {
        category = Category.builder()
                .id(1L)
                .title("Test category")
                .description("Description for Test category")
                .build();

        product = Product.builder()
                .id(UUID.fromString(PRODUCT_ID))
                .title("Test star")
                .description("Description for test star")
                .price(155.0)
                .status(ProductStatus.IN_STOCK)
                .category(category)
                .build();
        products = List.of(product);
    }

    @Test
    void shouldMapProductToProductResponseDto() {
        ProductResponseDto productResponseDto = productMapper.toProductResponseDto(product);

        assertNotNull(productResponseDto);
        assertEquals(product.getId().toString(), productResponseDto.getId());
        assertEquals(product.getTitle(), productResponseDto.getTitle());
        assertEquals(product.getDescription(), productResponseDto.getDescription());
        assertEquals(product.getPrice(), productResponseDto.getPrice());
        assertEquals(product.getCategory().getTitle(), productResponseDto.getCategory().getTitle());
        assertEquals(product.getCategory().getDescription(), productResponseDto.getCategory().getDescription());
        assertEquals(product.getStatus().getDisplayName(), productResponseDto.getStatus());
    }

    @Test
    void shouldMapProductListToProductResponseDtoList() {
        List<ProductResponseDto> productResponseDtoList = productMapper.toProductResponseDtoList(products);

        assertNotNull(productResponseDtoList);
        assertEquals(productResponseDtoList.size(), products.size());
    }
}
