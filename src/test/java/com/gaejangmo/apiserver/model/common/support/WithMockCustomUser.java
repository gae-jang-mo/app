package com.gaejangmo.apiserver.model.common.support;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {
    String id() default "1";

    String oauthId() default "12345";

    String username() default "rob";

    String email() default "example@gmail.com";
}
