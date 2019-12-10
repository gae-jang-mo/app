package com.gaejangmo.apiserver.model.userproduct.domain.vo;

import com.gaejangmo.apiserver.model.userproduct.domain.exception.ProductTypeNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum ProductType {
    MAIN_DEVICE("컴퓨터", 1),
    MOUSE("마우스", 2),
    KEY_BOARD("키보드", 3),
    MONITOR("모니터", 4),
    SUPPLEMENT("영양제", 5),
    ETC("기타", 99);

    private final String name;
    private final int code;

    public static ProductType ofCode(final int code) {
        return Stream.of(values())
                .filter(productType -> productType.code == code)
                .findAny()
                .orElseThrow(() -> new ProductTypeNotFoundException("등록되지 않은 코드입니다."))
                ;
    }

    public static ProductType ofName(final String name) {
        return Stream.of(values())
                .filter(productType -> productType.name.equals(name))
                .findAny()
                .orElseThrow(() -> new ProductTypeNotFoundException("등록되지 않은 타입입니다."))
                ;
    }
}