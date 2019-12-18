package com.gaejangmo.apiserver.model.userproduct.domain;

import com.gaejangmo.apiserver.model.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserProductRepository extends JpaRepository<UserProduct, Long> {
    List<UserProduct> findByUser(User user);

}
