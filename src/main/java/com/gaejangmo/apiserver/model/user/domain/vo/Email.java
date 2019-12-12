package com.gaejangmo.apiserver.model.user.domain.vo;

import com.gaejangmo.apiserver.model.user.exception.InvalidEmailException;
import lombok.EqualsAndHashCode;

import javax.persistence.Embeddable;
import java.util.regex.Pattern;

@Embeddable
@EqualsAndHashCode
public class Email {
    private static final String EMAIL_REGEX = "[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
    private static final Pattern PATTERN = Pattern.compile(EMAIL_REGEX);

    private String value;

    private Email() {
    }

    private Email(final String value) {
        this.value = validate(value);
    }

    public static Email of(final String value) {
        return new Email(value);
    }

    private String validate(final String value) {
        if (PATTERN.matcher(value).matches()) {
            return value;
        }
        throw new InvalidEmailException("유효한 이메일 포맷이 아닙니다");
    }

    public String value() {
        return value;
    }
}
