package com.gaejangmo.apiserver.model.userproduct.domain.converter;

import com.gaejangmo.apiserver.model.userproduct.domain.vo.ProductType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTypeAttributeConverterTest {
    private ProductTypeAttributeConverter converter = new ProductTypeAttributeConverter();

    @ParameterizedTest
    @EnumSource(ProductType.class)
    void convertToDatabaseColumn(final ProductType productType) {
        int expected = productType.getCode();
        int actual = converter.convertToDatabaseColumn(productType);

        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @EnumSource(ProductType.class)
    void convertToEntityAttribute(final ProductType productType) {
        ProductType actual = converter.convertToEntityAttribute(productType.getCode());

        assertThat(actual).isSameAs(productType);
    }
}