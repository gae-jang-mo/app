package com.gaejangmo.apiserver.model.product.domain.vo;

import com.gaejangmo.apiserver.model.product.exception.UrlFormatException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LinkTest {

    @Test
    void 유효한_링크() {
        assertThatCode(() ->
                Link.of("https://search.shopping.naver.com/detail/detail.nhn?cat_id=50000151&nv_mid=20571500240"))
                .doesNotThrowAnyException();
    }

    @Test
    void 유효하지_않은_링크() {
        assertThrows(UrlFormatException.class, () -> Link.of("d"));
    }
}