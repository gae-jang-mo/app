package com.gaejangmo.apiserver.model.user.domain;

import com.gaejangmo.apiserver.model.common.domain.vo.Link;
import com.gaejangmo.apiserver.model.image.domain.vo.FileFeature;
import com.gaejangmo.apiserver.model.image.domain.vo.ImageType;
import com.gaejangmo.apiserver.model.image.user.domain.UserImage;
import com.gaejangmo.apiserver.model.user.domain.vo.Email;
import com.gaejangmo.apiserver.model.user.domain.vo.Motto;
import com.gaejangmo.apiserver.model.user.domain.vo.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {
    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .role(Role.USER)
                .username("이바")
                .email(Email.of("lgi@lgi.com"))
                .imageUrl(Link.of("https://avatars2.githubusercontent.com/oa/1180268?s=240&u=ee5333200f2cb95b6f738bd4957371da26675e67&v=4"))
                .introduce("하이염")
                .motto(Motto.of("우아한"))
                .build();
    }

    @Test
    void 사용자_초기화() {
        assertThat(user.getRole()).isEqualTo(Role.USER);
        assertThat(user.getUsername()).isEqualTo("이바");
        assertThat(user.getEmail()).isEqualTo("lgi@lgi.com");
        assertThat(user.getImageUrl()).isEqualTo("https://avatars2.githubusercontent.com/oa/1180268?s=240&u=ee5333200f2cb95b6f738bd4957371da26675e67&v=4");
        assertThat(user.getIntroduce()).isEqualTo("하이염");
        assertThat(user.getMotto()).isEqualTo("우아한");
    }

    @Test
    void id_매칭_테스트() {
        long id = 1L;
        ReflectionTestUtils.setField(user, "id", id);

        assertThat(user.matchId(id)).isTrue();
        assertThat(user.matchId(2L)).isFalse();
    }

    @Test
    @DisplayName("getImageUrl() 했을 때 UserImage가 null이면 imageUrl을 반환해야 한다.")
    void getImageUrl01() {
        user.updateUserImage(null);
        String imageUrl = user.getImageUrl();

        assertThat(imageUrl).isNotNull();
    }

    @Test
    @DisplayName("getImageUrl() 했을 때 UserImage가 null이 아니면 UserImage를 반환해야 한다.")
    void getImageUrl02() {
        // given
        UserImage userImage = UserImage.builder()
                .fileFeature(
                        FileFeature.builder().url("url")
                                .savedName("savedName")
                                .originalName("originalName")
                                .size(11)
                                .imageType(ImageType.of("jpg"))
                                .build())
                .build();

        String expected = userImage.getFileFeature().getUrl();

        // when
        user.updateUserImage(userImage);
        String actual = user.getImageUrl();

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 자기소개_수정() {
        // given
        String updatedIntroduce = "하이염2";

        // when
        User updatedUser = user.updateIntroduce(updatedIntroduce);

        // then
        assertThat(updatedUser.getIntroduce()).isEqualTo(updatedIntroduce);
    }
}