package com.gaejangmo.apiserver.model.product.testdata;

import com.gaejangmo.apiserver.model.common.domain.vo.Link;
import com.gaejangmo.apiserver.model.product.domain.Product;
import com.gaejangmo.apiserver.model.product.domain.vo.*;
import com.gaejangmo.apiserver.model.product.dto.ManagedProductResponseDto;
import com.gaejangmo.apiserver.model.product.dto.NaverProductResponseDto;
import com.gaejangmo.apiserver.model.product.dto.ProductRequestDto;
import org.springframework.test.util.ReflectionTestUtils;

public class ProductTestData {
    public static final ProductRequestDto REQUEST_DTO = ProductRequestDto.builder()
            .title("애플 맥북 프로 15형 2019년형 MV912KH/A")
            .link("https://shopping-phinf.pstatic.net/main_2057150/20571500240.20190819112004.jpg")
            .image("https://shopping-phinf.pstatic.net/main_1980579/19805790169.20190617105809.jpg")
            .lowestPrice(2980720L)
            .highestPrice(4835230L)
            .mallName("네이버")
            .productId(19805790169L)
            .naverProductType(NaverProductType.find(1).toString())
            .build();
    public static final ProductRequestDto REQUEST_DTO2 = ProductRequestDto.builder()
            .title("애플 맥북 프로 15형 2019년형 MV913KH/A")
            .link("https://shopping-phinf.pstatic.net/main_2057150/20571500240.20190819112004.jpg")
            .image("https://shopping-phinf.pstatic.net/main_1980579/19805790169.20190617105809.jpg")
            .lowestPrice(2980720L)
            .highestPrice(4835230L)
            .mallName("네이버")
            .productId(19805790160L)
            .naverProductType(NaverProductType.find(1).toString())
            .build();
    public static final ProductRequestDto INVALID_LINK_REQUEST_DTO = ProductRequestDto.builder()
            .title("애플 맥북 프로 15형 2019년형 MV912KH/A")
            .link("wrong")
            .image("https://shopping-phinf.pstatic.net/main_1980579/19805790169.20190617105809.jpg")
            .lowestPrice(2980720L)
            .highestPrice(4835230L)
            .mallName("네이버")
            .productId(19805790169L)
            .naverProductType(NaverProductType.find(1).toString())
            .build();
    public static final ProductRequestDto INVALID_PRICE_REQUEST_DTO = ProductRequestDto.builder()
            .title("애플 맥북 프로 15형 2019년형 MV912KH/A")
            .link("wrong")
            .image("https://shopping-phinf.pstatic.net/main_1980579/19805790169.20190617105809.jpg")
            .lowestPrice(-100L)
            .highestPrice(4835230L)
            .mallName("네이버")
            .productId(19805790169L)
            .naverProductType(NaverProductType.find(1).toString())
            .build();
    public static final ProductRequestDto INVALID_PRODUCT_TYPE_REQUEST_DTO = ProductRequestDto.builder()
            .title("애플 맥북 프로 15형 2019년형 MV912KH/A")
            .link("wrong")
            .image("https://shopping-phinf.pstatic.net/main_1980579/19805790169.20190617105809.jpg")
            .lowestPrice(-100L)
            .highestPrice(4835230L)
            .mallName("네이버")
            .productId(19805790169L)
            .naverProductType("wrong params")
            .build();
    public static final NaverProductResponseDto NAVER_PRODUCT_RESPONSE_DTO = NaverProductResponseDto.builder()
            .productName("애플 맥북 프로 15형 2019년형 MV912KH/A")
            .buyUrl("https://shopping-phinf.pstatic.net/main_2057150/20571500240.20190819112004.jpg")
            .imageUrl("https://shopping-phinf.pstatic.net/main_1980579/19805790169.20190617105809.jpg")
            .lowestPrice(2980720)
            .highestPrice(4835230)
            .mallName("네이버")
            .productId(19805790169L)
            .naverProductType(NaverProductType.find(1))
            .build();

    public static final ManagedProductResponseDto MANAGED_PRODUCT_RESPONSE_DTO = ManagedProductResponseDto.builder()
            .id(1L)
            .productName("애플 맥북 프로 15형 2019년형 MV912KH/A")
            .buyUrl("https://shopping-phinf.pstatic.net/main_2057150/20571500240.20190819112004.jpg")
            .imageUrl("https://shopping-phinf.pstatic.net/main_1980579/19805790169.20190617105809.jpg")
            .lowestPrice(2980720)
            .highestPrice(4835230)
            .mallName("네이버")
            .productId(19805790169L)
            .naverProductType(NaverProductType.find(1))
            .build();

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
        ReflectionTestUtils.setField(ProductTestData.ENTITY, "id", 1L);
    }
}