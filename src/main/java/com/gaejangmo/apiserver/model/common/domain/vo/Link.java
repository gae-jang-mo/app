package com.gaejangmo.apiserver.model.common.domain.vo;

import com.gaejangmo.apiserver.model.product.exception.UrlFormatException;
import lombok.EqualsAndHashCode;

import javax.persistence.Embeddable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Embeddable
@EqualsAndHashCode
public class Link {
    private static final Pattern URL_REGEX = Pattern.compile("^(?:(?:http(?:s)?|ftp)://)(?:\\S+(?::(?:\\S)*)?@)?(?:(?:[a-z0-9\u00a1-\uffff](?:-)*)*(?:[a-z0-9\u00a1-\uffff])+)(?:\\.(?:[a-z0-9\u00a1-\uffff](?:-)*)*(?:[a-z0-9\u00a1-\uffff])+)*(?:\\.(?:[a-z0-9\u00a1-\uffff]){2,})(?::(?:\\d){2,5})?(?:/(?:\\S)*)?$");

    private String value;

    private Link() {
    }

    private Link(final String value) {
        this.value = validate(value);
    }

    private String validate(final String value) {
        Matcher matcher = URL_REGEX.matcher(value);
        if (matcher.matches()) {
            return value;
        }
        throw new UrlFormatException("유효한 url이 아닙니다");
    }

    public static Link of(final String value) {
        return new Link(value);
    }

    public String value() {
        return value;
    }
}
