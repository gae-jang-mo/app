package com.gaejangmo.apiserver.model.product.domain.vo;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductIdTest {

    @Test
    void 상품_id_초기화() {
        ProductId productId = ProductId.of(12123124);
        assertThat(productId.value()).isEqualTo(12123124);
    }
}