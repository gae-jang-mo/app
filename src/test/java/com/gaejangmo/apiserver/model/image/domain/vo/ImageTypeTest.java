package com.gaejangmo.apiserver.model.image.domain.vo;

import com.gaejangmo.apiserver.model.image.utils.ImageTypeValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ImageTypeTest {

    @ParameterizedTest
    @ValueSource(strings = {"a.jpg", "a.gif", "a.png", "a.bmp",
            "..jpg", "..gif", "..png", "..bmp",
            "a.JPG", "a.GIF", "a.PNG", "a.BMP",
            "a.JpG", "a.GiF", "a.PnG", "a.BmP",
            "jpg.jpg", "gif.gif", "png.png", "bmp.bmp"})
    void 유효하지_않은_타입(final String fileType) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> ImageType.of(fileType));
        assertThat(e.getMessage()).isEqualTo(ImageTypeValidator.NOT_MATCH_IMAGE_TYPES_MESSAGE);
    }

    @ParameterizedTest
    @ValueSource(strings = {"jpeg", "jpg", "png", "bmp", "gif", "JPG", "JPEG", "PNG", "BMP"})
    void 유효한_타입(final String fileType) {
        assertDoesNotThrow(() -> ImageType.of(fileType));
    }

    @Test
    void equals_hashCode_테스트() {
        final ImageType imageType1 = ImageType.of("jpg");
        final ImageType imageType2 = ImageType.of("jpg");

        assertThat(imageType1).isEqualTo(imageType2);
    }
}