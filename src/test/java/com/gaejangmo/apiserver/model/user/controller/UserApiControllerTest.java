package com.gaejangmo.apiserver.model.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaejangmo.apiserver.model.user.dto.UserResponseDto;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserApiControllerTest {
    private static final String USER_API = linkTo(UserApiController.class).toString();
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    // TODO
    @Ignore
    void 사용자_로그인_시_로그인_정보_반환() throws Exception {
        ResultActions resultActions = mockMvc.perform(get(USER_API + "/logined")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        byte[] contentAsByteArray = resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsByteArray();

        UserResponseDto userResponseDto = MAPPER.readValue(contentAsByteArray, UserResponseDto.class);
    }
}