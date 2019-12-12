package com.gaejangmo.apiserver.model.user.domain.vo;

import com.gaejangmo.apiserver.model.user.domain.exception.RoleNotFoundException;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum Role {
    GUEST("ROLE_GUEST", "손님", 1),
    USER("ROLE_USER", "사용자", 2);

    private final String type;
    private final String title;
    private final int code;

    Role(final String type, final String title, final int code) {
        this.type = type;
        this.title = title;
        this.code = code;
    }

    public static Role ofCode(final int code) {
        return Stream.of(values())
                .filter(role -> role.getCode() == code)
                .findAny()
                .orElseThrow(() -> new RoleNotFoundException("없는 사용자 역할입니다."));
    }
}
