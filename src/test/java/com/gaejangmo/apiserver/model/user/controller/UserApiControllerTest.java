package com.gaejangmo.apiserver.model.user.controller;

import com.gaejangmo.apiserver.config.aws.S3Connector;
import com.gaejangmo.apiserver.model.MockMvcTest;
import com.gaejangmo.apiserver.model.common.support.WithMockCustomUser;
import com.gaejangmo.apiserver.model.image.domain.vo.FileFeature;
import com.gaejangmo.apiserver.model.image.dto.FileResponseDto;
import com.gaejangmo.apiserver.model.user.domain.vo.Motto;
import com.gaejangmo.apiserver.model.user.dto.UserResponseDto;
import com.gaejangmo.apiserver.model.user.dto.UserSearchDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.gaejangmo.apiserver.model.common.support.ApiDocumentUtils.getDocumentRequest;
import static com.gaejangmo.apiserver.model.common.support.ApiDocumentUtils.getDocumentResponse;
import static com.gaejangmo.apiserver.model.user.testdata.UserTestData.RESPONSE_DTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserApiControllerTest extends MockMvcTest {
    private static final String USER_API = getApiUrl(UserApiController.class);

    @MockBean
    private S3Connector s3Connector;

    private FieldDescriptor[] userResponseDtoDescriptors = {fieldWithPath("id").type(JsonFieldType.NUMBER).description("식별자"),
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
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(userResponseDtoDescriptors)
                ));

        // then
        byte[] contentAsByteArray = resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsByteArray();

        UserResponseDto userResponseDto = OBJECT_MAPPER.readValue(contentAsByteArray, UserResponseDto.class);

        assertThat(userResponseDto.getId()).isEqualTo(RESPONSE_DTO.getId());
    }

    @Test
    @WithAnonymousUser
    void username으로_유저_정보_받기() throws Exception {
        // when
        ResultActions resultActions = mockMvc.perform(
                RestDocumentationRequestBuilders.get("/api/v1/users/{name}", RESPONSE_DTO.getUsername())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("user/showUserByName",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("name").description("검색하고 싶은 유저의 이름")
                        ),
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

    @Test
    @WithMockCustomUser
    void UserImage_수정() throws Exception {
        // given
        MockMultipartFile file = new MockMultipartFile("file", "fileName.jpg", "image/jpeg", "image".getBytes());
        String expectedUrl = "expected url";
        when(s3Connector.upload(eq(file), anyString())).thenReturn(expectedUrl);

        // when
        ResultActions resultActions = mockMvc.perform(multipart(USER_API + "/image")
                .file(file)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("user/updateUserImage",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParts(
                                partWithName("file").description("수정하려는 이미지 파일")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("식별자"),
                                fieldWithPath("fileFeature.url").type(JsonFieldType.STRING).description("이미지를 호출할 수 있는 url"),
                                fieldWithPath("fileFeature.imageType").type(JsonFieldType.STRING).description("이미지의 확장자"),
                                fieldWithPath("fileFeature.originalName").type(JsonFieldType.STRING).description("파일의 원래 이름"),
                                fieldWithPath("fileFeature.savedName").type(JsonFieldType.STRING).description("저장된 이름"),
                                fieldWithPath("fileFeature.size").type(JsonFieldType.NUMBER).description("파일 크기"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성 날짜")
                        )
                ));

        // then
        byte[] contentAsByteArray = resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsByteArray();

        FileResponseDto fileResponseDto = OBJECT_MAPPER.readValue(contentAsByteArray, FileResponseDto.class);
        FileFeature fileFeature = fileResponseDto.getFileFeature();

        assertThat(fileFeature.getSize()).isEqualTo(file.getSize());
        assertThat(fileFeature.getImageType()).isEqualTo(file.getContentType());
        assertThat(fileFeature.getOriginalName()).isEqualTo(file.getOriginalFilename());
        assertThat(fileFeature.getUrl()).isEqualTo(expectedUrl);
        assertThat(fileFeature.getSavedName()).isNotBlank();
    }

    @Test
    @WithMockCustomUser
    void 이름으로_유저_리스트_조회() throws Exception {
        // when
        ResultActions resultActions = mockMvc.perform(get(USER_API + "/search")
                .param("username", "junho")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("user/search",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("username").description("검색하고 싶은 유저 이름")
                        ),
                        responseFields(
                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("식별자"),
                                fieldWithPath("[].imageUrl").type(JsonFieldType.STRING).description("프로필 사진"),
                                fieldWithPath("[].username").type(JsonFieldType.STRING).description("유저 이름")
                        )
                ));

        // then
        String contentAsString = resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        List<UserSearchDto> userSearchDtos = OBJECT_MAPPER.readValue(contentAsString, List.class);
        assertThat(userSearchDtos).hasSize(3);
    }
}