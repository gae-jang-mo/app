package com.gaejangmo.apiserver.model.like.service;

import com.gaejangmo.apiserver.model.like.domain.LikeRepository;
import com.gaejangmo.apiserver.model.like.domain.Likes;
import com.gaejangmo.apiserver.model.user.domain.User;
import com.gaejangmo.apiserver.model.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LikeServiceTest {
    private static final Long SOURCE_ID = 1L;
    private static final Long TARGET_ID = 2L;

    @InjectMocks
    private LikeService likeService;

    @Mock
    private LikeRepository likeRepository;

    @Mock
    private UserService userService;

    @Test
    void 좋아요_저장() {
        // given
        given(userService.findById(anyLong())).willReturn(mock(User.class));
        given(likeRepository.save(any())).willReturn(mock(Likes.class));

        // when & then
        assertDoesNotThrow(() -> likeService.save(SOURCE_ID, TARGET_ID));
    }

    @Test
    void 내가_좋아요를_누른_사람들_조회() {
        // given
        Likes like = Likes.builder()
                .source(mock(User.class))
                .target(mock(User.class))
                .build();

        given(userService.findById(SOURCE_ID)).willReturn(mock(User.class));
        given(likeRepository.findAllBySource(any())).willReturn(List.of(like));

        // when
        List<Likes> likes = likeService.findAllBySource(SOURCE_ID);

        // then
        assertThat(likes).isEqualTo(List.of(like));
        verify(userService, times(1)).findById(SOURCE_ID);
        verify(likeRepository, times(1)).findAllBySource(any());
    }

    @Test
    void 좋아요_취소() {
        // given
        given(userService.findById(anyLong())).willReturn(mock(User.class));
        doNothing().when(likeRepository).deleteBySourceAndTarget(any(), any());

        // when & then
        assertDoesNotThrow(() -> likeService.deleteBySourceAndTarget(SOURCE_ID, TARGET_ID));
        verify(userService, times(2)).findById(anyLong());
        verify(likeRepository, times(1)).deleteBySourceAndTarget(any(), any());
    }
}