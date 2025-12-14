package com.tb.javaecommerce.service.exception;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(Long id) {
        super("Category with id " + id + " not found");
    }
}
