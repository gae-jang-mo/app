package com.gaejangmo.apiserver.model.common.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApiErrorResponse {
    private String message;
    private int status;
    private String sourceUri;

    @Builder
    public ApiErrorResponse(final String message, final int status, final String sourceUri) {
        this.message = message;
        this.status = status;
        this.sourceUri = sourceUri;
    }
}
