package com.gaejangmo.apiserver.model.like.domain;

import com.gaejangmo.apiserver.model.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Likes, Long> {
    List<Likes> findAllBySource(final User source);

    Optional<Likes> findBySourceAndTarget(final User source, final User target);

    void deleteBySourceAndTarget(final User source, final User target);
}
