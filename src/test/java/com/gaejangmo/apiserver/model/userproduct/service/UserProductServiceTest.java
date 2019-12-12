package com.gaejangmo.apiserver.model.userproduct.service;

import com.gaejangmo.apiserver.model.common.domain.vo.Link;
import com.gaejangmo.apiserver.model.product.domain.Product;
import com.gaejangmo.apiserver.model.product.service.ProductService;
import com.gaejangmo.apiserver.model.user.domain.User;
import com.gaejangmo.apiserver.model.user.service.UserService;
import com.gaejangmo.apiserver.model.userproduct.domain.UserProduct;
import com.gaejangmo.apiserver.model.userproduct.domain.UserProductRepository;
import com.gaejangmo.apiserver.model.userproduct.domain.vo.Comment;
import com.gaejangmo.apiserver.model.userproduct.domain.vo.ProductType;
import com.gaejangmo.apiserver.model.userproduct.service.dto.UserProductResponseDto;
import com.gaejangmo.apiserver.model.userproduct.service.exception.NotUserProductOwnerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import javax.persistence.EntityNotFoundException;
import java.util.List;
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
    @Mock
    private UserService userService;

    private UserProduct userProduct;
    private UserProduct mockUserProduct;
    private User user;
    private Product product;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .build();

        ReflectionTestUtils.setField(user, "id", USER_ID);

        product = Product.builder()
                .imageUrl(Link.of("https://search.shopping.naver.com"))
                .build();

        userProduct = UserProduct.builder()
                .comment(Comment.of("comment"))
                .productType(ProductType.MAIN_DEVICE)
                .product(product)
                .user(user)
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
        assertThat(actual).isEqualTo(userProduct);

        verify(userProductRepository).findById(USER_PRODUCT_ID);
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
        assertThat(result).isTrue();

        verify(mockUserProduct).matchUser(USER_ID);
        verify(mockUserProduct).delete();
        verify(userProductRepository).findById(USER_PRODUCT_ID);
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

    @Test
    void userId로_장비_검색() {
        // given
        User mockUser = mock(User.class);
        List<UserProduct> userProducts = List.of(userProduct);
        when(userService.findById(USER_ID)).thenReturn(mockUser);
        when(userProductRepository.findByUser(mockUser)).thenReturn(userProducts);

        // when
        List<UserProductResponseDto> actual = userProductService.findByUserId(USER_ID);

        // then
        assertThat(actual.get(0).getComment()).isEqualTo(userProducts.get(0).getComment());
        assertThat(actual.get(0).getProductType()).isEqualTo(userProducts.get(0).getProductType().getName());

        verify(userService).findById(USER_ID);
        verify(userProductRepository).findByUser(mockUser);
    }

    @Test
    void Comment_수정() {
        // given
        String comment = "changed comment";
        when(userProductRepository.findById(USER_PRODUCT_ID)).thenReturn(Optional.of(userProduct));

        // when
        UserProductResponseDto responseDto = userProductService.updateComment(USER_PRODUCT_ID, USER_ID, comment);

        // then
        assertThat(responseDto.getComment()).isEqualTo(comment);
    }

    @Test
    void ProductType_수정() {
        // given
        ProductType productType = ProductType.KEY_BOARD;
        when(userProductRepository.findById(USER_PRODUCT_ID)).thenReturn(Optional.of(userProduct));

        // when
        UserProductResponseDto responseDto = userProductService.updateProductType(USER_PRODUCT_ID, USER_ID, productType);

        // then
        assertThat(responseDto.getProductType()).isEqualTo(productType.getName());

    }
}