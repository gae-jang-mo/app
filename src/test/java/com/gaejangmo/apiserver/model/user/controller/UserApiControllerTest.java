package com.gaejangmo.apiserver.model.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaejangmo.apiserver.model.image.domain.vo.FileFeature;
import com.gaejangmo.apiserver.model.image.domain.vo.ImageType;
import com.gaejangmo.apiserver.model.image.dto.FileResponseDto;
import com.gaejangmo.apiserver.model.user.dto.UserResponseDto;
import com.gaejangmo.apiserver.model.user.service.UserService;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
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

    @MockBean
    private UserService userService;

    private FileResponseDto fileResponseDto;
    private MockMultipartFile mockMultipartFile;

    @BeforeEach
    void setUp() {
        mockMultipartFile = new MockMultipartFile("user-file", "fileName.jpg", "image/jpeg", "test data".getBytes());

        fileResponseDto = FileResponseDto.builder()
                .fileFeature(FileFeature.builder()
                        .imageType(ImageType.of(mockMultipartFile.getContentType()))
                        .size(mockMultipartFile.getSize())
                        .originalName(mockMultipartFile.getOriginalFilename())
                        .url("dummy")
                        .build())
                .build();
    }

    // TODO
    @Ignore
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
        UserResponseDto userResponseDto = MAPPER.readValue(contentAsByteArray, UserResponseDto.class);
    }

    // TODO: 2019/12/17 이바가 올린 시큐리티 테스트 이용해서 테스트 코드 수정
    @Test
    @WithMockUser
    void 프로필_이미지_수정() throws Exception {
        // given
        when(userService.updateUserImage(mockMultipartFile, eq(anyLong()))).thenReturn(fileResponseDto);

        // when
        ResultActions resultActions = mockMvc.perform(
//                post(USER_API + "/image")
//                        .content(mockMultipartFile.)
                multipart(USER_API + "/image").file(mockMultipartFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(print());

        // then
        byte[] contentAsByteArray = resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsByteArray();

        FileResponseDto response = MAPPER.readValue(contentAsByteArray, FileResponseDto.class);
        FileFeature fileFeature = response.getFileFeature();

        assertThat(fileFeature).isEqualTo(fileResponseDto.getFileFeature());
    }
}