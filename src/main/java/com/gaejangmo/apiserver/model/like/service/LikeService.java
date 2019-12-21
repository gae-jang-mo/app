package com.gaejangmo.apiserver.model.like.service;

import com.gaejangmo.apiserver.model.like.domain.LikeRepository;
import com.gaejangmo.apiserver.model.like.domain.Likes;
import com.gaejangmo.apiserver.model.user.domain.User;
import com.gaejangmo.apiserver.model.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserService userService;

    public LikeService(final LikeRepository likeRepository, final UserService userService) {
        this.likeRepository = likeRepository;
        this.userService = userService;
    }

    public void save(final Long sourceId, final Long targetId) {
        User source = userService.findById(sourceId);
        User target = userService.findById(targetId);

        Likes like = Likes.builder()
                .source(source)
                .target(target)
                .build();

        likeRepository.save(like);
    }

    public List<Likes> findAllBySource(final Long sourceId) {
        User source = userService.findById(sourceId);
        return likeRepository.findAllBySource(source);
    }
}
