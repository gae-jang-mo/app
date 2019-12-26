package com.gaejangmo.apiserver.model.userproduct.service;

import com.gaejangmo.apiserver.config.oauth.SecurityUser;
import com.gaejangmo.apiserver.model.common.domain.vo.Link;
import com.gaejangmo.apiserver.model.like.service.LikeService;
import com.gaejangmo.apiserver.model.product.domain.Product;
import com.gaejangmo.apiserver.model.product.service.ProductService;
import com.gaejangmo.apiserver.model.product.testdata.ProductTestData;
import com.gaejangmo.apiserver.model.user.domain.User;
import com.gaejangmo.apiserver.model.user.service.UserService;
import com.gaejangmo.apiserver.model.user.testdata.UserTestData;
import com.gaejangmo.apiserver.model.userproduct.domain.UserProduct;
import com.gaejangmo.apiserver.model.userproduct.domain.UserProductRepository;
import com.gaejangmo.apiserver.model.userproduct.domain.vo.Comment;
import com.gaejangmo.apiserver.model.userproduct.domain.vo.ProductType;
import com.gaejangmo.apiserver.model.userproduct.domain.vo.Status;
import com.gaejangmo.apiserver.model.userproduct.dto.CommentDto;
import com.gaejangmo.apiserver.model.userproduct.service.dto.UserProductLatestResponseDto;
import com.gaejangmo.apiserver.model.userproduct.service.dto.UserProductResponseDto;
import com.gaejangmo.apiserver.model.userproduct.service.exception.NotUserProductOwnerException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class UserProductServiceTest {
    private static final long USER_PRODUCT_ID = 15L;
    private static final long USER_ID = 100L;
    private static final int DEFAULT_PAGE_NUM = 0;

    @InjectMocks
    private UserProductService userProductService;
    @Mock
    private ProductService productService;
    @Mock
    private UserProductRepository userProductRepository;
    @Mock
    private UserService userService;
    @Mock
    private LikeService likeService;

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
        CommentDto commentDto = new CommentDto("changed comment");
        when(userProductRepository.findById(USER_PRODUCT_ID)).thenReturn(Optional.of(userProduct));

        // when
        UserProductResponseDto responseDto = userProductService.updateComment(USER_PRODUCT_ID, USER_ID, commentDto);

        // then
        assertThat(responseDto.getComment()).isEqualTo(commentDto.getComment());
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

    @ParameterizedTest
    @ValueSource(ints = {5, 6, 7})
    void 가장_최근에_등록된_장비_검색(final int size) {
        // given
        Pageable pageable = PageRequest.of(DEFAULT_PAGE_NUM, size);

        Page<UserProduct> pagedUserProducts = new PageImpl<>(LongStream.rangeClosed(1, size)
                .mapToObj(this::createUserProduct)
                .collect(Collectors.toList()));

        when(userProductRepository.findAll(pageable)).thenReturn(pagedUserProducts);
        when(likeService.isLiked(any(), anyLong())).thenReturn(true);

        // when
        List<UserProductLatestResponseDto> results =
                userProductService.findAllByPageable(pageable, SecurityUser.builder().id(1L).build());

        // then
        assertThat(results)
                .hasSizeLessThanOrEqualTo(size)
                .contains(
                        new UserProductLatestResponseDto((long) size,
                                ProductType.MOUSE, Status.ON_USE, ProductTestData.ENTITY.getImageUrl(), ProductTestData.ENTITY.getProductName(),
                                UserTestData.ENTITY.getImageUrl(), UserTestData.ENTITY.getUsername(), UserTestData.ENTITY.getMotto(),
                                true, null));
        verify(likeService, times(size)).isLiked(any(), anyLong());
    }

    private UserProduct createUserProduct(final long id) {
        UserProduct userProduct = UserProduct.builder()
                .product(ProductTestData.ENTITY)
                .user(UserTestData.ENTITY)
                .comment(Comment.of(String.valueOf(id)))
                .productType(ProductType.MOUSE)
                .status(Status.ON_USE)
                .build();
        ReflectionTestUtils.setField(userProduct, "id", id);
        return userProduct;
    }
}