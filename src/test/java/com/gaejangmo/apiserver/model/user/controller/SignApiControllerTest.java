package com.gaejangmo.apiserver.model.user.controller;

import com.gaejangmo.apiserver.model.MockMvcTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import static com.gaejangmo.apiserver.model.common.support.ApiDocumentUtils.getDocumentRequest;
import static com.gaejangmo.apiserver.model.common.support.ApiDocumentUtils.getDocumentResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SignApiControllerTest extends MockMvcTest {
    private static final String SIGN_API = getApiUrl(SignApiController.class);

    @Test
    void 로그인_하지_않은_상태_확인() throws Exception {
        // given
        ResultActions resultActions = mockMvc.perform(get(SIGN_API + "/login/state")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("sign/checkLogin",
                        getDocumentRequest(),
                        getDocumentResponse()
                ));

        // when
        String result = resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        // then
        assertThat(Boolean.valueOf(result)).isFalse();
    }

    @Test
    @WithMockUser
    void 로그인_성공_상태_확인() throws Exception {
        // given
        ResultActions resultActions = mockMvc.perform(get(SIGN_API + "/login/state")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        // when
        String result = resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        // then
        assertThat(Boolean.valueOf(result)).isTrue();
    }
}