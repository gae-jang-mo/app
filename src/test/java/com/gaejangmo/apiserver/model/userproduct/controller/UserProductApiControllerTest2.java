package com.gaejangmo.apiserver.model.userproduct.controller;

import com.gaejangmo.apiserver.model.MockMvcTest;
import com.gaejangmo.apiserver.model.common.support.WithMockCustomUser;
import com.gaejangmo.apiserver.model.userproduct.domain.vo.ProductType;
import com.gaejangmo.apiserver.model.userproduct.service.dto.UserProductCreateDto;
import com.gaejangmo.apiserver.model.userproduct.service.dto.UserProductResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserProductApiControllerTest2 extends MockMvcTest {
    private static final String USER_PRODUCT_URI = getApiUrl(UserProductApiController.class);
    private static final long PRODUCT_ID = 1L;

    @Test
    @WithMockCustomUser
    void 장비_등록() throws Exception {
        // given
        UserProductCreateDto userProductCreateDto = getUserProductCreateDto();

        // when
        ResultActions resultActions = mockMvc.perform(post(USER_PRODUCT_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(userProductCreateDto)))
                .andDo(print())
                .andDo(document("userproduct/create",
                        requestFields(
                                fieldWithPath("productId").type(JsonFieldType.NUMBER).description("Product의 식별자"),
                                fieldWithPath("productType").type(JsonFieldType.STRING).description("장비의 타입"),
                                fieldWithPath("comment").type(JsonFieldType.STRING).description("제품에 대한 코멘트")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("UserProduct의 식별자"),
                                fieldWithPath("productId").type(JsonFieldType.NUMBER).description("Product의 식별자"),
                                fieldWithPath("comment").type(JsonFieldType.STRING).description("장비에 대한 코멘트"),
                                fieldWithPath("productType").type(JsonFieldType.STRING).description("장비의 타입"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("유저 장비 생성 날짜"),
                                fieldWithPath("imageUrl").type(JsonFieldType.STRING).description("유저 장비의 사진")
                        )));

        // then
        String content = resultActions.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        UserProductResponseDto responseDto = OBJECT_MAPPER.readValue(content, UserProductResponseDto.class);

        assertThat(responseDto.getId()).isNotNull();
        assertThat(responseDto.getProductType()).isEqualTo(userProductCreateDto.getProductType().getName());
        assertThat(responseDto.getComment()).isEqualTo(userProductCreateDto.getComment());
        assertThat(responseDto.getProductId()).isEqualTo(userProductCreateDto.getProductId());
    }

    @Test
    @WithAnonymousUser
    void 비로그인_장비_등록시도_Unauthorized() throws Exception {
        // given
        UserProductCreateDto userProductCreateDto = getUserProductCreateDto();

        // when
        ResultActions resultActions = mockMvc.perform(post(USER_PRODUCT_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(userProductCreateDto)))
                .andDo(print());

        // then
        resultActions.andExpect(status().isUnauthorized());
    }

    private UserProductCreateDto getUserProductCreateDto() {
        return UserProductCreateDto.builder()
                .productId(PRODUCT_ID)
                .productType(ProductType.KEY_BOARD)
                .comment("인생 마우스")
                .build();
    }

    @Test
    @WithAnonymousUser
    void 유저_ID로_소유장비_리스트_조회() throws Exception {
        // given
        UserProductResponseDto userProductResponseDto = getUserProductResponseDto();

        // when
        ResultActions resultActions = mockMvc.perform(
                RestDocumentationRequestBuilders.get(USER_PRODUCT_URI + "/{userId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("userproduct/list",
                        pathParameters(parameterWithName("userId").description("조회할 user의 식별자")),
                        responseFields(
                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("UserProduct의 식별자"),
                                fieldWithPath("[].productId").type(JsonFieldType.NUMBER).description("Product의 식별자"),
                                fieldWithPath("[].productType").type(JsonFieldType.STRING).description("장비에 대한 코멘트"),
                                fieldWithPath("[].comment").type(JsonFieldType.STRING).description("장비의 타입"),
                                fieldWithPath("[].createdAt").type(JsonFieldType.STRING).description("유저 장비 생성 날짜"),
                                fieldWithPath("[].imageUrl").type(JsonFieldType.STRING).description("유저 장비의 사진"))
                        )
                );

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].comment").value(userProductResponseDto.getComment()))
                .andExpect(jsonPath("$[0].id").value(userProductResponseDto.getId()));
    }

    private UserProductResponseDto getUserProductResponseDto() {
        return UserProductResponseDto.builder()
                .id(1L)
                .productId(1L)
                .productType(ProductType.MOUSE.name())
                .comment("ㅎㅎ장비좋아요ㅋㅋ")
                .createdAt(LocalDateTime.of(2014, 4, 1, 0, 0, 0, 0))
                .imageUrl("https://shopping-phinf.pstatic.net/main_2051460/20514607838.20190806111610.jpg")
                .build();
    }

    @Test
    @WithMockCustomUser
    void 장비_삭제() throws Exception {
        mockMvc.perform(
                RestDocumentationRequestBuilders.delete(USER_PRODUCT_URI + "/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("userproduct/delete",
                        pathParameters(parameterWithName("id").description("삭제할 유저 장비의 식별자")))
                )
                .andExpect(status().isNoContent());
    }
}