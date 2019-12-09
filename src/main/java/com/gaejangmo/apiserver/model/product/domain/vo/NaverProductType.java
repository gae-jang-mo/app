package com.gaejangmo.apiserver.model.product.domain.vo;


import com.gaejangmo.apiserver.model.product.exception.NaverProductTypeNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum NaverProductType {
    // 일반 상품
    GENERAL_PRICE_COMPARISON_PRICE(1),
    GENERAL_PRICE_NON_MATCHING_GENERAL_PRODUCT(2),
    GENERAL_PRICE_COMPARISON_MATCHING_GENERAL_PRODUCT(3),

    // 중고 상품
    USED_PRICE_COMPARISON_PRICE(4),
    USED_PRICE_NON_MATCHING_GENERAL_PRODUCT(5),
    USED_PRICE_COMPARISON_MATCHING_GENERAL_PRODUCT(6),

    // 단종 상품
    DISCONTINUED_PRICE_COMPARISON_PRICE(7),
    DISCONTINUED_PRICE_NON_MATCHING_GENERAL_PRODUCT(8),
    DISCONTINUED_PRICE_COMPARISON_MATCHING_GENERAL_PRODUCT(9),

    // 판매 예정 상품
    SCHEDULED_DISCONTINUED_PRICE_COMPARISON_PRICE(10),
    SCHEDULED_DISCONTINUED_PRICE_NON_MATCHING_GENERAL_PRODUCT(11),
    SCHEDULED_DISCONTINUED_PRICE_COMPARISON_MATCHING_GENERAL_PRODUCT(12);

    private final int value;

    public static NaverProductType find(final int value) {
        return Stream.of(values())
                .filter(naverProductType -> naverProductType.value == value)
                .findAny()
                .orElseThrow(() -> new NaverProductTypeNotFoundException("제품 코드를 찾을 수 없습니다"));
    }
}
