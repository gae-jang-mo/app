package com.gaejangmo.apiserver.model.user.domain.vo;

import com.gaejangmo.apiserver.model.user.exception.InvalidEmailException;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.regex.Pattern;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Email {
    private static final String EMAIL_REGEX = "[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
    private static final Pattern PATTERN = Pattern.compile(EMAIL_REGEX);
    private static final String EMPTY = "";
    private static final String NULL = "null";

    private String value;

    private Email(final String value) {
        this.value = validate(value);
    }

    public static Email of(final String value) {
        return new Email(value);
    }

    private String validate(final String value) {
        // 깃허브에서 email이 없을 때 "null"이라는 문자열이 리턴된다
        if (NULL.equals(value)) {
            return EMPTY;
        }

        if (PATTERN.matcher(value).matches()) {
            return value;
        }
        throw new InvalidEmailException("유효한 이메일 포맷이 아닙니다");
    }

    public String value() {
        return value;
    }
}
