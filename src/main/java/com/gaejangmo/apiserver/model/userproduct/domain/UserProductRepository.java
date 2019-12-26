package com.gaejangmo.apiserver.model.userproduct.domain;

import com.gaejangmo.apiserver.model.user.domain.User;
import com.gaejangmo.apiserver.model.userproduct.domain.vo.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserProductRepository extends JpaRepository<UserProduct, Long> {
    List<UserProduct> findByUser(User user);

    Page<UserProduct> findAllByStatusNotAndUser_Id(Status status, long userId, Pageable pageable);
}
