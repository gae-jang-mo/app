package com.gaejangmo.apiserver.model.userproduct.domain;

import com.gaejangmo.apiserver.model.product.domain.Product;
import com.gaejangmo.apiserver.model.product.testdata.ProductTestData;
import com.gaejangmo.apiserver.model.user.domain.User;
import com.gaejangmo.apiserver.model.userproduct.domain.exception.AlreadyDeleteException;
import com.gaejangmo.apiserver.model.userproduct.domain.vo.Comment;
import com.gaejangmo.apiserver.model.userproduct.domain.vo.ProductType;
import com.gaejangmo.apiserver.model.userproduct.domain.vo.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserProductTest {
    private UserProduct userProduct;
    private User mockUser;
    private Product mockProduct;

    @BeforeEach
    void setUp() {
        mockUser = mock(User.class);
        mockProduct = mock(Product.class);

        userProduct = UserProduct.builder()
                .user(mockUser)
                .product(mockProduct)
                .comment(Comment.of("comment"))
                .productType(ProductType.ETC)
                .status(Status.ON_USE)
                .build();

        ReflectionTestUtils.setField(ProductTestData.ENTITY, "id", 1L);
    }

    @Test
    void 작성자면_true() {
        // given
        long userId = 1L;
        when(mockUser.matchId(userId)).thenReturn(true);

        // when
        boolean actual = userProduct.matchUser(userId);

        // then
        assertThat(actual).isTrue();
        verify(mockUser).matchId(userId);
    }

    @Test
    void 작성자_아니면_false() {
        // given
        long userId = 1L;
        when(mockUser.matchId(userId)).thenReturn(false);

        // when
        boolean actual = userProduct.matchUser(userId);

        // then
        assertThat(actual).isFalse();
        verify(mockUser).matchId(userId);
    }

    @Test
    void Comment_수정() {
        // given
        String value = "updatedComment";

        // when
        UserProduct changed = userProduct.changeComment(Comment.of(value));

        // then
        assertThat(changed.getComment()).isEqualTo(value);
    }

    @Test
    void ProductType_수정() {
        // given
        ProductType keyBoard = ProductType.KEY_BOARD;

        // when
        UserProduct changed = userProduct.changeProductType(keyBoard);

        // then
        assertThat(changed.getProductType()).isEqualTo(keyBoard);
    }

    @Test
    void 삭제() {
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