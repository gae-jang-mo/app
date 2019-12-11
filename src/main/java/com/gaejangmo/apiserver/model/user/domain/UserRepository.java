package com.gaejangmo.apiserver.model.user.domain;

import com.gaejangmo.apiserver.model.user.domain.vo.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(Email email);
}
