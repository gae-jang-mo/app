package com.gaejangmo.apiserver.model.user.controller;

import com.gaejangmo.apiserver.model.MockMvcTest;
import com.gaejangmo.apiserver.model.common.support.WithMockCustomUser;
import com.gaejangmo.apiserver.model.user.dto.UserResponseDto;
import com.gaejangmo.apiserver.model.user.testdata.UserTestData;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserApiControllerTest extends MockMvcTest {
    private static final String USER_API = getApiUrl(UserApiController.class);

    @Test
    @WithMockCustomUser
    void 사용자_로그인_시_로그인_정보_반환() throws Exception {
        // given
        ResultActions resultActions = mockMvc.perform(get(USER_API + "/logined")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        // when
        byte[] contentAsByteArray = resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsByteArray();

        // then
        UserResponseDto userResponseDto = OBJECT_MAPPER.readValue(contentAsByteArray, UserResponseDto.class);

        assertThat(userResponseDto).isEqualTo(UserTestData.RESPONSE_DTO);
    }
}