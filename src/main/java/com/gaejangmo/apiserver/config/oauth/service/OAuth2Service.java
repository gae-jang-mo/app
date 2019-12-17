package com.gaejangmo.apiserver.config.oauth.service;

import com.gaejangmo.apiserver.config.oauth.SecurityUser;
import com.gaejangmo.apiserver.config.oauth.dto.OAuthAttributesDto;
import com.gaejangmo.apiserver.model.common.domain.vo.Link;
import com.gaejangmo.apiserver.model.user.domain.User;
import com.gaejangmo.apiserver.model.user.domain.UserRepository;
import com.gaejangmo.apiserver.model.user.domain.vo.Email;
import com.gaejangmo.apiserver.model.user.domain.vo.Grade;
import com.gaejangmo.apiserver.model.user.domain.vo.Motto;
import com.gaejangmo.apiserver.model.user.domain.vo.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Component
public class OAuth2Service {
    private final UserRepository userRepository;

    public OAuth2Service(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    OAuth2User getUser(final String registrationId, final String userNameAttributeName,
                       final Map<String, Object> attributes) {
        OAuthAttributesDto attributesDto = OAuthAttributesDto.of(registrationId, userNameAttributeName, attributes);

        User user = saveOrUpdate(attributesDto);

        return SecurityUser.builder()
                .id(user.getId())
                .oauthId(user.getOauthId())
                .username(user.getUsername())
                .email(user.getEmail())
                .authorities(Collections.singleton(new SimpleGrantedAuthority(user.getRoleType())))
                .attributes(attributes)
                .build();
    }

    // TODO: 2019-12-16 사용자 프로필 사진이 직접 설정한 경우 예외 상황 관리 요구
    private User saveOrUpdate(final OAuthAttributesDto attributes) {
        User user = userRepository.findByOauthId(attributes.getOauthId())
                .map(userEntity -> userEntity.update(attributes.getUsername(), attributes.getImageUrl()))
                .orElseGet(() -> toEntity(attributes));

        return userRepository.save(user);
    }

    private User toEntity(final OAuthAttributesDto attributes) {
        return User.builder()
                .oauthId(attributes.getOauthId())
                .username(attributes.getUsername())
                .imageUrl(Link.of(attributes.getImageUrl()))
                .role(Role.USER)
                .grade(Grade.GENERAL)
                .email(Email.of(attributes.getEmail()))
                .motto(Motto.of(""))
                .build();
    }
}