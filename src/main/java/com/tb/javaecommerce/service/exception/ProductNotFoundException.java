package com.tb.javaecommerce.service.exception;

public class ProductNotFoundException extends RuntimeException {
    private static final String PRODUCT_NOT_FOUND_MESSAGE = "Product With ID - %s Not Found";

    public ProductNotFoundException(String id) {
        super(String.format(PRODUCT_NOT_FOUND_MESSAGE, id));
    }
}
