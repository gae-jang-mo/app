package com.gaejangmo.apiserver.model.product.converter;

import com.gaejangmo.apiserver.model.product.domain.vo.NaverProductType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

class NaverProductTypeConverterTest {
    private NaverProductTypeConverter converter = new NaverProductTypeConverter();

    @ParameterizedTest
    @EnumSource(NaverProductType.class)
    void convertToDatabaseColumn(final NaverProductType naverProductType) {
        int expected = naverProductType.getValue();
        int actual = converter.convertToDatabaseColumn(naverProductType);

        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @EnumSource(NaverProductType.class)
    void convertToEntityAttribute(final NaverProductType naverProductType) {
        NaverProductType expected = NaverProductType.find(naverProductType.getValue());
        NaverProductType actual = converter.convertToEntityAttribute(naverProductType.getValue());

        assertThat(actual).isEqualTo(expected);
    }
}