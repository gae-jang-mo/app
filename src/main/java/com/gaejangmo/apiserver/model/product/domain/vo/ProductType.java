package com.gaejangmo.apiserver.model.product.domain.vo;

import com.gaejangmo.apiserver.model.product.exception.ProductTypeNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum ProductType {
    MAIN_DEVICE(1),
    MOUSE(2),
    KEY_BOARD(3),
    MONITOR(4),
    SUPPLEMENT(5),
    ETC(99);

    private final int value;

    public static ProductType find(final int value) {
        return Stream.of(values()).filter(productType -> productType.value == value)
                .findAny()
                .orElseThrow(() -> new ProductTypeNotFoundException("장비 코드를 찾을 수 없습니다"));
    }
}
