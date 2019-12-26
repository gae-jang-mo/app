package com.gaejangmo.apiserver.model.userproduct.domain.converter;

import com.gaejangmo.apiserver.model.userproduct.domain.vo.Status;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

class StatusAttributeConverterTest {
    private StatusAttributeConverter converter = new StatusAttributeConverter();

    @ParameterizedTest
    @EnumSource(Status.class)
    void convertToDatabaseColumn(final Status status) {
        int expected = status.getCode();
        int actual = converter.convertToDatabaseColumn(status);

        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @EnumSource(Status.class)
    void convertToEntityAttribute(final Status status) {
        Status actual = converter.convertToEntityAttribute(status.getCode());

        assertThat(actual).isSameAs(status);
    }

}