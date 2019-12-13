package com.gaejangmo.apiserver.config.oauth;

import com.gaejangmo.apiserver.config.oauth.exception.NotExistedProviderException;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum ProviderAttributes {
    GITHUB("github", "id", "login", "email", "avatar_url");

    private final String registrationId;
    private final String oauthId;
    private final String username;
    private final String email;
    private final String imageUrl;

    ProviderAttributes(final String registrationId, final String oauthId, final String username, final String email, final String imageUrl) {
        this.registrationId = registrationId;
        this.oauthId = oauthId;
        this.username = username;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    public static ProviderAttributes of(final String registrationId) {
        return Stream.of(values())
                .filter(providerAttributes -> providerAttributes.registrationId.equals(registrationId.toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new NotExistedProviderException("존재하지않는 OAuth 제공자입니다."));
    }

}
