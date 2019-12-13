package com.gaejangmo.apiserver.model.user.domain.vo;

import com.gaejangmo.apiserver.model.user.domain.exception.NotFoundGradeException;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum Grade {
    GENERAL("일반", 1),
    CELEBRITY("셀럽", 2);

    private final String title;
    private final int code;

    Grade(final String title, final int code) {
        this.title = title;
        this.code = code;
    }

    public static Grade ofCode(final int code) {
        return Stream.of(values())
                .filter(grade -> grade.getCode() == code)
                .findAny()
                .orElseThrow(() -> new NotFoundGradeException("없는 사용자 등급입니다."));
    }
}
