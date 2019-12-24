package com.gaejangmo.apiserver.model.like.exception;

public class ImpossibleLikeSameUserException extends RuntimeException {
    public ImpossibleLikeSameUserException(final String message) {
        super(message);
    }
}
