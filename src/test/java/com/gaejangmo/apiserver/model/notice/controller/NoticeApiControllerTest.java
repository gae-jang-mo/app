package com.gaejangmo.apiserver.model.notice.controller;

import com.gaejangmo.apiserver.model.MockMvcTest;
import com.gaejangmo.apiserver.model.notice.dto.NoticeResponseDto;
import com.gaejangmo.apiserver.model.notice.testdata.NoticeTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static com.gaejangmo.apiserver.model.common.support.ApiDocumentUtils.getDocumentRequest;
import static com.gaejangmo.apiserver.model.common.support.ApiDocumentUtils.getDocumentResponse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class NoticeApiControllerTest extends MockMvcTest {
    private static final String NOTICE_API = getApiUrl(NoticeApiController.class);

    @BeforeEach
    void setUp() throws Exception {
        mockMvc.perform(post(NOTICE_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(NoticeTestData.NOTICE_REQUEST_DTO)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("notice/save",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("noticeType").type(JsonFieldType.STRING).description("공지 타입"),
                                fieldWithPath("header").type(JsonFieldType.STRING).description("공지 헤더"),
                                fieldWithPath("contents").type(JsonFieldType.STRING).description("공지 본문")
                        )));
    }

    @Test
    void 공지사항_조회() throws Exception {
        ResultActions resultActions = mockMvc.perform(
                RestDocumentationRequestBuilders.get(NOTICE_API + "/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        byte[] contentAsByteArray = resultActions.andExpect(status().isOk())
                .andDo(document("notice",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("공지 고유 번호")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("공지 고유 번호"),
                                fieldWithPath("noticeType").type(JsonFieldType.STRING).description("공지 타입"),
                                fieldWithPath("header").type(JsonFieldType.STRING).description("공지 헤더"),
                                fieldWithPath("contents").type(JsonFieldType.STRING).description("공지 본문")
                        )))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsByteArray();

        NoticeResponseDto noticeResponseDto = OBJECT_MAPPER.readValue(contentAsByteArray, NoticeResponseDto.class);

        assertNotNull(noticeResponseDto);
    }
}