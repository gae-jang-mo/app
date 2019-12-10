package com.gaejangmo.apiserver.model.userproduct.service.exception;

public class NotUserProductOwnerException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "본인이 아닙니다.";

    public NotUserProductOwnerException() {
        super(DEFAULT_MESSAGE);
    }
}
