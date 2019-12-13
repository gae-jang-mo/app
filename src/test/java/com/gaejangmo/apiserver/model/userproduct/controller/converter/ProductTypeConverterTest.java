package com.gaejangmo.apiserver.model.userproduct.controller.converter;

import com.gaejangmo.apiserver.model.userproduct.domain.vo.ProductType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTypeConverterTest {
    private ProductTypeConverter converter = new ProductTypeConverter();

    @ParameterizedTest
    @EnumSource(ProductType.class)
    void convert(final ProductType productType) {
        ProductType actual = converter.convert(productType.getName());

        assertThat(productType).isSameAs(actual);
    }
}