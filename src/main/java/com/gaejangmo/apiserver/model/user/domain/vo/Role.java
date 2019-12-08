package com.gaejangmo.apiserver.model.user.domain.vo;

import lombok.Getter;

@Getter
public enum Role {
    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "사용자");

    private final String type;
    private final String title;

    Role(final String type, final String title) {
        this.type = type;
        this.title = title;
    }
}
