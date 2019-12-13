package com.gaejangmo.apiserver.model.common.resolver;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Getter
@ToString
@NoArgsConstructor
public class SessionUser implements Serializable {
    public static final String USER_SESSION_KEY = "loginUser";

    private Long id;
    private String email;
    private String userName;

    @Builder
    public SessionUser(final Long id, final String email, final String userName) {
        this.id = id;
        this.email = email;
        this.userName = userName;
    }
}
