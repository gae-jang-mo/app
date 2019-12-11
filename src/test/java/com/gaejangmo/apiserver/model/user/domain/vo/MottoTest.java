package com.gaejangmo.apiserver.model.user.domain.vo;

import com.gaejangmo.apiserver.model.user.exception.InvalidMottoException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MottoTest {

    @Test
    void 생성_테스트() {
        Motto motto = Motto.of("좌우명");

        assertThat(motto).isEqualTo(Motto.of("좌우명"));
    }

    @Test
    void 최대_길이를_초과했을_경우_예외처리() {
        assertThrows(InvalidMottoException.class, () -> Motto.of("01234567890"));
    }

    @Test
    void 최대_길이인_경우_테스트() {
        assertDoesNotThrow(() -> Motto.of("0123456789"));
    }
}