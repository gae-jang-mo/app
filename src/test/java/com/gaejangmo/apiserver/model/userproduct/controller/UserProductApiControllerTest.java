package com.gaejangmo.apiserver.model.userproduct.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaejangmo.apiserver.model.userproduct.domain.vo.ProductType;
import com.gaejangmo.apiserver.model.userproduct.service.UserProductService;
import com.gaejangmo.apiserver.model.userproduct.service.dto.UserProductCreateDto;
import com.gaejangmo.apiserver.model.userproduct.service.dto.UserProductResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserProductApiControllerTest {
    private static final String USER_PRODUCT_URI = linkTo(UserProductApiController.class).toString();

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserProductService userProductService;
    @Autowired
    private ObjectMapper objectMapper;

    private UserProductCreateDto userProductCreateDto;
    private UserProductResponseDto userProductResponseDto;

    @BeforeEach
    void setUp() {
        userProductCreateDto = UserProductCreateDto.builder()
                .productId(1L)
                .productType(ProductType.KEY_BOARD)
                .comment("인생 마우스")
                .build();

        userProductResponseDto = UserProductResponseDto.builder()
                .id(1L)
                .comment("userProductResponseDto comment")
                .build();
    }

    @Test
    @WithMockUser
    void 장비_등록() throws Exception {
        // TODO: 2019/12/10 추후 userId 1L 대신 UserDetail 정보에서  id 가져와서 넣어주기 (Mock 사용)
        when(userProductService.save(userProductCreateDto, 1L)).thenReturn(userProductResponseDto);

        // when
        ResultActions resultActions = mockMvc.perform(post(USER_PRODUCT_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userProductCreateDto)))
                .andDo(print());

        // then
        resultActions.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").value(userProductResponseDto.getId()))
                .andExpect(jsonPath("comment").value(userProductResponseDto.getComment()));
    }

    @Test
    @WithAnonymousUser
    void 비로그인_장비_등록시도_Unauthorized() throws Exception {
        // when
        ResultActions resultActions = mockMvc.perform(post(USER_PRODUCT_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userProductCreateDto)))
                .andDo(print());

        // then
        resultActions.andExpect(status().isUnauthorized());
    }

    @Test
    @WithAnonymousUser
    void 리스트_조회() throws Exception {
        // given
        List<UserProductResponseDto> expected = List.of(userProductResponseDto);
        when(userProductService.findByUserId(1L)).thenReturn(expected);

        // when
        ResultActions resultActions = mockMvc.perform(get(USER_PRODUCT_URI + "/1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].comment").value(userProductResponseDto.getComment()))
                .andExpect(jsonPath("$[0].id").value(userProductResponseDto.getId()));
    }

    @Test
    @WithMockUser
    void 장비_삭제() throws Exception {
        // given
        when(userProductService.delete(1L, 1L)).thenReturn(true);
        // when
        ResultActions resultActions = mockMvc.perform(delete(USER_PRODUCT_URI + "/1"))
                .andDo(print());

        // then
        resultActions.andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void 장비_comment_수정() throws Exception {
        // given
        String comment = "updatedComment";
        UserProductResponseDto userProductResponseDto = UserProductResponseDto.builder().comment(comment).build();
        when(userProductService.updateComment(1L, 1L, comment)).thenReturn(userProductResponseDto);

        // when
        ResultActions resultActions = mockMvc.perform(put(USER_PRODUCT_URI + "/1/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(comment))
                .andDo(print());

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("comment").value(comment));
    }

    @Test
    @WithAnonymousUser
    void 비로그인_장비_Comment_수정_시도_Unauthorized() throws Exception {
        // given
        String comment = "updatedComment";

        // when
        ResultActions resultActions = mockMvc.perform(put(USER_PRODUCT_URI + "/1/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(comment))
                .andDo(print());

        // then
        resultActions.andExpect(status().isUnauthorized());
    }

}