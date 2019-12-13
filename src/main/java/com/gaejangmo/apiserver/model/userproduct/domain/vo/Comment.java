package com.gaejangmo.apiserver.model.userproduct.domain.vo;

import com.gaejangmo.utils.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Embeddable;


@ToString
@EqualsAndHashCode
@Embeddable
public class Comment {
    static final String EMPTY_VALUE = "";

    private final String value;

    public Comment(final String value) {
        this.value = validate(value);
    }

    private String validate(final String value) {
        if (StringUtils.isNull(value)) {
            return EMPTY_VALUE;
        }
        return value;
    }

    public static Comment of(final String value) {
        return new Comment(value);
    }

    public String value() {
        return value;
    }
}

