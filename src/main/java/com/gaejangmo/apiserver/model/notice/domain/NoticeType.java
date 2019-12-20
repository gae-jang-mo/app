package com.gaejangmo.apiserver.model.notice.domain;

import com.gaejangmo.apiserver.model.notice.exception.NoticeTypeNotFoundException;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum NoticeType {
    NOTICE("공지", 1),
    EVENT("이벤트", 2);

    private final String name;
    private final int code;

    NoticeType(final String name, final int code) {
        this.name = name;
        this.code = code;
    }

    public static NoticeType ofCode(final int code) {
        return Stream.of(values())
                .filter(noticeType -> noticeType.code == code)
                .findAny()
                .orElseThrow(() -> new NoticeTypeNotFoundException("등록되지 않은 코드입니다."));
    }

    public static NoticeType ofName(final String name) {
        return Stream.of(values())
                .filter(noticeType -> noticeType.name.equals(name))
                .findAny()
                .orElseThrow(() -> new NoticeTypeNotFoundException("등록되지 않은 공지 타입입니다."));
    }
}
