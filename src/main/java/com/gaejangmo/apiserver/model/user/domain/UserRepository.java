package com.gaejangmo.apiserver.model.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByOauthId(Long oauthId);
    Optional<User> findByUsername(String username);

    @Query("SELECT MAX(id) FROM User")
    long getMaxId();
}
