package com.gaejangmo.apiserver.model.user.domain.vo;

import lombok.Getter;

@Getter
public enum Grade {
    GENERAL("일반"),
    CELEBRITY("셀럽");

    private final String title;

    Grade(final String title) {
        this.title = title;
    }
}
