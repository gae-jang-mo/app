package com.gaejangmo.apiserver.model.product.domain.vo;

import org.junit.jupiter.api.Test;

class LinkTest {

    @Test
    void 유효한_링크() {
        Link link = Link.of("https://search.shopping.naver.com/detail/detail.nhn?cat_id=50000151&nv_mid=20571500240");

    }

    @Test
    void 유효하지_않은_링크() {
        Link link = Link.of("d");
        // TODO
    }
}