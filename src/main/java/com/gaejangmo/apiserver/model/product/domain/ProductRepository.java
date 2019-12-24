package com.gaejangmo.apiserver.model.product.domain;

import com.gaejangmo.apiserver.model.product.domain.vo.ProductName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByProductName(ProductName name);
}
