package com.gaejangmo.apiserver.model.image.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ImageTypeValidatorTest {

    @ParameterizedTest
    @ValueSource(strings = {"jpeg", "jpg", "png", "bmp", "gif", "JPG", "JPEG", "PNG", "BMP", "image/png", "image/jpeg", "image/gif", "image/bmp"})
    void 유효한_이미지타입_확인(final String fileType) {
        assertDoesNotThrow(() -> ImageTypeValidator.validateImage(fileType));
    }

    @ParameterizedTest
    @ValueSource(strings = {"je", "text", "TEXT", "tiff", "json"})
    void 유효하지_않은_이미지타입_확인(final String fileType) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> ImageTypeValidator.validateImage(fileType));
        assertThat(e.getMessage()).isEqualTo(ImageTypeValidator.NOT_MATCH_IMAGE_TYPES_MESSAGE);
    }

    @ParameterizedTest
    @MethodSource("validMultipartFiles")
    void 유효한_MultipartFile_확인(final MultipartFile multipartFile) {
        assertDoesNotThrow(() -> ImageTypeValidator.validateImage(multipartFile));
    }

    static Stream<MultipartFile> validMultipartFiles() {
        return Stream.of(
                new MockMultipartFile("photo", "filename.jpg", "image/png", new byte[10]),
                new MockMultipartFile("test_image", "test_image", "image/bmp", new byte[10]),
                new MockMultipartFile("test_image", "test_image", "image/gif", new byte[10]),
                new MockMultipartFile("test_image", "test_image", "image/jpeg", new byte[10])
        );
    }

    @ParameterizedTest
    @MethodSource("inValidMultipartFiles")
    void 유효하지_않은_MultipartFile_확인(final MultipartFile multipartFile) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> ImageTypeValidator.validateImage(multipartFile));
        assertThat(e.getMessage()).isEqualTo(ImageTypeValidator.NOT_MATCH_CONTENT_TYPES_MESSAGE);
    }

    static Stream<MultipartFile> inValidMultipartFiles() {
        return Stream.of(
                new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes()),
                new MockMultipartFile("data", "other-file-name.data", "text/plain", "some other type".getBytes()),
                new MockMultipartFile("json", "", "application/json", "{\"json\": \"someValue\"}".getBytes())
        );
    }


}