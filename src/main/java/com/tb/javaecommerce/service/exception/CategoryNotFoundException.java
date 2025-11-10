package com.tb.javaecommerce.service.exception;

public class CategoryNotFoundException extends RuntimeException {
  private static final String CATEGORY_NOT_FOUND_MESSAGE = "Category With ID - %s Not Found";

  public CategoryNotFoundException(int id) {
    super(String.format(CATEGORY_NOT_FOUND_MESSAGE, id));
  }
}
