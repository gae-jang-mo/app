package com.gaejangmo.apiserver.model.userproduct.domain.vo;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

class StatusTest {

    @ParameterizedTest
    @EnumSource(Status.class)
    void ofCodeTest(final Status status) {
        // given
        int code = status.getCode();

        // when
        Status actual = Status.ofCode(code);

        // then
        assertThat(actual).isEqualTo(status);
    }

    @ParameterizedTest
    @EnumSource(Status.class)
    void ofTypeTest(final Status status) {
        // given
        String type = status.getType();

        // when
        Status actual = Status.ofType(type);

        // then
        assertThat(actual).isEqualTo(status);
    }

    @ParameterizedTest
    @EnumSource(Status.class)
    void 삭제된_상태인지_확인_테스트(final Status status) {
        boolean actual = (status == Status.DELETED);

        assertThat(status.isDeleted()).isEqualTo(actual);
    }

}