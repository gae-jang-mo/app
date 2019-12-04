package com.gaejangmo.apiserver.model.product.domain.vo;

import com.gaejangmo.apiserver.model.product.exception.EmptyValueException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductNameTest {

    @Test
    void 상품_이름_초기화() {
        ProductName productName = ProductName.of("애플 맥북 에어 13형 2019년형 MVFK2KH/A");
        assertThat(productName.value()).isEqualTo("애플 맥북 에어 13형 2019년형 MVFK2KH/A");
    }

    @Test
    void 비어있는_상품_이름_예외발생() {
        assertThrows(EmptyValueException.class, () -> ProductName.of(""));
    }
}