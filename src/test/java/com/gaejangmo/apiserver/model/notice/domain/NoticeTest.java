package com.gaejangmo.apiserver.model.notice.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class NoticeTest {
    private Notice notice;

    @BeforeEach
    void setUp() {
        notice = Notice.builder()
                .noticeType(NoticeType.NOTICE)
                .header("개장모 서비스 오픈!")
                .contents("많이 이용해주세요!! by 개장모 CREW")
                .build();
    }

    @Test
    void 공지사항_초기화() {
        String createdDate = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);

        assertThat(notice.getCreatedDate()).isEqualTo(createdDate);
        assertFalse(notice.isDeleted());
    }

    @Test
    void 공지사항_삭제() {
        notice.delete();
        assertTrue(notice.isDeleted());
    }
}