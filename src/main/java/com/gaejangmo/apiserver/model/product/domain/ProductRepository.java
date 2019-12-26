package com.gaejangmo.apiserver.model.product.domain;

import com.gaejangmo.apiserver.model.product.domain.vo.ProductName;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByProductName(ProductName name);

    @Query("SELECT p FROM Product p WHERE p.productName.value LIKE %:name%")
    List<Product> findProductsByProductName(@Param("name") String name, Pageable pageable);
}
