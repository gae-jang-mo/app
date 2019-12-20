package com.gaejangmo.apiserver.model.notice.exception;

public class NoticeTypeNotFoundException extends RuntimeException {
    public NoticeTypeNotFoundException(final String message) {
        super(message);
    }
}
