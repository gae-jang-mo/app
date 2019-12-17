package com.gaejangmo.apiserver.model.image.domain.user.service;

import com.gaejangmo.apiserver.config.aws.S3Connector;
import com.gaejangmo.apiserver.model.image.domain.vo.FileFeature;
import com.gaejangmo.apiserver.model.image.domain.vo.ImageType;
import com.gaejangmo.apiserver.model.image.user.domain.UserImage;
import com.gaejangmo.apiserver.model.image.user.domain.UserImageRepository;
import com.gaejangmo.apiserver.model.image.utils.UploadFileNameCreator;
import com.gaejangmo.apiserver.model.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserImageServiceTest {

    @InjectMocks
    private UserImageService userImageService;
    @Mock
    private UserImageRepository userImageRepository;
    @Mock
    private S3Connector s3Connector;
    @Mock
    private UploadFileNameCreator uploadFileNameCreator;

    private long oauthId = 1234L;
    private String originalFilename = "fileName.jpg";
    private String savedName = String.join("/", String.valueOf(oauthId), originalFilename);
    private String url = "expected/url";

    private MockMultipartFile mockMultipartFile;
    private User user;
    private FileFeature fileFeature;

    @BeforeEach
    void setUp() {
        mockMultipartFile = new MockMultipartFile("user-file", originalFilename, "image/jpeg", new byte[10]);

        user = User.builder().oauthId(oauthId).build();

        fileFeature = FileFeature.builder()
                .originalName(originalFilename)
                .savedName(savedName)
                .size(10)
                .imageType(ImageType.of("jpg"))
                .url(url)
                .build();
    }

    @Test
    void save() {
        // given
        UserImage expected = new UserImage(fileFeature);

        given(uploadFileNameCreator.create(anyString())).willReturn(originalFilename);
        given(s3Connector.upload(mockMultipartFile, savedName)).willReturn(url);
        given(userImageRepository.save(expected)).willReturn(expected);

        // when
        UserImage actual = userImageService.save(mockMultipartFile, user);

        // then
        assertSame(actual, expected);

        verify(s3Connector).upload(mockMultipartFile, savedName);
        verify(uploadFileNameCreator).create(anyString());
        verify(userImageRepository).save(expected);
    }
}