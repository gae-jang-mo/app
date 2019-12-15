package com.gaejangmo.apiserver.model.image.domain.vo;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

class FileFeatureTest {

    @ParameterizedTest
    @MethodSource("provide")
    void name(final String url, final String originalName, final int size) {
        assertThrows(IllegalArgumentException.class,
                () -> FileFeature.builder()
                        .imageType(ImageType.of("jpg"))
                        .originalName(originalName)
                        .url(url)
                        .size(size)
                        .build());
    }

    static Stream<Arguments> provide() {
        return Stream.of(
                Arguments.of("url", "originalNmae", -1),
                Arguments.of("url", "originalNmae", 0),
                Arguments.of("", "originalNmae", 1),
                Arguments.of("url", "", 1)
        );
    }
}