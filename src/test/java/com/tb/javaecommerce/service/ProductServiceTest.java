package com.tb.javaecommerce.service;

import com.tb.javaecommerce.common.ProductStatus;
import com.tb.javaecommerce.domain.Category;
import com.tb.javaecommerce.domain.Product;
import com.tb.javaecommerce.dto.product.ProductRequestDto;
import com.tb.javaecommerce.service.exception.CategoryNotFoundException;
import com.tb.javaecommerce.service.exception.ProductNotFoundException;
import com.tb.javaecommerce.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Product Service Test")
@SpringBootTest(classes = {ProductServiceImpl.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductServiceTest {

    private static final int CATEGORY_ID = 1;
    private static final String CATEGORY_TITLE = "TEST";
    private static final String CATEGORY_DESCRIPTION = "TEST";
    private static final String PRODUCT_ID = "92cbf62b-abab-451b-9e8f-b092ee27cb62";

    @MockBean
    private CategoryService mockCategoryService;

    @Autowired
    @Spy
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Order(1)
    void shouldGetProductList() {
        List<Product> productList = productService.getAllProducts();
        assertNotNull(productList);
    }

    @Test
    @Order(2)
    void shouldGetProductById() {
        Category category = categoryBuilder();
        Product mockProduct = productBuilder(category);

        when(productService.getProductById(PRODUCT_ID)).thenReturn(mockProduct);

        Product result = productService.getProductById(PRODUCT_ID);
        assertEquals(mockProduct, result);
    }

    @Test
    @Order(3)
    void shouldThrowNotFoundWhenProductDoesNotExist() {
        when(productService.getProductById(PRODUCT_ID))
                .thenThrow(new ProductNotFoundException(PRODUCT_ID));

        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(PRODUCT_ID));
    }

    @Test
    @Order(4)
    void shouldCreateProductSuccessfully() {
        Category category = categoryBuilder();
        ProductRequestDto request = productRequestDtoBuilder();

        when(mockCategoryService.findCategoryById(CATEGORY_ID)).thenReturn(category);
        Product created = productService.createProduct(request);

        assertNotNull(created);
        assertEquals("Test", created.getTitle());
        assertEquals(ProductStatus.IN_STOCK, created.getStatus());
    }

    @Test
    @Order(5)
    void shouldThrowCategoryNotFoundOnCreate() {
        ProductRequestDto request = productRequestDtoBuilder();
        when(productService.createProduct(request))
                .thenThrow(new CategoryNotFoundException(CATEGORY_ID));

        assertThrows(CategoryNotFoundException.class, () -> productService.createProduct(request));
    }

    @Test
    @Order(6)
    void shouldUpdateProductSuccessfully() {
        Category category = categoryBuilder();
        ProductRequestDto request = productRequestDtoBuilder();

        when(mockCategoryService.findCategoryById(CATEGORY_ID)).thenReturn(category);
        Product updated = productService.updateProduct(request, PRODUCT_ID);

        assertNotNull(updated);
        assertEquals("Test", updated.getTitle());
    }

    @Test
    @Order(7)
    void shouldThrowProductNotFoundOnUpdate() {
        ProductRequestDto request = productRequestDtoBuilder();

        when(productService.updateProduct(request, PRODUCT_ID))
                .thenThrow(new ProductNotFoundException(PRODUCT_ID));

        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(request, PRODUCT_ID));
    }

    @Test
    @Order(8)
    void shouldThrowCategoryNotFoundOnUpdate() {
        ProductRequestDto request = productRequestDtoBuilder();

        when(productService.updateProduct(request, PRODUCT_ID))
                .thenThrow(new CategoryNotFoundException(CATEGORY_ID));

        assertThrows(CategoryNotFoundException.class, () -> productService.updateProduct(request, PRODUCT_ID));
    }

    @Test
    @Order(9)
    void shouldDeleteProductSuccessfully() {
        // deleteProduct теперь void, поэтому просто проверяем вызов
        doNothing().when(productService).deleteProduct(PRODUCT_ID);

        productService.deleteProduct(PRODUCT_ID);

        verify(productService, times(1)).deleteProduct(PRODUCT_ID);
    }

    @Test
    @Order(10)
    void shouldThrowExceptionWhenDeletingNonExistingProduct() {
        doThrow(new ProductNotFoundException("92cbf62b-abab-451b-9e8f-b092ee27cb63"))
                .when(productService).deleteProduct("92cbf62b-abab-451b-9e8f-b092ee27cb63");

        assertThrows(ProductNotFoundException.class,
                () -> productService.deleteProduct("92cbf62b-abab-451b-9e8f-b092ee27cb63"));
    }

    private static Category categoryBuilder() {
        return Category.builder()
                .id(CATEGORY_ID)
                .title(CATEGORY_TITLE)
                .description(CATEGORY_DESCRIPTION)
                .build();
    }

    private static Product productBuilder(Category category) {
        return Product.builder()
                .id(UUID.fromString(PRODUCT_ID))
                .title("Test")
                .category(category)
                .status(ProductStatus.IN_STOCK)
                .description("Test")
                .price(150.0)
                .build();
    }

    private static ProductRequestDto productRequestDtoBuilder() {
        return ProductRequestDto.builder()
                .title("Test")
                .categoryId(CATEGORY_ID)
                .price(150.0)
                .description("Test")
                .status(ProductStatus.IN_STOCK)
                .build();
    }
}
