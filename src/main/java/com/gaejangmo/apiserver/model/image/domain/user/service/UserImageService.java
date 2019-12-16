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
    private final UserImageRepository userImageRepository;
    private final S3Connector s3Connector;
    private final UploadFileNameCreator uploadFileNameCreator;

    public UserImageService(final UserImageRepository userImageRepository, final S3Connector s3Connector, final UploadFileNameCreator uploadFileNameCreator) {
        this.userImageRepository = userImageRepository;
        this.s3Connector = s3Connector;
        this.uploadFileNameCreator = uploadFileNameCreator;
    }

    public UserImage save(final MultipartFile multipartFile, final User user) {
        String fileName = uploadFileNameCreator.create(multipartFile.getName());
        String directoryName = String.valueOf(user.getOauthId());
        String url = s3Connector.upload(multipartFile, directoryName, fileName);

        UserImage userImage = createUserImage(multipartFile, url);
        return userImageRepository.save(userImage);
    }

    public void delete(final UserImage userImage) {
        userImageRepository.delete(userImage);
    }

    private UserImage createUserImage(final MultipartFile multipartFile, final String url) {
        return new UserImage(FileFeature.builder()
                .url(url)
                .originalName(multipartFile.getOriginalFilename())
                .imageType(ImageType.of(multipartFile.getContentType()))
                .size(multipartFile.getSize())
                .build());
    }
}
