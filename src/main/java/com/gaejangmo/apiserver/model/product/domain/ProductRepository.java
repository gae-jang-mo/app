package com.gaejangmo.apiserver.model.product.domain;

import com.gaejangmo.apiserver.model.product.domain.vo.ProductName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    //Product findByProductName(ProductName name);

    List<Product> findByProductName(ProductName name);
}
