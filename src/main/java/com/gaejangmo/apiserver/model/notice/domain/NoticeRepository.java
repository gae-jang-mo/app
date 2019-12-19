package com.gaejangmo.apiserver.model.notice.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    Optional<Notice> findByCreatedDate(String createdDate);
}
