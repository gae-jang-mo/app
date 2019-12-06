package com.gaejangmo.apiserver.model.product.service;

import com.gaejangmo.apiserver.model.product.domain.ProductRepository;
import com.gaejangmo.apiserver.model.product.testdata.ProductTestData;
import com.gaejangmo.apiserver.model.product.domain.vo.ProductName;
import com.gaejangmo.apiserver.model.product.dto.ProductResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
class ProductServiceTest {
    private static final String PRODUCT_NAME = "애플 맥북 프로 15형 2019년형 MV912KH/A";

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    void 상품_조회() {
        given(productRepository.findByProductName(ProductName.of(PRODUCT_NAME))).willReturn(ProductTestData.ENTITY);

        ProductResponseDto result = productService.findByProductName(PRODUCT_NAME);

        assertThat(result).isEqualTo(ProductTestData.RESPONSE_DTO);
    }

    @Test
    void 상품_저장() {
        given(productRepository.save(ProductTestData.ENTITY)).willReturn(ProductTestData.ENTITY);

        ProductResponseDto saved = productService.save(ProductTestData.REQUEST_DTO);

        assertThat(saved).isEqualTo(ProductTestData.RESPONSE_DTO);
    }
}