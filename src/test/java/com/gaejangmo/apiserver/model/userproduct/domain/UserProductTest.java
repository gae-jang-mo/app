package com.gaejangmo.apiserver.model.userproduct.domain;

import com.gaejangmo.apiserver.model.userproduct.domain.exception.AlreadyDeleteException;
import com.gaejangmo.apiserver.model.userproduct.domain.vo.ProductType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserProductTest {
    private UserProduct userProduct;

    @BeforeEach
    void setUp() {
        userProduct = UserProduct.builder()
                .comment("comment")
                .productType(ProductType.ETC)
                .build();
    }

    // TODO: 2019/12/11 유저 추가후 테스트 구현
    @Test
    void matchUser() {
        // given

        // when

        // then

    }

    @Test
    void delete() {
        boolean actual = assertDoesNotThrow(() -> userProduct.delete());

        assertTrue(actual);
    }

    @Test
    void 이미_삭제된_장비_삭제시도_예외처리() {
        // given
        String expected = AlreadyDeleteException.DEFAULT_MESSAGE + "null";
        userProduct.delete();

        // when & then
        AlreadyDeleteException exception = assertThrows(AlreadyDeleteException.class, () -> userProduct.delete());
        assertThat(exception.getMessage()).isEqualTo(expected);
    }
}