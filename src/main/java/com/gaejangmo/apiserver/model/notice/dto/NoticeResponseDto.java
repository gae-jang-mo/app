package com.gaejangmo.apiserver.model.notice.dto;

import com.gaejangmo.apiserver.model.notice.domain.NoticeType;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class NoticeResponseDto {
    private Long id;
    private NoticeType noticeType;
    private String header;
    private String contents;
    private String noticeCreatedDate;
}
