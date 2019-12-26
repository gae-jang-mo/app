package com.gaejangmo.apiserver.model.userproduct.domain.exception;

public class AlreadyDeleteException extends IllegalStateException {
    public static final String DEFAULT_MESSAGE = "해당 장비는 이미 삭제되었습니다.";

    public AlreadyDeleteException(final Long id) {
        super(DEFAULT_MESSAGE + id);
    }
}
