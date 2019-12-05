package com.gaejangmo.apiserver.model.product.domain.vo;

import com.gaejangmo.apiserver.model.product.exception.UrlFormatException;
import lombok.EqualsAndHashCode;

import javax.persistence.Embeddable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Embeddable
@EqualsAndHashCode
public class Link {
    private static final Pattern URL_REGEX = Pattern.compile("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");

    private String value;

    private Link() {
    }

    private Link(final String value) {
        this.value = validate(value);
    }

    private String validate(final String value) {
        Matcher matcher = URL_REGEX.matcher(value);
        if (!matcher.matches()) {
            throw new UrlFormatException("유효한 url이 아닙니다");
        }
        return value;
    }

    public static Link of(final String value) {
        return new Link(value);
    }

    public String value() {
        return value;
    }
}
