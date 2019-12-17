package com.gaejangmo.apiserver.model.common.support;

import com.gaejangmo.apiserver.config.oauth.SecurityUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockCustomUserSecurityContextFactory
        implements WithSecurityContextFactory<WithMockCustomUser> {

    @Override
    public SecurityContext createSecurityContext(final WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        SecurityUser principal = SecurityUser.builder()
                .id(Long.valueOf(customUser.id()))
                .oauthId(Long.valueOf(customUser.oauthId()))
                .username(customUser.username())
                .email(customUser.email())
                .build();

        Authentication auth =
                new UsernamePasswordAuthenticationToken(principal, "password", principal.getAuthorities());
        context.setAuthentication(auth);
        return context;
    }
}
