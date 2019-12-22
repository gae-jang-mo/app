package com.gaejangmo.apiserver.model.like.service;

import com.gaejangmo.apiserver.config.oauth.SecurityUser;
import com.gaejangmo.apiserver.model.like.domain.LikeRepository;
import com.gaejangmo.apiserver.model.like.domain.Likes;
import com.gaejangmo.apiserver.model.user.domain.User;
import com.gaejangmo.apiserver.model.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
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

    @Transactional(readOnly = true)
    public List<Likes> findAllBySource(final Long sourceId) {
        User source = userService.findById(sourceId);

        return likeRepository.findAllBySource(source);
    }

    public void deleteBySourceAndTarget(final Long sourceId, final Long targetId) {
        User source = userService.findById(sourceId);
        User target = userService.findById(targetId);

        likeRepository.deleteBySourceAndTarget(source, target);
    }

    public boolean isLiked(final SecurityUser loginUser, final Long targetId) {
        if (Objects.isNull(loginUser)) {
            return false;
        }

        User source = userService.findById(loginUser.getId());
        User target = userService.findById(targetId);

        Optional<Likes> like = likeRepository.findBySourceAndTarget(source, target);

        return like.isPresent();
    }
}
