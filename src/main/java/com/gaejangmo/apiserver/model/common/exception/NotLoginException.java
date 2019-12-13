package com.gaejangmo.apiserver.model.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotLoginException extends RuntimeException {
    private static final Logger log = LoggerFactory.getLogger(NotLoginException.class);

    public static final String NOT_LOGIN_MESSAGE = "로그인되어있지 않습니다.";

    public NotLoginException() {
        super(NOT_LOGIN_MESSAGE);
        log.error(NOT_LOGIN_MESSAGE);
    }
}