package com.gaejangmo.apiserver.model.user.domain.vo;

import com.gaejangmo.apiserver.model.user.exception.InvalidEmailException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmailTest {

    @Test
    void 생성_테스트() {
        Email email = Email.of("lgi@gmail.com");

        assertThat(email).isEqualTo(Email.of("lgi@gmail.com"));
    }

    @Test
    void 유효하지_않은_이메일_포맷일_경우_예외처리() {
        assertThrows(InvalidEmailException.class, () -> Email.of("@lgi.com"));
    }
}