package com.gaejangmo.apiserver.model.image.domain.vo;


import com.gaejangmo.apiserver.model.image.utils.ImageTypeValidator;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embeddable;


@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Embeddable
public class ImageType {
    private String value;

    private ImageType(final String value) {
        ImageTypeValidator.validateImage(value);
        this.value = value;
    }

    public static ImageType of(final String value) {
        return new ImageType(value);
    }

    public String value() {
        return value;
    }
}
