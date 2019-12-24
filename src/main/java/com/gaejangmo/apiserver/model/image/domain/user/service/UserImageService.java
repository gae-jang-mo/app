package com.gaejangmo.apiserver.model.image.domain.user.service;

import com.gaejangmo.apiserver.config.aws.S3Connector;
import com.gaejangmo.apiserver.model.image.domain.vo.FileFeature;
import com.gaejangmo.apiserver.model.image.domain.vo.ImageType;
import com.gaejangmo.apiserver.model.image.user.domain.UserImage;
import com.gaejangmo.apiserver.model.image.user.domain.UserImageRepository;
import com.gaejangmo.apiserver.model.image.utils.UploadFileNameCreator;
import com.gaejangmo.apiserver.model.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class UserImageService {
    private static final String DIRECTORY_DELIMITER = "/";

    private final UserImageRepository userImageRepository;
    private final S3Connector s3Connector;
    private final UploadFileNameCreator uploadFileNameCreator;

    public UserImageService(final UserImageRepository userImageRepository, final S3Connector s3Connector, final UploadFileNameCreator uploadFileNameCreator) {
        this.userImageRepository = userImageRepository;
        this.s3Connector = s3Connector;
        this.uploadFileNameCreator = uploadFileNameCreator;
    }

    public UserImage save(final MultipartFile multipartFile, final User user) {
        String fileName = uploadFileNameCreator.create(multipartFile.getOriginalFilename());
        String directoryName = String.valueOf(user.getOauthId());
        String savedName = String.join(DIRECTORY_DELIMITER, directoryName, fileName);
        String url = s3Connector.upload(multipartFile, savedName);

        UserImage userImage = createUserImage(multipartFile, url, savedName);
        return userImageRepository.save(userImage);
    }

    public void delete(final UserImage userImage) {
        s3Connector.delete(userImage.getFileFeature().getSavedName());
        userImageRepository.delete(userImage);
    }

    private UserImage createUserImage(final MultipartFile multipartFile, final String url, final String savedName) {
        return new UserImage(FileFeature.builder()
                .url(url)
                .originalName(multipartFile.getOriginalFilename())
                .savedName(savedName)
                .imageType(ImageType.of(multipartFile.getContentType()))
                .size(multipartFile.getSize())
                .build());
    }
}
