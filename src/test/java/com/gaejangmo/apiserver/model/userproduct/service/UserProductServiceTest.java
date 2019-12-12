package com.gaejangmo.apiserver.model.userproduct.service;

import com.gaejangmo.apiserver.model.product.service.ProductService;
import com.gaejangmo.apiserver.model.userproduct.domain.UserProduct;
import com.gaejangmo.apiserver.model.userproduct.domain.UserProductRepository;
import com.gaejangmo.apiserver.model.userproduct.domain.vo.Comment;
import com.gaejangmo.apiserver.model.userproduct.domain.vo.ProductType;
import com.gaejangmo.apiserver.model.userproduct.service.exception.NotUserProductOwnerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserProductServiceTest {

    private static final long USER_PRODUCT_ID = 15L;
    private static final long USER_ID = 100L;
    @InjectMocks
    private UserProductService userProductService;
    @Mock
    private ProductService productService;
    @Mock
    private UserProductRepository userProductRepository;

    private UserProduct userProduct;
    private UserProduct mockUserProduct;

    @BeforeEach
    void setUp() {
        userProduct = UserProduct.builder()
                .comment(Comment.of("comment"))
                .productType(ProductType.MAIN_DEVICE)
                .build();

        mockUserProduct = mock(UserProduct.class);
    }

    @Test
    void 존재하는_유저_장비_id로_조회() {
        // given
        when(userProductRepository.findById(USER_PRODUCT_ID)).thenReturn(Optional.of(userProduct));

        // when
        UserProduct actual = userProductService.findById(USER_PRODUCT_ID);

        // then
        verify(userProductRepository).findById(USER_PRODUCT_ID);
        assertThat(actual).isEqualTo(userProduct);
    }

    @Test
    void 존재하지_않는_유저_장비_id로_조회_예외처리() {
        // given
        when(userProductRepository.findById(USER_PRODUCT_ID)).thenReturn(Optional.empty());

        // when & then
        assertThrows(EntityNotFoundException.class, () -> userProductService.findById(USER_PRODUCT_ID));
        verify(userProductRepository).findById(USER_PRODUCT_ID);
    }

    @Test
    void 본인_장비_삭제() {
        // given
        when(mockUserProduct.matchUser(USER_ID)).thenReturn(true);
        when(mockUserProduct.delete()).thenReturn(true);
        when(userProductRepository.findById(USER_PRODUCT_ID)).thenReturn(Optional.of(mockUserProduct));

        // when
        boolean result = userProductService.delete(USER_PRODUCT_ID, USER_ID);

        // then
        verify(mockUserProduct).matchUser(USER_ID);
        verify(mockUserProduct).delete();
        verify(userProductRepository).findById(USER_PRODUCT_ID);
        assertThat(result).isTrue();
    }

    @Test
    void 다른_유저_장비_삭제_예외처리() {
        // given
        when(mockUserProduct.matchUser(USER_ID)).thenReturn(false);
        when(userProductRepository.findById(USER_PRODUCT_ID)).thenReturn(Optional.of(mockUserProduct));

        // when & then
        assertThrows(NotUserProductOwnerException.class, () -> userProductService.delete(USER_PRODUCT_ID, USER_ID));

        verify(mockUserProduct).matchUser(USER_ID);
        verify(userProductRepository).findById(USER_PRODUCT_ID);
    }
}