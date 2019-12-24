package com.gaejangmo.apiserver.model.like.service;

import com.gaejangmo.apiserver.config.oauth.SecurityUser;
import com.gaejangmo.apiserver.model.like.domain.LikeRepository;
import com.gaejangmo.apiserver.model.like.domain.Likes;
import com.gaejangmo.apiserver.model.like.exception.InvalidMySelfLikeException;
import com.gaejangmo.apiserver.model.user.domain.User;
import com.gaejangmo.apiserver.model.user.domain.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;

    public LikeService(final LikeRepository likeRepository, final UserRepository userRepository) {
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
    }

    public void save(final Long sourceId, final Long targetId) {
        if (sourceId.equals(targetId)) {
            throw new InvalidMySelfLikeException("나 자신을 좋아요할 수 없습니다.");
        }

        User source = findById(sourceId);
        User target = findById(targetId);

        Likes like = Likes.builder()
                .source(source)
                .target(target)
                .build();

        likeRepository.save(like);
    }

    @Transactional(readOnly = true)
    public List<Likes> findAllBySource(final Long sourceId) {
        User source = findById(sourceId);

        return likeRepository.findAllBySource(source);
    }

    public void deleteBySourceAndTarget(final Long sourceId, final Long targetId) {
        User source = findById(sourceId);
        User target = findById(targetId);

        likeRepository.deleteBySourceAndTarget(source, target);
    }

    @Transactional(readOnly = true)
    public boolean isLiked(final SecurityUser loginUser, final Long targetId) {
        if (Objects.isNull(loginUser)) {
            return false;
        }

        User source = findById(loginUser.getId());
        User target = findById(targetId);

        Optional<Likes> like = likeRepository.findBySourceAndTarget(source, target);

        return like.isPresent();
    }

    private User findById(final Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당하는 유저가 없습니다."));
    }
}
