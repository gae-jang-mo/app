package com.gaejangmo.apiserver.config.oauth.service;

import com.gaejangmo.apiserver.config.oauth.dto.OAuthAttributesDto;
import com.gaejangmo.apiserver.model.common.domain.vo.Link;
import com.gaejangmo.apiserver.model.user.domain.User;
import com.gaejangmo.apiserver.model.user.domain.UserRepository;
import com.gaejangmo.apiserver.model.user.domain.vo.Email;
import com.gaejangmo.apiserver.model.user.domain.vo.Grade;
import com.gaejangmo.apiserver.model.user.domain.vo.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Component
public class OAuth2Service {
    private final UserRepository userRepository;

    public OAuth2Service(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public DefaultOAuth2User getUser(final String registrationId, final String userNameAttributeName, final Map<String, Object> attributes) {
        OAuthAttributesDto attributesDto = OAuthAttributesDto.of(registrationId, userNameAttributeName, attributes);

        User user = saveOrUpdate(attributesDto);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleType())),
                attributesDto.getAttributes(),
                attributesDto.getNameAttributeKey()
        );
    }

    private User saveOrUpdate(final OAuthAttributesDto attributes) {
        User user = userRepository.findByEmail(Email.of(attributes.getEmail()))
                .map(userEntity -> userEntity.update(attributes.getUsername(), attributes.getImageUrl()))
                .orElseGet(() -> toEntity(attributes));

        return userRepository.save(user);
    }

    private User toEntity(final OAuthAttributesDto attributes) {
        return User.builder()
                .username(attributes.getUsername())
                .email(Email.of(attributes.getEmail()))
                .imageUrl(Link.of(attributes.getImageUrl()))
                .role(Role.USER)
                .grade(Grade.GENERAL)
                .build();
    }
}
