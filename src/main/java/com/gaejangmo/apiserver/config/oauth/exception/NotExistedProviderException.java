package com.gaejangmo.apiserver.config.oauth.exception;

public class NotExistedProviderException extends RuntimeException {
    public NotExistedProviderException(final String message) {
        super(message);
    }
}
