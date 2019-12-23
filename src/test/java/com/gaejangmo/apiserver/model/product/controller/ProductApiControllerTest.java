package com.gaejangmo.apiserver.model.product.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gaejangmo.apiserver.model.MockMvcTest;
import com.gaejangmo.apiserver.model.product.dto.ProductResponseDto;
import com.gaejangmo.apiserver.model.product.testdata.ProductTestData;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.gaejangmo.apiserver.model.common.support.ApiDocumentUtils.getDocumentRequest;
import static com.gaejangmo.apiserver.model.common.support.ApiDocumentUtils.getDocumentResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProductApiControllerTest extends MockMvcTest {
    private static final String PRODUCT_API = getApiUrl(ProductApiController.class);

    @Test
    @WithMockUser
    void 장비_저장_url_불일치() throws Exception {
        mockMvc.perform(post(PRODUCT_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(ProductTestData.INVALID_LINK_REQUEST_DTO)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void 장비_저장_가격_불일치() throws Exception {
        mockMvc.perform(post(PRODUCT_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(ProductTestData.INVALID_PRICE_REQUEST_DTO)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void 장비_저장_enum_불일치() throws Exception {
        mockMvc.perform(post(PRODUCT_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(ProductTestData.INVALID_PRODUCT_TYPE_REQUEST_DTO)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void DB에_존재하는_장비_조회() throws Exception {
        mockMvc.perform(post(PRODUCT_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(ProductTestData.REQUEST_DTO)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("product/save",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제품 이름"),
                                fieldWithPath("link").type(JsonFieldType.STRING).description("제품 판매 경로"),
                                fieldWithPath("image").type(JsonFieldType.STRING).description("제품 사진"),
                                fieldWithPath("lowestPrice").type(JsonFieldType.NUMBER).description("제품 최저 가격"),
                                fieldWithPath("highestPrice").type(JsonFieldType.NUMBER).description("제품 최고 가격"),
                                fieldWithPath("mallName").type(JsonFieldType.STRING).description("제품 판매처"),
                                fieldWithPath("productId").type(JsonFieldType.NUMBER).description("제품 외부 고유 번호"),
                                fieldWithPath("naverProductType").type(JsonFieldType.STRING).description("네이버 기준 제품 종류"),
                                fieldWithPath("productType").type(JsonFieldType.STRING).description("제품 종류")
                        )));

        ResultActions resultActions = mockMvc.perform(get(PRODUCT_API + "/internal")
                .param("productName", "애플 맥북 프로 15형 2019년형 MV912KH/A")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("product/findFromInternalResource",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("productName").description("제품 이름")
                        ),
                        responseFields(
                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("제품 고유 번호"),
                                fieldWithPath("[].productName").type(JsonFieldType.STRING).description("제품 이름"),
                                fieldWithPath("[].buyUrl").type(JsonFieldType.STRING).description("제품 구매 경로"),
                                fieldWithPath("[].imageUrl").type(JsonFieldType.STRING).description("제품 사진"),
                                fieldWithPath("[].lowestPrice").type(JsonFieldType.NUMBER).description("제품 최저 가격"),
                                fieldWithPath("[].highestPrice").type(JsonFieldType.NUMBER).description("제품 최고 가격"),
                                fieldWithPath("[].mallName").type(JsonFieldType.STRING).description("제품 판매처"),
                                fieldWithPath("[].productId").type(JsonFieldType.NUMBER).description("제품 외부 고유 번호"),
                                fieldWithPath("[].naverProductType").type(JsonFieldType.STRING).description("네이버 기준 제품 종류"),
                                fieldWithPath("[].productType").type(JsonFieldType.STRING).description("제품 종류"))
                ));

        byte[] contentAsByteArray = resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsByteArray();

        List<ProductResponseDto> managedProductResponseDtos = OBJECT_MAPPER.readValue(contentAsByteArray, new TypeReference<List<ProductResponseDto>>() {
        });

        assertThat(managedProductResponseDtos.size()).isEqualTo(1);
    }

    @Ignore
    void 장비_조회_외부_api_호출() throws Exception {
        // DB에 없는 장비를 조회한다.
        ResultActions resultActions = mockMvc.perform(get(PRODUCT_API + "/external")
                .param("productName", "애플 맥북 프로 15형 2019년형 MV912KH/A")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        byte[] contentAsByteArray = resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsByteArray();

        List<ProductResponseDto> naverProductResponseDtos =
                OBJECT_MAPPER.readValue(contentAsByteArray, new TypeReference<List<ProductResponseDto>>() {
                });

        assertThat(naverProductResponseDtos).isNotNull();
    }
}