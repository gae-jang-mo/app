package com.gaejangmo.apiserver.model.user.service;

import com.gaejangmo.apiserver.model.user.domain.User;
import com.gaejangmo.apiserver.model.user.domain.UserRepository;
import com.gaejangmo.apiserver.model.user.domain.vo.Motto;
import com.gaejangmo.apiserver.model.user.dto.UserResponseDto;
import com.gaejangmo.apiserver.model.user.dto.UserSearchDto;
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

    @Test
    void OauthId_유저_조회() {
        given(userRepository.findByOauthId(anyLong())).willReturn(Optional.of(UserTestData.ENTITY));

        UserResponseDto result = userService.findUserResponseDtoByOauthId(1234L);

        assertThat(result).isEqualTo(UserTestData.RESPONSE_DTO);
    }

    @Test
    void username_유저_조회() {
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
        verify(userRepository, times(1)).findById(USER_ID);
    }

    @Test
    void username으로_list_검색() {
        // given
        String username = "username";
        List<User> users = List.of(UserTestData.ENTITY);
        List<UserSearchDto> expected = List.of(new UserSearchDto( UserTestData.ENTITY.getId(), UserTestData.ENTITY.getImageUrl(), UserTestData.ENTITY.getUsername()));
        when(userRepository.findAllByUsernameContainingIgnoreCase(username)).thenReturn(users);

        // when
        List<UserSearchDto> actual = userService.findUserSearchDtosByUserName(username);

        // then
        assertThat(actual).isEqualTo(expected);

    }
}