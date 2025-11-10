package com.tb.javaecommerce.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tb.javaecommerce.common.ProductStatus;
import com.tb.javaecommerce.domain.Category;
import com.tb.javaecommerce.domain.Product;
import com.tb.javaecommerce.dto.product.ProductRequestDto;
import com.tb.javaecommerce.service.ProductService;
import com.tb.javaecommerce.service.exception.CategoryNotFoundException;
import com.tb.javaecommerce.service.exception.ProductNotFoundException;
import com.tb.javaecommerce.service.mappers.ProductMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@AutoConfigureMockMvc
@SpringBootTest
public class ProductControllerTestIT {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductMapper productMapper;

    private Product product;

    private ProductRequestDto productRequestDto;
    private static final String PRODUCT_ID = "92cbf62b-abab-451b-9e8f-b092ee27cb62";


    @BeforeEach
    public void init() {
        Category category = Category.builder().id(1).title("Test category 1").description("Test category 1").build();
        product = Product.builder().id(UUID.fromString("92cbf62b-abab-451b-9e8f-b092ee27cb62")).title("Test 1").description("Test 1").price(150.0).status(ProductStatus.IN_STOCK).category(category).build();
        productRequestDto = ProductRequestDto.builder().title("Test 1 star").description("Test 1").status(ProductStatus.IN_STOCK).categoryId(1).price(150.0).build();
    }


    @Test
    public void shouldCreateProductSuccess() throws Exception {
        Mockito.when(productService.createProduct(productRequestDto)).thenReturn(product);

        ResultActions response = mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productRequestDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldCreateProductFail() throws Exception {
        Mockito.when(productService.createProduct(productRequestDto)).thenReturn(product);

        ResultActions response = mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"));

        response.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void shouldGetAllProducts() throws Exception {
        List<Product> response = List.of(product);
        Mockito.when(productService.getAllProducts()).thenReturn(response);


        mockMvc.perform(get("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(productMapper.toProductResponseDtoList(response))));
    }

    @Test
    public void shouldGetProductById() throws Exception {
        Mockito.when(productService.getProductById(PRODUCT_ID)).thenReturn(product);

        mockMvc.perform(get("/api/v1/products/{id}", PRODUCT_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(productMapper.toProductResponseDto(product))));

    }

    @Test
    public void shouldGetProductByIdFail() throws Exception {
        Mockito.when(productService.getProductById(PRODUCT_ID)).thenThrow(ProductNotFoundException.class);

        mockMvc.perform(get("/api/v1/products/{id}", PRODUCT_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void shouldUpdateProductSuccess() throws Exception {
        Mockito.when(productService.updateProduct(productRequestDto, PRODUCT_ID)).thenReturn(product);

        mockMvc.perform(put("/api/v1/products/{id}", PRODUCT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(productMapper.toProductResponseDto(product))));
    }

    @Test
    public void shouldUpdateProductNotFoundCategoryException() throws Exception {
        Mockito.when(productService.updateProduct(productRequestDto, PRODUCT_ID)).thenThrow(CategoryNotFoundException.class);

        mockMvc.perform(put("/api/v1/products/{id}", PRODUCT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void shouldUpdateProductNotFoundProductException() throws Exception {
        Mockito.when(productService.updateProduct(productRequestDto, PRODUCT_ID)).thenThrow(ProductNotFoundException.class);

        mockMvc.perform(put("/api/v1/products/{id}", PRODUCT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

@Test
public void shouldDeleteProductSuccess() throws Exception {
    Mockito.doNothing().when(productService).deleteProduct(PRODUCT_ID);

    mockMvc.perform(delete("/api/v1/products/{id}", PRODUCT_ID)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNoContent());
}

@Test
public void shouldDeleteProductFail() throws Exception {
    Mockito.doThrow(ProductNotFoundException.class).when(productService).deleteProduct(PRODUCT_ID);

    mockMvc.perform(delete("/api/v1/products/{id}", PRODUCT_ID)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNoContent());
}

}
