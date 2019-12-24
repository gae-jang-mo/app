package com.gaejangmo.apiserver.model.like.controller;

import com.gaejangmo.apiserver.model.MockMvcTest;
import com.gaejangmo.apiserver.model.common.support.WithMockCustomUser;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static com.gaejangmo.apiserver.model.common.support.ApiDocumentUtils.getDocumentRequest;
import static com.gaejangmo.apiserver.model.common.support.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LikeControllerTest extends MockMvcTest {
    private static final String LIKE_API = getApiUrl(LikeController.class);
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
}