package com.gaejangmo.apiserver.model.common.support;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {
    String id() default "1";

    String oauthId() default "20608121";

    String username() default "JunHoPark93";

    String email() default "abc@gmail.com";

    String role() default "ROLE_USER";
}
