package com.gaejangmo.apiserver.model.userproduct.domain.vo;

import com.gaejangmo.apiserver.model.userproduct.domain.exception.ProductTypeNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

//@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@RequiredArgsConstructor
public enum Status {
    ON_USE("사용중", 1),
    OUTDATED("사용안함", 2),
    DELETED("삭제됨", 3),
    DIBS("찜", 4),
    ETC("기타", 99);

    private final String type;
    private final int code;

    public static Status ofCode(final int code) {
        return Stream.of(values())
                .filter(status -> status.code == code)
                .findAny()
                .orElseThrow(() -> new ProductTypeNotFoundException("등록되지 않은 코드입니다."))
                ;
    }

    public static Status ofType(final String type) {
        return Stream.of(values())
                .filter(status -> status.type.equals(type))
                .findAny()
                .orElse(ETC)
                ;
    }

    public boolean isDeleted() {
        return (this == DELETED);
    }
}
