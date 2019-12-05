package com.gaejangmo.apiserver.model.product.domain.vo;

import com.gaejangmo.apiserver.model.product.exception.EmptyValueException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductNameTest {

    @Test
    void 상품_이름_초기화() {
        ProductName productName = ProductName.of("애플 맥북 에어 13형 2019년형 MVFK2KH/A");
        assertThat(productName.value()).isEqualTo("애플 맥북 에어 13형 2019년형 MVFK2KH/A");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    void 비어있는_상품_이름_예외발생(String input) {
        assertThrows(EmptyValueException.class, () -> ProductName.of(input));
    }
}