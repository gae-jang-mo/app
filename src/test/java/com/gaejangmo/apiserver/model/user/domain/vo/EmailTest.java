package com.gaejangmo.apiserver.model.user.domain.vo;

import com.gaejangmo.apiserver.model.user.exception.InvalidEmailException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmailTest {

    @Test
    void 생성_테스트() {
        Email email = Email.of("lgi@gmail.com");

        assertThat(email).isEqualTo(Email.of("lgi@gmail.com"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "email@[123.123.123.123]",
            "\"email\"@example.com",
            "@lgi.com",
            "firstname-lastname@example.com\n"})
    void 유효하지_않은_이메일_포맷일_경우_예외처리(final String sample) {
        assertThrows(InvalidEmailException.class, () -> Email.of(sample));
    }

    @Test
    void equals_테스트() {
        String value = "email@email.com";
        Email email = Email.of(value);

        assertThat(email.equals(Email.of(value))).isTrue();
    }

    @Test
    void hashCode_테스트() {
        String value = "email@email.com";
        Email email = Email.of(value);

        assertThat(email.hashCode()).isEqualTo(Email.of(value).hashCode());
    }
}