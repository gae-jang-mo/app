package com.gaejangmo.apiserver.model.user.controller;

import com.gaejangmo.apiserver.model.MockMvcTest;
import com.gaejangmo.apiserver.model.common.support.WithMockCustomUser;
import com.gaejangmo.apiserver.model.user.domain.vo.Motto;
import com.gaejangmo.apiserver.model.user.dto.UserResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.ResultActions;

import static com.gaejangmo.apiserver.model.user.testdata.UserTestData.RESPONSE_DTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserApiControllerTest extends MockMvcTest {
    private static final String USER_API = getApiUrl(UserApiController.class);

    FieldDescriptor[] userResponseDtoDescriptors = {fieldWithPath("id").type(JsonFieldType.NUMBER).description("식별자"),
            fieldWithPath("oauthId").type(JsonFieldType.NUMBER).description("oauth의 id"),
            fieldWithPath("username").type(JsonFieldType.STRING).description("사용자 이름"),
            fieldWithPath("email").type(JsonFieldType.STRING).description("사용자 이메일"),
            fieldWithPath("motto").type(JsonFieldType.STRING).description("좌우명"),
            fieldWithPath("imageUrl").type(JsonFieldType.STRING).description("프로필 사진"),
            fieldWithPath("introduce").type(JsonFieldType.STRING).description("소개")};


    @Test
    @WithMockCustomUser
    void 사용자_로그인_시_정보_반환() throws Exception {
        // when
        ResultActions resultActions = mockMvc.perform(get(USER_API + "/logined")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("user/showUser",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(userResponseDtoDescriptors)
                ));

        // then
        byte[] contentAsByteArray = resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsByteArray();

        UserResponseDto userResponseDto = OBJECT_MAPPER.readValue(contentAsByteArray, UserResponseDto.class);

        assertThat(userResponseDto).isEqualTo(RESPONSE_DTO);
    }

    @Test
    @WithAnonymousUser
    void username으로_유저_정보_받기() throws Exception {
        // when
        ResultActions resultActions = mockMvc.perform(get(USER_API + "/{name}", RESPONSE_DTO.getUsername())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("user/showUserByName",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(userResponseDtoDescriptors)
                ));

        // then
        byte[] contentAsByteArray = resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsByteArray();

        UserResponseDto userResponseDto = OBJECT_MAPPER.readValue(contentAsByteArray, UserResponseDto.class);

        assertThat(userResponseDto).isEqualTo(RESPONSE_DTO);
    }

    @Test
    @WithMockCustomUser
    void Motto_수정() throws Exception {
        // given
        Motto motto = Motto.of("game");

        // when
        ResultActions resultActions = mockMvc.perform(put(USER_API + "/motto")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(motto)))
                .andDo(print())
                .andDo(document("user/updateMotto",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("value").type(JsonFieldType.STRING).description("수정하려는 좌우명")
                        ),
                        responseFields(
                                fieldWithPath("value").type(JsonFieldType.STRING).description("좌우명")
                        )
                ));

        // then
        byte[] contentAsByteArray = resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsByteArray();

        Motto actual = OBJECT_MAPPER.readValue(contentAsByteArray, Motto.class);

        assertThat(actual).isEqualTo(motto);
    }

}