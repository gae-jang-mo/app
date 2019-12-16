package com.gaejangmo.apiserver.config.oauth;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Getter
public class SecurityUser implements OAuth2User {
    private final Long oauthId;
    private final String username;
    private final String email;
    private final Set<GrantedAuthority> authorities;
    private final Map<String, Object> attributes;
    private final String nameAttributeKey;


    @Builder
    public SecurityUser(final Long oauthId, final String username, final String email,
                        final Set<GrantedAuthority> authorities, final Map<String, Object> attributes, final String nameAttributeKey) {
        this.oauthId = oauthId;
        this.username = username;
        this.email = email;
        this.authorities = authorities;
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
    }

    @Override
    public String getName() {
        return this.getAttribute(this.nameAttributeKey).toString();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }
}
