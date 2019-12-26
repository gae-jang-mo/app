package com.gaejangmo.apiserver.model.image.domain.vo;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

class FileFeatureTest {

    static Stream<Arguments> provide() {
        return Stream.of(
                Arguments.of("url", "originalNmae", "savedName", -1),
                Arguments.of("url", "originalNmae", "savedName", FileFeature.MIN_SIZE - 1),
                Arguments.of("url", "originalNmae", "savedName", FileFeature.MAX_SIZE + 1),
                Arguments.of("", "originalNmae", "savedName", 1),
                Arguments.of("url", "", "savedName", 1),
                Arguments.of("url", "originalName", "", 1)
        );
    }

    @ParameterizedTest
    @MethodSource("provide")
    void name(final String url, final String originalName, final String savedName, final int size) {
        assertThrows(IllegalArgumentException.class,
                () -> FileFeature.builder()
                        .imageType(ImageType.of("jpg"))
                        .originalName(originalName)
                        .savedName(savedName)
                        .url(url)
                        .size(size)
                        .build());
    }
}