package com.gaejangmo.apiserver.model.product.domain.vo;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class PriceTest {

    @Test
    void 가격_초기화() {
        Price price = Price.of(1000);
        assertThat(price.value()).isEqualTo(1000);
    }

    @Test
    void 가격_더하기() {
        Price price = Price.of(1000);
        Price newlyAddedPrice = Price.of(2000);

        assertThat(price.add(newlyAddedPrice)).isEqualTo(Price.of(3000));
    }

    @Test
    void 가격_수정_시_새로운_오브젝트_생성() {
        Price price = Price.of(1000);
        Price changedPrice = price.setPrice(2000);

        assertNotSame(price, changedPrice);
    }
}