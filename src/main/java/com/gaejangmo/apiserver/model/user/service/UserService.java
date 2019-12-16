package com.gaejangmo.apiserver.model.user.service;

import com.gaejangmo.apiserver.model.user.domain.User;
import com.gaejangmo.apiserver.model.user.domain.UserRepository;
import com.gaejangmo.apiserver.model.user.domain.vo.Motto;
import com.gaejangmo.apiserver.model.user.dto.UserResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
public class UserService {
    private static final String USER_NOT_FOUND_MESSAGE = "해당하는 유저가 없습니다.";
    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(final Long id) {
        return userRepository.findById(id).get();
    }

    public UserResponseDto findUserResponseDtoByOauthId(final Long oauthId) {
        User user = userRepository.findByOauthId(oauthId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));
        return toDto(user);
    }

    public Motto updateMotto(final Motto motto, final Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));
        user.updateMotto(motto);
        return motto;
    }

    private UserResponseDto toDto(final User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .oauthId(user.getOauthId())
                .username(user.getUsername())
                .email(user.getEmail())
                .imageUrl(user.getImageUrl())
                .introduce(user.getIntroduce())
                .motto(user.getMotto())
                .build();
    }
}
