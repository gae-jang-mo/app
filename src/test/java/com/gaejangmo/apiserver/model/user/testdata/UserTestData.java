package com.gaejangmo.apiserver.model.user.testdata;

import com.gaejangmo.apiserver.model.common.domain.vo.Link;
import com.gaejangmo.apiserver.model.user.domain.User;
import com.gaejangmo.apiserver.model.user.domain.vo.Email;
import com.gaejangmo.apiserver.model.user.domain.vo.Grade;
import com.gaejangmo.apiserver.model.user.domain.vo.Motto;
import com.gaejangmo.apiserver.model.user.dto.UserResponseDto;
import org.springframework.test.util.ReflectionTestUtils;

public class UserTestData {
    public static final User ENTITY_GENERAL = User.builder()
            .oauthId(20608121L)
            .username("JunHoPark93")
            .email(Email.of("abc@gmail.com"))
            .motto(Motto.of("장비충개발자"))
            .imageUrl(Link.of("https://previews.123rf.com/images/aquir/aquir1311/aquir131100316/23569861-%EC%83%98%ED%94%8C-%EC%A7%80-%EB%B9%A8%EA%B0%84%EC%83%89-%EB%9D%BC%EC%9A%B4%EB%93%9C-%EC%8A%A4%ED%83%AC%ED%94%84.jpg"))
            .introduce("안녕 난 제이")
            .grade(Grade.GENERAL)
            .build();

    public static final User ENTITY_CELEBRITY = User.builder()
            .oauthId(206081L)
            .username("JunHoPark94")
            .email(Email.of("abcd@gmail.com"))
            .motto(Motto.of("비타민충개발자"))
            .imageUrl(Link.of("https://previews.123rf.com/images/aquir/aquir1311/aquir131100316/23569861-%EC%83%98%ED%94%8C-%EC%A7%80-%EB%B9%A8%EA%B0%84%EC%83%89-%EB%9D%BC%EC%9A%B4%EB%93%9C-%EC%8A%A4%ED%83%AC%ED%94%84.jpg"))
            .introduce("안녕 난 제이, 인간 비타민이지")
            .grade(Grade.CELEBRITY)
            .build();

    public static final User ENTITY_NOT_INCLUDE_ISLIKED = User.builder()
            .oauthId(47378236L)
            .username("kmdngyu")
            .email(Email.of("abc2@gmail.com"))
            .motto(Motto.of("폭풍개발자"))
            .imageUrl(Link.of("https://previews.123rf.com/images/aquir/aquir1311/aquir131100316/23569861-%EC%83%98%ED%94%8C-%EC%A7%80-%EB%B9%A8%EA%B0%84%EC%83%89-%EB%9D%BC%EC%9A%B4%EB%93%9C-%EC%8A%A4%ED%83%AC%ED%94%84.jpg"))
            .introduce("안녕 난 규동")
            .grade(Grade.GENERAL)
            .build();

    public static final UserResponseDto RESPONSE_DTO = UserResponseDto.builder()
            .id(1L)
            .oauthId(20608121L)
            .username("JunHoPark93")
            .email("abc@gmail.com")
            .motto("장비충개발자")
            .imageUrl("https://previews.123rf.com/images/aquir/aquir1311/aquir131100316/23569861-%EC%83%98%ED%94%8C-%EC%A7%80-%EB%B9%A8%EA%B0%84%EC%83%89-%EB%9D%BC%EC%9A%B4%EB%93%9C-%EC%8A%A4%ED%83%AC%ED%94%84.jpg")
            .introduce("안녕 난 제이")
            .isLiked(false)
            .isCelebrity(false)
            .build();

    public static final UserResponseDto RESPONSE_DTO_NOT_INCLUDE_ISLIKED = UserResponseDto.builder()
            .id(3L)
            .oauthId(47378236L)
            .username("kmdngyu")
            .email("abc2@gmail.com")
            .motto("폭풍개발자")
            .imageUrl("https://previews.123rf.com/images/aquir/aquir1311/aquir131100316/23569861-%EC%83%98%ED%94%8C-%EC%A7%80-%EB%B9%A8%EA%B0%84%EC%83%89-%EB%9D%BC%EC%9A%B4%EB%93%9C-%EC%8A%A4%ED%83%AC%ED%94%84.jpg")
            .introduce("안녕 난 규동")
            .isCelebrity(false)
            .build();


    static {
        ReflectionTestUtils.setField(UserTestData.ENTITY_GENERAL, "id", 1L);
        ReflectionTestUtils.setField(UserTestData.ENTITY_NOT_INCLUDE_ISLIKED, "id", 3L);
    }
}
