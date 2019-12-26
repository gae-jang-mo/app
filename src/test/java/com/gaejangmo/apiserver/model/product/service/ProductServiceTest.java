package com.gaejangmo.apiserver.model.product.service;

import com.gaejangmo.apiserver.model.product.domain.Product;
import com.gaejangmo.apiserver.model.product.domain.ProductRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class ProductServiceTest {
    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    void 상품_조회() {
        String productName = "맥북";
        List<Product> expected = List.of(ProductTestData.ENTITY);
        Pageable pageable = PageRequest.of(0, 10);
        given(productRepository.findProductsByProductName(productName, pageable)).willReturn(expected);

        List<ManagedProductResponseDto> fromInternal = productService.findFromInternal(productName, pageable);

        assertNotNull(fromInternal);
        verify(productRepository, times(1)).findProductsByProductName(productName, pageable);
    }

    @Test
    void 상품_저장() {
        given(productRepository.save(any())).willReturn(ProductTestData.ENTITY);

        ManagedProductResponseDto saved = productService.save(ProductTestData.REQUEST_DTO);

        assertThat(saved).isEqualTo(ProductTestData.MANAGED_PRODUCT_RESPONSE_DTO);
        verify(productRepository, times(1)).save(any());
    }
}