package com.gaejangmo.apiserver.model.image.utils;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

public class ImageTypeValidator {
    private static final List<String> contentTypes = List.of("image/png", "image/jpeg", "image/gif", "image/bmp");
    private static final List<String> imageTypes = List.of("jpg", "jpeg", "png", "gif", "bmp");
    public static final String NOT_MATCH_CONTENT_TYPES_MESSAGE = String.format("%s 의 Content-Type만 가능합니다.", contentTypes.toString());
    public static final String NOT_MATCH_IMAGE_TYPES_MESSAGE = String.format("%s 타입만 가능합니다.", imageTypes.toString());

    private ImageTypeValidator() {
    }

    public static void validateImage(final MultipartFile multipartFile) {
        String contentType = Objects.requireNonNull(multipartFile.getContentType());
        if (!contentTypes.contains(contentType)) {
            throw new IllegalArgumentException(NOT_MATCH_CONTENT_TYPES_MESSAGE);
        }
    }

    public static void validateImage(final String fileType) {
        if (!imageTypes.contains(fileType.toLowerCase()) && !contentTypes.contains(fileType.toLowerCase())) {
            throw new IllegalArgumentException(NOT_MATCH_IMAGE_TYPES_MESSAGE);
        }
    }
}
