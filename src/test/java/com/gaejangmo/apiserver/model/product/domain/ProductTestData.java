package com.gaejangmo.apiserver.model.product.domain;

import com.gaejangmo.apiserver.model.product.domain.vo.*;
import com.gaejangmo.apiserver.model.product.dto.ProductDto;

public class ProductTestData {
    public static final ProductDto DTO = new ProductDto();
    public static final Product ENTITY = Product.builder()
            .productName(ProductName.of("애플 맥북 프로 15형 2019년형 MV912KH/A"))
            .buyUrl(Link.of("https://shopping-phinf.pstatic.net/main_2057150/20571500240.20190819112004.jpg"))
            .imageUrl(Link.of("https://shopping-phinf.pstatic.net/main_1980579/19805790169.20190617105809.jpg"))
            .lowestPrice(Price.of(2980720))
            .highestPrice(Price.of(4835230))
            .mallName(MallName.of("네이버"))
            .productId(ProductId.of(19805790169L))
            .naverProductType(NaverProductType.find(1))
            .productType(ProductType.find(1))
            .build();

    static {
        DTO.setTitle("애플 맥북 프로 15형 2019년형 MV912KH/A");
        DTO.setLink("https://shopping-phinf.pstatic.net/main_2057150/20571500240.20190819112004.jpg");
        DTO.setImage("https://shopping-phinf.pstatic.net/main_1980579/19805790169.20190617105809.jpg");
        DTO.setLowestPrice(2980720);
        DTO.setHighestPrice(4835230);
        DTO.setMallName("네이버");
        DTO.setProductId(19805790169L);
        DTO.setNaverProductType(NaverProductType.find(1));
        DTO.setProductType(ProductType.find(1));
    }
}