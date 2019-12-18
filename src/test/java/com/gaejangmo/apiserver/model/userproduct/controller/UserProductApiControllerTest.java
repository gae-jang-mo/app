package com.gaejangmo.apiserver.model.userproduct.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaejangmo.apiserver.model.common.resolver.SessionUser;
import com.gaejangmo.apiserver.model.product.testdata.ProductTestData;
import com.gaejangmo.apiserver.model.user.testdata.UserTestData;
import com.gaejangmo.apiserver.model.userproduct.domain.UserProduct;
import com.gaejangmo.apiserver.model.userproduct.domain.vo.Comment;
import com.gaejangmo.apiserver.model.userproduct.domain.vo.ProductType;
import com.gaejangmo.apiserver.model.userproduct.service.UserProductService;
import com.gaejangmo.apiserver.model.userproduct.service.dto.UserProductCreateDto;
import com.gaejangmo.apiserver.model.userproduct.service.dto.UserProductLatestResponseDto;
import com.gaejangmo.apiserver.model.userproduct.service.dto.UserProductResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserProductApiControllerTest {
    private static final String USER_PRODUCT_URI = linkTo(UserProductApiController.class).toString();
    private static final int DEFAULT_PAGE_NUM = 0;
    private static final long USER_ID = 1L;
    private static final long PRODUCT_ID = 10L;

    @MockBean
    private UserProductService userProductService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    public MockHttpSession session;
    private UserProductCreateDto userProductCreateDto;
    private UserProductResponseDto userProductResponseDto;

    @BeforeEach
    void setUp() {
        userProductCreateDto = UserProductCreateDto.builder()
                .productId(PRODUCT_ID)
                .productType(ProductType.KEY_BOARD)
                .comment("인생 마우스")
                .build();

        userProductResponseDto = UserProductResponseDto.builder()
                .id(1L)
                .comment("userProductResponseDto comment")
                .build();

        SessionUser sessionUser = new SessionUser(USER_ID, "email@gmail.com", "userName");
        session = new MockHttpSession();
        session.setAttribute(SessionUser.USER_SESSION_KEY, sessionUser);

    }

    @Test
    @WithMockUser
    void 장비_등록() throws Exception {
        when(userProductService.save(userProductCreateDto, USER_ID)).thenReturn(userProductResponseDto);

        // when
        ResultActions resultActions = mockMvc.perform(post(USER_PRODUCT_URI)
                .session(session)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userProductCreateDto)))
                .andDo(print());

        // then
        resultActions.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").value(userProductResponseDto.getId()))
                .andExpect(jsonPath("comment").value(userProductResponseDto.getComment()));
    }

    @Test
    @WithAnonymousUser
    void 비로그인_장비_등록시도_Unauthorized() throws Exception {
        // when
        ResultActions resultActions = mockMvc.perform(post(USER_PRODUCT_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userProductCreateDto)))
                .andDo(print());

        // then
        resultActions.andExpect(status().isUnauthorized());
    }

    @Test
    @WithAnonymousUser
    void 리스트_조회() throws Exception {
        // given
        List<UserProductResponseDto> expected = List.of(userProductResponseDto);
        when(userProductService.findByUserId(USER_ID)).thenReturn(expected);

        // when
        ResultActions resultActions = mockMvc.perform(get(USER_PRODUCT_URI + "/1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].comment").value(userProductResponseDto.getComment()))
                .andExpect(jsonPath("$[0].id").value(userProductResponseDto.getId()));
    }

    @Test
    @WithMockUser
    void 장비_삭제() throws Exception {
        // given
        when(userProductService.delete(1L, USER_ID)).thenReturn(true);

        // when
        ResultActions resultActions = mockMvc.perform(delete(USER_PRODUCT_URI + "/1")
                .session(session))
                .andDo(print());

        // then
        resultActions.andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void 장비_comment_수정() throws Exception {
        // given
        String comment = "updatedComment";
        UserProductResponseDto userProductResponseDto = UserProductResponseDto.builder().comment(comment).build();
        when(userProductService.updateComment(1L, USER_ID, comment)).thenReturn(userProductResponseDto);

        // when
        ResultActions resultActions = mockMvc.perform(put(USER_PRODUCT_URI + "/1/comment")
                .session(session)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(comment))
                .andDo(print());

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("comment").value(comment));
    }

    @Test
    @WithAnonymousUser
    void 비로그인_장비_Comment_수정_시도_Unauthorized() throws Exception {
        // given
        String comment = "updatedComment";

        // when
        ResultActions resultActions = mockMvc.perform(put(USER_PRODUCT_URI + "/1/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(comment))
                .andDo(print());

        // then
        resultActions.andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void 장비_ProductType_수정() throws Exception {
        // given
        ProductType productType = ProductType.MAIN_DEVICE;
        UserProductResponseDto userProductResponseDto = UserProductResponseDto.builder().productType(productType.getName()).build();
        when(userProductService.updateProductType(1L, USER_ID, productType)).thenReturn(userProductResponseDto);

        // when
        ResultActions resultActions = mockMvc.perform(put(USER_PRODUCT_URI + "/1/product-type")
                .session(session)
                .accept(MediaType.APPLICATION_JSON)
                .content(productType.getName()))
                .andDo(print());

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("productType").value(productType.getName()));
    }

    @Test
    @WithAnonymousUser
    void 비로그인_장비_ProductType_수정_시도_Unauthorized() throws Exception {
        // given
        ProductType productType = ProductType.MAIN_DEVICE;
        UserProductResponseDto userProductResponseDto = UserProductResponseDto.builder().productType(productType.getName()).build();
        when(userProductService.updateProductType(1L, USER_ID, productType)).thenReturn(userProductResponseDto);

        // when
        ResultActions resultActions = mockMvc.perform(put(USER_PRODUCT_URI + "/1/product-type")
                .accept(MediaType.APPLICATION_JSON)
                .content(productType.getName()))
                .andDo(print());

        // then
        resultActions.andExpect(status().isUnauthorized());
    }

    @Test
    void 최근에_등록된_데이터_조회() throws Exception {
        // given
        int page = 2;
        int size = 3;
        int firstItemId = page * size + 1;
        int lastItemId = (page + 1) * size;

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");

        List<UserProductLatestResponseDto> userProductLatestResponseDtos = IntStream.rangeClosed(firstItemId, lastItemId)
                .mapToObj(this::createUserProduct)
                .map(this::toLatestDto)
                .sorted(Collections.reverseOrder(Comparator.comparingLong(UserProductLatestResponseDto::getId)))
                .collect(Collectors.toList());

        when(userProductService.findAllByPageable(pageable)).thenReturn(userProductLatestResponseDtos);

        // when
        ResultActions resultActions = mockMvc.perform(get(USER_PRODUCT_URI + "/latest")
                .param("page", String.valueOf(page)).param("size", String.valueOf(size))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(lastItemId))
                .andExpect(jsonPath("$.[1].id").value(lastItemId - 1));

    }

    private UserProductLatestResponseDto toLatestDto(final UserProduct userProduct) {
        return UserProductLatestResponseDto.builder()
                .id(userProduct.getId())
                .productType(userProduct.getProductType())
                .productImageUrl(userProduct.getProduct().getImageUrl())
                .productName(userProduct.getProduct().getProductName())
                .userImageUrl(userProduct.getUser().getImageUrl())
                .username(userProduct.getUser().getUsername())
                .motto(userProduct.getUser().getMotto())
                .createdAt(userProduct.getCreatedAt())
                .build();
    }

    private UserProduct createUserProduct(final long id) {
        UserProduct userProduct = UserProduct.builder()
                .product(ProductTestData.ENTITY)
                .user(UserTestData.ENTITY)
                .comment(Comment.of(String.valueOf(id)))
                .productType(ProductType.MOUSE)
                .build();
        ReflectionTestUtils.setField(userProduct, "id", id);
        return userProduct;
    }
}