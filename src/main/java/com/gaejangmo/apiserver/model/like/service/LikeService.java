package com.gaejangmo.apiserver.model.like.service;

import com.gaejangmo.apiserver.model.like.domain.LikeRepository;
import com.gaejangmo.apiserver.model.like.domain.Likes;
import com.gaejangmo.apiserver.model.like.exception.InvalidMySelfLikeException;
import com.gaejangmo.apiserver.model.user.domain.User;
import com.gaejangmo.apiserver.model.user.domain.UserRepository;
import com.gaejangmo.apiserver.model.user.dto.UserSearchDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.gaejangmo.apiserver.model.common.resolver.SecurityUserArgumentResolver.NOT_EXISTED_ID;

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
        checkMySelf(sourceId, targetId);

        User source = findById(sourceId);
        User target = findById(targetId);

        Likes like = Likes.builder()
                .source(source)
                .target(target)
                .build();

        likeRepository.save(like);
    }

    private void checkMySelf(final Long sourceId, final Long targetId) {
        if (sourceId.equals(targetId)) {
            throw new InvalidMySelfLikeException("나 자신을 좋아요할 수 없습니다.");
        }
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
    public boolean isLiked(final Long sourceId, final Long targetId) {
        if (NOT_EXISTED_ID.equals(sourceId) || NOT_EXISTED_ID.equals(targetId)) {
            return false;
        }

        User source = findById(sourceId);
        User target = findById(targetId);
        Optional<Likes> like = likeRepository.findBySourceAndTarget(source, target);

        return like.isPresent();
    }

    private User findById(final Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당하는 유저가 없습니다."));
    }

    @Transactional(readOnly = true)
    public List<UserSearchDto> findRanking(final Pageable pageable) {
        return likeRepository.findUserRanking(pageable).stream()
                .map(this::toUserSearchDto)
                .collect(Collectors.toList());
    }

    public int countLikeByTarget(final User user) {
        return likeRepository.countLikesByTarget(user);
    }

    private UserSearchDto toUserSearchDto(final User user) {
        return UserSearchDto.builder()
                .id(user.getId())
                .imageUrl(user.getImageUrl())
                .username(user.getUsername())
                .isCelebrity(user.isCelebrity())
                .build();
    }
}
