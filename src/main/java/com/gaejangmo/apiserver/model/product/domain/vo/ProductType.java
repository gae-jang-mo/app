package com.gaejangmo.apiserver.model.product.domain.vo;

import com.gaejangmo.apiserver.model.product.exception.ProductTypeNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum ProductType {
    MAIN_DEVICE(1, "메인 디바이스"),
    MOUSE(2, "마우스"),
    KEY_BOARD(3, "키보드"),
    MONITOR(4, "모니터"),
    SUPPLEMENT(5, "영양제"),
    ETC(99, "기타");

    private final int value;
    private final String name;

    public static ProductType find(final int value) {
        return Stream.of(values()).filter(productType -> productType.value == value)
                .findAny()
                .orElseThrow(() -> new ProductTypeNotFoundException("장비 코드를 찾을 수 없습니다"));
    }
}
