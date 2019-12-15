package com.gaejangmo.apiserver.model.image.domain.vo;


import com.gaejangmo.apiserver.model.image.utils.ImageTypeValidator;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Embeddable;


@ToString
@EqualsAndHashCode
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
