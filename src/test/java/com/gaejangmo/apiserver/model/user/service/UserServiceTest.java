package com.gaejangmo.apiserver.model.user.service;

import com.gaejangmo.apiserver.model.user.domain.UserRepository;
import com.gaejangmo.apiserver.model.user.dto.UserResponseDto;
import com.gaejangmo.apiserver.model.user.testdata.UserTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    private static final long USER_ID = 100L;
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @Test
    void 유저_조회() {
        given(userRepository.findByOauthId(anyLong())).willReturn(Optional.of(UserTestData.ENTITY));

        UserResponseDto result = userService.findUserResponseDtoByOauthId(1234L);

        assertThat(result).isEqualTo(UserTestData.RESPONSE_DTO);
    }

    @Test
    void 모토_업데이트() {
        given(userRepository.findById(USER_ID)).willReturn(Optional.of(UserTestData.ENTITY));
        when()
    }
}