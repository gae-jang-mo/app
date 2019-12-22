package com.gaejangmo.apiserver.model.userproduct.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gaejangmo.apiserver.model.MockMvcTest;
import com.gaejangmo.apiserver.model.common.support.WithMockCustomUser;
import com.gaejangmo.apiserver.model.userproduct.domain.vo.ProductType;
import com.gaejangmo.apiserver.model.userproduct.service.dto.UserProductCreateDto;
import com.gaejangmo.apiserver.model.userproduct.service.dto.UserProductLatestResponseDto;
import com.gaejangmo.apiserver.model.userproduct.service.dto.UserProductResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.List;

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

    private UserProductResponseDto getUserProductResponseDto() {
        return UserProductResponseDto.builder()
                .id(1L)
                .productId(1L)
                .productType(ProductType.MOUSE.name())
                .comment("ㅎㅎ")
                .createdAt(LocalDateTime.of(2014, 4, 1, 0, 0, 0, 0))
                .imageUrl("https://shopping-phinf.pstatic.net/main_2051460/20514607838.20190806111610.jpg")
                .build();
    }

    @Test
    @WithAnonymousUser
    void 가장_최근에_등록된_장비_검색() throws Exception {
        // given
        Pageable pageable = PageRequest.of(0, 20);
        UserProductLatestResponseDto userProductLatestResponseDto = getUserProductLatestResponseDto();

        // when
        ResultActions resultActions = mockMvc.perform(
                RestDocumentationRequestBuilders.get(USER_PRODUCT_URI + "/latest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(pageable)))
                .andDo(print())
                .andDo(document("userproduct/latest",
                        requestFields(
                                fieldWithPath("pageNumber").type(JsonFieldType.NUMBER).description("출력할 페이지 번호").optional(),
                                fieldWithPath("pageSize").type(JsonFieldType.NUMBER).description("한 번에 출력할 페이지의 크기").optional(),
                                fieldWithPath("offset").type(JsonFieldType.NUMBER).ignored(),
                                fieldWithPath("paged").type(JsonFieldType.BOOLEAN).ignored(),
                                fieldWithPath("unpaged").type(JsonFieldType.BOOLEAN).ignored(),
                                fieldWithPath("sort.unsorted").type(JsonFieldType.BOOLEAN).ignored(),
                                fieldWithPath("sort.sorted").type(JsonFieldType.BOOLEAN).ignored(),
                                fieldWithPath("sort.empty").type(JsonFieldType.BOOLEAN).ignored()
                        ),
                        responseFields(
                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("UserProduct의 식별자"),
                                fieldWithPath("[].product.type").type(JsonFieldType.STRING).description("장비의 타입"),
                                fieldWithPath("[].product.imageUrl").type(JsonFieldType.STRING).description("장비의 사진 URL"),
                                fieldWithPath("[].product.name").type(JsonFieldType.STRING).description("장비의 이름"),
                                fieldWithPath("[].user.imageUrl").type(JsonFieldType.STRING).description("유저의 사진 URL"),
                                fieldWithPath("[].user.username").type(JsonFieldType.STRING).description("유저의 등록된 이름"),
                                fieldWithPath("[].user.motto").type(JsonFieldType.STRING).description("유저의 좌우명"),
                                fieldWithPath("[].createdAt").type(JsonFieldType.STRING).description("유저 장비 생성 날짜")
                        )));

        // then
        String content = resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        List<UserProductLatestResponseDto> responseDtos = OBJECT_MAPPER.readValue(content, new TypeReference<>() {
        });
        UserProductLatestResponseDto responseDto = responseDtos.get(0);

        assertThat(responseDto)
                .hasFieldOrProperty("id")
                .hasFieldOrPropertyWithValue("createdAt", userProductLatestResponseDto.getCreatedAt());
        assertThat(responseDto.getProduct())
                .containsEntry("type", ((ProductType) userProductLatestResponseDto.getProduct().get("type")).name())
                .containsEntry("imageUrl", userProductLatestResponseDto.getProduct().get("imageUrl"))
                .containsEntry("name", userProductLatestResponseDto.getProduct().get("name"));
        assertThat(responseDto.getUser())
                .containsEntry("imageUrl", userProductLatestResponseDto.getUser().get("imageUrl"))
                .containsEntry("username", userProductLatestResponseDto.getUser().get("username"))
                .containsEntry("motto", userProductLatestResponseDto.getUser().get("motto"));
    }

    private UserProductLatestResponseDto getUserProductLatestResponseDto() {
        return UserProductLatestResponseDto.builder()
                .id(1L)
                .productType(ProductType.MAIN_DEVICE)
                .productName("애플 맥북 프로 13형 2019년형 MUHN2KH/A")
                .productImageUrl("https://shopping-phinf.pstatic.net/main_2048607/20486074380.20191210164911.jpg")
                .username("JunHoPark93")
                .userImageUrl("https://previews.123rf.com/images/aquir/aquir1311/aquir131100316/23569861-%EC%83%98%ED%94%8C-%EC%A7%80-%EB%B9%A8%EA%B0%84%EC%83%89-%EB%9D%BC%EC%9A%B4%EB%93%9C-%EC%8A%A4%ED%83%AC%ED%94%84.jpg")
                .motto("장비충개발자")
                .createdAt(LocalDateTime.of(2014, 4, 1, 0, 0, 0))
                .build();
    }
}