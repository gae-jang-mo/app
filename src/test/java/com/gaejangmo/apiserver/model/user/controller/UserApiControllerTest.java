package com.gaejangmo.apiserver.model.user.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gaejangmo.apiserver.config.aws.S3Connector;
import com.gaejangmo.apiserver.model.MockMvcTest;
import com.gaejangmo.apiserver.model.common.support.WithMockCustomUser;
import com.gaejangmo.apiserver.model.image.domain.vo.FileFeature;
import com.gaejangmo.apiserver.model.image.dto.FileResponseDto;
import com.gaejangmo.apiserver.model.user.domain.vo.Motto;
import com.gaejangmo.apiserver.model.user.dto.UserIntroduceDto;
import com.gaejangmo.apiserver.model.user.dto.UserResponseDto;
import com.gaejangmo.apiserver.model.user.dto.UserSearchDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static com.gaejangmo.apiserver.model.common.support.ApiDocumentUtils.getDocumentRequest;
import static com.gaejangmo.apiserver.model.common.support.ApiDocumentUtils.getDocumentResponse;
import static com.gaejangmo.apiserver.model.user.testdata.UserTestData.RESPONSE_DTO;
import static com.gaejangmo.apiserver.model.user.testdata.UserTestData.RESPONSE_DTO_NOT_INCLUDE_ISLIKED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserApiControllerTest extends MockMvcTest {
    private static final String USER_API = getApiUrl(UserApiController.class);

    @MockBean
    private S3Connector s3Connector;

    private List<FieldDescriptor> userResponseDtoDescriptors = List.of(
            fieldWithPath("id").type(JsonFieldType.NUMBER).description("식별자"),
            fieldWithPath("oauthId").type(JsonFieldType.NUMBER).description("oauth의 id"),
            fieldWithPath("username").type(JsonFieldType.STRING).description("사용자 이름"),
            fieldWithPath("email").type(JsonFieldType.STRING).description("사용자 이메일"),
            fieldWithPath("motto").type(JsonFieldType.STRING).description("좌우명"),
            fieldWithPath("imageUrl").type(JsonFieldType.STRING).description("프로필 사진"),
            fieldWithPath("isCelebrity").type(JsonFieldType.BOOLEAN).description("셀럽 여부"),
            fieldWithPath("introduce").type(JsonFieldType.STRING).description("소개")
    );

    @Test
    @WithMockCustomUser(id = "3", oauthId = "47378236", username = "kmdngyu", email = "abc2@gmail.com")
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

        assertThat(userResponseDto).isEqualTo(RESPONSE_DTO_NOT_INCLUDE_ISLIKED);
    }

    @Test
    @WithMockCustomUser
    void username으로_유저_정보_받기() throws Exception {
        // given
        List<FieldDescriptor> userAndLikedResponseDtoDescriptors = new ArrayList<>(userResponseDtoDescriptors);
        userAndLikedResponseDtoDescriptors.add(fieldWithPath("isLiked").type(JsonFieldType.BOOLEAN).description("좋아요 여부"));
        userAndLikedResponseDtoDescriptors.add(fieldWithPath("isCelebrity").type(JsonFieldType.BOOLEAN).description("셀럽 여부"));

        // when
        ResultActions resultActions = mockMvc.perform(
                RestDocumentationRequestBuilders.get(USER_API + "/{name}", RESPONSE_DTO.getUsername())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("user/showUserByName",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("name").description("검색하고 싶은 유저의 이름")
                        ),
                        responseFields(userAndLikedResponseDtoDescriptors)
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
    void 로그인된_사용자가_좋아요한_사용자_목록_조회() throws Exception {
        // when
        ResultActions resultActions = mockMvc.perform(get(USER_API + "/likes")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("user/showLikeUser",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("식별자"),
                                fieldWithPath("[].oauthId").type(JsonFieldType.NUMBER).description("oauth의 id"),
                                fieldWithPath("[].username").type(JsonFieldType.STRING).description("사용자 이름"),
                                fieldWithPath("[].email").type(JsonFieldType.STRING).description("사용자 이메일"),
                                fieldWithPath("[].motto").type(JsonFieldType.STRING).description("좌우명"),
                                fieldWithPath("[].imageUrl").type(JsonFieldType.STRING).description("프로필 사진"),
                                fieldWithPath("[].introduce").type(JsonFieldType.STRING).description("소개"),
                                fieldWithPath("[].isLiked").type(JsonFieldType.BOOLEAN).description("좋아요 여부"),
                                fieldWithPath("[].isCelebrity").type(JsonFieldType.BOOLEAN).description("셀럽 여부")
                        )
                ));

        // then
        byte[] contentAsByteArray = resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsByteArray();

        List<UserResponseDto> userResponseDto = OBJECT_MAPPER.readValue(contentAsByteArray, new TypeReference<>() {
        });

        assertThat(userResponseDto).isEqualTo(
                List.of(UserResponseDto.builder()
                        .id(3L)
                        .oauthId(47378236L)
                        .username("kmdngyu")
                        .email("abc2@gmail.com")
                        .motto("폭풍개발자")
                        .imageUrl("https://previews.123rf.com/images/aquir/aquir1311/aquir131100316/23569861-%EC%83%98%ED%94%8C-%EC%A7%80-%EB%B9%A8%EA%B0%84%EC%83%89-%EB%9D%BC%EC%9A%B4%EB%93%9C-%EC%8A%A4%ED%83%AC%ED%94%84.jpg")
                        .introduce("안녕 난 규동")
                        .isLiked(true)
                        .isCelebrity(false)
                        .build()));
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
                        getDocumentRequest(),
                        getDocumentResponse(),
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
    void 자기소개_수정() throws Exception {
        // given
        UserIntroduceDto introduce = new UserIntroduceDto("수정된 자기소개");

        // when
        ResultActions resultActions = mockMvc.perform((put(USER_API + "/introduce"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(introduce)))
                .andDo(print())
                .andDo(document("user/updateIntroduce",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("introduce").type(JsonFieldType.STRING).description("수정하려는 자기소개")
                        ),
                        responseFields(
                                fieldWithPath("introduce").type(JsonFieldType.STRING).description("수정된 자기소개")
                        )
                ));

        // then
        byte[] contentAsByteArray = resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsByteArray();

        UserIntroduceDto actual = OBJECT_MAPPER.readValue(contentAsByteArray, UserIntroduceDto.class);

        assertThat(actual.getIntroduce()).isEqualTo(introduce.getIntroduce());
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
                        getDocumentRequest(),
                        getDocumentResponse(),
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
                                fieldWithPath("[].username").type(JsonFieldType.STRING).description("유저 이름"),
                                fieldWithPath("[].isCelebrity").type(JsonFieldType.BOOLEAN).description("셀럽 여부")
                        )
                ));

        // then
        String contentAsString = resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        List<UserSearchDto> userSearchDtos = OBJECT_MAPPER.readValue(contentAsString, new TypeReference<>() {
        });

        assertThat(userSearchDtos).hasSize(3);
    }

    @Test
    @WithMockCustomUser
    void 랜덤_유저_3명_pick() throws Exception {
        byte[] contentAsByteArray = mockMvc.perform(get(USER_API + "/random")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(document("user/random",
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
                .andReturn().getResponse().getContentAsByteArray();

        List<UserSearchDto> userResponseDtos = OBJECT_MAPPER.readValue(contentAsByteArray, new TypeReference<>() {
        });

        assertThat(userResponseDtos.size()).isEqualTo(3);
    }
}