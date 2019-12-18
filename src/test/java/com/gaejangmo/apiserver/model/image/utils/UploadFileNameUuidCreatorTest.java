package com.gaejangmo.apiserver.model.image.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UploadFileNameUuidCreatorTest {
    private UploadFileNameUuidCreator uploadFileNameUuidCreator = new UploadFileNameUuidCreator();

    @Test
    @DisplayName("파일 이름은 UUID 뒤에 확장자까지 나와야 한다.")
    void create() {
        String createdName = uploadFileNameUuidCreator.create("image.jpg");
        String actual = createdName.split("\\.")[1];

        assertThat(actual).isEqualTo("jpg");
    }
}