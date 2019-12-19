package com.gaejangmo.apiserver.model.notice.domain;

import com.gaejangmo.apiserver.model.notice.exception.NoticeTypeNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum NoticeType {
    // TODO code 추가
    NOTICE("공지"),
    EVENT("이벤트");

    private final String name;

    public static NoticeType ofName(final String name) {
        return Stream.of(values())
                .filter(noticeType -> noticeType.name.equals(name))
                .findAny()
                .orElseThrow(() -> new NoticeTypeNotFoundException("등록되지 않은 공지 타입입니다."));
    }
}
