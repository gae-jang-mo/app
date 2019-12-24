package com.gaejangmo.apiserver.model.userproduct.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gaejangmo.apiserver.model.MockMvcTest;
import com.gaejangmo.apiserver.model.common.support.WithMockCustomUser;
import com.gaejangmo.apiserver.model.userproduct.domain.vo.Comment;
import com.gaejangmo.apiserver.model.userproduct.domain.vo.ProductType;
import com.gaejangmo.apiserver.model.userproduct.dto.CommentDto;
import com.gaejangmo.apiserver.model.userproduct.dto.ProductTypeDto;
import com.gaejangmo.apiserver.model.userproduct.service.dto.*;
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

import static com.gaejangmo.apiserver.model.common.support.ApiDocumentUtils.getDocumentRequest;
import static com.gaejangmo.apiserver.model.common.support.ApiDocumentUtils.getDocumentResponse;
import static com.gaejangmo.apiserver.model.product.testdata.ProductTestData.REQUEST_DTO2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserProductApiControllerTest extends MockMvcTest {
    private static final String USER_PRODUCT_URI = getApiUrl(UserProductApiController.class);
    private static final long PRODUCT_ID = 1L;

    @Test
    @WithMockCustomUser
    void 내부_장비_UserProduct_등록() throws Exception {
        // given
        UserProductRequestDto userProductRequestDto = new UserProductRequestDto(ProductType.MOUSE, "댓글");
        UserProductInternalRequestDto userProductInternalRequestDto = new UserProductInternalRequestDto(userProductRequestDto, PRODUCT_ID);

        // when
        ResultActions resultActions = mockMvc.perform(post(USER_PRODUCT_URI + "/products/internal")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(userProductInternalRequestDto)))
                .andDo(print())
                .andDo(document("userproduct/createFromInternal",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("productId").type(JsonFieldType.NUMBER).description("Product의 식별자"),
                                fieldWithPath("userProductRequestDto.productType").type(JsonFieldType.STRING).description("장비의 타입"),
                                fieldWithPath("userProductRequestDto.comment").type(JsonFieldType.STRING).description("제품에 대한 코멘트")
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
        resultActions.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").value(7L))
                .andExpect(jsonPath("comment").value(userProductRequestDto.getComment()));
    }

    @Test
    @WithMockCustomUser
    void 외부_장비_UserProduct_등록() throws Exception {
        // given
        UserProductRequestDto userProductRequestDto = new UserProductRequestDto(ProductType.MOUSE, "댓글");
        UserProductExternalRequestDto userProductExternalRequestDto = new UserProductExternalRequestDto(userProductRequestDto, REQUEST_DTO2);

        // when
        ResultActions resultActions = mockMvc.perform(post(USER_PRODUCT_URI + "/products/external")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(userProductExternalRequestDto)))
                .andDo(print())
                .andDo(document("userproduct/createFromExternal",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("userProductRequestDto.productType").type(JsonFieldType.STRING).description("장비의 타입"),
                                fieldWithPath("userProductRequestDto.comment").type(JsonFieldType.STRING).description("제품에 대한 코멘트"),
                                fieldWithPath("productRequestDto.title").type(JsonFieldType.STRING).description("제품의 이름"),
                                fieldWithPath("productRequestDto.link").type(JsonFieldType.STRING).description("제품의 링크"),
                                fieldWithPath("productRequestDto.image").type(JsonFieldType.STRING).description("제품의 이미지 링크"),
                                fieldWithPath("productRequestDto.lowestPrice").type(JsonFieldType.NUMBER).description("제품의 최저값"),
                                fieldWithPath("productRequestDto.highestPrice").type(JsonFieldType.NUMBER).description("제품의 최고값"),
                                fieldWithPath("productRequestDto.mallName").type(JsonFieldType.STRING).description("제품 판매 상점 이름"),
                                fieldWithPath("productRequestDto.productId").type(JsonFieldType.NUMBER).description("제품의 식별자"),
                                fieldWithPath("productRequestDto.naverProductType").type(JsonFieldType.STRING).description("네이버에 등록된 제품 타입")
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
        assertThat(responseDto.getProductType()).isEqualTo(userProductExternalRequestDto.getUserProductRequestDto().getProductType().getName());
        assertThat(responseDto.getComment()).isEqualTo(userProductExternalRequestDto.getUserProductRequestDto().getComment());
    }


    @Test
    @WithAnonymousUser
    void 비로그인_장비_등록시도_Unauthorized() throws Exception {
        // given
        UserProductRequestDto userProductRequestDto = new UserProductRequestDto(ProductType.MOUSE, "댓글");
        UserProductInternalRequestDto userProductInternalRequestDto = new UserProductInternalRequestDto(userProductRequestDto, PRODUCT_ID);

        // when
        ResultActions resultActions = mockMvc.perform(post(USER_PRODUCT_URI + "/products/internal")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(userProductInternalRequestDto)))
                .andDo(print());

        // then
        resultActions.andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockCustomUser
    void 유저_ID로_소유장비_리스트_조회() throws Exception {
        // given
        UserProductResponseDto userProductResponseDto = getUserProductResponseDto();

        // when
        ResultActions resultActions = mockMvc.perform(
                RestDocumentationRequestBuilders.get(USER_PRODUCT_URI + "/{userId}/products", 2L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("userproduct/list",
                        getDocumentRequest(),
                        getDocumentResponse(),
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
                .andExpect(jsonPath("$[0].comment").value("ㅎㅎ장비좋아요ㅋㅋㅋ"))
                .andExpect(jsonPath("$[0].id").value(3));
    }

    @Test
    @WithMockCustomUser
    void 장비_삭제() throws Exception {
        mockMvc.perform(
                RestDocumentationRequestBuilders.delete(USER_PRODUCT_URI + "/products/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("userproduct/delete",
                        pathParameters(parameterWithName("id").description("삭제할 유저 장비의 식별자")))
                )
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockCustomUser
    void 장비_comment_수정() throws Exception {
        // given
        CommentDto commentDto = new CommentDto("짜잔");

        // when
        ResultActions resultActions = mockMvc.perform(
                RestDocumentationRequestBuilders.put(USER_PRODUCT_URI + "/products/{id}/comment", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(commentDto)))
                .andDo(print())
                .andDo(document("userproduct/updateComment",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(parameterWithName("id").description("상품평을 수정할 유저 장비의 식별자")),
                        requestFields(fieldWithPath("comment").type(JsonFieldType.STRING).description("수정할 유저 장비 상품평의 내용")),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("UserProduct의 식별자"),
                                fieldWithPath("productId").type(JsonFieldType.NUMBER).description("Product의 식별자"),
                                fieldWithPath("productType").type(JsonFieldType.STRING).description("장비에 대한 코멘트"),
                                fieldWithPath("comment").type(JsonFieldType.STRING).description("장비의 타입"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("유저 장비 생성 날짜"),
                                fieldWithPath("imageUrl").type(JsonFieldType.STRING).description("유저 장비의 사진"))
                        )
                );

        // then
        String content = resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        UserProductResponseDto responseDto = OBJECT_MAPPER.readValue(content, UserProductResponseDto.class);

        assertThat(responseDto)
                .hasFieldOrProperty("id")
                .hasFieldOrPropertyWithValue("comment", commentDto.getComment());
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
    @WithAnonymousUser
    void 비로그인_장비_Comment_수정_시도_Unauthorized() throws Exception {
        // given
        Comment comment = Comment.of("updatedComment");

        // when
        ResultActions resultActions = mockMvc.perform(put(USER_PRODUCT_URI + "/1/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(comment)))
                .andDo(print());

        // then
        resultActions.andExpect(status().isUnauthorized());
    }

    @Test
    @WithAnonymousUser
    void 비로그인_장비_ProductType_수정_시도_Unauthorized() throws Exception {
        // given
        ProductType productType = ProductType.MAIN_DEVICE;

        // when
        ResultActions resultActions = mockMvc.perform(put(USER_PRODUCT_URI + "/1/product-type")
                .accept(MediaType.APPLICATION_JSON)
                .content(productType.getName()))
                .andDo(print());

        // then
        resultActions.andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockCustomUser
    void 장비_ProductType_수정() throws Exception {
        // given
        ProductTypeDto productTypeDto = new ProductTypeDto("키보드");

        // when
        ResultActions resultActions = mockMvc.perform(
                RestDocumentationRequestBuilders.put(USER_PRODUCT_URI + "/products/{id}/product-type", 1L)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(productTypeDto)))
                .andDo(print())
                .andDo(document("userproduct/updateProductType",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(parameterWithName("id").description("상품평을 수정할 유저 장비의 식별자")),
                        requestFields(fieldWithPath("productType").type(JsonFieldType.STRING).description("수정할 유저 장비 상품평의 내용")),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("UserProduct의 식별자"),
                                fieldWithPath("productId").type(JsonFieldType.NUMBER).description("Product의 식별자"),
                                fieldWithPath("productType").type(JsonFieldType.STRING).description("장비에 대한 코멘트"),
                                fieldWithPath("comment").type(JsonFieldType.STRING).description("장비의 타입"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("유저 장비 생성 날짜"),
                                fieldWithPath("imageUrl").type(JsonFieldType.STRING).description("유저 장비의 사진"))
                        )
                );

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("productType").value(productTypeDto.getProductType()));
    }

    @Test
    @WithMockCustomUser
    void 가장_최근에_등록된_장비_검색() throws Exception {
        // given
        Pageable pageable = PageRequest.of(0, 20);
        UserProductLatestResponseDto userProductLatestResponseDto = getUserProductLatestResponseDto();

        // when
        ResultActions resultActions = mockMvc.perform(
                RestDocumentationRequestBuilders.get(USER_PRODUCT_URI + "/products/latest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(pageable)))
                .andDo(print())
                .andDo(document("userproduct/latest",
                        getDocumentRequest(),
                        getDocumentResponse(),
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
                                fieldWithPath("[].user.isLiked").type(JsonFieldType.BOOLEAN).description("해당 유저가 장비에 대해 좋아요를 눌렀는지에 대한 여부"),
                                fieldWithPath("[].createdAt").type(JsonFieldType.STRING).description("유저 장비 생성 날짜")
                        )));

        // then
        String content = resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        List<UserProductLatestResponseDto> responseDtos = OBJECT_MAPPER.readValue(content, new TypeReference<>() {
        });
        UserProductLatestResponseDto responseDto = responseDtos.get(0);

        assertThat(responseDto).hasFieldOrProperty("id");
        assertThat(responseDto.getProduct())
                .containsEntry("type", "MOUSE")
                .containsEntry("imageUrl", "https://shopping-phinf.pstatic.net/main_2051460/20514607838.20190806111610.jpg")
                .containsEntry("name", "애플 맥북 에어 13형 2019년형 MVFH2KH/A");
        assertThat(responseDto.getUser())
                .containsEntry("imageUrl", "https://previews.123rf.com/images/aquir/aquir1311/aquir131100316/23569861-%EC%83%98%ED%94%8C-%EC%A7%80-%EB%B9%A8%EA%B0%84%EC%83%89-%EB%9D%BC%EC%9A%B4%EB%93%9C-%EC%8A%A4%ED%83%AC%ED%94%84.jpg")
                .containsEntry("username", "JunHoPark93")
                .containsEntry("motto", "장비충개발자")
                .containsEntry("isLiked", false);
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