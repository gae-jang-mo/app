package com.gaejangmo.apiserver.model.userproduct.domain.vo;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CommentTest {

    @Test
    void equals_hashCode_테스트() {
        assertThat(Comment.of("comment")).isEqualTo(Comment.of("comment"));
    }

    @Test
    void null_입력시_공백으로_변환() {
        Comment comment = Comment.of(null);
        assertThat(comment.value()).isEqualTo(Comment.EMPTY_VALUE);
    }
}