package com.gaejangmo.apiserver.model.notice.dto;

import com.gaejangmo.apiserver.model.notice.domain.NoticeType;
import lombok.*;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NoticeResponseDto {
    private Long id;
    private NoticeType noticeType;
    private String header;
    private String contents;

    @Builder
    public NoticeResponseDto(final Long id, final NoticeType noticeType, final String header, final String contents) {
        this.id = id;
        this.noticeType = noticeType;
        this.header = header;
        this.contents = contents;
    }
}
