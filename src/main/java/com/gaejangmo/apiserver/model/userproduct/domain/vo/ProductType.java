package com.gaejangmo.apiserver.model.userproduct.domain.vo;

import com.gaejangmo.apiserver.model.userproduct.domain.exception.ProductTypeNotFoundException;

import java.util.stream.Stream;

public enum ProductType {
    MAIN_DEVICE(1),
    MOUSE(2),
    KEY_BOARD(3),
    MONITOR(4),
    SUPPLEMENT(5),
    ETC(99);

    private final int value;

    ProductType(final int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    public static ProductType find(final int value) {
        return Stream.of(values()).filter(productType -> productType.value == value)
                .findAny()
                .orElseThrow(() -> new ProductTypeNotFoundException("장비 코드를 찾을 수 없습니다"));
    }
}