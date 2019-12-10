package com.gaejangmo.apiserver.model.userproduct.domain.vo;

import com.gaejangmo.apiserver.model.userproduct.domain.exception.ProductTypeNotFoundException;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum ProductType {
    MAIN_DEVICE("컴퓨터", 1),
    MOUSE("마우스", 2),
    KEY_BOARD("키보드", 3),
    MONITOR("모니터", 4),
    SUPPLEMENT("영양제", 5),
    ETC("기타", 99);

    private final String name;
    private final int code;

    ProductType(final String name, final int code) {
        this.name = name;
        this.code = code;
    }

    public static ProductType ofCode(final int code) {
        return Stream.of(values()).filter(productType -> productType.code == code)
                .findAny()
                .orElseThrow(() -> new ProductTypeNotFoundException("등록되지 않은 코드입니다."));
    }
}