package com.gaejangmo.apiserver.model.user.service;

import com.gaejangmo.apiserver.model.like.domain.Likes;
import com.gaejangmo.apiserver.model.like.service.LikeService;
import com.gaejangmo.apiserver.model.user.domain.User;
import com.gaejangmo.apiserver.model.user.domain.UserRepository;
import com.gaejangmo.apiserver.model.user.domain.vo.Motto;
import com.gaejangmo.apiserver.model.user.dto.UserResponseDto;
import com.gaejangmo.apiserver.model.user.testdata.UserTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    private static final long USER_ID = 100L;
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private LikeService likeService;

    @Test
    void OauthId_유저_조회() {
        given(userRepository.findByOauthId(anyLong())).willReturn(Optional.of(UserTestData.ENTITY));

        UserResponseDto result = userService.findUserResponseDtoByOauthId(1234L);

        assertThat(result).isEqualTo(UserTestData.RESPONSE_DTO);
    }

    @Test
    void username_유저_조회(){
        given(userRepository.findByUsername(anyString())).willReturn(Optional.of(UserTestData.ENTITY));

        User user = UserTestData.ENTITY;
        UserResponseDto result = userService.findUserResponseDtoByName(user.getUsername());

        assertThat(result).isEqualTo(UserTestData.RESPONSE_DTO);
    }

    @Test
    void 모토_업데이트() {
        //given
        Motto updatedMotto = Motto.of("updated");
        given(userRepository.findById(USER_ID)).willReturn(Optional.of(UserTestData.ENTITY));

        //when
        UserResponseDto actual = userService.updateMotto(USER_ID, updatedMotto);

        //then
        assertThat(actual.getMotto()).isEqualTo(updatedMotto.value());
        verify(userRepository,times(1)).findById(USER_ID);
    }

    @Test
    void 내가_좋아요를_누른_사람들을_조회() {
        // given
        Likes like = Likes.builder()
                .source(mock(User.class))
                .target(UserTestData.ENTITY)
                .build();

        given(likeService.findAllBySource(anyLong())).willReturn(List.of(like));

        // when
        List<UserResponseDto> actual = userService.findUserResponseDtoBySourceId(1L);

        // then
        assertThat(actual).isEqualTo(List.of(UserTestData.RESPONSE_DTO));
        verify(likeService, times(1)).findAllBySource(1L);
    }
}