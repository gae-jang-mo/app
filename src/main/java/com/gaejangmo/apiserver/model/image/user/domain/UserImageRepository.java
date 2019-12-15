package com.gaejangmo.apiserver.model.image.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserImageRepository extends JpaRepository<UserImage, Long> {
}
