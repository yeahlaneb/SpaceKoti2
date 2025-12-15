package com.tb.javaecommerce.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ProductStatus {
    IN_STOCK("In stock"),
    OUT_OF_STOCK("Out of stock"),
    DISCONTINUED("Discontinued");

    private final String displayName;
}
