package com.gaejangmo.apiserver.config.oauth.dto;

import com.gaejangmo.apiserver.config.oauth.ProviderAttributes;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributesDto {
    private final Map<String, Object> attributes;
    private final String nameAttributeKey;
    private final String username;
    private final String email;
    private final String imageUrl;

    @Builder
    public OAuthAttributesDto(final Map<String, Object> attributes, final String nameAttributeKey, final String username,
                              final String email, final String imageUrl) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.username = username;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    public static OAuthAttributesDto of(final String registrationId, final String userNameAttributeName,
                                        final Map<String, Object> attributes) {
        ProviderAttributes providerAttributes = ProviderAttributes.of(registrationId);

        return OAuthAttributesDto.builder()
                .username(String.valueOf(attributes.get(providerAttributes.getUsername())))
                .email(String.valueOf(attributes.get(providerAttributes.getEmail())))
                .imageUrl(String.valueOf(attributes.get(providerAttributes.getImageUrl())))
                .nameAttributeKey(userNameAttributeName)
                .attributes(attributes)
                .build();
    }
}
