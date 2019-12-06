package com.gaejangmo.apiserver.model.user.domain;

import com.gaejangmo.apiserver.model.common.domain.vo.Link;
import com.gaejangmo.apiserver.model.user.domain.vo.Motto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    void 사용자_초기화() {
        User user = User.builder()
                .githubId(123L)
                .username("이바")
                .imageUrl(Link.of("https://avatars2.githubusercontent.com/oa/1180268?s=240&u=ee5333200f2cb95b6f738bd4957371da26675e67&v=4"))
                .introduce("하이염")
                .motto(Motto.of("우아한"))
                .build();

        assertThat(user.getGithubId()).isEqualTo(123L);
        assertThat(user.getUsername()).isEqualTo("이바");
        assertThat(user.getImageUrl()).isEqualTo("https://avatars2.githubusercontent.com/oa/1180268?s=240&u=ee5333200f2cb95b6f738bd4957371da26675e67&v=4");
        assertThat(user.getIntroduce()).isEqualTo("하이염");
        assertThat(user.getMotto()).isEqualTo("우아한");
    }
}