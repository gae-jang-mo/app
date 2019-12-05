package com.gaejangmo.apiserver.model.product.domain.vo;

import com.gaejangmo.apiserver.model.product.exception.EmptyValueException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MallNameTest {

    @Test
    void 판매처_초기화() {
        MallName mallName = MallName.of("야시장");
        assertThat(mallName.value()).isEqualTo("야시장");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void 비어있는_판매처_예외발생(String input) {
        assertThrows(EmptyValueException.class, () -> MallName.of(input));
    }
}