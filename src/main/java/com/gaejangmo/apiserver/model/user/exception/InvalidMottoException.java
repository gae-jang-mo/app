package com.gaejangmo.apiserver.model.user.exception;

public class InvalidMottoException extends RuntimeException {
    public InvalidMottoException(final String message) {
        super(message);
    }
}
