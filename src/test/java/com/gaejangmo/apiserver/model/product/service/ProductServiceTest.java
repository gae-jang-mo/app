package com.gaejangmo.apiserver.model.product.service;

import com.gaejangmo.apiserver.model.product.domain.ProductRepository;
import com.gaejangmo.apiserver.model.product.domain.ProductTestData;
import com.gaejangmo.apiserver.model.product.domain.vo.ProductName;
import com.gaejangmo.apiserver.model.product.dto.ProductDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class ProductServiceTest {
    private static final String TARGET = "애플 맥북 프로 15형 2019년형 MV912KH/A";

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    void 상품_조회() {
        given(productRepository.findByProductName(ProductName.of(TARGET))).willReturn(ProductTestData.ENTITY);

        ProductDto result = productService.findByProductName(TARGET);

        assertThat(result).isEqualTo(ProductTestData.DTO);
    }

    @Test
    void 상품_저장() {
        given(productRepository.save(ProductTestData.ENTITY)).willReturn(ProductTestData.ENTITY);

        ProductDto saved = productService.save(ProductTestData.DTO);

        assertThat(saved).isEqualTo(ProductTestData.DTO);
    }
}