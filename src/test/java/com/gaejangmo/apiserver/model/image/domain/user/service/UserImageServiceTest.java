package com.gaejangmo.apiserver.model.image.domain.user.service;

import com.gaejangmo.apiserver.config.aws.S3Connector;
import com.gaejangmo.apiserver.model.image.domain.vo.FileFeature;
import com.gaejangmo.apiserver.model.image.domain.vo.ImageType;
import com.gaejangmo.apiserver.model.image.user.domain.UserImage;
import com.gaejangmo.apiserver.model.image.user.domain.UserImageRepository;
import com.gaejangmo.apiserver.model.image.utils.UploadFileNameCreator;
import com.gaejangmo.apiserver.model.image.utils.UploadFileNameUuidCreator;
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
import static org.mockito.Mockito.*;

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
    private String url = "expected/url";

    private MockMultipartFile mockMultipartFile;
    private User user;
    private FileFeature fileFeature;

    @BeforeEach
    void setUp() {
        userImageRepository = mock(UserImageRepository.class);
        s3Connector = mock(S3Connector.class);
        uploadFileNameCreator = mock(UploadFileNameUuidCreator.class);

        mockMultipartFile = new MockMultipartFile("user-file", originalFilename, "image/jpeg", new byte[10]);

        user = User.builder().oauthId(oauthId).build();

        fileFeature = FileFeature.builder()
                .originalName(originalFilename)
                .size(10)
                .imageType(ImageType.of("jpg"))
                .url(url)
                .build();
    }

    // TODO: 2019/12/16 Mock이 안됨.
    @Test
    void save() {
        // given
        UserImage expected = new UserImage(fileFeature);

        when(uploadFileNameCreator.create(anyString())).thenReturn(originalFilename);
        when(s3Connector.upload(mockMultipartFile, String.valueOf(oauthId), originalFilename)).thenReturn(url);
        when(userImageRepository.save(expected)).thenReturn(expected);

        // when
        UserImage actual = userImageService.save(mockMultipartFile, user);

        // then
        assertSame(actual, expected);

        verify(s3Connector).upload(mockMultipartFile, String.valueOf(oauthId), originalFilename);
        verify(uploadFileNameCreator).create(anyString());
        verify(userImageRepository).save(expected);
    }
}