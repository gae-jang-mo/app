package com.gaejangmo.apiserver.config.aws.exception;

public class S3FileUploadException extends RuntimeException {
    public S3FileUploadException(final String message) {
        super(message);
    }
}
