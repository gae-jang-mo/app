package com.gaejangmo.apiserver.model.like.domain;

import com.gaejangmo.apiserver.model.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Likes, Long> {
    List<Likes> findAllBySource(final User source);

    Optional<Likes> findBySourceAndTarget(final User source, final User target);

    void deleteBySourceAndTarget(final User source, final User target);

    @Query(value =
            "SELECT " +
                    "    likes.target, COUNT(likes.target) AS cnt " +
                    "FROM " +
                    "    Likes likes " +
                    "GROUP BY " +
                    "    likes.target " +
                    "ORDER BY cnt DESC ")
    List<User> findUserRanking(final Pageable pageable);
}
