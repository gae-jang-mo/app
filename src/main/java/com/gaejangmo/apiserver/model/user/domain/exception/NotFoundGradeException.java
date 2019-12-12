package com.gaejangmo.apiserver.model.user.domain.exception;

public class NotFoundGradeException extends RuntimeException {
    public NotFoundGradeException(final String message) {
        super(message);
    }
}
