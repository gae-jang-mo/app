package com.gaejangmo.apiserver.model.user.domain.exception;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(final String message) {
        super(message);
    }
}
