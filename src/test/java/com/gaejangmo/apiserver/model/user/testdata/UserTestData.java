package com.gaejangmo.apiserver.model.user.testdata;

import com.gaejangmo.apiserver.model.common.domain.vo.Link;
import com.gaejangmo.apiserver.model.product.testdata.ProductTestData;
import com.gaejangmo.apiserver.model.user.domain.User;
import com.gaejangmo.apiserver.model.user.domain.vo.Email;
import com.gaejangmo.apiserver.model.user.domain.vo.Motto;
import com.gaejangmo.apiserver.model.user.dto.UserResponseDto;
import org.springframework.test.util.ReflectionTestUtils;

public class UserTestData {
    public static final User ENTITY = User.builder()
            .username("username")
            .email(Email.of("email@email.com"))
            .motto(Motto.of("motto"))
            .imageUrl(Link.of("http://image.url"))
            .introduce("introduce")
            .build();

    public static final UserResponseDto RESPONSE_DTO = UserResponseDto.builder()
            .id(1L)
            .username("username")
            .email("email@email.com")
            .motto("motto")
            .imageUrl("http://image.url")
            .introduce("introduce")
            .build();

    static {
        ReflectionTestUtils.setField(UserTestData.ENTITY, "id", 1L);
    }
}
