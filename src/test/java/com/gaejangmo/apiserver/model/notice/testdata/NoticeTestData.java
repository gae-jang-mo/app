package com.gaejangmo.apiserver.model.notice.testdata;

import com.gaejangmo.apiserver.model.notice.dto.NoticeRequestDto;

public class NoticeTestData {
    public static final NoticeRequestDto NOTICE_REQUEST_DTO = NoticeRequestDto.builder()
            .noticeType("NOTICE")
            .header("개장모 서비스 오픈했어요~~")
            .contents("많은 이용 부탁드려요! 개장모!!")
            .build();
}
