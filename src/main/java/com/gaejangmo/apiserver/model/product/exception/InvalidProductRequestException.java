package com.gaejangmo.apiserver.model.product.exception;

public class InvalidProductRequestException extends RuntimeException {
    public InvalidProductRequestException(final String message) {
        super(message);
    }
}
