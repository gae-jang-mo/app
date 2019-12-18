package com.gaejangmo.apiserver.model.user.domain.vo;

import com.gaejangmo.apiserver.model.user.exception.InvalidMottoException;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Motto {
    private static final int MAX_LENGTH = 10;

    private String value;

    private Motto(final String value) {
        this.value = validate(value);
    }

    public static Motto of(final String value) {
        return new Motto(value);
    }

    private String validate(final String value) {
        if (value.length() <= MAX_LENGTH) {
            return value;
        }
        throw new InvalidMottoException(String.format("입력값의 길이가 %d자 이하여야 합니다.", MAX_LENGTH));
    }

    public String value() {
        return value;
    }
}
