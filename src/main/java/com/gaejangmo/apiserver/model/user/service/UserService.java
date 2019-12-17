package com.gaejangmo.apiserver.model.user.service;

import com.gaejangmo.apiserver.model.image.domain.user.service.UserImageService;
import com.gaejangmo.apiserver.model.image.dto.FileResponseDto;
import com.gaejangmo.apiserver.model.image.user.domain.UserImage;
import com.gaejangmo.apiserver.model.user.domain.User;
import com.gaejangmo.apiserver.model.user.domain.UserRepository;
import com.gaejangmo.apiserver.model.user.dto.UserResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserImageService userImageService;

    public UserService(final UserRepository userRepository, final UserImageService userImageService) {
        this.userRepository = userRepository;
        this.userImageService = userImageService;
    }

    public User findById(final Long id) {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public UserResponseDto findUserResponseDtoByOauthId(final Long oauthId) {
        User user = userRepository.findByOauthId(oauthId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 유저가 없습니다."));
        return toDto(user);
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
