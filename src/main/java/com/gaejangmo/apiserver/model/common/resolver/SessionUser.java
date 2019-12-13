package com.gaejangmo.apiserver.model.common.resolver;

import com.gaejangmo.apiserver.model.user.domain.vo.Role;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
public class SessionUser {
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
