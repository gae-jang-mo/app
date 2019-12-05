package com.gaejangmo.apiserver.model.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaejangmo.apiserver.model.product.domain.ProductTestData;
import com.gaejangmo.apiserver.model.product.dto.ProductResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductApiControllerTest {
    // TODO: 12/5/19 product_api 안가져와짐
    //private static final String PRODUCT_API = linkTo(ProductApiController.class).toString();

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void 장비_저장_url_불일치() throws Exception {
        mockMvc.perform(post("/api/v1/products")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(MAPPER.writeValueAsString(ProductTestData.INVALID_LINK_REQUEST_DTO)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void 장비_저장_가격_불일치() throws Exception {
        mockMvc.perform(post("/api/v1/products")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(MAPPER.writeValueAsString(ProductTestData.INVALID_PRICE_REQUEST_DTO)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void 장비_저장_enum_불일치() throws Exception {
        mockMvc.perform(post("/api/v1/products")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(MAPPER.writeValueAsString(ProductTestData.INVALID_PRODUCT_TYPE_REQUEST_DTO)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void 장비_조회() throws Exception {
        mockMvc.perform(post("/api/v1/products")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(MAPPER.writeValueAsString(ProductTestData.REQUEST_DTO)))
                .andDo(print())
                .andExpect(status().isCreated());

        ResultActions resultActions = mockMvc.perform(get("/api/v1/products")
                .param("productName", "애플 맥북 프로 15형 2019년형 MV912KH/A")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        byte[] contentAsByteArray = resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsByteArray();

        ProductResponseDto productResponseDto = MAPPER.readValue(contentAsByteArray, ProductResponseDto.class);

        assertThat(productResponseDto).isEqualTo(ProductTestData.RESPONSE_DTO);
    }
}