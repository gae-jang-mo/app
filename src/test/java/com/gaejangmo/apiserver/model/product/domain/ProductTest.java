package com.gaejangmo.apiserver.model.product.domain;

import com.gaejangmo.apiserver.model.common.domain.vo.Link;
import com.gaejangmo.apiserver.model.product.domain.vo.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {

    @Test
    void 상품_초기화() {
        Product product = Product.builder()
                .productName(ProductName.of("애플 맥북 에어 13형 2019년형 MVFK2KH/A"))
                .buyUrl(Link.of("https://search.shopping.naver.com/gate.nhn?id=20571500240"))
                .imageUrl(Link.of("https://shopping-phinf.pstatic.net/main_2057150/20571500240.20190819112004.jpg"))
                .lowestPrice(Price.of(1231010))
                .highestPrice(Price.of(2735120))
                .mallName(MallName.of("네이버"))
                .productId(ProductId.of(20571500240L))
                .naverProductType(NaverProductType.find(1))
                .productType(ProductType.find(1))
                .build();

        assertThat(product.getProductName()).isEqualTo(ProductName.of("애플 맥북 에어 13형 2019년형 MVFK2KH/A").value());
        assertThat(product.getBuyUrl()).isEqualTo(Link.of("https://search.shopping.naver.com/gate.nhn?id=20571500240").value());
        assertThat(product.getImageUrl()).isEqualTo(Link.of("https://shopping-phinf.pstatic.net/main_2057150/20571500240.20190819112004.jpg").value());
        assertThat(product.getLowestPrice()).isEqualTo(Price.of(1231010).value());
        assertThat(product.getHighestPrice()).isEqualTo(Price.of(2735120).value());
        assertThat(product.getMallName()).isEqualTo(MallName.of("네이버").value());
        assertThat(product.getProductId()).isEqualTo(ProductId.of(20571500240L).value());
        assertThat(product.getNaverProductType()).isEqualTo(NaverProductType.find(1));
        assertThat(product.getProductType()).isEqualTo(ProductType.find(1));
    }
}