package com.gaejangmo.apiserver.model.like.domain;

import com.gaejangmo.apiserver.model.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

class LikesTest {
    private Likes like;

    @BeforeEach
    void setUp() {
        User source = mock(User.class);
        User target = mock(User.class);

        like = Likes.builder()
                .source(source)
                .target(target)
                .build();
    }

    @Test
    void 좋아요_동등성_테스트() {
        assertThat(like).isEqualTo(
                Likes.builder()
                        .source(mock(User.class))
                        .target(mock(User.class))
                        .build());
    }

    @Test
    void 도메인_생성_오류_확인() {
        assertDoesNotThrow(() ->
                Likes.builder()
                        .source(mock(User.class))
                        .target(mock(User.class))
                        .build());
    }
}
