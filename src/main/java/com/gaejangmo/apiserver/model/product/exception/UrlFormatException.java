package com.gaejangmo.apiserver.model.product.exception;

public class UrlFormatException extends RuntimeException {
    public UrlFormatException(final String message) {
        super(message);
    }
}
