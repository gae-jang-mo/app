package com.gaejangmo.apiserver.model.userproduct.controller;

import com.gaejangmo.apiserver.model.MockMvcTest;
import com.gaejangmo.apiserver.model.common.support.WithMockCustomUser;
import com.gaejangmo.apiserver.model.product.domain.vo.NaverProductType;
import com.gaejangmo.apiserver.model.product.dto.ProductRequestDto;
import com.gaejangmo.apiserver.model.userproduct.domain.vo.ProductType;
import com.gaejangmo.apiserver.model.userproduct.service.UserProductService;
import com.gaejangmo.apiserver.model.userproduct.service.dto.UserProductExternalRequestDto;
import com.gaejangmo.apiserver.model.userproduct.service.dto.UserProductInternalRequestDto;
import com.gaejangmo.apiserver.model.userproduct.service.dto.UserProductRequestDto;
import com.gaejangmo.apiserver.model.userproduct.service.dto.UserProductResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserProductApiControllerTest extends MockMvcTest {
    private static final String USER_PRODUCT_URI = getApiUrl(UserProductApiController.class);
    private static final long USER_ID = 1L;

    @MockBean
    private UserProductService userProductService;

    private UserProductResponseDto userProductResponseDto;
    private UserProductInternalRequestDto userProductInternalRequestDto;
    private UserProductExternalRequestDto userProductExternalRequestDto;

    @BeforeEach
    void setUp() {
        ProductRequestDto productRequestDto = ProductRequestDto.builder()
                .title("애플 맥북 프로 15형 2019년형 MV912KH/A")
                .link("https://shopping-phinf.pstatic.net/main_2057150/20571500240.20190819112004.jpg")
                .image("https://shopping-phinf.pstatic.net/main_1980579/19805790169.20190617105809.jpg")
                .lowestPrice(2980720)
                .highestPrice(4835230)
                .mallName("네이버")
                .productId(19805790169L)
                .naverProductType(NaverProductType.find(1).toString())
                .build();

        UserProductRequestDto userProductRequestDto = UserProductRequestDto.builder()
                .comment("넘 좋앙")
                .productType(ProductType.MAIN_DEVICE)
                .build();

        userProductResponseDto = UserProductResponseDto.builder()
                .id(1L)
                .productId(1L)
                .productType(ProductType.MAIN_DEVICE.getName())
                .comment("넘 좋앙")
                .createdAt(LocalDateTime.now())
                .imageUrl("https://shopping-phinf.pstatic.net/main_1980579/19805790169.20190617105809.jpg")
                .build();

        userProductInternalRequestDto = UserProductInternalRequestDto.builder()
                .userProductRequestDto(userProductRequestDto)
                .productId(1L)
                .build();

        userProductExternalRequestDto = UserProductExternalRequestDto.builder()
                .userProductRequestDto(userProductRequestDto)
                .productRequestDto(productRequestDto)
                .build();
    }

    @Test
    @WithMockCustomUser
    void 내부_장비_UserProduct_등록() throws Exception {
        when(userProductService.saveFromInternal(userProductInternalRequestDto, USER_ID)).thenReturn(userProductResponseDto);

        // when
        ResultActions resultActions = mockMvc.perform(post(USER_PRODUCT_URI + "/internal")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(userProductInternalRequestDto)))
                .andDo(print());

        // then
        resultActions.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").value(userProductResponseDto.getId()))
                .andExpect(jsonPath("comment").value(userProductResponseDto.getComment()));
    }

    @Test
    @WithMockCustomUser
    void 외부_장비_UserProduct_등록() throws Exception {
        when(userProductService.saveFromExternal(userProductExternalRequestDto, USER_ID)).thenReturn(userProductResponseDto);

        // when
        ResultActions resultActions = mockMvc.perform(post(USER_PRODUCT_URI + "/external")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(userProductExternalRequestDto)))
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
        ResultActions resultActions = mockMvc.perform(post(USER_PRODUCT_URI + "/internal")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(userProductInternalRequestDto)))
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
    @WithMockCustomUser
    void 장비_삭제() throws Exception {
        // given
        when(userProductService.delete(1L, USER_ID)).thenReturn(true);

        // when
        ResultActions resultActions = mockMvc.perform(delete(USER_PRODUCT_URI + "/1"))
                .andDo(print());

        // then
        resultActions.andExpect(status().isNoContent());
    }

    @Test
    @WithMockCustomUser
    void 장비_comment_수정() throws Exception {
        // given
        String comment = "updatedComment";
        UserProductResponseDto userProductResponseDto = UserProductResponseDto.builder().comment(comment).build();
        when(userProductService.updateComment(1L, USER_ID, comment)).thenReturn(userProductResponseDto);

        // when
        ResultActions resultActions = mockMvc.perform(put(USER_PRODUCT_URI + "/1/comment")
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
    @WithMockCustomUser
    void 장비_ProductType_수정() throws Exception {
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
}