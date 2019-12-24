package com.gaejangmo.apiserver.model.user.service;

import com.gaejangmo.apiserver.model.image.domain.user.service.UserImageService;
import com.gaejangmo.apiserver.model.image.dto.FileResponseDto;
import com.gaejangmo.apiserver.model.image.user.domain.UserImage;
import com.gaejangmo.apiserver.model.user.domain.User;
import com.gaejangmo.apiserver.model.user.domain.UserRepository;
import com.gaejangmo.apiserver.model.user.domain.vo.Motto;
import com.gaejangmo.apiserver.model.user.dto.UserResponseDto;
import com.gaejangmo.apiserver.model.user.dto.UserSearchDto;
import com.gaejangmo.utils.RandomUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    private static final int RANDOM_USER_COUNT = 3;
    private static final int USER_START_IDX = 1;
    private static final String USER_NOT_FOUND_MESSAGE = "해당하는 유저가 없습니다.";

    private final UserRepository userRepository;
    private final UserImageService userImageService;

    public UserService(final UserRepository userRepository, final UserImageService userImageService) {
        this.userRepository = userRepository;
        this.userImageService = userImageService;
    }

    @Transactional(readOnly = true)
    public User findById(final Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));
    }

    public UserResponseDto findUserResponseDtoById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));
        return toDto(user);
    }

    public UserResponseDto findUserResponseDtoByName(final String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));
        return toDto(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDto findUserResponseDtoByOauthId(final Long oauthId) {
        User user = userRepository.findByOauthId(oauthId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));
        return toDto(user);
    }

    public List<UserSearchDto> findUserSearchDtosByUserName(final String username) {
        List<User> users = userRepository.findAllByUsernameContainingIgnoreCase(username);
        return users.stream()
                .map(this::toUserSearchDto)
                .collect(Collectors.toList());
    }

    public UserResponseDto updateMotto(final Long id, final Motto motto) {
        return updateTemplate(id, (user) -> toDto(user.updateMotto(motto)));
    }

    private <T> T updateTemplate(final Long id, final Function<User, T> function) {
        User user = findById(id);
        return function.apply(user);
    }

    public FileResponseDto updateUserImage(final MultipartFile multipartFile, final Long id) {
        User user = findById(id);
        UserImage savedUserImage = userImageService.save(multipartFile, user);

        Optional<UserImage> oldUserImage = user.getUserImage();
        user.updateUserImage(savedUserImage);
        oldUserImage.ifPresent(userImageService::delete);

        return FileResponseDto.builder()
                .createdAt(savedUserImage.getCreatedAt())
                .id(savedUserImage.getId())
                .fileFeature(savedUserImage.getFileFeature())
                .build();
    }

    private UserSearchDto toUserSearchDto(final User user) {
        return UserSearchDto.builder()
                .id(user.getId())
                .imageUrl(user.getImageUrl())
                .username(user.getUsername())
                .build();
    }

    public List<UserSearchDto> findRandomUserResponse() {
        return RandomUtils.getRandomLongsInRange(USER_START_IDX, userRepository.getMaxId(), RANDOM_USER_COUNT)
                .stream()
                .map(this::findById)
                .map(this::toUserSearchDto)
                .collect(Collectors.toList());
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
