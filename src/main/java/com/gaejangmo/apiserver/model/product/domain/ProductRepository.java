package com.gaejangmo.apiserver.model.product.domain;

import com.gaejangmo.apiserver.model.product.domain.vo.ProductName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByProductName(ProductName name);
}
