package com.gaejangmo.apiserver.model.userproduct.domain.vo;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTypeTest {

    @ParameterizedTest
    @EnumSource(ProductType.class)
    void ofCodeTest(final ProductType productType) {
        // given
        int code = productType.getCode();

        // when
        ProductType actual = ProductType.ofCode(code);

        // then
        assertThat(actual).isEqualTo(productType);
    }

    @ParameterizedTest
    @EnumSource(ProductType.class)
    void ofNameTest(final ProductType productType) {
        // given
        String name = productType.getName();

        // when
        ProductType actual = ProductType.ofName(name);

        // then
        assertThat(actual).isEqualTo(productType);
    }


}