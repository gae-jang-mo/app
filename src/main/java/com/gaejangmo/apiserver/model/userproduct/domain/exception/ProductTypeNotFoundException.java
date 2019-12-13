package com.gaejangmo.apiserver.model.userproduct.domain.exception;

public class ProductTypeNotFoundException extends RuntimeException {
    public ProductTypeNotFoundException(final String message) {
        super(message);
    }
}
