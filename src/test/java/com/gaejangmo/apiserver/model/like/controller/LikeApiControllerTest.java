package com.gaejangmo.apiserver.model.like.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gaejangmo.apiserver.model.MockMvcTest;
import com.gaejangmo.apiserver.model.common.support.WithMockCustomUser;
import com.gaejangmo.apiserver.model.user.dto.UserSearchDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static com.gaejangmo.apiserver.model.common.support.ApiDocumentUtils.getDocumentRequest;
import static com.gaejangmo.apiserver.model.common.support.ApiDocumentUtils.getDocumentResponse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LikeApiControllerTest extends MockMvcTest {
    private static final String LIKE_API = getApiUrl(LikeApiController.class);
    private static final long TARGET_ID = 2L;

    @Test
    @WithMockCustomUser
    void 좋아요_저장() throws Exception {
        mockMvc.perform(
                post(LIKE_API + "/{targetId}", TARGET_ID)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("like/save",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("targetId").description("좋아요를 당한 사용자 고유 번호")
                        )))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockCustomUser
    void 좋아요_취소() throws Exception {
        mockMvc.perform(delete(LIKE_API + "/{targetId}", TARGET_ID)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("like/delete",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("targetId").description("좋아요 취소당한 사용자 고유 번호")
                        )))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void 좋아요_랭킹() throws Exception {
        String result = mockMvc.perform(get(LIKE_API + "/ranking")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("like/ranking",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("식별자"),
                                fieldWithPath("[].imageUrl").type(JsonFieldType.STRING).description("프로필 사진"),
                                fieldWithPath("[].username").type(JsonFieldType.STRING).description("유저 이름"),
                                fieldWithPath("[].isCelebrity").type(JsonFieldType.BOOLEAN).description("셀럽 여부")
                        )))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        List<UserSearchDto> response = OBJECT_MAPPER.readValue(result, new TypeReference<>() {
        });

        assertNotNull(response);
    }
}