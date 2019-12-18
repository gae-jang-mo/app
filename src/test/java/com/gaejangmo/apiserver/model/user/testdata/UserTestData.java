package com.gaejangmo.apiserver.model.user.testdata;

import com.gaejangmo.apiserver.model.common.domain.vo.Link;
import com.gaejangmo.apiserver.model.user.domain.User;
import com.gaejangmo.apiserver.model.user.domain.vo.Email;
import com.gaejangmo.apiserver.model.user.domain.vo.Motto;
import com.gaejangmo.apiserver.model.user.dto.UserResponseDto;
import org.springframework.test.util.ReflectionTestUtils;

public class UserTestData {
    public static final User ENTITY = User.builder()
            .oauthId(20608121L)
            .username("JunHoPark93")
            .email(Email.of("abc@gmail.com"))
            .motto(Motto.of("장비충개발자"))
            .imageUrl(Link.of("https://previews.123rf.com/images/aquir/aquir1311/aquir131100316/23569861-%EC%83%98%ED%94%8C-%EC%A7%80-%EB%B9%A8%EA%B0%84%EC%83%89-%EB%9D%BC%EC%9A%B4%EB%93%9C-%EC%8A%A4%ED%83%AC%ED%94%84.jpg"))
            .introduce("안녕 난 제이")
            .build();

    public static final UserResponseDto RESPONSE_DTO = UserResponseDto.builder()
            .id(1L)
            .oauthId(20608121L)
            .username("JunHoPark93")
            .email("abc@gmail.com")
            .motto("장비충개발자")
            .imageUrl("https://previews.123rf.com/images/aquir/aquir1311/aquir131100316/23569861-%EC%83%98%ED%94%8C-%EC%A7%80-%EB%B9%A8%EA%B0%84%EC%83%89-%EB%9D%BC%EC%9A%B4%EB%93%9C-%EC%8A%A4%ED%83%AC%ED%94%84.jpg")
            .introduce("안녕 난 제이")
            .build();

    static {
        ReflectionTestUtils.setField(UserTestData.ENTITY, "id", 1L);
    }
}
