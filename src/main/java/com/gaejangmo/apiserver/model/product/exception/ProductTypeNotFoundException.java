package com.gaejangmo.apiserver.model.product.exception;

public class ProductTypeNotFoundException extends RuntimeException {
    public ProductTypeNotFoundException(final String message) {
        super(message);
    }
}
