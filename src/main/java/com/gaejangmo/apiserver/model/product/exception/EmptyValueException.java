package com.gaejangmo.apiserver.model.product.exception;

public class EmptyValueException extends RuntimeException {
    public EmptyValueException(final String message) {
        super(message);
    }
}
