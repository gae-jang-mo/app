package com.gaejangmo.apiserver.model.product.domain;

import com.gaejangmo.apiserver.model.product.dto.ProductDto;

public class TestData {
    public static final ProductDto TEST_PRODUCT_DTO = new ProductDto();

    static {
        TEST_PRODUCT_DTO.setTitle("애플 맥북 프로 15형 2019년형 MV912KH/A");
        TEST_PRODUCT_DTO.setLink("https://shopping-phinf.pstatic.net/main_2057150/20571500240.20190819112004.jpg");
        TEST_PRODUCT_DTO.setImage("https://shopping-phinf.pstatic.net/main_1980579/19805790169.20190617105809.jpg");
        TEST_PRODUCT_DTO.setLowestPrice(2980720);
        TEST_PRODUCT_DTO.setHighestPrice(4835230);
        TEST_PRODUCT_DTO.setMallName("네이버");
        TEST_PRODUCT_DTO.setProductId(19805790169L);
        TEST_PRODUCT_DTO.setNaverProductType(NaverProductType.find(1));
        TEST_PRODUCT_DTO.setProductType(ProductType.find(1));
    }
}