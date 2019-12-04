package com.gaejangmo.apiserver.model.product.domain.vo;

import com.gaejangmo.apiserver.model.product.exception.EmptyValueException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MallNameTest {

    @Test
    void 판매처_초기화() {
        MallName mallName = MallName.of("야시장");
        assertThat(mallName.value()).isEqualTo("야시장");
    }

    @Test
    void 비어있는_판매처_예외발생() {
        assertThrows(EmptyValueException.class, () -> MallName.of(""));
    }
}