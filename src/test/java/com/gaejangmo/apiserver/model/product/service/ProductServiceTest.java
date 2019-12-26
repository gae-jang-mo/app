package com.gaejangmo.apiserver.model.product.service;

import com.gaejangmo.apiserver.model.product.domain.ProductRepository;
import com.gaejangmo.apiserver.model.product.domain.vo.ProductName;
import com.gaejangmo.apiserver.model.product.dto.ManagedProductResponseDto;
import com.gaejangmo.apiserver.model.product.testdata.ProductTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
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
        given(productRepository.findByProductName(ProductName.of(PRODUCT_NAME))).willReturn(Optional.of(ProductTestData.ENTITY));

        Pageable pageable = PageRequest.of(0, 10);
        List<ManagedProductResponseDto> fromInternal = productService.findFromInternal(PRODUCT_NAME, pageable);

        assertNotNull(fromInternal);
    }

    @Test
    void 상품_저장() {
        given(productRepository.save(any())).willReturn(ProductTestData.ENTITY);

        ManagedProductResponseDto saved = productService.save(ProductTestData.REQUEST_DTO);

        assertThat(saved).isEqualTo(ProductTestData.MANAGED_PRODUCT_RESPONSE_DTO);
    }
}